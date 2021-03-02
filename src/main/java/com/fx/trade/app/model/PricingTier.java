package com.fx.trade.app.model;

import java.math.BigDecimal;

/**
 * Pricing Tier for user type with spread.
 */
public enum PricingTier {

    //Can be done better
    A("tier.user.spread.A"),B("tier.user.spread.B"),C("tier.user.spread.C");

    PricingTier(String key) {
        this.key = key;
    }

    private String key;

    public String getKey() {
        return key;
    }
}
