package com.ecommerce.application.service.impl;

import com.ecommerce.application.domain.Cart;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.dto.ItemDto;
import com.ecommerce.application.dto.VasItemDto;
import com.ecommerce.application.mapper.ItemMapper;
import com.ecommerce.application.service.CartService;
import com.ecommerce.application.service.VasItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private final Cart cart;
    private final CartValidator cartValidator;
    private final ItemValidator itemValidator;
    private final ItemMapper itemMapper;
    private final VasItemService vasItemService;
    public CartServiceImpl(Cart cart, CartValidator cartValidator, ItemValidator itemValidator, ItemMapper itemMapper, VasItemService vasItemService) {
        this.cart = cart;
        this.cartValidator = cartValidator;
        this.itemValidator = itemValidator;
        this.itemMapper = itemMapper;
        this.vasItemService = vasItemService;
    }

    @Override
    public boolean addItemToCart(ItemDto itemDto) {
        logger.info("Adding item to cart: {}", itemDto);
        Item item = itemMapper.updateItemFromDto(itemDto);

        if (itemDto.getSellerId() == 5003) {
            logger.error("Item cannot be added from seller with ID 5003: {}", itemDto);
            return false;
        }

        if (!itemValidator.isValidItem(item)) {
            logger.error("Invalid item: {}", item);
            return false;
        }

        if (updateExistingItemQuantity(item, cart)) {
            return true;
        }

        if (!cartValidator.isCartValid(item, cart)) {
            logger.error("Cart validation failed for item: {}", item);
            return false;
        }

        boolean isAdded = cart.addItem(item);
        if (isAdded) {
            logger.info("Item added to cart: {}", item);
        } else {
            logger.error("Failed to add item: {}", item);
        }

        return isAdded;
    }

    private boolean updateExistingItemQuantity(Item item, Cart cart) {
        Item existingItem = cartValidator.findExistingItemInCart(item, cart);

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + item.getQuantity();
            if (newQuantity > 10) {
                logger.error("Cannot add more than 10 of the same item. Current quantity: {}", newQuantity);
                return false;
            }
            existingItem.setQuantity(newQuantity);
            logger.info("Quantity updated for item: {}", existingItem);
            return true;
        }
        return false;
    }


    @Override
    public boolean addVasItemToItem(VasItemDto vasItemDto) {
        return vasItemService.addVasItemToItem(vasItemDto);
    }

    @Override
    public boolean resetCart() {
        boolean isCleared = cart.clearCart();
        if (isCleared) {
            logger.info("Cart reset successfully.");
        } else {
            logger.error("Failed to reset cart.");
        }
        return isCleared;
    }

    @Override
    public void displayCart() {
        cart.getItems().forEach(item -> System.out.printf("Item ID: %d, Price: %.2f\n", item.getItemId(), item.getTotalPrice()));
    }

    @Override
    public boolean removeItemFromCart(int itemId) {
        Item itemToRemove = cartValidator.findItemInCart(itemId, cart);
        if (itemToRemove == null) {
            logger.warn("Item with ID {} not found in cart.", itemId);
            return false;
        }

        boolean isRemoved = cart.removeItem(itemId);
        if (isRemoved) {
            logger.info("Item removed from cart: {}", itemToRemove);
        } else {
            logger.error("Failed to remove item: {}", itemToRemove);
        }

        return isRemoved;
    }
}
