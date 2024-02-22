package st.firstsecurityspring.services;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import st.firstsecurityspring.dto.UserDto;
import st.firstsecurityspring.models.Role;
import st.firstsecurityspring.models.User;
import st.firstsecurityspring.repositories.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .toList();
    }

    private UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public User findUserByName(String name) {
        return userRepository.findByName(name);
    }
}