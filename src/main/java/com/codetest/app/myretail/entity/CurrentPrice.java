package com.codetest.app.myretail.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrentPrice {

    private BigDecimal value;
    private String currencyCode;

    /**
     * Empty constructor.
     */
    public CurrentPrice() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param value
     * @param currencyCode
     */
    public CurrentPrice(BigDecimal value, String currencyCode) {
        this.value = value;
        this.currencyCode = currencyCode;
    }
}
