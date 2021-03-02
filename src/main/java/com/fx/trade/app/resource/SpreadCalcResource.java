package com.fx.trade.app.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fx.trade.app.PropertiesManager;
import com.fx.trade.app.exception.ResourceException;
import com.fx.trade.app.model.MidFxRates;
import com.fx.trade.app.model.PricingTier;
import com.fx.trade.app.model.UserRate;
import com.fx.trade.app.model.UserRates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpreadCalcResource {

    private static final Logger LOGGER = LogManager.getLogger(SpreadCalcResource.class);
    public static final String APPLICATION_BASE_CURRENCY = "application.base.currency";
    final HttpResource httpResource;
    final ObjectMapper objectMapper;


    /**
     * Spread Calc Resource.
     * @param httpResource http resource.
     * @param objectMapper object mapper.
     */
    public SpreadCalcResource(HttpResource httpResource, ObjectMapper objectMapper) {
        this.httpResource = httpResource;
        this.objectMapper = objectMapper;
    }

    /**
     * Get User Rates.
     * @param pricingTier pricing tier.
     * @return userRates
     * @throws ResourceException
     */
    public UserRates getUserRates(final PricingTier pricingTier) throws ResourceException {
        final String spreadString = PropertiesManager.getProperty(pricingTier.getKey());
        //Would add a caching option if i know the frequency of rates publishing
        final String baseCurrency = PropertiesManager.getProperty(APPLICATION_BASE_CURRENCY);
        final List<String> filterList = getFilterList();
        final BigDecimal spread = new BigDecimal(spreadString);
        final MidFxRates midFXRates = getMidFXRates(baseCurrency);
        final Map<String, UserRate> userRateMap = new HashMap<>(filterList.size());
        filterList.forEach(currency -> {
            final BigDecimal midPrice = midFXRates.getFxRateMap().get(currency);
            final UserRate userRate = getUserRate(spread, midPrice);
            userRateMap.put(currency, userRate);
        });
        return new UserRates(baseCurrency, userRateMap);
    }

    private UserRate getUserRate(final BigDecimal spread,final BigDecimal midPrice) {
        //I know there is more complex calc by calculating bid first and calculating ask from that value.
        //Not entirely certain of the formula.
        final BigDecimal spreadValue = midPrice.multiply(spread);
        final BigDecimal bid = midPrice.subtract(spreadValue);
        final BigDecimal ask = midPrice.add(spreadValue);
        return  new UserRate(bid,midPrice,ask);
    }

    private List<String> getFilterList() {
        final String property = PropertiesManager.getProperty("application.filter.currency.list");
        return Arrays.asList(property.split(",",-1));
    }

    private MidFxRates getMidFXRates(String baseCurrency) throws ResourceException {
        try {
            return httpResource.getMidFxRates(baseCurrency);
        } catch (IOException| InterruptedException e) {
            LOGGER.error("Error getting mid fx rates from source :"+e.getMessage(),e);
            throw new ResourceException("Server Error : "+e.getMessage(),e);
        }
    }
}
