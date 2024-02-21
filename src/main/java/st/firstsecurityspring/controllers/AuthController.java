package st.firstsecurityspring.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import st.firstsecurityspring.dto.UserDto;
import st.firstsecurityspring.models.User;
import st.firstsecurityspring.services.UserService;
import st.firstsecurityspring.util.TrackUserAction;

import java.util.List;

@Controller
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * Обработчик запроса для главной страницы.
     * Извлекает роль пользователя из контекста безопасности и добавляет ее в модель.
     *
     * @param model Модель для передачи данных в представление.
     * @return Строка с именем представления "index".
     */
    @TrackUserAction
    @GetMapping("/")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String userRole = userDetails.getAuthorities().iterator().next().getAuthority();

            model.addAttribute("userRole", userRole);
        }
        return "index";
    }

    @TrackUserAction
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * Обработчик запроса для отображения формы регистрации.
     * Создает новый объект UserDto и добавляет его в модель.
     *
     * @param model Модель для передачи данных в представление.
     * @return Строка с именем представления "register".
     */
    @TrackUserAction
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    /**
     * Обработчик запроса для сохранения регистрации пользователя.
     * Проверяет наличие пользователя с таким же email или именем,
     * сохраняет пользователя и перенаправляет на стартовую страницу.
     *
     * @param userDto Объект UserDto с данными пользователя.
     * @param result  Результат валидации данных.
     * @param model   Модель для передачи данных в представление.
     * @return Строка с перенаправлением на страницу "register?success" в случае успешной регистрации.
     */
    @TrackUserAction
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {

        User existingUserByEmail = userService.findUserByEmail(userDto.getEmail());
        if (existingUserByEmail != null && existingUserByEmail.getEmail() != null
                && !existingUserByEmail.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        User existingUserByName = userService.findUserByName(userDto.getUsername());
        if (existingUserByName != null && existingUserByName.getName() != null
                && !existingUserByName.getName().isEmpty()) {
            result.rejectValue("username", null,
                    "There is already an account registered with the same user name");
        }
        if (result.hasErrors()) {
            model.addAttribute("username", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/";
    }

    /**
     * Обработчик запроса для отображения списка пользователей.
     * Получает список всех пользователей, добавляет его в модель и возвращает имя представления "users".
     *
     * @param model Модель для передачи данных в представление.
     * @return Строка с именем представления "users".
     */
    @TrackUserAction
    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}