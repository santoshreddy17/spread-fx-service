package com.fx.trade.app.api;

public class ErrorResponse {

    int code;
    String message;

    public ErrorResponse() {
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public ErrorResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
