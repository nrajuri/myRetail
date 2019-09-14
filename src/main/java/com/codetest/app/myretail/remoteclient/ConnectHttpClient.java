/**
 *
 */
package com.codetest.app.myretail.remoteclient;


import com.codetest.app.myretail.exception.MyRetailException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
                JSONObject jsonObject = new JSONObject(jsonResponse);
                log.debug("JSON Response from Remote Client  :" + jsonResponse.toString());
                if (jsonObject.getJSONObject("product").getJSONObject("item")
                        .getJSONObject("product_description") != null) {
                    JSONObject productDescription = jsonObject.getJSONObject("product").getJSONObject("item")
                            .getJSONObject("product_description");
                    productName = productDescription.getString("title");
                } else {
                    log.debug("Product title JSON value Unavailable in Product API");
                    throw new MyRetailException(HttpStatus.NO_CONTENT.value(),
                            "The title does not exists for the product");
                }
            }
        } catch (RestClientException | JSONException e) {
            log.debug("Product API unavailable  :" + apiEndpointURL + productId);
            throw new MyRetailException(HttpStatus.NOT_FOUND.value(), "Product Remote API unavailable");
        }
        return productName;
    }

}
