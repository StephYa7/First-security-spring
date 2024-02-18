package st.firstsecurityspring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import st.firstsecurityspring.services.UserService;

@Controller
@RequestMapping("/")
public class UsersController {
    UserService userService;

    @GetMapping("/")
    public String authenticationForm() {
        return "login";
    }

    @GetMapping("/new")
    public String registrationForm() {
        return "registration";
    }

    @GetMapping("/users")
    public String allUsers() {
        userService.getAllUsers();
        return "users";
    }

    @GetMapping("/users/{id}")
    public String userById(@PathVariable("id") int id) {
        userService.getUserById(id);
        return "index";
    }


}