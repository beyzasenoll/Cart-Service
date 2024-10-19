package com.ecommerce.application.domain.cart;

import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.domain.item.VasItem;
import com.ecommerce.application.exception.cart.CartValidationException;
import com.ecommerce.application.exception.item.ItemException;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    private final Logger logger = LoggerFactory.getLogger(Cart.class);

    private final List<Item> items = new ArrayList<>();
    private double totalPrice;
    private static final Integer MAX_TOTAL_ITEMS = 30;
    private static final Double MAX_TOTAL_PRICE = 500000.0;
    private static final int MAX_UNIQUE_ITEMS = 10;
    public boolean addItem(Item item) {
        if (item == null) {
            return false;
        }

        Item existingItem = findItemInCart(item.getItemId(), this.items);
        if (existingItem != null) {
            updateExistingItem(item, existingItem);
        } else {
            addNewItem(item);
        }

        calculateTotalPrice();
        return true;
    }

    private void updateExistingItem(Item item, Item existingItem) {
        item.updateExistingItemQuantity(item, existingItem);
    }

    private void addNewItem(Item item) {
        items.add(item);
    }


    public boolean removeItem(int itemId) {
        Item itemToRemove = findItemInCart(itemId, this.items);
        if (itemToRemove == null) {
            logger.warn("Item with ID {} not found in cart.", itemId);
            throw new ItemException.ItemNotFoundException("Item with ID " + itemId + " not found in cart.");
        }

        boolean removed = items.removeIf(item -> item.getItemId() == itemId);
        if (removed) {
            calculateTotalPrice();
            logger.info("Item removed from cart: {}", itemToRemove);
        } else {
            logger.error("Failed to remove item: {}", itemToRemove);
            throw new ItemException.ItemRemoveException("Failed to remove item with ID " + itemId);
        }
        return removed;
    }



    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public boolean clearCart() {
        boolean wasEmpty = items.isEmpty();
        items.clear();
        return !wasEmpty;
    }

    public void calculateTotalPrice() {
        totalPrice = items.stream().mapToDouble(item -> item.getTotalPrice()).sum();
    }

    public int calculateTotalQuantity() {
        return items.stream().mapToInt(Item::getQuantity).sum();
    }

    public boolean hasSameSellerItems() {
        if (items.isEmpty()) {
            return false;
        }

        int firstSeller = items.get(0).getSellerId();
        return items.stream().allMatch(item -> firstSeller == item.getSellerId());
    }

    public void applyDiscount(double discount) {
        calculateTotalPrice();
        totalPrice -= discount;
    }

    public int calculateUniqueItems(Cart cart) {
        HashSet<Integer> uniqueItems = new HashSet<>();
        for (Item cartItem : cart.getItems()) {
            if (!(cartItem instanceof VasItem)) {
                uniqueItems.add(cartItem.getItemId());
            }
        }
        return uniqueItems.size();
    }

    public Item findItemInCart(int itemId,List<Item> items) {
        for (Item cartItem : items) {
            if (cartItem.getItemId() == itemId) {
                return cartItem;
            }
        }
        return null;
    }
    public boolean isCartValid(Item item, Cart cart) {
        logger.info("Validating cart for item: {}", item);

        int totalQuantity = cart.calculateTotalQuantity();
        double totalPrice = cart.getTotalPrice();
        int uniqueItemCount = cart.calculateUniqueItems(cart);

        if (uniqueItemCount >= MAX_UNIQUE_ITEMS) {
            logger.error(CartValidationException.TOO_MANY_UNIQUE_ITEMS);
            throw new CartValidationException(CartValidationException.TOO_MANY_UNIQUE_ITEMS);
        }

        if (totalQuantity + item.getQuantity() > MAX_TOTAL_ITEMS) {
            logger.error(CartValidationException.TOTAL_ITEM_COUNT_EXCEEDED);
            throw new CartValidationException(CartValidationException.TOTAL_ITEM_COUNT_EXCEEDED);
        }

        if (totalPrice + item.getTotalPrice() > MAX_TOTAL_PRICE) {
            logger.error(CartValidationException.TOTAL_PRICE_EXCEEDED);
            throw new CartValidationException(CartValidationException.TOTAL_PRICE_EXCEEDED);
        }

        logger.info("Cart validation successful.");
        return true;
    }

}
