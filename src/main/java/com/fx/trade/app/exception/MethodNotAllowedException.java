package com.fx.trade.app.exception;

/**
 * Method not allowed exception.
 */
public class MethodNotAllowedException extends ApplicationException {

    MethodNotAllowedException(int code, String message) {
        super(code, message);
    }
}
