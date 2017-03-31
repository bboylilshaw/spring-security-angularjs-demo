package org.jshaw.demo.service;

import org.jshaw.demo.domain.User;
import org.jshaw.demo.exception.UserAlreadyExistException;

public interface UserService {
    User add(User user) throws UserAlreadyExistException;
}
