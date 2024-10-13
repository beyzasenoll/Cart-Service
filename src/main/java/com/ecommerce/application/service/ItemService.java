package com.ecommerce.application.service;

import com.ecommerce.application.domain.cart.Cart;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.dto.item.ItemRequestDto;

public interface ItemService {

    boolean isItemAddable(ItemRequestDto itemRequestDto, Item item);

    boolean updateExistingItemQuantity(Item item, Cart cart);

    boolean hasDifferentAttributes(Item existingItem, Item newItem);

    boolean isValidItem(Item item);
}
