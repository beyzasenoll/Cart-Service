package com.ecommerce.application.domain.promotion;

import com.ecommerce.application.domain.cart.Cart;

public class TotalPricePromotion implements Promotion {
    private static final int ID = 1232;

    @Override
    public double applyDiscount(Cart cart) {
        double totalPrice = cart.getTotalPrice();
        double discount = 0;

        if (!isApplicable(cart)) {
            return 0;
        }
        if (totalPrice < 5000) {
            discount = 250;
        } else if (totalPrice < 10000) {
            discount = 500;
        } else if (totalPrice < 50000) {
            discount = 1000;
        } else {
            discount = 2000;
        }

        return discount;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public boolean isApplicable(Cart cart) {
        return cart.getTotalPrice() > 0;
    }
}
