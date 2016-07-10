package org.jshaw.demo.service;

import org.jshaw.demo.domain.User;
import org.jshaw.demo.exception.UserAlreadyExistException;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    User signUp(User user, HttpServletResponse response) throws UserAlreadyExistException;
}
