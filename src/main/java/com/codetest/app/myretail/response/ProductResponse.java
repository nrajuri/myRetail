package com.codetest.app.myretail.response;

import lombok.Data;

/**
 * @author nrajuri
 */
@Data
public class ProductResponse {

    private int statusCode;
    private String message;

    /**
     * @param statusCode
     * @param message
     */
    public ProductResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
