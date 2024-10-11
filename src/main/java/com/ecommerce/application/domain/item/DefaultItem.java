package com.ecommerce.application.domain.item;

public class DefaultItem extends Item {

    public DefaultItem(int itemId, int categoryId, int sellerId, double price, int quantity) {
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public boolean canAddVasItem(VasItem vasItem) {
        return categoryId == 1001 || categoryId == 3004; // Sadece Mobilya veya Elektronik kategorisine VAS item eklenebilir.
    }

    @Override
    public double getTotalPrice() {
        return price * quantity;
    }

    @Override
    public boolean isValidQuantity(int quantity) {
        return quantity <= 10; // Default item maksimum 10 adet olabilir.
    }
}
