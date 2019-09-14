/**
 *
 */
package com.codetest.app.myretail.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author nrajuri
 *
 */
@Data
@Document(collection = "productprice")
public class Product {

    @Id
    private String productId;
    private CurrentPrice currentPrice;

    public Product() {
    }

    public Product(String productId, CurrentPrice currentPrice) {
        this.productId = productId;
        this.currentPrice = currentPrice;
    }
}
