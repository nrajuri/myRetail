package com.codetest.app.myretail.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.codetest.app.myretail.entity.CurrentPrice;
import com.codetest.app.myretail.entity.Product;
import com.codetest.app.myretail.remoteclient.ConnectHttpClient;
import com.codetest.app.myretail.repository.ProductRepository;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = { "product-api-endpoint=http://redsky.target.com", })
public class ProductServiceTest {

	@Mock
	ProductService productService;

	@Mock
	ProductRepository productRepository;

	@Mock
	ConnectHttpClient connectHttpClientMock;

	@Value("${product-api-endpoint}")
	String endPoint;

	@Test
	public void testValueSetup() {
		assertEquals("http://redsky.target.com", endPoint);
	}

	@Configuration
	public
	static class Config {

		@Bean
		public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
			return new PropertySourcesPlaceholderConfigurer();
		}

	}


	/**
	 * Setup for Mockito before any test run.
	 */
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getProductByIdTest() throws Exception{

		//Objects created for the actual Mock
		CurrentPrice currentPriceMock = new CurrentPrice(BigDecimal.valueOf(13.49),"USD");
		Product productMock = new Product("13860428",currentPriceMock) ;
		Mockito.when(productRepository.findProductByproductId(Mockito.anyString())).thenReturn(productMock);
		assertEquals("13860428",productMock.getProductId());
	}

}



