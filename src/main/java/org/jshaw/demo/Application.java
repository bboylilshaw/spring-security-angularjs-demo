package org.jshaw.demo;

import org.jshaw.demo.common.Role;
import org.jshaw.demo.domain.Book;
import org.jshaw.demo.domain.User;
import org.jshaw.demo.repository.BookRepository;
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
    private BookRepository bookRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void  init(){
        System.out.println("Initializing data");
        User jason = User.of(1L, "jason", passwordEncoder.encode("password"), "jason@jason.com", Role.ADMIN);
        User john = User.of(2L, "john", passwordEncoder.encode("password"), "john@john.com", Role.USER);
        Book book1 = Book.of(1L, "Effective Java", "Joshua Bloch");
        Book book2 = Book.of(2L, "Introduction to Algorithms", "Tomas H. Cormen");
        userRepository.save(jason);
        userRepository.save(john);
        bookRepository.save(book1);
        bookRepository.save(book2);
    }

}
