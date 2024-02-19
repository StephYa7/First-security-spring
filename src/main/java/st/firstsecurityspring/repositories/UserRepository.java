package st.firstsecurityspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import st.firstsecurityspring.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByName(String name);
}