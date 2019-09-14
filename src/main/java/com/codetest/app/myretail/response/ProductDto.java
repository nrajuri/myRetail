package com.codetest.app.myretail.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NotEmpty
public class ProductDto {

    @JsonProperty(value = "id")
    private String productId;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "current_price")
    private CurrentPriceDto currentPrice;

    @Override
    public String toString() {
        return "ProductResponse [productId=" + productId + ", name=" + name + ", currentPrice=" + currentPrice + "]";
    }
}
