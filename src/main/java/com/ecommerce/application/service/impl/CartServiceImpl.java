package com.ecommerce.application.service.impl;

import com.ecommerce.application.domain.Cart;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.domain.promotion.Promotion;
import com.ecommerce.application.dto.ItemDto;
import com.ecommerce.application.factory.impl.ItemFactoryImpl;
import com.ecommerce.application.service.CartService;
import com.ecommerce.application.service.PromotionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final Cart cart;
    private List<Promotion> promotions;

    public CartServiceImpl(Cart cart, PromotionService promotionService, List<Promotion> promotions) {
        this.cart = cart;
        this.promotions = promotions;
    }

    @Override
    public void resetCart() {
        cart.clearCart();
        System.out.println("Cart has been reset.");
    }

    @Override
    public void addItemToCart(ItemDto itemdto) {
        // todo : map ItemDto to Item

        ItemFactoryImpl factory = new ItemFactoryImpl();

        Item item = factory.createItem(
                itemdto.itemId,
                itemdto.categoryId,
                itemdto.sellerId,
                itemdto.price,
                itemdto.quantity
        );

        if (cart.addItem(item)) {
            System.out.println("Item added to cart.");
        } else {
            System.out.println("Failed to add item.");
        }
    }

    @Override
    public void removeItemFromCart(int itemId) {
        if (cart.removeItem(itemId)) {
            System.out.println("Item removed from cart.");
        } else {
            System.out.println("Item not found in cart.");
        }
    }

    @Override
    public void displayCartItems() {
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