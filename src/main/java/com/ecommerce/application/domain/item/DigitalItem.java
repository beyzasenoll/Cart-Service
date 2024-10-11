package com.ecommerce.application.domain.item;

public class DigitalItem extends Item {
    public DigitalItem(int itemId, int categoryId, int sellerId, double price, int quantity) {
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public boolean canAddVasItem(VasItem vasItem) {
        return false; // DigitalItem'a VAS item eklenemez.
    }

    @Override
    public double getTotalPrice() {
        return price * quantity;
    }

    @Override
    public boolean isValidQuantity(int quantity) {
        return quantity <= 5; // Digital item maksimum 5 adet olabilir.
    }
}

