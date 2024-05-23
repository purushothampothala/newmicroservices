package com.purushotham.microservices.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purushotham.microservices.productservice.dto.ProductRequest;
import com.purushotham.microservices.productservice.dto.ProductResponse;
import com.purushotham.microservices.productservice.model.Product;
import com.purushotham.microservices.productservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
@Autowired
    private MockMvc mockMvc;

@MockBean
    private ProductService productService;
private ProductRequest productRequest;
private ProductResponse productResponse;
    private ObjectMapper objectMapper;

@BeforeEach
    void Setup(){
    objectMapper= new ObjectMapper();
    MockitoAnnotations.openMocks(this);
}

@Test
    void testSaveStudent() throws Exception {
    productRequest= new ProductRequest("realme","product from realme",2000.0);
    productResponse= new ProductResponse("1","realme","product from realme", 2000.0);
    Mockito.when(productService.createProduct(productRequest)).thenReturn(productResponse);
    mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productRequest)))
            .andExpect(status().isCreated());
}

@Test
    void testGetAllProducts() throws Exception {
    List<ProductResponse> listOfProducts= new ArrayList<>();
    ProductResponse response = new ProductResponse("1","realme","product from realme",2000.0);
    ProductResponse response1 = new ProductResponse("2","realme","product from realme",2000.0);
    listOfProducts.add(response);
    listOfProducts.add(response1);
    Mockito.when(productService.getAllProducts()).thenReturn(listOfProducts);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
            .andExpect(status().isAccepted())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(listOfProducts)));

}
@Test
public void testDeleteById() throws Exception {
    Product product= new Product("2","realme","product from realme",2000.0);
    ProductResponse productResponse= new ProductResponse("2","realme","product from realme",2000.0);
    Mockito.when(productService.getProductById("2")).thenReturn(Optional.of(productResponse));
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/product/delete/"+product.getId()))
            .andDo(print())
            .andExpect(status().isOk());
}

}


