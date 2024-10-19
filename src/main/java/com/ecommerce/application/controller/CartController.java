package com.ecommerce.application.controller;

import com.ecommerce.application.dto.CartDisplayDto;
import com.ecommerce.application.dto.ResponseDto;
import com.ecommerce.application.dto.item.ItemRequestDto;
import com.ecommerce.application.service.CartService;
import com.ecommerce.application.utils.ValidationUtil;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final Logger logger = LoggerFactory.getLogger(CartController.class);

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/addItem")
    public ResponseEntity<ResponseDto> addItem(@Valid @RequestBody ItemRequestDto itemRequestDto, BindingResult bindingResult) {
        logger.info("Attempting to add item: {}", itemRequestDto);
        ResponseEntity<ResponseDto> errorMessage = ValidationUtil.getResponseDtoResponseEntity(bindingResult);
        if (errorMessage != null) return errorMessage;

        boolean result = cartService.addItemToCart(itemRequestDto);
        String message = result ? "Item added successfully." : "Failed to add item.";
        logger.info("Add item result: {}", message);

        return ResponseEntity.ok(new ResponseDto(result, message));
    }



    @PostMapping("/addVasItemToItem")
    public ResponseEntity<ResponseDto> addVasItemToItem(@Valid @RequestBody ItemRequestDto itemRequestDto, BindingResult bindingResult) {
        logger.info("Attempting to add VAS item: {}", itemRequestDto);

        ResponseEntity<ResponseDto> errorMessage = ValidationUtil.getResponseDtoResponseEntity(bindingResult);
        if (errorMessage != null) return errorMessage;

        boolean result = cartService.addVasItemToItem(itemRequestDto);
        String message = result ? "VAS item added successfully." : "Failed to add VAS item.";
        logger.info("Add VAS item result: {}", message);
        return ResponseEntity.ok(new ResponseDto(result, message));
    }


    @PostMapping("/resetCart")
    public ResponseEntity<ResponseDto> resetCart() {
        logger.info("Attempting to reset cart.");
        boolean result = cartService.resetCart();
        String message = result ? "Cart reset successfully." : "Failed to reset cart.";
        logger.info("Reset cart result: {}", message);
        return ResponseEntity.ok(new ResponseDto(result, message));
    }

    @DeleteMapping("/removeItem/{itemId}")
    public ResponseEntity<ResponseDto> removeItemFromCart(@PathVariable int itemId ) {
        logger.info("Attempting to remove item with ID: {}", itemId);
        boolean result = cartService.removeItemFromCart(itemId);
        String message = result ? "Item removed successfully." : "Failed to remove item.";
        logger.info("Remove item result: {}", message);
        return ResponseEntity.ok(new ResponseDto(result, message));
    }

    @GetMapping("/displayCart")
    public ResponseEntity<ResponseDto> displayCart() {
        logger.info("Attempting to display cart.");
        CartDisplayDto displayInfo = cartService.displayCart();
        logger.info("Display cart result: {}", displayInfo);
        return ResponseEntity.ok(new ResponseDto(true, displayInfo));
    }
}
