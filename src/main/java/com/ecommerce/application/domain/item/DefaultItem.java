package com.ecommerce.application.domain.item;

public class DefaultItem extends Item {

    private static final int MAX_QUANTITY = 10;

    public DefaultItem(int itemId, int categoryId, int sellerId, double price, int quantity) {
        super(itemId, categoryId, sellerId, price, quantity);
    }

    @Override
    public double getTotalPrice() {
        return getPrice() * getQuantity();
    }

    @Override
    public boolean isValidQuantity(int quantity) {
        return quantity <= MAX_QUANTITY;
    }

    public boolean canAddVasItem() {
        return (getCategoryId() == 1001 || getCategoryId() == 3004); // Mobilya veya Elektronik
    }
}
