package com.fx.trade.app.handler;

import com.fx.trade.app.api.StatusCode;
import com.fx.trade.app.data.UserRepository;
import com.fx.trade.app.exception.ApplicationExceptions;
import com.fx.trade.app.exception.GlobalExceptionHandler;
import com.fx.trade.app.model.User;
import com.fx.trade.app.utils.AppUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fx.trade.app.api.RequestType;
import com.sun.net.httpserver.HttpExchange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * User Handler.
 * to handle get calls with userid.
 */
public class UserHandler extends Handler {

    private static final Logger LOGGER = LogManager.getLogger(UserHandler.class);

    private final UserRepository userRepository;

    public UserHandler(ObjectMapper objectMapper, GlobalExceptionHandler exceptionHandler, UserRepository userRepository) {
        super(objectMapper, exceptionHandler);
        this.userRepository = userRepository;
    }

    @Override
    protected void execute(final HttpExchange exchange) throws IOException {
        if (RequestType.GET.equals(exchange.getRequestMethod())) {
            LOGGER.info("GET USER REQUEST");
            final Map<String, List<String>> params = AppUtils.splitQueryParameters(exchange.getRequestURI().getRawQuery());
            if (params.containsKey("userId")) {
                try(OutputStream output = exchange.getResponseBody()){
                    final User user = userRepository.get(params.get("userId").get(0));
                    if(user!=null){
                        LOGGER.debug("USER Found :"+user);
                        final byte[] bytes = super.writeResponse(user);
                        exchange.sendResponseHeaders(StatusCode.OK.getCode(), bytes.length);
                        output.write(bytes);
                        output.flush();
                    }else {
                        throw ApplicationExceptions.resourceNotFound(
                                new StringBuilder().append("User not found ").append(exchange.getRequestURI()).toString()).get();
                    }
                }
            }else{
                throw ApplicationExceptions.invalidParameters(new StringBuilder().append("Invalid parameters passed for call : ").append(exchange.getRequestMethod()).append(" with parameters : ").append(exchange.getRequestURI()).toString()).get();
            }
        } else {
            throw ApplicationExceptions.methodNotAllowed(
                    new StringBuilder().append("Method ").append(exchange.getRequestMethod()).append(" is not allowed for ").append(exchange.getRequestURI()).toString()).get();
        }
        exchange.close();
    }
}
