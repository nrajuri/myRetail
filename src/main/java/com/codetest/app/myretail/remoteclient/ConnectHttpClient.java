/**
 *
 */
package com.codetest.app.myretail.remoteclient;


import com.codetest.app.myretail.exception.MyRetailException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

/**
 * @author nrajuri
 *
 */
@Component
@Slf4j
public class ConnectHttpClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${product-api-endpoint}")
    private String apiEndpointURL;
    private String productName = null;

    public ConnectHttpClient() {
    }

    @HystrixCommand(fallbackMethod = "getProductName_FallBack")
    public String getProductNameByRemoteCall(String productId) throws MyRetailException {

        try {
            log.info("Inside ConnectHttpClient().getProductNameByRemoteCall");
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiEndpointURL + productId)
                    .queryParam("excludes",
                            "taxonomy,price,promotion,bulk_ship,rating_and_review_reviews," +
                                    "rating_and_review_statistics,question_answer_statistics");

            // Send request with GET method, and Headers.
            String jsonResponse = restTemplate.getForObject(builder.build().encode().toUri(), String.class);
            if (jsonResponse != null) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonObject = mapper.readTree(jsonResponse);
                log.debug("JSON Response from Remote Client  :" + jsonResponse.toString());
                if (jsonObject.get("product").get("item")
                        .get("product_description") != null) {
                    JsonNode productDescription = jsonObject.get("product").get("item")
                            .get("product_description");
                    productName = productDescription.get("title").asText();
                } else {
                    log.debug("Product title JSON value Unavailable in Product API");
                    throw new MyRetailException(HttpStatus.NO_CONTENT.value(),
                            "The title does not exists for the product");
                }
            }
        } catch (IOException e) {
            throw new MyRetailException(HttpStatus.NOT_FOUND.value(), "Error parsing the remote API response");
        }
        return productName;
    }

    //Fallback method when the remote client is down.
    public String getProductName_FallBack(String productId) {
        log.info("Inside fallBack method. Product API unavailable  :" + apiEndpointURL + "/" + productId);
        return "";
    }

}
