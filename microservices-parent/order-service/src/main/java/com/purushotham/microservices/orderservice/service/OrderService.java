package com.purushotham.microservices.orderservice.service;

import com.purushotham.microservices.orderservice.dto.InventoryResponse;
import com.purushotham.microservices.orderservice.dto.OrderLineItemsDto;
import com.purushotham.microservices.orderservice.dto.OrderRequest;
import com.purushotham.microservices.orderservice.exception.StockNotFoundException;
import com.purushotham.microservices.orderservice.feignclient.InventoryClient;
import com.purushotham.microservices.orderservice.model.Order;
import com.purushotham.microservices.orderservice.model.OrderLineItems;
import com.purushotham.microservices.orderservice.repository.OrderRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {
@Autowired
    private OrderRepository orderRepository;
@Autowired
private ModelMapper modelMapper;
@Autowired
private InventoryClient inventoryClient;

private final WebClient webClient;
    @Transactional
    public void placeOrder(OrderRequest orderRequest) throws IllegalAccessException {
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(dto -> modelMapper.map(dto, OrderLineItems.class))
                .collect(Collectors.toList());

        order.setOrderLineItemsList(orderLineItemsList);

   List<String> skuCodes= order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();
        List<InventoryResponse> inventoryResponses = inventoryClient.isInStock(skuCodes);

        boolean allProductsInStock = inventoryResponses.stream()
                .allMatch(InventoryResponse::isInStock);
              if (allProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new StockNotFoundException("Product not in stock, Please try again");
        }
    }
    private String generateOrderNumber() {
        return UUID.randomUUID().toString();
    }
}
