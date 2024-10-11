package com.ecommerce.application.controller;

import com.ecommerce.application.dto.ItemDto;
import com.ecommerce.application.dto.ResponseDto;
import com.ecommerce.application.service.CartService;
import com.ecommerce.application.service.PromotionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService, PromotionService promotionService) {
        this.cartService = cartService;
    }

    @PostMapping("/resetCart")
    public ResponseEntity<ResponseDto> resetCart() {
        boolean result = cartService.resetCart();
        String message = result ? "Cart reset successfully." : "Failed to reset cart.";
        return ResponseEntity.ok(new ResponseDto(result, message));
    }


    @PostMapping("/addItem")
    public ResponseEntity<ResponseDto> addItem(@RequestBody ItemDto itemDto) {
        boolean result = cartService.addItemToCart(itemDto);
        String message = result ? "Item added successfully." : "Failed to add item.";
        return ResponseEntity.ok(new ResponseDto(result, message));
    }

    @DeleteMapping("/removeItem/{itemId}")
    public ResponseEntity<ResponseDto> removeItemFromCart(@PathVariable int itemId) {
        boolean result = cartService.removeItemFromCart(itemId);
        String message = result ? "Item removed successfully." : "Failed to remove item.";
        return ResponseEntity.ok(new ResponseDto(result, message));
    }

   /*  @GetMapping("/displayCart")
    public ResponseEntity<ResponseDto> displayCart() {
        CartDisplayDto displayInfo = cartService.displayCart();
        return ResponseEntity.ok(new ResponseDto(true, displayInfo));
    } */
}
