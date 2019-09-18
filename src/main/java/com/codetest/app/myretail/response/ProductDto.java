package com.codetest.app.myretail.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author nrajuri
 */
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

}
