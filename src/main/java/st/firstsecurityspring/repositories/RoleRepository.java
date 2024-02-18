package st.firstsecurityspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import st.firstsecurityspring.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}