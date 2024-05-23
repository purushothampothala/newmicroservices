package com.purushotham.microservices.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data

public class ProductResponse{
  private  String id;
  private  String name;
  private  String description;
  private  Double price;
}


