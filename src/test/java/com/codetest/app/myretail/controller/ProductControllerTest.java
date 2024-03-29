package com.codetest.app.myretail.controller;


import com.codetest.app.myretail.response.CurrentPriceDto;
import com.codetest.app.myretail.response.ProductDto;
import com.codetest.app.myretail.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = ProductController.class, secure = false)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getProductByIdTest() throws Exception {

        ProductDto product = new ProductDto();
        CurrentPriceDto currentPrice = new CurrentPriceDto();
        product.setProductId("13860428");
        product.setName("The Big Lebowski (Blu-ray)");
        currentPrice.setCurrencyCode("USD");
        currentPrice.setValue(BigDecimal.valueOf(13.49));
        product.setCurrentPrice(currentPrice);
        Mockito.when(productService.getProductById(Mockito.anyString())).thenReturn(product);
        String prod_url = "/products/13860428";
        RequestBuilder builder = MockMvcRequestBuilders.get(prod_url)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        MvcResult actual = mockMvc.perform(builder).andReturn();
        String expected = "{\"id\":\"13860428\"" +
                ",\"name\":\"The Big Lebowski (Blu-ray)\",\"current_price\":{\"value\": 13.49" +
                ",\"currency_code\":\"USD\"}}";
        JSONAssert.assertEquals(expected, actual.getResponse().getContentAsString(), false);
    }

    @Test
    public void getProductByWrongIdTest() throws Exception {
        String actual = mockMvc.perform(get("/products/123456"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", actual);
    }
}
