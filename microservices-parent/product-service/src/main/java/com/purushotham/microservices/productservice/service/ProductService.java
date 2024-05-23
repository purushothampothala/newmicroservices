package com.purushotham.microservices.productservice.service;

import com.purushotham.microservices.productservice.dto.ProductRequest;
import com.purushotham.microservices.productservice.dto.ProductResponse;
import com.purushotham.microservices.productservice.exception.NoDataInDbException;
import com.purushotham.microservices.productservice.exception.ResourceNotFoundException;
import com.purushotham.microservices.productservice.model.Product;
import com.purushotham.microservices.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.purushotham.microservices.productservice.util.StringConstants.NO_DATA_FOUND;
import static com.purushotham.microservices.productservice.util.StringConstants.RESOURCE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;


    public ProductResponse createProduct(ProductRequest productRequest) {

        Product product= modelMapper.map(productRequest,Product.class);
        productRepository.save(product);
        log.info("product {} created successfully",product.getId());
        return modelMapper.map(product, ProductResponse.class);

    }


    public List<ProductResponse> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        if (productList.isEmpty()) {
            throw new NoDataInDbException(NO_DATA_FOUND);
        }
            return productList
                    .stream().map(product ->
                            modelMapper.map(product, ProductResponse.class)).toList();
        }


    public Optional<ProductResponse> getProductById(String id) {
        Optional<Product> productById = productRepository.findById(id);
        return Optional.of(productById.map(product -> modelMapper.map(product, ProductResponse.class))
                .orElseThrow(()-> new ResourceNotFoundException(RESOURCE_NOT_FOUND + id)));
    }

    public Optional<ProductResponse> deleteProductById(String id) {
        Optional<Product>productById=productRepository.findById(id);
        if(productById.isPresent()){
            productRepository.deleteById(id);
        }else{
            throw new ResourceNotFoundException(RESOURCE_NOT_FOUND+id);
        }
        return productById.map(product -> modelMapper.map(productById, ProductResponse.class));
    }
}
