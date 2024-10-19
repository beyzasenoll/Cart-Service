package com.ecommerce.application.domain.item;

import com.ecommerce.application.exception.item.ItemException;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
public abstract class Item {
    private static final Integer VAS_ITEM_SELLER_ID = 5003;


    private double price;
    private int quantity;
    private int itemId;
    private int categoryId;
    private int sellerId;
    private final Logger logger = LoggerFactory.getLogger(Item.class);

    public Item(int itemId, int categoryId, int sellerId, double price, int quantity) {
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
        this.price = price;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    public abstract boolean isValidQuantity(int quantity);

    public boolean updateExistingItemQuantity(Item item, Item existingItem) {

        if (hasDifferentAttributes(existingItem, item)) {
            logger.error("Cannot add item with the same itemId but different attributes. ItemId: {}", item.getItemId());
            throw new ItemException.ItemAttributeMismatchException("Cannot add item with the same itemId but different attributes. ItemId: " + item.getItemId());
        }
        int newQuantity = existingItem.getQuantity() + item.getQuantity();
        if (!existingItem.isValidQuantity(newQuantity)) {
            logger.error("Cannot update item: quantity exceeds maximum limit. New quantity: {}", newQuantity);
            throw new ItemException.ItemQuantityExceededException("Cannot update item: quantity exceeds maximum limit. New quantity: " + newQuantity);
        }
        existingItem.setQuantity(newQuantity);
        item = existingItem;
        logger.info("Quantity updated for item: {}", existingItem);
        return true;
    }
    public boolean hasDifferentAttributes(Item existingItem, Item newItem) {
        return existingItem.getCategoryId() != newItem.getCategoryId() ||
                existingItem.getSellerId() != newItem.getSellerId() ||
                Double.compare(existingItem.getPrice(), newItem.getPrice()) != 0;
    }
    public boolean validateItem(Item validateItem) {
        if (validateItem.getSellerId() == VAS_ITEM_SELLER_ID) {
            logger.error("Item cannot be added from seller with ID 5003");
            throw new ItemException.ItemValidationException("Item cannot be added from seller with ID 5003: " + validateItem);
        }
        return true;
    }

}

