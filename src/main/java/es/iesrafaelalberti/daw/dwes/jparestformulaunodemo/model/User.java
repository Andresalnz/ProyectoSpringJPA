package es.iesrafaelalberti.daw.dwes.jparestformulaunodemo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

     String username;
     String password;
     String token;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id",referencedColumnName = "id"))
    Set<Role> roles  = new HashSet<>();

    public User() {
    }

    public User(String username, String password, Role rol) {
        this.username = username;
        this.password = new BCryptPasswordEncoder().encode(password);
        roles.add(rol);
        rol.getUsers().add(this);
    }


    public void addRole(Role rol) {
        roles.add(rol);
        rol.getUsers().add(this);
    }
}
