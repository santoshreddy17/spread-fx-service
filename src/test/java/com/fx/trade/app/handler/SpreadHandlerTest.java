package com.fx.trade.app.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fx.trade.app.FxObjectMapperProvider;
import com.fx.trade.app.data.UserRepository;
import com.fx.trade.app.exception.*;
import com.fx.trade.app.model.PricingTier;
import com.fx.trade.app.model.UserRate;
import com.fx.trade.app.model.UserRates;
import com.fx.trade.app.resource.SpreadCalcResource;
import com.sun.net.httpserver.HttpExchange;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static com.fx.trade.app.TestUtils.getHttpExchange;
import static com.fx.trade.app.api.RequestType.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SpreadHandlerTest {

    private SpreadHandler spreadHandler;
    public static final String USER_123 = "{\"userId\":\"123\",\"pricingTier\":\"A\",\"emailAddress\":\"xyz@gmail.com\"}";


    @Test
    public void executePost() throws IOException, ResourceException {
        String result ="{\"base\":\"SGD\",\"rates\":{\"HKD\":{\"bid\":1,\"market\":1,\"ask\":1},\"USD\":{\"bid\":1,\"market\":1,\"ask\":1},\"MYR\":{\"bid\":1,\"market\":1,\"ask\":1}}}";
        final ObjectMapper objectMapper = FxObjectMapperProvider.getObjectMapper();
        SpreadCalcResource spreadCalcResource = Mockito.mock(SpreadCalcResource.class);
        spreadHandler = new SpreadHandler(objectMapper, new GlobalExceptionHandler(objectMapper),spreadCalcResource,new UserRepository());
        Map<String, UserRate> userRateMap = new HashMap<>();
        userRateMap.put("USD",new UserRate(BigDecimal.ONE,BigDecimal.ONE,BigDecimal.ONE));
        userRateMap.put("HKD",new UserRate(BigDecimal.ONE,BigDecimal.ONE,BigDecimal.ONE));
        userRateMap.put("MYR",new UserRate(BigDecimal.ONE,BigDecimal.ONE,BigDecimal.ONE));
        UserRates  userRates = new UserRates("SGD",userRateMap);
        System.out.println(userRates);
        when(spreadCalcResource.getUserRates(any(PricingTier.class))).thenReturn(userRates);
        HttpExchange httpExchange = getHttpExchange(POST, "localhost:8000/rates/latest", USER_123);
        spreadHandler.execute(httpExchange);
        Assert.assertNotNull(httpExchange.getResponseBody());
        Assert.assertEquals(result,httpExchange.getResponseBody().toString());
    }

    @Test
    public void executeGet() throws IOException, ResourceException {
        String result ="{\"base\":\"SGD\",\"rates\":{\"HKD\":{\"bid\":1,\"market\":1,\"ask\":1},\"USD\":{\"bid\":1,\"market\":1,\"ask\":1},\"MYR\":{\"bid\":1,\"market\":1,\"ask\":1}}}";
        final ObjectMapper objectMapper = FxObjectMapperProvider.getObjectMapper();
        SpreadCalcResource spreadCalcResource = Mockito.mock(SpreadCalcResource.class);
        spreadHandler = new SpreadHandler(objectMapper, new GlobalExceptionHandler(objectMapper),spreadCalcResource,new UserRepository());
        Map<String, UserRate> userRateMap = new HashMap<>();
        userRateMap.put("USD",new UserRate(BigDecimal.ONE,BigDecimal.ONE,BigDecimal.ONE));
        userRateMap.put("HKD",new UserRate(BigDecimal.ONE,BigDecimal.ONE,BigDecimal.ONE));
        userRateMap.put("MYR",new UserRate(BigDecimal.ONE,BigDecimal.ONE,BigDecimal.ONE));
        UserRates  userRates = new UserRates("SGD",userRateMap);
        when(spreadCalcResource.getUserRates(any(PricingTier.class))).thenReturn(userRates);
        HttpExchange httpExchange = getHttpExchange(GET, "http://localhost:8000/rates/latest?userId=123", "");
        spreadHandler.execute(httpExchange);
        Assert.assertNotNull(httpExchange.getResponseBody());
        Assert.assertEquals(result,httpExchange.getResponseBody().toString());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void executeGetResourceException() throws IOException, ResourceException {
        final ObjectMapper objectMapper = FxObjectMapperProvider.getObjectMapper();
        SpreadCalcResource spreadCalcResource = Mockito.mock(SpreadCalcResource.class);
        spreadHandler = new SpreadHandler(objectMapper, new GlobalExceptionHandler(objectMapper),spreadCalcResource,new UserRepository());
        HttpExchange httpExchange = getHttpExchange(GET, "http://localhost:8000/rates/latest?userId=369", "");
        spreadHandler.execute(httpExchange);
    }

    @Test(expected = InvalidParametersException.class)
    public void executeGetInvalidException() throws IOException, ResourceException {
        final ObjectMapper objectMapper = FxObjectMapperProvider.getObjectMapper();
        SpreadCalcResource spreadCalcResource = Mockito.mock(SpreadCalcResource.class);
        spreadHandler = new SpreadHandler(objectMapper, new GlobalExceptionHandler(objectMapper),spreadCalcResource,new UserRepository());
        HttpExchange httpExchange = getHttpExchange(GET, "http://localhost:8000/rates/latest?mep=369", "");
        spreadHandler.execute(httpExchange);
    }

    @Test(expected = MethodNotAllowedException.class)
    public void executeMethodNotAllowed() throws IOException, ResourceException {
        final ObjectMapper objectMapper = FxObjectMapperProvider.getObjectMapper();
        SpreadCalcResource spreadCalcResource = Mockito.mock(SpreadCalcResource.class);
        spreadHandler = new SpreadHandler(objectMapper, new GlobalExceptionHandler(objectMapper),spreadCalcResource,new UserRepository());
        HttpExchange httpExchange = getHttpExchange(PUT, "http://localhost:8000/rates/latest?mep=369", "");
        spreadHandler.execute(httpExchange);
    }

}