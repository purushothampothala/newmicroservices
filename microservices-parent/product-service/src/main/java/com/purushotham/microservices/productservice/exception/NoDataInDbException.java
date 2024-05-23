package com.purushotham.microservices.productservice.exception;

public class NoDataInDbException extends RuntimeException {
        public NoDataInDbException(String message){

                super(message);
        }

}
