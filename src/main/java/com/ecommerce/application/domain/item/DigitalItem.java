package com.ecommerce.application.domain.item;

public class DigitalItem extends Item {

    private static final int MAX_QUANTITY = 5;
    private static final int DEFAULT_CATEGORY_ID = 7889;


    public DigitalItem(int itemId, int sellerId, double price, int quantity) {
        super(itemId, DEFAULT_CATEGORY_ID, sellerId, price, quantity);
    }

    @Override
    public double getTotalPrice() {
        return getPrice() * getQuantity();
    }

    @Override
    public boolean isValidQuantity(int quantity) {
        return quantity <= MAX_QUANTITY;
    }
}
