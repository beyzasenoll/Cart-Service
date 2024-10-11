package com.ecommerce.application.domain.promotion;

import com.ecommerce.application.domain.Cart;
import com.ecommerce.application.domain.item.Item;

public class CategoryPromotion implements Promotion {
    private static final int ID = 5676;

    @Override
    public double applyDiscount(Cart cart) {
        double discount = 0;
        for (Item item : cart.getItems()) {
            if (isApplicable(cart)) {
                discount += item.getTotalPrice() * 0.05; // %5 discount
            }
        }
        return discount;
    }

    @Override
    public int getId() {
        return ID;
    }

    public boolean isApplicable(Cart cart) {
        for (Item item : cart.getItems()) {
            if (item.getCategoryId() == 3003) {
                return true; // At least one item with CategoryID 3003
            }
        }
        return false;
    }
}

