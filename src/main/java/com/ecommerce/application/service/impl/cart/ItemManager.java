package com.ecommerce.application.service.impl.cart;

import com.ecommerce.application.domain.cart.Cart;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.dto.item.ItemRequestDto;
import com.ecommerce.application.service.impl.validator.CartValidator;
import com.ecommerce.application.service.impl.validator.ItemValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ItemManager {

    private final Logger logger = LoggerFactory.getLogger(ItemManager.class);
    private final CartValidator cartValidator;
    private final ItemValidator itemValidator;


    public ItemManager(CartValidator cartValidator, ItemValidator itemValidator) {
        this.cartValidator = cartValidator;
        this.itemValidator = itemValidator;
    }

    public boolean isItemAddable(ItemRequestDto itemRequestDto, Item item) {
        if (itemRequestDto.getSellerId() == 5003) {
            logger.error("Item cannot be added from seller with ID 5003: {}", itemRequestDto);
            return false;
        }

        if (!itemValidator.isValidItem(item)) {
            logger.error("Invalid item: {}", item);
            return false;
        }
        return true;
    }

    public boolean updateExistingItemQuantity(Item item, Cart cart) {
        Item existingItem = cartValidator.findItemInCart(item.getItemId(), cart);

        if (existingItem == null) {
            return true;
        }

        if (hasDifferentAttributes(existingItem, item)) {
            logger.error("Cannot add item with the same itemId but different attributes. ItemId: {}", item.getItemId());
            return false;
        }

        int newQuantity = existingItem.getQuantity() + item.getQuantity();
        if (!existingItem.isValidQuantity(newQuantity)) {
            logger.error("Cannot add more of the same item. Current quantity: {}", newQuantity);
            return false;
        }

        existingItem.setQuantity(newQuantity);
        logger.info("Quantity updated for item: {}", existingItem);
        return true;
    }

    private boolean hasDifferentAttributes(Item existingItem, Item newItem) {
        return existingItem.getCategoryId() != newItem.getCategoryId() ||
                existingItem.getSellerId() != newItem.getSellerId() ||
                Double.compare(existingItem.getPrice(), newItem.getPrice()) != 0;
    }

}
