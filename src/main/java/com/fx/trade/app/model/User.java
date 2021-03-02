package com.fx.trade.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * User.
 */
public class User {

    @JsonProperty("userId")
    private String userId;
    @JsonProperty("pricingTier")
    private PricingTier pricingTier;
    @JsonProperty("emailAddress")
    private String emailAddress;

    /**
     * For Object creation by mapper.
     */
    public User() {
        //No arg constructor.
    }

    /**
     * All Arg Constructor for building a user.
     * @param userId user id.
     * @param pricingTier pricing tier.~ ( A/B/C )
     * @param emailAddress email address.
     */
    public User(String userId, String pricingTier, String emailAddress) {
        this.userId = userId;
        this.pricingTier = PricingTier.valueOf(pricingTier);
        this.emailAddress = emailAddress;
    }


    public String getUserId() {
        return userId;
    }

    public String getPricingTier() {
        return pricingTier.name();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId) &&
                emailAddress.equals(user.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, emailAddress);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("userId='" + userId + "'")
                .add("pricingTier=" + pricingTier)
                .add("emailAddress='" + emailAddress + "'")
                .toString();
    }
}
