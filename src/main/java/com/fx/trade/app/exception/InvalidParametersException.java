package com.fx.trade.app.exception;

public class InvalidParametersException extends ApplicationException {

    InvalidParametersException(int code, String message) {
        super(code, message);
    }
}
