package com.fx.trade.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Util class to load system level properties.
 */
public final class PropertiesManager {

    private static final Properties properties= new Properties();
    private static final Logger LOGGER = LogManager.getLogger(PropertiesManager.class);

    static {
        try {
//            System.getProperties().setProperty("jdk.internal.httpclient.disableHostnameVerification", "true");
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties");
            properties.load(inputStream);
            LOGGER.debug(properties);
        } catch (IOException e) {
            LOGGER.error("Error loading properties from properties file"+e.getMessage(),e);
        }
    }

    public static String getProperty(final String key){
        return (String)properties.get(key);
    }


}
