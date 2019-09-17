/**
 *
 */
package com.codetest.app.myretail.service;

import com.codetest.app.myretail.entity.Product;
import com.codetest.app.myretail.exception.MyRetailException;
import com.codetest.app.myretail.helper.ProductHelper;
import com.codetest.app.myretail.remoteclient.ConnectHttpClient;
import com.codetest.app.myretail.repository.ProductRepository;
import com.codetest.app.myretail.response.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * @author nrajuri
 *
 */
@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ConnectHttpClient connectHttpClient;

    @Autowired
    private ProductHelper helperObject;

    public ProductDto getProductById(final String productId) throws MyRetailException {
        log.info("Inside ProductService().getProductById");

        Product product = new Product();
        String productName = null;
        //retrieve from MongoDB
        product = productRepository.findProductByproductId(productId);
        if (product == null) {
            log.debug("Product Not Found Exception while fetching product data from DB ");
            throw new MyRetailException(HttpStatus.NOT_FOUND.value(), "Product not found in DB");
        }

        //Retrieve title from remote API
        productName = connectHttpClient.getProductNameByRemoteCall(productId);
        if (productName.isEmpty()) {
            throw new MyRetailException(HttpStatus.NOT_FOUND.value(), "Product Remote API unavailable");
        }
        return helperObject.generateProductResponse(product, productName);

    }

    public void updateProductById(final ProductDto ProductDto) throws MyRetailException {
        try {
            Product product = helperObject.getProductDomainObject(ProductDto);
            productRepository.save(product);
        } catch (Exception exception) { //TODO handle only specific exceptions.
            log.debug("Product Not Found Exception while doing update " + exception);
            throw new MyRetailException(HttpStatus.NOT_FOUND.value(), "Product not found while update");
        }
    }

}
