package com.ecommerce.application.service.impl;

import com.ecommerce.application.domain.Cart;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.domain.promotion.Promotion;
import com.ecommerce.application.dto.ItemDto;
import com.ecommerce.application.mapper.ItemMapper;
import com.ecommerce.application.service.CartService;
import com.ecommerce.application.service.PromotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private final Cart cart;
    private List<Promotion> promotions;
    ItemMapper itemMapper;

    public CartServiceImpl(Cart cart, PromotionService promotionService, List<Promotion> promotions) {
        this.cart = cart;
        this.promotions = promotions;
    }

    @Override
    public boolean resetCart() {
        boolean isCleared = cart.clearCart();
        if (isCleared) {
            logger.info("Cart reset successfully.");
        } else {
            logger.error("Failed to reset cart");
        }
        return isCleared;
    }
    public boolean addItemToCart(ItemDto itemDto) {
        Item item = itemMapper.updateItemFromDto(itemDto);

        boolean isAdded = cart.addItem(item);
        if (isAdded) {
            logger.info("Item added to cart: {}", item);
        } else {
            logger.error("Failed to add item: {}", item);
        }
        return isAdded;
    }

    @Override
    public boolean removeItemFromCart(int itemId) {
        boolean isRemove=cart.removeItem(itemId);
        if (isRemove) {
            logger.info("Item removed from cart");
        } else {
            logger.info("Failed to remove item");
        }
        return isRemove;
    }

    @Override
    public void displayCart() {
        cart.getItems().forEach((item) -> {
            System.out.printf("Item ID: %d, Price: %.2f\n",
                    item.getItemId(), item.getTotalPrice());
        });
    }

    @Override
    public int getCartItemCount() {
        return cart.getItemCount();
    }


    @Override
    public double getDiscountedPrice() {
        double totalPrice = cart.getTotalPrice();
        double discount = getDiscount();
        double finalPrice = totalPrice - discount;
        return finalPrice;
    }

    @Override
    public double getDiscount() {
        double bestDiscount = 0;
        Promotion bestPromotion = null;

        for (Promotion promotion : promotions) {
            double discount = promotion.applyDiscount(cart);
            if (discount > bestDiscount) {
                bestDiscount = discount;
                bestPromotion = promotion;
            }
        }

        return bestDiscount; // Return the best discount found
    }
    }