package com.codetest.app.myretail.controller;

import com.codetest.app.myretail.entity.Product;
import com.codetest.app.myretail.exception.MyRetailException;
import com.codetest.app.myretail.response.ProductDto;
import com.codetest.app.myretail.response.ProductResponse;
import com.codetest.app.myretail.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author nrajuri
 */
@RequestMapping("/products")
@RestController
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * @return This method can be used to fetch all the products.
     * No authentication required.
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Product> getAllProducts() throws MyRetailException {
        log.info("No security on me. I can show all the products.");
        return productService.getAllProducts();
    }

    /**
     * Gets product details by Id.
     *
     * @param productId
     * @return ProductDto
     * @throws MyRetailException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") String productId) throws MyRetailException {
        try {
            log.info("Inside getProductById :  " + productId);
            ProductDto product = productService.getProductById(productId);
            log.debug(" product Response " + product);
            return new ResponseEntity<ProductDto>(product, HttpStatus.OK);
        } catch (MyRetailException e) {
            log.error("Exception occurred while fetching product data.", e.getMessage());
            throw new MyRetailException(e.getErrorCode(), e.getErrorMessage());
        }
    }

    /**
     * Updates product price details.
     *
     * @param product
     * @param productId
     * @return Ok status code.
     * @throws MyRetailException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> updatePrice(@RequestBody ProductDto product,
                                                       @PathVariable("id") String productId) throws MyRetailException {
        if (!product.getProductId().equalsIgnoreCase(productId)) {
            throw new MyRetailException(HttpStatus.BAD_REQUEST.value(), "Product Id in URL and JSON mismatch");
        }
        productService.updateProductById(product);
        return new ResponseEntity<ProductResponse>(HttpStatus.OK);
    }

    /**
     * @param ex
     * @return error response
     */
    @ExceptionHandler(MyRetailException.class)
    public ResponseEntity<ProductResponse> exceptionHandler(MyRetailException ex) {
        ProductResponse error = new ProductResponse(ex.getErrorCode(), ex.getMessage());
        log.debug(ex.getErrorMessage(), ex);
        return new ResponseEntity<>(error, HttpStatus.valueOf(ex.getErrorCode()));
    }
}
