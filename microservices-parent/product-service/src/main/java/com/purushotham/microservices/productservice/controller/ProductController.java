package com.purushotham.microservices.productservice.controller;

import com.purushotham.microservices.productservice.dto.ProductRequest;
import com.purushotham.microservices.productservice.dto.ProductResponse;
import com.purushotham.microservices.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest){
    return productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public  List<ProductResponse> getAllProducts(){
    return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductResponse>> getProductById(@PathVariable String id){
        return ResponseEntity.ok().body(productService.getProductById(id));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Optional<ProductResponse>> deleteroductById(@PathVariable String id){
        return ResponseEntity.ok().body(productService.deleteProductById(id));

    }
}
