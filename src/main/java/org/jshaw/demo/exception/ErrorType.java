package org.jshaw.demo.exception;


public enum ErrorType {

    USER_ALREADY_EXIST(1001, "User already exist");

    private final int code;
    private final String message;

    ErrorType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "[" +
                "code=" + code +
                ", message='" + message + '\'' +
                ']';
    }
}
