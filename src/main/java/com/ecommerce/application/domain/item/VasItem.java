package com.ecommerce.application.domain.item;

public class VasItem extends Item {

    private static final int MAX_QUANTITY = 3;
    private static final int DEFAULT_SELLER_ID = 5003;

    public VasItem(int itemId, int categoryId, double price, int quantity) {
        super(itemId, categoryId, DEFAULT_SELLER_ID, price, quantity);
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
