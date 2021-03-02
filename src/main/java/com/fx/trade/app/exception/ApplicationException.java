package com.fx.trade.app.exception;


/**
 * Application exception to bubble to be handled by global exception handler.
 */
public class ApplicationException extends RuntimeException {

    private final int code;

    public ApplicationException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}