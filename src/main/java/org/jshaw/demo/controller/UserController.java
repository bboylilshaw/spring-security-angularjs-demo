package org.jshaw.demo.controller;

import org.jshaw.demo.domain.User;
import org.jshaw.demo.exception.UserAlreadyExistException;
import org.jshaw.demo.security.CurrentUser;
import org.jshaw.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/signup")
    public ResponseEntity signUp(@RequestBody @Valid User user) throws Exception {
        logger.info("Sign up for user: {}", user);
        User newUser;
        try {
            newUser = userService.add(user);
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(newUser);
    }

    @GetMapping(value = "/user/current")
    public ResponseEntity getCurrentUser(@CurrentUser Authentication authentication) {
        logger.info("Get current user info");
        return ResponseEntity.ok(authentication.getDetails());
    }

}
