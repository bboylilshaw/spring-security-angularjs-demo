package org.jshaw.demo.exception;

public class UserAlreadyExistException extends GenericException {

    public UserAlreadyExistException() {
        super(ErrorType.USER_ALREADY_EXIST);
    }

    public UserAlreadyExistException(String message) {
        super(ErrorType.USER_ALREADY_EXIST.getCode(), message);
    }
}
