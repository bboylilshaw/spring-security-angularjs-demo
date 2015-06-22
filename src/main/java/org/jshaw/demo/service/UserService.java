package org.jshaw.demo.service;

import org.jshaw.demo.domain.User;
import org.jshaw.demo.exception.UserExistsException;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by xiaoj7 on 2015/6/19.
 */
public interface UserService {
    User signup(User user, HttpServletResponse response) throws UserExistsException;
}
