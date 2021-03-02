package com.fx.trade.app.exception;

/**
 * Resource not found exception.
 */
public class ResourceNotFoundException extends ApplicationException {

    ResourceNotFoundException(int code, String message) {
        super(code, message);
    }
}
