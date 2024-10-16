package com.ecommerce.application.domain.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Item {

    public double price;
    public int quantity;
    private int itemId;
    private int categoryId;
    private int sellerId;

    public Item(int itemId, int categoryId, int sellerId, double price, int quantity) {
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
        this.price = price;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    public abstract boolean isValidQuantity(int quantity);
}

