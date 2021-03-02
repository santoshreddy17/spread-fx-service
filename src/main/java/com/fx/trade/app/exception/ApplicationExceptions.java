package com.fx.trade.app.exception;

import com.fx.trade.app.api.StatusCode;

import java.util.function.Function;
import java.util.function.Supplier;


/**
 * Raise Exceptions to bubble for global exception handler to handle.
 */
public class ApplicationExceptions {

    public static Supplier<RuntimeException> invalidRequest(String message) {
        return () -> new InvalidRequestException(StatusCode.BAD_REQUEST.getCode(), message);
    }

    public static Supplier<RuntimeException> invalidParameters(String message) {
        return () -> new InvalidParametersException(StatusCode.INVALID_PARAMETERS.getCode(), message);
    }

    public static Supplier<RuntimeException> methodNotAllowed(String message) {
        return () -> new MethodNotAllowedException(StatusCode.METHOD_NOT_ALLOWED.getCode(), message);
    }

    public static Supplier<RuntimeException> resourceNotFound(String message) {
        return () -> new ResourceNotFoundException(StatusCode.RESOURCE_NOT_FOUND.getCode(), message);
    }
}
