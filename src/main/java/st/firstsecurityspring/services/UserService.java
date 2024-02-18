package st.firstsecurityspring.services;

import org.springframework.stereotype.Service;
import st.firstsecurityspring.models.User;
import st.firstsecurityspring.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;

    public void getAllUsers() {
        userRepository.findAll();
    }

    public void getUserById(int id) {
        userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User myUser) {
        return userRepository.save(myUser);
    }
}