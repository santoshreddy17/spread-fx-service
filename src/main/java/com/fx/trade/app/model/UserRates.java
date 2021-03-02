package com.fx.trade.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.StringJoiner;

/**
 * Get rates based on user.
 */
public class UserRates {
    @JsonProperty("base")
    private String baseCurrency;
    @JsonProperty("rates")
    private Map<String, UserRate> currencyUserRates;

    /***
     * User Rates.
     * @param baseCurrency base Currency.
     * @param currencyUserRates currency Rates based on user type.
     */
    public UserRates(final String baseCurrency, final Map<String, UserRate> currencyUserRates) {
        this.baseCurrency = baseCurrency;
        this.currencyUserRates = currencyUserRates;
    }

    public Map<String, UserRate> getCurrencyUserRates() {
        return currencyUserRates;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", UserRates.class.getSimpleName() + "[", "]")
                .add("baseCurrency='" + baseCurrency + "'")
                .add("currencyUserRates=" + currencyUserRates)
                .toString();
    }
}
