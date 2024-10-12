package com.ecommerce.application.domain.item;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DefaultItem extends Item {
    private static final int MAX_QUANTITY = 10;
    private List<VasItem> vasItems = new ArrayList<>();

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

    public boolean addVasItem(VasItem vasItem) {
        if (vasItem != null) {
            vasItems.add(vasItem);
            return true;
        }
        return false;
    }
}