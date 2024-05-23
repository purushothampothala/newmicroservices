package com.purushotham.microservices.productservice.repository;

import com.purushotham.microservices.productservice.dto.ProductRequest;
import com.purushotham.microservices.productservice.dto.ProductResponse;
import com.purushotham.microservices.productservice.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.mongodb.core.aggregation.ConditionalOperators.Switch.CaseOperator.when;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DataMongoTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Mock
    private ModelMapper modelMapper;
    Product product;
@BeforeEach
public void init(){
    productRepository.deleteAll();
    }
    @Test
    public void testSaveProduct() {
        // Given
        product = new Product("1", "Realme", "Product from Realme", 20000.0);
        // When
        Product savedProduct = productRepository.save(product);
        // Then
        assertEquals(product.getId(), savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getDescription(), savedProduct.getDescription());
        assertEquals(product.getPrice(), savedProduct.getPrice());
    }

    @Test
    public void TestGetAllProducts() {
        Product product = new Product("1", "Realme", "Product from Realme", 20000.0);
        Product product2 = new Product("2", "Realme15", "Product from Realme", 200000.0);
        // When
        Product savedProduct = productRepository.save(product);
        Product savedProduct2 = productRepository.save(product2);
        List<Product> listOfProducts = productRepository.findAll();
        assertEquals(2,listOfProducts.size());
        assertTrue(listOfProducts.contains(savedProduct));
        assertTrue(listOfProducts.contains(savedProduct2));
    }

    @Test
    public void testGetProductById() {
        //given
        Product product= new Product("1", "Realme", "Product from Realme", 20000.0);
        //when
        String id= product.getId();
        productRepository.save(product);
       Optional<Product> receivedResponse=productRepository.findById(id);
       assertEquals(receivedResponse.get().getId(),product.getId());

    }
}