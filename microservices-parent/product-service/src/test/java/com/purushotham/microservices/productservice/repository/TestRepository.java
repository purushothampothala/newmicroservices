package com.purushotham.microservices.productservice.repository;

import com.purushotham.microservices.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<Product,String> {
}
