package com.ecommerce.application.domain.item;

import java.util.List;

public abstract class Item {

    public int itemId;
    public int categoryId;
    public int sellerId;
    public double price;
    public int quantity;
    public int getItemId() {
        return itemId;
    }


    public int getCategoryId() {
        return categoryId;
    }


    public int getSellerId() {
        return sellerId;
    }


    public double getPrice() {
        return price;
    }


    public int getQuantity() {
        return quantity;
    }

    public abstract double getTotalPrice();

    abstract boolean isValidQuantity(int quantity);
}

