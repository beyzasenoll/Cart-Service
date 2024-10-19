package com.ecommerce.application.domain.promotion;

import com.ecommerce.application.domain.cart.Cart;
import com.ecommerce.application.domain.item.Item;
import org.springframework.stereotype.Component;

@Component
public class CategoryPromotion implements Promotion {
    private static final int PROMOTION_ID = 5676;
    private static final int TARGET_CATEGORY_ID = 3003;
    private static final double DISCOUNT_RATE = 0.05;

    @Override
    public double applyDiscount(Cart cart) {
        if (!isApplicable(cart)) {
            return 0;
        }

        double discount = 0;
        for (Item item : cart.getItems()) {
            if (item.getCategoryId() == TARGET_CATEGORY_ID) {
                discount += item.getTotalPrice() * DISCOUNT_RATE;
            }
        }
        cart.setTotalPrice(cart.getTotalPrice() - discount);
        return discount;
    }

    @Override
    public int getId() {
        return PROMOTION_ID;
    }

    @Override
    public boolean isApplicable(Cart cart) {
        for (Item item : cart.getItems()) {
            if (item.getCategoryId() == TARGET_CATEGORY_ID) {
                return true;
            }
        }
        return false;
    }
}


