package com.ecommerce.application.service;

import com.ecommerce.application.dto.CartDisplayDto;
import com.ecommerce.application.dto.item.ItemRequestDto;
import com.ecommerce.application.dto.vasItem.VasItemRequestDto;

public interface CartService {


    boolean resetCart();

    boolean addItemToCart(ItemRequestDto itemRequestDto);
    boolean addVasItemToItem(VasItemRequestDto vasItemRequestDto);
    boolean removeItemFromCart(int itemId);
    CartDisplayDto displayCart();

}

