package com.ecommerce.application.service.impl.validator;

import com.ecommerce.application.domain.cart.Cart;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.domain.item.VasItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class CartValidator {

    private static final Logger logger = LoggerFactory.getLogger(CartValidator.class);
    private static final int MAX_UNIQUE_ITEMS = 10;
    private static final int MAX_TOTAL_ITEMS = 30;
    private static final double MAX_TOTAL_PRICE = 500000;

    public boolean isCartValid(Item item, Cart cart) {
        logger.info("Validating cart for item: {}", item);

        int totalQuantity = cart.calculateTotalQuantity();
        double totalPrice = cart.getTotalPrice();
        int uniqueItemCount = calculateUniqueItems(cart);

        // Validate cart based on the defined constraints
        if (uniqueItemCount >= MAX_UNIQUE_ITEMS) {
            logger.error("Cart validation failed: too many unique items.");
            return false;
        }

        if (totalQuantity + item.getQuantity() > MAX_TOTAL_ITEMS) {
            logger.error("Cart validation failed: total item count exceeds limit.");
            return false;
        }

        if (totalPrice + (item.getTotalPrice()) > MAX_TOTAL_PRICE) {
            logger.error("Cart validation failed: total price exceeds limit.");
            return false;
        }

        logger.info("Cart validation successful.");
        return true;
    }

    private int calculateUniqueItems(Cart cart) {
        HashSet<Integer> uniqueItems = new HashSet<>();
        for (Item cartItem : cart.getItems()) {
            if (!(cartItem instanceof VasItem)) {
                uniqueItems.add(cartItem.getItemId());
            }
        }
        return uniqueItems.size();
    }

    public Item findItemInCart(int itemId, Cart cart) {
        return cart.getItems().stream().filter(cartItem -> cartItem.getItemId() == itemId).findFirst().orElse(null);
    }
}

