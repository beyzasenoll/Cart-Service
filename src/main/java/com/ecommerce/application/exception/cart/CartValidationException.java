package com.ecommerce.application.exception.cart;

public class CartValidationException extends RuntimeException {
    public static final String TOO_MANY_UNIQUE_ITEMS = "Cart validation failed: too many unique items.";
    public static final String TOTAL_ITEM_COUNT_EXCEEDED = "Cart validation failed: total item count exceeds limit.";
    public static final String TOTAL_PRICE_EXCEEDED = "Cart validation failed: total price exceeds limit.";

    public CartValidationException(String message) {
        super(message);
    }
}
