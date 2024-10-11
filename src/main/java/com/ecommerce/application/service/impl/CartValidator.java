package com.ecommerce.application.service.impl;

import com.ecommerce.application.domain.Cart;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.domain.item.VasItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CartValidator {

    Logger logger = LoggerFactory.getLogger(CartValidator.class);
    private static final int MAX_UNIQUE_ITEMS = 10;
    private static final int MAX_TOTAL_ITEMS = 30;
    private static final double MAX_TOTAL_PRICE = 500000;

    public boolean isCartValid(Item item, Cart cart) {
        logger.info("Validating cart for item: {}", item);

        int uniqueItemCount = 0;
        int totalQuantity = 0;
        double totalPrice = 0.0;

        for (Item cartItem : cart.getItems()) {
            if (!(cartItem instanceof VasItem)) {
                uniqueItemCount++;
            }
            totalQuantity += cartItem.getQuantity();
            totalPrice += cartItem.getPrice() * cartItem.getQuantity();
        }

        if (uniqueItemCount >= MAX_UNIQUE_ITEMS || totalQuantity + item.getQuantity() > MAX_TOTAL_ITEMS ||
                totalPrice + (item.getPrice() * item.getQuantity()) > MAX_TOTAL_PRICE) {
            logger.error("Cart validation failed.");
            return false;
        }

        logger.info("Cart validation successful.");
        return true;
    }

    public Item findExistingItemInCart(Item item, Cart cart) {
        for (Item cartItem : cart.getItems()) {
            if (cartItem.getItemId() == item.getItemId()) {
                return cartItem;
            }
        }
        return null;
    }

    public Item findItemInCart(int itemId, Cart cart) {
        for (Item cartItem : cart.getItems()) {
            if (cartItem.getItemId() == itemId) {
                return cartItem;
            }
        }
        return null;
    }
}
