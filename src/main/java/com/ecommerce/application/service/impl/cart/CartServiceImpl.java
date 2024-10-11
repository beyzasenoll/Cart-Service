package com.ecommerce.application.service.impl.cart;

import com.ecommerce.application.dto.CartDisplayDto;
import com.ecommerce.application.dto.ItemDto;
import com.ecommerce.application.dto.VasItemDto;
import com.ecommerce.application.service.CartService;
import com.ecommerce.application.service.VasItemService;
import com.ecommerce.application.service.impl.vasItem.VasItemManager;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private final CartManager cartManager;
    private final VasItemService vasItemService;

    public CartServiceImpl(CartManager cartManager, VasItemManager vasItemManager, VasItemService vasItemService) {
        this.cartManager = cartManager;
        this.vasItemService = vasItemService;
    }

    @Override
    public boolean addItemToCart(ItemDto itemDto) {
        return cartManager.addItemToCart(itemDto);
    }

    @Override
    public boolean addVasItemToItem(VasItemDto vasItemDto) {
        return vasItemService.addVasItemToItem(vasItemDto);
    }

    @Override
    public boolean resetCart() {
        return cartManager.resetCart();
    }

    @Override
    public CartDisplayDto displayCart() {
        return cartManager.displayCart();
    }

    @Override
    public boolean removeItemFromCart(int itemId) {
        return cartManager.removeItemFromCart(itemId);
    }
}


