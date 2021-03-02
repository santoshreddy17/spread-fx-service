package com.fx.trade.app.exception;

/**
 * Resource Level Exception bubbling.
 */
public class ResourceException extends Exception {

    public ResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
