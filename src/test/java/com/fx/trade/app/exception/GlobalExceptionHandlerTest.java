package com.fx.trade.app.exception;

import com.fx.trade.app.FxObjectMapperProvider;
import com.fx.trade.app.TestUtils;
import com.sun.net.httpserver.HttpExchange;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static com.fx.trade.app.api.RequestType.GET;
import static org.junit.Assert.*;

public class GlobalExceptionHandlerTest {

    @Test
    public void handle() {
        HttpExchange httpExchange = TestUtils.getHttpExchange(GET, "http://localhost:8000/rates/latest?userId=123", "");
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler(FxObjectMapperProvider.getObjectMapper());
        globalExceptionHandler.handle(new InvalidRequestException(1,"Meh"),httpExchange);
        Assert.assertTrue(httpExchange.getResponseBody().toString().contains("Meh"));

        globalExceptionHandler = new GlobalExceptionHandler(FxObjectMapperProvider.getObjectMapper());
        globalExceptionHandler.handle(new InvalidParametersException(1,"Meh"),httpExchange);
        Assert.assertTrue(httpExchange.getResponseBody().toString().contains("Meh"));

        globalExceptionHandler = new GlobalExceptionHandler(FxObjectMapperProvider.getObjectMapper());
        globalExceptionHandler.handle(new MethodNotAllowedException(1,"Meh"),httpExchange);
        Assert.assertTrue(httpExchange.getResponseBody().toString().contains("Meh"));


        globalExceptionHandler = new GlobalExceptionHandler(FxObjectMapperProvider.getObjectMapper());
        globalExceptionHandler.handle(new ResourceNotFoundException(1,"Meh"),httpExchange);
        Assert.assertTrue(httpExchange.getResponseBody().toString().contains("Meh"));

        globalExceptionHandler = new GlobalExceptionHandler(FxObjectMapperProvider.getObjectMapper());
        globalExceptionHandler.handle(new ResourceException("MESSAGE",new Throwable()),httpExchange);
        Assert.assertTrue(httpExchange.getResponseBody().toString().contains("500"));
        Assert.assertTrue(httpExchange.getResponseBody().toString().contains("MESSAGE"));
    }
}