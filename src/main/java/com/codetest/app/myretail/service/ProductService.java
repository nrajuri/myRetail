/**
 *
 */
package com.codetest.app.myretail.service;

import com.codetest.app.myretail.entity.Product;
import com.codetest.app.myretail.exception.MyRetailException;
import com.codetest.app.myretail.helper.ProductHelper;
import com.codetest.app.myretail.remoteclient.RemoteClient;
import com.codetest.app.myretail.repository.ProductRepository;
import com.codetest.app.myretail.response.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private RemoteClient remoteClient;

    @Autowired
    private ProductHelper helperObject;

    /**
     * Gets all product details from DB
     * @return (@ Link Product) list of products.
     * @throws MyRetailException
     */
    public List<Product> getAllProducts() throws MyRetailException {
        List<Product> productList = productRepository.findAll();
        if (productList.size() == 0) {
            log.debug("Exception while fetching product data from DB ");
            throw new MyRetailException(HttpStatus.NOT_FOUND.value(), "Products not found in DB");
        }
        return productList;
    }

    /**
     * Gets product information from DB.
     * @param productId
     * @return ProductDto
     * @throws MyRetailException
     */
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
        productName = remoteClient.getProductNameByRemoteCall(productId);
        if (productName.isEmpty()) {
            throw new MyRetailException(HttpStatus.NOT_FOUND.value(), "Product Remote API unavailable");
        }
        return helperObject.generateProductResponse(product, productName);

    }

    /**
     * Updates product details in the DB.
     * @param ProductDto
     * @throws MyRetailException
     */
    public void updateProductById(final ProductDto ProductDto) throws MyRetailException {
        try {
            Product product = helperObject.getProductDomainObject(ProductDto);
            productRepository.save(product);
        } catch (Exception exception) {
            log.debug("Product Not Found Exception while doing update " + exception);
            throw new MyRetailException(HttpStatus.NOT_FOUND.value(), "Product not found while update");
        }
    }

}
