package st.firstsecurityspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import st.firstsecurityspring.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);}