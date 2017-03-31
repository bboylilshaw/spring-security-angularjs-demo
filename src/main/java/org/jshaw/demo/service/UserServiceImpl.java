package org.jshaw.demo.service;

import org.jshaw.demo.domain.User;
import org.jshaw.demo.exception.UserAlreadyExistException;
import org.jshaw.demo.repository.UserRepository;
import org.jshaw.demo.security.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User add(User user) throws UserAlreadyExistException {
        Assert.notNull(user, "User object cannot be null");
        Assert.isNull(user.getId(), "User id filed must be null");

        // check if username exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("User already exist with username: " + user.getUsername());
        }

        // encode password and set default role
        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole(Role.ROLE_USER);

        logger.info("Saving user: {}", user);
        return userRepository.save(user);
    }
}
