package com.purushotham.microservices.inventoryservice.service;

import com.purushotham.microservices.inventoryservice.dto.InventoryResponse;
import com.purushotham.microservices.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    @Autowired
    private final InventoryRepository inventoryRepository;
@Transactional(readOnly = true)
    public List<InventoryResponse> isInstock(List<String> skuCode){
     return   inventoryRepository.findBySkuCodeIn(skuCode).stream()
             .map(inventory->
                 InventoryResponse.builder().skuCode(inventory.getSkuCode())
                         .isInStock(inventory.getQuantity()>0)
                         .build()
             ).toList();

    }

}
