package com.ecommerce.application.service;

import com.ecommerce.application.dto.ItemDto;

public interface CartService {


    void resetCart();

    void addItemToCart(ItemDto itemdto);
    void removeItemFromCart(int itemId);
    void displayCartItems();
    int getCartItemCount();
    double getDiscountedPrice();
    double getDiscount();
}

