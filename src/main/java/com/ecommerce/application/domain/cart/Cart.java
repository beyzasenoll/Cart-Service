package com.ecommerce.application.domain.cart;

import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.domain.item.VasItem;
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

    public boolean addItem(Item item) {
        if (item != null) {
            items.add(item);
            calculateTotalPrice();
            return true;
        }
        return false;
    }

    public boolean removeItem(int itemId) {
        Item itemToRemove = findItemInCart(itemId, this);
        if (itemToRemove == null) {
            logger.warn("Item with ID {} not found in cart.", itemId);
            return false;
        }

        boolean removed = items.removeIf(item -> item.getItemId() == itemId);
        if (removed) {
            calculateTotalPrice();
            logger.info("Item removed from cart: {}", itemToRemove);
        } else {
            logger.error("Failed to remove item: {}", itemToRemove);
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

    public Item findItemInCart(int itemId, Cart cart) {
        for (Item cartItem : cart.getItems()) {
            if (cartItem.getItemId() == itemId) {
                return cartItem;
            }
        }
        return null;
    }
}
