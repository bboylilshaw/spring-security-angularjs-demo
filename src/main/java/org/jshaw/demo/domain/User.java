package org.jshaw.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.jshaw.demo.security.Role;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "t_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Size(max = 100)
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    @Size(max = 100)
    private String firstName;

    @NotEmpty
    @Size(max = 100)
    private String lastName;

    @Email
    @Size(max = 100)
    private String email;

    @NotNull
    private Role role;

    public User(User u) {
        this.id = u.getId();
        this.username = u.getUsername();
        this.password = u.getPassword();
        this.firstName = u.getFirstName();
        this.lastName = u.getLastName();
        this.email = u.getEmail();
        this.role = u.getRole();
    }

}
