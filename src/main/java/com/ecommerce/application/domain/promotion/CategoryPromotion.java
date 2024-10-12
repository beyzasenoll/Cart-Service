package com.ecommerce.application.domain.promotion;

import com.ecommerce.application.domain.cart.Cart;
import com.ecommerce.application.domain.item.Item;

import org.springframework.stereotype.Component;

@Component
public class CategoryPromotion implements Promotion {
    private static final int ID = 5676;

    @Override
    public double applyDiscount(Cart cart) {
        if (!isApplicable(cart)) {
            return 0;
        }

        double discount = 0;
        for (Item item : cart.getItems()) {
            if (item.getCategoryId() == 3003) {
                discount += item.getTotalPrice() * 0.05; // %5 discount
            }
        }
        cart.setTotalPrice(cart.getTotalPrice() - discount);
        return discount;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public boolean isApplicable(Cart cart) {
        for (Item item : cart.getItems()) {
            if (item.getCategoryId() == 3003) {
                return true;
            }
        }
        return false;
    }
}


