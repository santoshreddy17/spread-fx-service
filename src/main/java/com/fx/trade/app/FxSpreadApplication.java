package com.fx.trade.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fx.trade.app.data.UserRepository;
import com.fx.trade.app.exception.GlobalExceptionHandler;
import com.fx.trade.app.handler.UserHandler;
import com.fx.trade.app.handler.SpreadHandler;
import com.fx.trade.app.resource.HttpResource;
import com.fx.trade.app.resource.SpreadCalcResource;
import com.sun.net.httpserver.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Main Application.
 */

public class FxSpreadApplication {
    private static final Logger LOGGER = LogManager.getLogger(FxSpreadApplication.class);

    private static final UserRepository userRepository = new UserRepository();
    public static final String GET_USER_URL = "/user/profile/userId";
    public static final String POST_SPREAD_URL = "/rates/latest";
    private static String banner ="                                           _                          _        \n" +
            "  ___   _ __    _ __    ___    __ _    __| |           ___    __ _  | |   ___ \n" +
            " / __| | '_ \\  | '__|  / _ \\  / _` |  / _` |  _____   / __|  / _` | | |  / __|\n" +
            " \\__ \\ | |_) | | |    |  __/ | (_| | | (_| | |_____| | (__  | (_| | | | | (__ \n" +
            " |___/ | .__/  |_|     \\___|  \\__,_|  \\__,_|          \\___|  \\__,_| |_|  \\___|\n" +
            "       |_|               ";

    /**
     * App Start.
     * @param args
     */
    public static void main(String[] args) throws IOException {
        int port = Integer.valueOf(PropertiesManager.getProperty("application.server.port"));
        final ObjectMapper objectMapper = FxObjectMapperProvider.getObjectMapper();
        final GlobalExceptionHandler globalErrorHandler = new GlobalExceptionHandler(objectMapper);
        final HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        //Create GET USER by ID request
        final UserHandler userHandler = new UserHandler(objectMapper, globalErrorHandler,userRepository);
        server.createContext(GET_USER_URL, userHandler::handle);
        //Create POST USER to get Spread ,GET spread using id
        final SpreadCalcResource spreadCalcResource = new SpreadCalcResource(new HttpResource(objectMapper), objectMapper);
        final SpreadHandler spreadHandler = new SpreadHandler(objectMapper,globalErrorHandler,spreadCalcResource,userRepository);
        server.createContext(POST_SPREAD_URL, spreadHandler::handle);
        server.setExecutor(null);
        server.start();
        LOGGER.info(banner);
    }
}
