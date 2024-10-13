package com.ecommerce.application.domain.promotion;

import com.ecommerce.application.domain.cart.Cart;
import org.springframework.stereotype.Component;
@Component
public class TotalPricePromotion implements Promotion {
    private static final int ID = 1232;

    private static final double DISCOUNT_1_THRESHOLD = 5000;
    private static final double DISCOUNT_2_THRESHOLD = 10000;
    private static final double DISCOUNT_3_THRESHOLD = 50000;
    private static final double DISCOUNT_1_AMOUNT = 250;
    private static final double DISCOUNT_2_AMOUNT = 500;
    private static final double DISCOUNT_3_AMOUNT = 1000;
    private static final double DISCOUNT_4_AMOUNT = 2000;

    @Override
    public double applyDiscount(Cart cart) {
        double totalPrice = cart.getTotalPrice();

        if (!isApplicable(cart)) {
            return 0;
        }

        return calculateDiscount(totalPrice);
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public boolean isApplicable(Cart cart) {
        return cart.getTotalPrice() > 0;
    }

    private double calculateDiscount(double totalPrice) {
        if (totalPrice < DISCOUNT_1_THRESHOLD) {
            return DISCOUNT_1_AMOUNT;
        } else if (totalPrice < DISCOUNT_2_THRESHOLD) {
            return DISCOUNT_2_AMOUNT;
        } else if (totalPrice < DISCOUNT_3_THRESHOLD) {
            return DISCOUNT_3_AMOUNT;
        } else {
            return DISCOUNT_4_AMOUNT;
        }
    }
}

