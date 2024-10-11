package com.ecommerce.application.service;

import com.ecommerce.application.dto.ItemDto;

public interface CartService {


    boolean resetCart();

    boolean addItemToCart(ItemDto itemdto);
    boolean removeItemFromCart(int itemId);
    void displayCart();
    int getCartItemCount();
    double getDiscountedPrice();
    double getDiscount();
}

