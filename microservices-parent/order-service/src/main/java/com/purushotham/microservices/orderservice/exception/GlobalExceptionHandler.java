package com.purushotham.microservices.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<ApiException> handlingStockNotFoundException(StockNotFoundException ex){
    ApiException errorMessage= new ApiException(HttpStatus.OK.value(),ex.getMessage());
    return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
    }


}
