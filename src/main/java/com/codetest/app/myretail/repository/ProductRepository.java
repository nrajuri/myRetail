/**
 *
 */
package com.codetest.app.myretail.repository;

import com.codetest.app.myretail.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author nrajuri
 *
 */
public interface ProductRepository extends MongoRepository<Product, String> {


    /**
     * @param productId
     * @return
     */
    public Product findProductByproductId(String productId);


}
