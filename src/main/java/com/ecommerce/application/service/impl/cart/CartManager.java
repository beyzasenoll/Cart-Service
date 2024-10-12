package com.ecommerce.application.service.impl.cart;

import com.ecommerce.application.domain.cart.Cart;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.dto.CartDisplayDto;
import com.ecommerce.application.dto.item.ItemRequestDto;
import com.ecommerce.application.dto.item.ItemResponseDto;
import com.ecommerce.application.mapper.ItemMapper;
import com.ecommerce.application.service.PromotionService;
import com.ecommerce.application.service.impl.validator.CartValidator;
import com.ecommerce.application.service.impl.validator.ItemValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartManager {

    private final Logger logger = LoggerFactory.getLogger(CartManager.class);
    private final Cart cart;
    private final CartValidator cartValidator;
    private final ItemValidator itemValidator;
    private final ItemMapper itemMapper;
    private final PromotionService promotionService;
    private final ItemManager itemManager;

    public CartManager(Cart cart, CartValidator cartValidator, ItemValidator itemValidator,
                       ItemMapper itemMapper, PromotionService promotionService, ItemManager itemManager) {
        this.cart = cart;
        this.cartValidator = cartValidator;
        this.itemValidator = itemValidator;
        this.itemMapper = itemMapper;
        this.promotionService = promotionService;
        this.itemManager = itemManager;
    }

    public boolean addItemToCart(ItemRequestDto itemRequestDto) {
        logger.info("Adding item to cart: {}", itemRequestDto);
        Item item = itemMapper.updateItemFromDto(itemRequestDto);

        if (!itemManager.isItemAddable(itemRequestDto, item)) {
            return false;
        }

        if (!itemManager.updateExistingItemQuantity(item, cart)) {
            return false;
        }

        if (!cartValidator.isCartValid(item, cart)) {
            logger.error("Cart validation failed for item: {}", item);
            return false;
        }

        boolean isAdded = cart.addItem(item);
        if (isAdded) {
            logger.info("Item added to cart: {}", item);
            double discount=promotionService.applyBestPromotion(cart);
            cart.applyDiscount(discount);
        } else {
            logger.error("Failed to add item: {}", item);
        }

        return isAdded;
    }

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

    public CartDisplayDto displayCart() {
       List<ItemResponseDto> itemResponseDtoList = cart.getItems().stream()
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());

        double bestDiscount = promotionService.applyBestPromotion(cart);
        int bestPromotionId = promotionService.getBestPromotionId(cart);

        return new CartDisplayDto(itemResponseDtoList, cart.getTotalPrice(), bestDiscount, bestPromotionId);
    }

    public boolean resetCart() {
        boolean isCleared = cart.clearCart();
        logger.info(isCleared ? "Cart reset successfully." : "Failed to reset cart.");
        return isCleared;
    }

}
