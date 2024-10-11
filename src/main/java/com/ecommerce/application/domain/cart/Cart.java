package com.ecommerce.application.domain.cart;

import com.ecommerce.application.domain.item.Item;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Cart {
    private final List< Item> items;

    public Cart(List<Item> items) {
        this.items = items;
    };

    public boolean addItem(Item item) {
        if (item != null) {
            items.add(item);
            return true;
        }
        return false;
    }

    public boolean removeItem(int itemId) {
        return items.remove(itemId) != null;
    }

    public double getTotalPrice() {
       double totalPrice=0;
          for (Item item : items) {
            totalPrice+=item.getTotalPrice();
        }
       return totalPrice;
    }

    public List <Item> getItems() {
        return items; // Return a copy for protection
    }

    public boolean clearCart() {
        items.clear();
        return true;
    }


    public int getItemCount() {
        return items.size();
    }

    public boolean hasSameSellerItems() {
        if (items.isEmpty()) {
            return false;
        }

        String firstSeller = null;
        for (Item item : items) {
            if (firstSeller == null) {
                firstSeller = String.valueOf(item.getSellerId());
            } else if (!firstSeller.equals(item.getSellerId())) {
                return false;
            }
        }
        return true;
    }
}
