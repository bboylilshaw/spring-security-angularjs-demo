package org.jshaw.demo;

import org.jshaw.demo.domain.Book;
import org.jshaw.demo.domain.User;
import org.jshaw.demo.repository.BookRepository;
import org.jshaw.demo.repository.UserRepository;
import org.jshaw.demo.security.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        logger.info("Start the application");
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void  init(){
        logger.info("Initializing data");
        User jason = new User("jason", passwordEncoder.encode("password"), "jason@test.com", Role.ADMIN);
        User john = new User("john", passwordEncoder.encode("password"), "john@test.com", Role.USER);
        Book book1 = new Book("Effective Java", "Joshua Bloch");
        Book book2 = new Book("Introduction to Algorithms", "Tomas H. Cormen");
        userRepository.save(jason);
        userRepository.save(john);
        bookRepository.save(book1);
        bookRepository.save(book2);
    }

}
