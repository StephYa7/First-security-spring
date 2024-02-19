package st.firstsecurityspring.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import st.firstsecurityspring.models.Role;
import st.firstsecurityspring.models.User;
import st.firstsecurityspring.repositories.UserRepository;

import java.util.Collection;
import java.util.Collections;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Используется для загрузки информации о пользователе по его электронной почте.
     * В данном методе происходит поиск пользователя в репозитории по электронной почте,
     * и если пользователь найден, создается объект UserDetails,
     * содержащий информацию о пользователе (электронная почта, пароль и роли).
     * В случае, если пользователь не найден, генерируется исключение UsernameNotFoundException
     *
     * @param email user email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.getRole()));
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    /**
     * Используется для преобразования ролей пользователя в объекты SimpleGrantedAuthority,
     * которые представляют разрешения пользователя в Spring Security
     * @param role the role from user
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role role) {
        return Collections.singleton(new SimpleGrantedAuthority(role.toString()));
    }
}