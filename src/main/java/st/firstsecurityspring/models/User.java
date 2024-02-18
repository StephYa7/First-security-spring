package st.firstsecurityspring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    @Size(max = 50, message = "Поле \"Имя\" должно быть короче 50 символов!")
    @NotEmpty(message = "Поле \"Имя\" не должно быть пустым!")
    private String username;

    @Column(name = "password")
    @Size(max = 50, message = "Поле \"Пароль\" должно быть короче 50 символов!")
    @NotEmpty(message = "Поле \"Пароль\" не должно быть пустым!")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private List<Role> roles = new ArrayList<>();
}