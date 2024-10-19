package com.ecommerce.application.service.impl;

import com.ecommerce.application.domain.cart.Cart;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.dto.CartDisplayDto;
import com.ecommerce.application.dto.item.ItemRequestDto;
import com.ecommerce.application.dto.item.ItemResponseDto;
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

    private final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
    private final Cart cart;
    private final ItemMapper itemMapper;
    private final PromotionService promotionService;
    private final VasItemService vasItemService;

    public CartServiceImpl(Cart cart,
                           ItemMapper itemMapper, PromotionService promotionService, VasItemService vasItemService) {
        this.cart = cart;
        this.itemMapper = itemMapper;
        this.promotionService = promotionService;
        this.vasItemService = vasItemService;
    }


    @Override
    public boolean addItemToCart(ItemRequestDto itemRequestDto) {
        logger.info("Adding item to cart: {}", itemRequestDto);
        Item item = itemMapper.updateItemFromDto(itemRequestDto);
        item.validateItem(item);
        boolean isAdded = isAdded(item);
        return isAdded;
        }

    private boolean isAdded(Item item) {
        boolean isAdded = cart.addItem(item);
        if (isAdded) {
            logger.info("Item added to cart: {}", item);
            cart.isCartValid(item, cart);
            double discount = promotionService.findDiscount(cart);
            cart.applyDiscount(discount);
        } else {
            logger.error("Failed to add item: {}", item);
        }
        return isAdded;
    }
    @Override
    public boolean addVasItemToItem(ItemRequestDto vasItemRequestDto) {
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

        double bestDiscount = promotionService.findDiscount(cart);
        int bestPromotionId = promotionService.getBestPromotionId(cart);
        double totalPrice = cart.getTotalPrice();
        return new CartDisplayDto(itemResponseDtoList, totalPrice, bestDiscount, bestPromotionId);
    }

    @Override
    public boolean removeItemFromCart(int itemId) {
        return cart.removeItem(itemId);
    }

}


