package com.fx.trade.app.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fx.trade.app.exception.ApplicationExceptions;
import com.fx.trade.app.exception.GlobalExceptionHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.InputStream;

/**
 * Handler for requests with some common methods.
 */
public abstract class Handler {

    private final ObjectMapper objectMapper;
    private final GlobalExceptionHandler globalExceptionHandler;

    public Handler(ObjectMapper objectMapper,
                   GlobalExceptionHandler globalExceptionHandler) {
        this.objectMapper = objectMapper;
        this.globalExceptionHandler = globalExceptionHandler;
    }

    public void handle(HttpExchange exchange) {
        try{
            execute(exchange);
        } catch (Exception e) {
            globalExceptionHandler.handle(e, exchange);
        }
    }

    protected abstract void execute(HttpExchange exchange) throws Exception;

    protected <T> T readRequest(InputStream is, Class<T> type) {
        try{
         return objectMapper.readValue(is, type);
        } catch (Exception e) {
            throw ApplicationExceptions.invalidRequest(e.getMessage()).get();
        }
    }

    protected <T> byte[] writeResponse(T response) {
        try{
            return objectMapper.writeValueAsBytes(response);
        } catch (Exception e) {
            throw ApplicationExceptions.invalidRequest(e.getMessage()).get();
        }
    }

}
