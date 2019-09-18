package com.codetest.app.myretail.helper;

import com.codetest.app.myretail.entity.CurrentPrice;
import com.codetest.app.myretail.entity.Product;
import com.codetest.app.myretail.exception.MyRetailException;
import com.codetest.app.myretail.response.CurrentPriceDto;
import com.codetest.app.myretail.response.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ProductHelper {

    /**
     * Builds the product response object.
     *
     * @param product
     * @param productName
     * @return ProductDto Returns the ProductDto object.
     * @throws MyRetailException
     */
    public ProductDto generateProductResponse(final Product product, final String productName) throws MyRetailException {
        ProductDto prodResponse = new ProductDto();
        CurrentPriceDto currentPriceResponse = new CurrentPriceDto();
        try {
            currentPriceResponse.setCurrencyCode(product.getCurrentPrice().getCurrencyCode());
            currentPriceResponse.setValue(product.getCurrentPrice().getValue());
            prodResponse.setProductId(product.getProductId());
            prodResponse.setCurrentPrice(currentPriceResponse);
            prodResponse.setName(productName);
        } catch (NullPointerException e) {
            throw new MyRetailException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
        return prodResponse;
    }

    /**
     * Builds the Product object to insert into the DB.
     *
     * @param ProductDto
     * @return Product
     */
    public Product getProductDomainObject(final ProductDto ProductDto) {
        Product product = new Product();
        CurrentPrice currentPrice = new CurrentPrice();
        product.setProductId(ProductDto.getProductId());
        currentPrice.setCurrencyCode(ProductDto.getCurrentPrice().getCurrencyCode());
        currentPrice.setValue(ProductDto.getCurrentPrice().getValue());
        product.setCurrentPrice(currentPrice);
        return product;
    }
}
