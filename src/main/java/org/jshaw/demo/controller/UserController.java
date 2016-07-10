package org.jshaw.demo.controller;

import org.jshaw.demo.domain.User;
import org.jshaw.demo.security.CurrentUser;
import org.jshaw.demo.security.UserAuthentication;
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

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> signUp(@RequestBody @Valid User user, HttpServletResponse response) throws Exception {
        logger.info("sign up for user: {}", user.toString());
        User u = userService.signUp(user, response);
        return ResponseEntity.ok(u);
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
