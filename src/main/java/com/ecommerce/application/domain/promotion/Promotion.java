package com.ecommerce.application.domain.promotion;

import com.ecommerce.application.domain.cart.Cart;

public interface Promotion {
    double applyDiscount(Cart cart);

    int getId();

    boolean isApplicable(Cart cart);
}

