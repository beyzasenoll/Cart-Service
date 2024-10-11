package com.ecommerce.application.domain.item;

public class VasItem extends Item {


    public VasItem(int itemId, int categoryId, int sellerId, double price, int quantity) {
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public boolean canAddVasItem(VasItem vasItem) {
        return false; // VAS Item başka VAS Item'a eklenemez.
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
