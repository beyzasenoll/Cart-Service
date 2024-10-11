package com.ecommerce.application.exception;

public class SellerNotAllowedException extends RuntimeException {
    public SellerNotAllowedException(String message) {
        super(message);
    }
}
