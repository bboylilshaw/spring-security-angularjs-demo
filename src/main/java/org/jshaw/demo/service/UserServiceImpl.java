package org.jshaw.demo.service;

import org.jshaw.demo.domain.User;
import org.jshaw.demo.exception.UserExistsException;
import org.jshaw.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by xiaoj7 on 2015/6/19.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User signup(User user) throws UserExistsException {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserExistsException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
