package org.jshaw.demo.web.controller;

import org.jshaw.demo.domain.User;
import org.jshaw.demo.exception.UserExistsException;
import org.jshaw.demo.repository.UserRepository;
import org.jshaw.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

/**
 * Created by xiaoj7 on 2015/6/19.
 */
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/api/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signup(@RequestBody @Valid User user) throws UserExistsException {
        System.out.println(user);
        userService.signup(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
