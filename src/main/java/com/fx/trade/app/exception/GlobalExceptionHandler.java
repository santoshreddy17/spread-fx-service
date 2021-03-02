package com.fx.trade.app.exception;

import com.fx.trade.app.api.ErrorResponse;
import com.fx.trade.app.utils.AppUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;


/**
 * Global Exception Handler.
 * Handle exceptions in a generic way to give consistent exceptions to clients.
 */
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Handle thrown exceptions in to generate generic error responses.
     */
    public void handle(Throwable throwable, HttpExchange exchange) {
        try {
            throwable.printStackTrace();
            exchange.getResponseHeaders().set(AppUtils.CONTENT_TYPE, AppUtils.APPLICATION_JSON);
            ErrorResponse response = getErrorResponse(throwable, exchange);
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(objectMapper.writeValueAsBytes(response));
            responseBody.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ErrorResponse getErrorResponse(Throwable throwable, HttpExchange exchange) throws IOException {
        ErrorResponse response = new ErrorResponse();
        if (throwable instanceof InvalidRequestException) {
            InvalidRequestException exc = (InvalidRequestException) throwable;
            response.setMessage(exc.getMessage()).setCode(exc.getCode());
            exchange.sendResponseHeaders(400, 0);
        } else if (throwable instanceof ResourceNotFoundException) {
            ResourceNotFoundException exc = (ResourceNotFoundException) throwable;
            response.setMessage(exc.getMessage()).setCode(exc.getCode());
            exchange.sendResponseHeaders(404, 0);
        } else if (throwable instanceof MethodNotAllowedException) {
            MethodNotAllowedException exc = (MethodNotAllowedException) throwable;
            response.setMessage(exc.getMessage()).setCode(exc.getCode());
            exchange.sendResponseHeaders(405, 0);
        } else {
            response.setCode(500).setMessage(throwable.getMessage());
            exchange.sendResponseHeaders(500, 0);
        }
        return response;
    }
}
