package com.ecommerce.application.domain.cart;

import com.ecommerce.application.domain.item.Item;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class Cart {
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
        boolean removed = items.removeIf(item -> item.getItemId() == itemId);
        if (removed) {
            calculateTotalPrice();
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

    private void calculateTotalPrice() {
        totalPrice = items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
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
}
