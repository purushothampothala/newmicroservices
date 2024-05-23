package com.purushotham.microservices.inventoryservice.controller;

import com.purushotham.microservices.inventoryservice.dto.InventoryResponse;
import com.purushotham.microservices.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")

public class InventoryController {
    @Autowired
    private  InventoryService inventoryService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){

        return inventoryService.isInstock(skuCode);
    }
}
