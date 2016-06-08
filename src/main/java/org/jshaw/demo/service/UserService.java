package org.jshaw.demo.service;

import org.jshaw.demo.domain.User;
import org.jshaw.demo.exception.UserExistsException;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    User signup(User user, HttpServletResponse response) throws UserExistsException;
}
