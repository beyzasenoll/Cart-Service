package com.ecommerce.application.domain.item;

public class VasItem extends Item {

    private static final int MAX_QUANTITY = 3;
    private static final int DEFAULT_SELLER_ID = 5003;
    private static final int DEAFULT_CATEGORY_ID = 3442;


    public VasItem(int itemId, double price, int quantity) {
        super(itemId, DEAFULT_CATEGORY_ID, DEFAULT_SELLER_ID, price, quantity);
    }

    @Override
    public double getTotalPrice() {
        return getPrice() * getQuantity();
    }

    @Override
    public boolean isValidQuantity(int quantity) {
        return 0 < quantity && quantity <= MAX_QUANTITY;
    }
}
