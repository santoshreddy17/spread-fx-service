package com.fx.trade.app.handler;


import com.fx.trade.app.FxObjectMapperProvider;
import com.fx.trade.app.data.UserRepository;
import com.fx.trade.app.exception.GlobalExceptionHandler;
import com.fx.trade.app.exception.InvalidParametersException;
import com.fx.trade.app.exception.MethodNotAllowedException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

import static com.fx.trade.app.TestUtils.getHttpExchange;
import static com.fx.trade.app.api.RequestType.GET;
import static com.fx.trade.app.api.RequestType.PUT;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserHandlerTest {

    public static final String USER_123 = "{\"userId\":\"123\",\"pricingTier\":\"A\",\"emailAddress\":\"xyz@gmail.com\"}";
    public static final String USER_234 = "{\"userId\":\"234\",\"pricingTier\":\"B\",\"emailAddress\":\"abc@gmail.com\"}";
    private static UserHandler userHandler;


    @BeforeClass
    public static void beforeClass() throws Exception {
        final ObjectMapper objectMapper = FxObjectMapperProvider.getObjectMapper();
        userHandler = new UserHandler(objectMapper,new GlobalExceptionHandler(objectMapper),new UserRepository());
    }

    @Test
    public void executeGetUser123() throws IOException {
        final HttpExchange exchange = getHttpExchange(GET, "http://localhost:8000/user/profile/userId?userId=123","");
        userHandler.execute(exchange);
        Assert.assertEquals(USER_123,exchange.getResponseBody().toString());
    }

    @Test
    public void executeGetUser234() throws IOException {
        HttpExchange exchange = getHttpExchange(GET, "http://localhost:8000/user/profile/userId?userId=234","");
        userHandler.execute(exchange);
        Assert.assertEquals(USER_234,exchange.getResponseBody().toString());
    }

    @Test(expected = MethodNotAllowedException.class)
    public void executePutUser234() throws IOException {
        HttpExchange exchange = getHttpExchange(PUT, "http://localhost:8000/user/profile/userId?userId=234","");
        userHandler.execute(exchange);
    }

    @Test(expected = InvalidParametersException.class)
    public void executeBadInput() throws IOException {
        HttpExchange exchange = getHttpExchange(GET, "http://localhost:8000/user/profile/userId?name=234","");
        userHandler.execute(exchange);
    }


}