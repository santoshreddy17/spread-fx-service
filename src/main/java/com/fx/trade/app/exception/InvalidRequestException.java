package com.fx.trade.app.exception;

/**
 * Invalid request exception.
 */
public class InvalidRequestException extends ApplicationException {

    public InvalidRequestException(int code, String message) {
        super(code, message);
    }
}
