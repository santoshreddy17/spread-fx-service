package com.fx.trade.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Fx application object mapper.
 * to get application config version of object mapper.
 */
public final class FxObjectMapperProvider {
    /**
     * Get application object mapper.
     * @return objectmapper.
     */
    public static ObjectMapper getObjectMapper(){
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        //TODO to add all configs to object mapper.
        return objectMapper;
    }
}
