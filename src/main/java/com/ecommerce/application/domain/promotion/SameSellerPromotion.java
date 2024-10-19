package com.ecommerce.application.domain.promotion;

import com.ecommerce.application.domain.cart.Cart;
import org.springframework.stereotype.Component;

@Component

public class SameSellerPromotion implements Promotion {
    private static final int PROMOTION_ID = 9909;
    private static final double DISCOUNT_RATE = 0.10;


    @Override
    public double applyDiscount(Cart cart) {
        if (isApplicable(cart)) {
            return cart.getTotalPrice() * DISCOUNT_RATE;
        }
        return 0;
    }

    @Override
    public int getId() {
        return PROMOTION_ID;
    }

    @Override
    public boolean isApplicable(Cart cart) {
        return cart.hasSameSellerItems();
    }
}

