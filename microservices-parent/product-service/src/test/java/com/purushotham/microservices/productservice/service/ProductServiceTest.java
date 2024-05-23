package com.purushotham.microservices.productservice.service;

import com.purushotham.microservices.productservice.dto.ProductRequest;
import com.purushotham.microservices.productservice.dto.ProductResponse;
import com.purushotham.microservices.productservice.exception.NoDataInDbException;
import com.purushotham.microservices.productservice.exception.ResourceNotFoundException;
import com.purushotham.microservices.productservice.model.Product;
import com.purushotham.microservices.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveProduct_Success() {
        //Given
        ProductRequest productRequest = new ProductRequest("IPhone", "product from I Phone company", 20000.0);
        Product product = new Product("1", "IPhone", "product from I phone company", 20000.0);
        ProductResponse productResponse = new ProductResponse("1", "IPhone", "product from I phone company", 20000.0);
        when(modelMapper.map(productRequest, Product.class)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(modelMapper.map(product, ProductResponse.class)).thenReturn(productResponse);
        //When
        ProductResponse expectedResult = productService.createProduct(productRequest);
        //then
        assertEquals(expectedResult, productResponse);
    }

    @Test
    public void testGetAllProducts() {
        //Given
        Product product1 = new Product("1", "HeadSet", "product from apple company", 2000.0);
        Product product2 = new Product("2", "AC", "product from LG company", 4000.0);
        List<Product> listOfProducts = new ArrayList<>();
        listOfProducts.add(product1);
        listOfProducts.add(product2);
        when(productRepository.findAll()).thenReturn(listOfProducts);
        when(modelMapper.map(any(Product.class), eq(ProductResponse.class)))
                .thenAnswer(invocation -> {
                    Product source = invocation.getArgument(0);
                    return new ProductResponse(source.getId(), source.getName(), source.getDescription(), source.getPrice());
                });
        List<ProductResponse> expectedResult = listOfProducts.stream().map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice())).toList();
        List<ProductResponse> actualResponse = productService.getAllProducts();
        log.info(expectedResult.toString());
        log.info(actualResponse.toString());
        assertEquals(expectedResult.size(), actualResponse.size());
        assertEquals(expectedResult, actualResponse);

    }
    @Test
    public void testListOfProductsException(){
        List<Product>emptyList=new ArrayList<>();
        when(productRepository.findAll()).thenReturn(emptyList);
        assertThrows(NoDataInDbException.class,()->productService.getAllProducts());
    }
    @Test
    void testGetProductByIdException(){
       Product product=new Product("3","Fridge","product from LG Company",50000.0);
       when(productRepository.findById("3")).thenReturn(Optional.empty());
       assertThrows(ResourceNotFoundException.class,()->productService.getProductById("3"));

    }
    @Test
    void testGetProductByIdSuccess(){
        Product product=new Product("3","Fridge","product from LG Company",50000.0);
        ProductResponse productresponse=new ProductResponse("3","Fridge","product from LG Company",50000.0);
        when(productRepository.findById("3")).thenReturn(Optional.of(product));
        when(modelMapper.map(any(Product.class),eq(ProductResponse.class))).thenReturn(productresponse);
        Optional<ProductResponse> response=productService.getProductById("3");
        assertEquals(product.getId(),response.get().getId());

    }
@Test
void testDeleteProductById() {
    // Given
    Product product = new Product("3", "HeadSet", "Product from apple company", 2000.0);
    Optional<Product> optionalProduct = Optional.of(product);

    // Mocking repository behavior
    when(productRepository.findById("3")).thenReturn(optionalProduct);

    // Mocking model mapper behavior
    ProductResponse expectedResponse = new ProductResponse("3", "HeadSet", "Product from apple company", 2000.0);
    when(modelMapper.map(product, ProductResponse.class)).thenReturn(expectedResponse);
    Optional<ProductResponse> actualResponse = productService.deleteProductById("3");
    //assertEquals(expectedResponse, actualResponse.get());
    verify(productRepository, times(1)).findById("3");
    verify(productRepository, times(1)).deleteById("3");
}
@Test
public void testDeleteByIdException(){
    Product product = new Product("3", "HeadSet", "Product from apple company", 2000.0);

    when(productRepository.findById("3")).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class,()->productService.deleteProductById("3"));
    verify(productRepository,times(1)).findById("3");


}
}