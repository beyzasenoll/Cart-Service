package com.ecommerce.application.domain.item;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DefaultItem extends Item {
    private static final int MAX_QUANTITY = 10;
    private final List<VasItem> vasItems = new ArrayList<>();

    public DefaultItem(int itemId, int categoryId, int sellerId, double price, int quantity) {
        super(itemId, categoryId, sellerId, price, quantity);
    }

    @Override
    public double getTotalPrice() {
        return getPrice() * getQuantity();
    }

    @Override
    public boolean isValidQuantity(int quantity) {
        return 0 < quantity && quantity <= MAX_QUANTITY;
    }

    public boolean canAddVasItem() {
        return (getCategoryId() == 1001 || getCategoryId() == 3004); // Mobilya veya Elektronik
    }

    public boolean addOrUpdateVasItem(VasItem newVasItem) {
        for (VasItem vasItem : vasItems) {
            if (vasItem.getItemId() == newVasItem.getItemId()) {
                int newQuantity = vasItem.getQuantity() + newVasItem.getQuantity();
                if (!vasItem.isValidQuantity(newQuantity)) {
                    return false;
                } else {
                    vasItem.setQuantity(newQuantity);
                    return true;
                }
            }
        }
        return vasItems.add(newVasItem);
    }
}