package com.fx.trade.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.StringJoiner;

/**
 * User Rate for a currency.
 */
public class UserRate {
    @JsonProperty("bid")
    private BigDecimal bidPrice;
    @JsonProperty("market")
    private BigDecimal midPrice;
    @JsonProperty("ask")
    private BigDecimal askPrice;

    public UserRate(BigDecimal bidPrice, BigDecimal midPrice, BigDecimal askPrice) {
        this.bidPrice = bidPrice;
        this.midPrice = midPrice;
        this.askPrice = askPrice;
    }

    public BigDecimal getBidPrice() {
        return bidPrice;
    }

    public BigDecimal getAskPrice() {
        return askPrice;
    }



    @Override
    public String toString() {
        return new StringJoiner(", ", UserRate.class.getSimpleName() + "[", "]")
                .add("bidPrice=" + bidPrice)
                .add("midPrice=" + midPrice)
                .add("askPrice=" + askPrice)
                .toString();
    }
}
