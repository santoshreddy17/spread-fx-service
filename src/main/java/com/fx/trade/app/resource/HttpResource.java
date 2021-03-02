package com.fx.trade.app.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fx.trade.app.PropertiesManager;
import com.fx.trade.app.model.MidFxRates;
import com.fx.trade.app.utils.AppUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpResource {

    private static final Logger LOGGER = LogManager.getLogger(HttpResource.class);
    private final ObjectMapper objectMapper;
    final HttpClient httpClient = HttpClient.newHttpClient();

    public HttpResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Get Mid Fx Rates.
     * @param baseCurrency base currency.
     * @return Mid Fx Rates for the currency.
     */
    public MidFxRates getMidFxRates(final String baseCurrency) throws IOException, InterruptedException {
            final HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(getURL(baseCurrency)))
                    .header(AppUtils.CONTENT_TYPE,AppUtils.APPLICATION_JSON)
                    .GET()
                    .build();
            final HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return getMidFxRatesFromJson(httpResponse.body());
    }

    private String getURL(final String baseCurrency) {
        final String baseUrl = PropertiesManager.getProperty("mid.price.base.url");
        final String filterList = PropertiesManager.getProperty("application.filter.currency.list");
        final StringBuilder url = new StringBuilder(baseUrl).append("?base=").append(baseCurrency);
        if(AppUtils.notNullOrEmpty(filterList)){
            url.append("&").append("symbols=").append(filterList);
        }

        return url.toString();
    }

    private MidFxRates getMidFxRatesFromJson(final String json) {
        try {
            final MidFxRates midFxRates = objectMapper.readValue(json, MidFxRates.class);
            System.out.println(midFxRates);
            return midFxRates;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
