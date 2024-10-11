package com.ecommerce.application.controller;

import com.ecommerce.application.dto.ItemDto;
import com.ecommerce.application.mapper.ItemMapper;
import com.ecommerce.application.service.CartService;
import com.ecommerce.application.service.PromotionService;
import org.apache.catalina.mapper.Mapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final PromotionService promotionService;

    public CartController(CartService cartService, PromotionService promotionService) {
        this.cartService = cartService;

        this.promotionService = promotionService;
    }

    @PostMapping("/reset")
    public void resetCart() {
        cartService.resetCart();
    }

    @GetMapping("/total")
    public void displayCartTotal() {
        cartService.getDiscountedPrice();
    }

    @PostMapping("/addItem")
    public void addItemToCart(@RequestBody ItemDto itemDto) {
    cartService.addItemToCart(itemDto);
    }

    @DeleteMapping("/remove/{itemId}")
    public void removeItemFromCart(@PathVariable int itemId) {
        cartService.removeItemFromCart(itemId);
    }

    @PostMapping("/get-promotion")
    public double getBestPromotion() {
        return cartService.getDiscount();
    }

    @GetMapping("/items")
    public void displayCartItems() {
        cartService.displayCartItems();
    }

    @GetMapping("/count")
    public int getCartItemCount() {
        return cartService.getCartItemCount();
    }
}
