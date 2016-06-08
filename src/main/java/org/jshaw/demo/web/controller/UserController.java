package org.jshaw.demo.web.controller;

import org.jshaw.demo.domain.User;
import org.jshaw.demo.exception.UserExistsException;
import org.jshaw.demo.security.CurrentUser;
import org.jshaw.demo.security.UserAuthentication;
import org.jshaw.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signup(@RequestBody @Valid User user, HttpServletResponse response) throws UserExistsException {
        logger.info("sign up for user: {}", user.toString());
        userService.signup(user, response);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getCurrentUser(@CurrentUser Authentication authentication) {
        logger.info("get current user info");
        if (authentication instanceof UserAuthentication) {
            return authentication.getPrincipal();
        }
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
