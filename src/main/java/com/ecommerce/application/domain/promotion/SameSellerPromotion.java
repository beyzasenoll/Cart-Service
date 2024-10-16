package com.ecommerce.application.domain.promotion;

import com.ecommerce.application.domain.cart.Cart;
import org.springframework.stereotype.Component;

@Component

public class SameSellerPromotion implements Promotion {
    private static final int ID = 9909;

    @Override
    public double applyDiscount(Cart cart) {
        if (isApplicable(cart)) {
            return cart.getTotalPrice() * 0.10; // %10 discount
        }
        return 0;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public boolean isApplicable(Cart cart) {
        return cart.hasSameSellerItems();
    }
}

