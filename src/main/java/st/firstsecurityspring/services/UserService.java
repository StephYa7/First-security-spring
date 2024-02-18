package st.firstsecurityspring.services;

import st.firstsecurityspring.dto.UserDto;
import st.firstsecurityspring.models.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}