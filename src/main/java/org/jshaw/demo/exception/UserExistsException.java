package org.jshaw.demo.exception;

public class UserExistsException extends Exception {

    public UserExistsException() {
        super("User already exist");
    }

    public UserExistsException(String message) {
        super(message);
    }
}
