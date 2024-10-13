package com.ecommerce.application.service.impl;

import com.ecommerce.application.domain.cart.Cart;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.dto.CartDisplayDto;
import com.ecommerce.application.dto.item.ItemRequestDto;
import com.ecommerce.application.dto.item.ItemResponseDto;
import com.ecommerce.application.dto.vasItem.VasItemRequestDto;
import com.ecommerce.application.mapper.ItemMapper;
import com.ecommerce.application.service.CartService;
import com.ecommerce.application.service.PromotionService;
import com.ecommerce.application.service.VasItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private static final int MAX_UNIQUE_ITEMS = 10;
    private static final int MAX_TOTAL_ITEMS = 30;
    private static final double MAX_TOTAL_PRICE = 500000;
    private final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
    private final Cart cart;
    private final ItemMapper itemMapper;
    private final PromotionService promotionService;
    private final ItemServiceImpl itemServiceImpl;
    private final VasItemService vasItemService;

    public CartServiceImpl(Cart cart,
                           ItemMapper itemMapper, PromotionService promotionService, ItemServiceImpl itemServiceImpl, VasItemService vasItemService) {
        this.cart = cart;
        this.itemMapper = itemMapper;
        this.promotionService = promotionService;
        this.itemServiceImpl = itemServiceImpl;
        this.vasItemService = vasItemService;
    }


    @Override
    public boolean addItemToCart(ItemRequestDto itemRequestDto) {
        logger.info("Adding item to cart: {}", itemRequestDto);
        Item item = itemMapper.updateItemFromDto(itemRequestDto);

        if (itemServiceImpl.updateExistingItemQuantity(item, cart)) {
            logger.info("Quantity updated for item in cart: {}", item);
            return true;
        } else {
            if (!itemServiceImpl.isItemAddable(itemRequestDto, item)) {
                return false;
            }

            Item existingItem = cart.findItemInCart(item.getItemId(), cart);
            if (existingItem != null) {
                int newQuantity = existingItem.getQuantity() + item.getQuantity();

                if (!existingItem.isValidQuantity(newQuantity)) {
                    logger.error("Cannot add item: quantity exceeds maximum limit. New quantity: {}", newQuantity);
                    return false;
                }
            }
            boolean isAdded = cart.addItem(item);
            if (isAdded) {
                logger.info("Item added to cart: {}", item);
                double discount = promotionService.applyBestPromotion(cart);
                cart.applyDiscount(discount);
            } else {
                logger.error("Failed to add item: {}", item);
            }

            return isAdded;
        }
    }

    @Override
    public boolean addVasItemToItem(VasItemRequestDto vasItemRequestDto) {
        return vasItemService.addVasItemToItem(vasItemRequestDto);
    }

    @Override
    public boolean resetCart() {
        boolean isCleared = cart.clearCart();
        logger.info(isCleared ? "Cart reset successfully." : "Failed to reset cart.");
        return isCleared;
    }

    @Override
    public CartDisplayDto displayCart() {
        List<ItemResponseDto> itemResponseDtoList = cart.getItems().stream()
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());

        double bestDiscount = promotionService.applyBestPromotion(cart);
        int bestPromotionId = promotionService.getBestPromotionId(cart);

        return new CartDisplayDto(itemResponseDtoList, cart.getTotalPrice(), bestDiscount, bestPromotionId);
    }

    @Override
    public boolean removeItemFromCart(int itemId) {
        Item itemToRemove = cart.findItemInCart(itemId, cart);
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

    public boolean isCartValid(Item item, Cart cart) {
        logger.info("Validating cart for item: {}", item);

        int totalQuantity = cart.calculateTotalQuantity();
        double totalPrice = cart.getTotalPrice();
        int uniqueItemCount = cart.calculateUniqueItems(cart);

        if (uniqueItemCount >= MAX_UNIQUE_ITEMS) {
            logger.error("Cart validation failed: too many unique items.");
            return false;
        }

        if (totalQuantity + item.getQuantity() > MAX_TOTAL_ITEMS) {
            logger.error("Cart validation failed: total item count exceeds limit.");
            return false;
        }

        if (totalPrice + (item.getTotalPrice()) > MAX_TOTAL_PRICE) {
            logger.error("Cart validation failed: total price exceeds limit.");
            return false;
        }

        logger.info("Cart validation successful.");
        return true;
    }

}


