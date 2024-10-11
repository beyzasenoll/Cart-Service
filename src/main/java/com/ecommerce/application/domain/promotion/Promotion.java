package com.ecommerce.application.domain.promotion;

import com.ecommerce.application.domain.Cart;

public interface Promotion {
    double applyDiscount(Cart cart);
    int getId();
}
