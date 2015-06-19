package org.jshaw.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.jshaw.demo.common.Role;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Jason on 6/15/15.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Size(max = 50)
    private String username;

    @NotEmpty
    private String password;

    @Email
    @Size(max = 50)
    private String email;

    @NotNull
    private Role role;

    public User(User u) {
        this.id = u.getId();
        this.username = u.getUsername();
        this.password = u.getPassword();
        this.email = u.getEmail();
        this.role = u.getRole();
    }

}
