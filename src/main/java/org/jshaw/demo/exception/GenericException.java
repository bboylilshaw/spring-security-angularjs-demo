package org.jshaw.demo.exception;

/**
 * @author Jason Xiao
 */
public class GenericException extends Exception {

    protected int code;

    public GenericException(int code, String message) {
        super(message);
        this.code = code;
    }

    public GenericException(ErrorType errorType) {
        this(errorType.getCode(), errorType.getMessage());
    }

    public int getCode() {
        return code;
    }
}
