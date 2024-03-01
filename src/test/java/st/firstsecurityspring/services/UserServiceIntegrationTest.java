package st.firstsecurityspring.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import st.firstsecurityspring.dto.UserDto;
import st.firstsecurityspring.models.Role;
import st.firstsecurityspring.models.User;
import st.firstsecurityspring.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceIntegrationTest {

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Test
    void testSaveUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("testUser");
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");

        User expectedUser = new User();
        expectedUser.setName(userDto.getUsername());
        expectedUser.setEmail(userDto.getEmail());
        expectedUser.setPassword("encodedPassword");
        expectedUser.setRole(Role.ROLE_USER);

        Mockito.when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");

        userService.saveUser(userDto);

        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.eq(expectedUser));
    }

    @Test
    void testFindUserByEmail() {
        String email = "test@example.com";
        User expectedUser = new User();
        expectedUser.setEmail(email);

        Mockito.when(userRepository.findByEmail(email)).thenReturn(expectedUser);

        User foundUser = userService.findUserByEmail(email);

        assertEquals(expectedUser, foundUser);
    }

    @Test
    void testFindAllUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User(1L, "user1", "test1@example.com", "user1", Role.ROLE_USER);
        User user2 = new User(2L, "user2", "test2@example.com", "user2", Role.ROLE_USER);
        users.add(user1);
        users.add(user2);

        Mockito.when(userRepository.findAll()).thenReturn(users);

        List<UserDto> usersDto = users.stream()
                .map(a -> userService.mapToUserDto(a))
                .toList();

        List<UserDto> foundUsers = userService.findAllUsers();

        assertEquals(foundUsers, usersDto);


    }

    @Test
    void testFindUserByName() {
        String name = "testUsername";
        User expectedUser = new User();
        expectedUser.setName(name);

        Mockito.when(userRepository.findByName(name)).thenReturn(expectedUser);

        User foundUser = userService.findUserByName(name);

        assertEquals(expectedUser, foundUser);
    }
}