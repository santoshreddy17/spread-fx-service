package com.fx.trade.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * To get the mid fx rates from the external service.
 */
public class MidFxRates {

    @JsonProperty("base")
    private String baseCurrency;
    @JsonProperty("date")
    private LocalDate localDate;
    @JsonProperty("rates")
    private Map<String, BigDecimal> fxRateMap = new HashMap<>();

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public MidFxRates setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
        return this;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }


    public Map<String, BigDecimal> getFxRateMap() {
        return fxRateMap;
    }

    public MidFxRates setFxRateMap(Map<String, BigDecimal> fxRateMap) {
        this.fxRateMap = fxRateMap;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MidFxRates that = (MidFxRates) o;
        return baseCurrency.equals(that.baseCurrency) &&
                localDate.equals(that.localDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseCurrency, localDate);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MidFxRates.class.getSimpleName() + "[", "]")
                .add("baseCurrency='" + baseCurrency + "'")
                .add("localDate=" + localDate)
                .add("fxRateMap=" + fxRateMap)
                .toString();
    }
}
