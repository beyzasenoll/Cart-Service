package com.ecommerce.application.service;

import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.dto.CartDisplayDto;
import com.ecommerce.application.dto.ItemDto;
import com.ecommerce.application.dto.VasItemDto;

import java.util.List;

public interface CartService {


    boolean resetCart();

    boolean addItemToCart(ItemDto itemdto);
    boolean addVasItemToItem(VasItemDto vasItemDto);
    boolean removeItemFromCart(int itemId);
    CartDisplayDto displayCart();

}

