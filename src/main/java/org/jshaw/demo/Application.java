package org.jshaw.demo;

import org.jshaw.demo.common.Role;
import org.jshaw.demo.domain.User;
import org.jshaw.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

/**
 * Created by Jason on 6/12/15.
 */
@SpringBootApplication
public class Application {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void  init(){
        System.out.println("Initializing data");
        User user = new User();
        user.setUsername("jason");
        user.setPassword(passwordEncoder.encode("password"));
        user.setEmail("jason@example.com");
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }
}
