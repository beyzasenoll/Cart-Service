package com.ecommerce.application.domain.item;

public class VasItem extends Item {

    public VasItem(int itemId, int categoryId, int sellerId, double price, int quantity) {
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.sellerId = 5003;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public double getTotalPrice() {
        return price * quantity;
    }

    @Override
    public boolean isValidQuantity(int quantity) {
        return quantity <= 3; // VAS item maksimum 3 adet olabilir.
    }
}
