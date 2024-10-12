package com.ecommerce.application.service.impl.cart;

import com.ecommerce.application.dto.CartDisplayDto;
import com.ecommerce.application.dto.item.ItemRequestDto;
import com.ecommerce.application.dto.vasItem.VasItemRequestDto;
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
    public boolean addItemToCart(ItemRequestDto itemRequestDto) {
        return cartManager.addItemToCart(itemRequestDto);
    }

    @Override
    public boolean addVasItemToItem(VasItemRequestDto vasItemRequestDto) {
        return vasItemService.addVasItemToItem(vasItemRequestDto);
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


