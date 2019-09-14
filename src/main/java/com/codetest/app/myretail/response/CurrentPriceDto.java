package com.codetest.app.myretail.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NotEmpty
public class CurrentPriceDto {

    @JsonProperty(value = "value")
    private BigDecimal value;
    @JsonProperty(value = "currency_code")
    private String currencyCode;

    @Override
    public String toString() {
        return "CurrentPriceDto [value=" + value + ", currencyCode=" + currencyCode + "]";
    }

}
