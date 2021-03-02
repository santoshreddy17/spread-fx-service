package com.fx.trade.app;

import com.fx.trade.app.model.User;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class TestUtils {


    public static HttpExchange getHttpExchange(final String requestType, final String url,final String user) {
        HttpExchange exchange = mock(HttpExchange.class);
        when(exchange.getRequestMethod()).thenReturn(requestType);
        when(exchange.getRequestURI()).thenReturn(URI.create(url));
        when(exchange.getResponseHeaders()).thenReturn(new Headers());
        InputStream targetStream = new ByteArrayInputStream(user.getBytes());
        when(exchange.getRequestBody()).thenReturn(targetStream);
        ByteArrayOutputStream response = new ByteArrayOutputStream();
        when(exchange.getResponseBody()).thenReturn(response);
        return exchange;
    }
}
