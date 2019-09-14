package com.codetest.app.myretail.controller;

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
import springfox.documentation.annotations.Cacheable;

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
    public String index() {
        return "No security on me. I can show all the products when implemented.";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ROLE_USER')")
    @Cacheable("Product")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") String productId) throws MyRetailException {
        log.info("Inside getProductById :  " + productId);
        ProductDto product = productService.getProductById(productId);
        log.debug(" product Response " + product);
        return new ResponseEntity<ProductDto>(product, HttpStatus.OK);
    }

    @ExceptionHandler(MyRetailException.class)
    public ResponseEntity<ProductResponse> exceptionHandler(MyRetailException ex) {
        ProductResponse error = new ProductResponse(ex.getErrorCode(), ex.getMessage());
        log.debug(ex.getErrorMessage(), ex);
        return new ResponseEntity<ProductResponse>(error, HttpStatus.valueOf(ex.getErrorCode()));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> updatePrice(@RequestBody ProductDto product,
                                                       @PathVariable("id") String productId) throws MyRetailException {
        if (!product.getProductId().equalsIgnoreCase(productId)) {
            throw new MyRetailException(HttpStatus.BAD_REQUEST.value(), "Product Id in URL and JSON mismatch");
        }
        productService.updateProductById(product);
        return new ResponseEntity<ProductResponse>(HttpStatus.OK);
    }
}

//TODO create "ErrorResponse" POJO to use in "ExceptionHandler" and "ProductResponse" only for success scenario.