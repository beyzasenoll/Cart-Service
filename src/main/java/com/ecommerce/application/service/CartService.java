package com.ecommerce.application.service;

import com.ecommerce.application.dto.CartDisplayDto;
import com.ecommerce.application.dto.item.ItemRequestDto;

public interface CartService {


    boolean resetCart();

    boolean addItemToCart(ItemRequestDto itemRequestDto);

    boolean addVasItemToItem(ItemRequestDto itemRequestDto);

    boolean removeItemFromCart(int itemId);

    CartDisplayDto displayCart();

}

