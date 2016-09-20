package org.jshaw.demo.controller;

import org.jshaw.demo.domain.User;
import org.jshaw.demo.security.CurrentUser;
import org.jshaw.demo.security.TokenAuthenticationService;
import org.jshaw.demo.security.UserAuthentication;
import org.jshaw.demo.security.UserDetailsImpl;
import org.jshaw.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    public UserController(UserService userService, TokenAuthenticationService tokenAuthenticationService) {
        this.userService = userService;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<User> signUp(@RequestBody @Valid User user,
                                       HttpServletResponse response) throws Exception {
        logger.info("Sign up for user: {}", user);
        User savedUser = userService.add(user);

        // set user authentication so that user does not need to login again after signup
        UserDetailsImpl userDetails = new UserDetailsImpl(savedUser);
        UserAuthentication userAuthentication = new UserAuthentication(userDetails);
        userAuthentication.setAuthenticated(true);
        tokenAuthenticationService.addAuthentication(response, userAuthentication);

        return ResponseEntity.ok(savedUser);
    }

    @RequestMapping(value = "/user/current", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getCurrentUser(@CurrentUser Authentication authentication) {
        logger.info("get current user info");
        if (authentication instanceof UserAuthentication) {
            return authentication.getPrincipal();
        }
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
