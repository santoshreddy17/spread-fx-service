package com.fx.trade.app.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fx.trade.app.api.RequestType;
import com.fx.trade.app.api.StatusCode;
import com.fx.trade.app.data.UserRepository;
import com.fx.trade.app.exception.ApplicationExceptions;
import com.fx.trade.app.exception.GlobalExceptionHandler;
import com.fx.trade.app.exception.ResourceException;
import com.fx.trade.app.model.PricingTier;
import com.fx.trade.app.model.User;
import com.fx.trade.app.model.UserRates;
import com.fx.trade.app.resource.SpreadCalcResource;
import com.fx.trade.app.utils.AppUtils;
import com.sun.net.httpserver.HttpExchange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Spread handler.
 * Handles POST & GET to do the same call depending on the request.
 * Returns the rates based on pricing tier.
 */
public class SpreadHandler extends Handler {

    private static final Logger LOGGER = LogManager.getLogger(SpreadHandler.class);
    final SpreadCalcResource spreadCalcResource;
    private final UserRepository userRepository;


    public SpreadHandler(ObjectMapper objectMapper, GlobalExceptionHandler exceptionHandler, SpreadCalcResource spreadCalcResource, UserRepository userRepository) {
        super(objectMapper, exceptionHandler);
        this.spreadCalcResource = spreadCalcResource;
        this.userRepository = userRepository;
    }

    @Override
    protected void execute(HttpExchange exchange) throws IOException {
        if (RequestType.POST.equals(exchange.getRequestMethod())) {
            final UserRates userRates = process(exchange.getRequestBody());
            writeResponse(exchange, userRates);
        } else if (RequestType.GET.equals(exchange.getRequestMethod())) {
            handleGetCall(exchange);
        } else {
            throw ApplicationExceptions.methodNotAllowed(
                    new StringBuilder().append("Method ").append(exchange.getRequestMethod()).append(" is not allowed for ").append(exchange.getRequestURI()).toString()).get();
        }
    }

    private void handleGetCall(HttpExchange exchange) throws IOException {
        final Map<String, List<String>> params = AppUtils.splitQueryParameters(exchange.getRequestURI().getRawQuery());
        if (params.containsKey("userId")) {
            final User user = userRepository.get(params.get("userId").get(0));
            if(user!=null) {
                final UserRates userRates = getUserRates(user);
                writeResponse(exchange, userRates);
            }else {
                throw ApplicationExceptions.resourceNotFound(
                        "User not found " + exchange.getRequestURI()).get();
            }
        } else {
            throw ApplicationExceptions.invalidParameters(new StringBuilder().append("Invalid parameters passed for call : ").append(exchange.getRequestMethod()).append(" with parameters : ").append(exchange.getRequestURI()).toString()).get();
        }
    }

    private void writeResponse(HttpExchange exchange, UserRates userRates) throws IOException {
        final byte[] bytes = super.writeResponse(userRates);
        exchange.sendResponseHeaders(StatusCode.OK.getCode(), bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private UserRates process(final InputStream requestBody) {
        final User requestUser = super.readRequest(requestBody, User.class);
        final User validUser = userRepository.get(requestUser.getUserId());
        if (validUser == null) {
            throw ApplicationExceptions.resourceNotFound(new StringBuilder().append("Invalid User, User with user id : ").append(requestUser.getUserId()).append(" does not exist").toString()).get();
        }
        return getUserRates(validUser);
    }

    private UserRates getUserRates(final User validUser) {
        try {
            return spreadCalcResource.getUserRates(PricingTier.valueOf(validUser.getPricingTier()));
        } catch (ResourceException e) {
            LOGGER.error(e);
            throw ApplicationExceptions.resourceNotFound("Error getting rates :"+e.getMessage()).get();
        }
    }
}
