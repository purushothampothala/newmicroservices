package com.purushotham.microservices.productservice;
import com.purushotham.microservices.productservice.dto.ProductRequest;
import com.purushotham.microservices.productservice.dto.ProductResponse;

import com.purushotham.microservices.productservice.model.Product;
import com.purushotham.microservices.productservice.repository.TestRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {
	@LocalServerPort
    private int port;

	private String baseUrl="http://localhost";

	private static RestTemplate restTemplate;
	@Autowired
	private TestRepository testRepository;

    @BeforeAll
	public static void init(){

		restTemplate=new RestTemplate();
	}
	@BeforeEach
     public void setUp(){
		baseUrl=baseUrl.concat(":").concat(port+"").concat("/api/product");
		testRepository.deleteAll();
}
	@Test
	void TestcreateProduct() {
		ProductRequest product= new ProductRequest("IPhone","Product from apple company", 2000.00);
	    ProductResponse response=restTemplate.postForObject(baseUrl,product, ProductResponse.class);
		Assert.assertEquals("IPhone",response.getName());
		Assert.assertEquals(1,testRepository.findAll().size());

	}
	@Test
		public void testGetProducts(){
		ProductResponse product= new ProductResponse("1","IPhone","Product from apple company", 2000.00);
		ProductResponse product1= new ProductResponse("1","IPhone","Product from apple company", 2000.00);

		restTemplate.postForObject(baseUrl,product, ProductResponse.class);
		restTemplate.postForObject(baseUrl,product1, ProductResponse.class);
		List<ProductResponse>listOfProducts= (List<ProductResponse>) restTemplate.getForObject(baseUrl,List.class);
		Assert.assertEquals(2,testRepository.findAll().size());
		Assert.assertEquals(2,listOfProducts.size());

//		assertEquals("IPhone", listOfProducts.get(0).getName());
//		assertEquals("Product from apple company", listOfProducts.get(0).getDescription());
//		assertEquals(2000.00, listOfProducts.get(0).getPrice());
	}

//	@Test
//	public void testGetProductById() throws Exception {
//		Product product= new Product("1","IPhone","Product from apple company", 20000.00);
//		restTemplate.postForObject(baseUrl,product, ProductResponse.class);
//		ProductResponse response= (ProductResponse) restTemplate.getForEntity(baseUrl+"/1",product,ProductResponse.class);
//		Assert.assertNotNull(testRepository.findById("1"));
//		//Assert.assertEquals(1,listOfProducts.size());

	}
	


