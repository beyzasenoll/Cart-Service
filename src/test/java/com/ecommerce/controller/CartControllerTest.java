package com.ecommerce.controller;

import com.ecommerce.application.controller.CartController;
import com.ecommerce.application.dto.CartDisplayDto;
import com.ecommerce.application.dto.ItemDto;
import com.ecommerce.application.dto.ResponseDto;
import com.ecommerce.application.dto.VasItemDto;
import com.ecommerce.application.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddItem() {
        ItemDto itemDto = new ItemDto(); // Initialize as necessary
        when(cartService.addItemToCart(any(ItemDto.class))).thenReturn(true);

        ResponseEntity<ResponseDto> response = cartController.addItem(itemDto);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isResult()); // Accessing the field directly
        assertEquals("Item added successfully.", response.getBody().getMessage());
    }

    @Test
    public void testAddVasItemToItem() {
        VasItemDto vasItemDto = new VasItemDto(); // Initialize as necessary
        when(cartService.addVasItemToItem(any(VasItemDto.class))).thenReturn(true);

        ResponseEntity<ResponseDto> response = cartController.addVasItemToItem(vasItemDto);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isResult()); // Accessing the field directly
        assertEquals("VAS item added successfully.", response.getBody().getMessage());
    }

    @Test
    public void testResetCart() {
        when(cartService.resetCart()).thenReturn(true);

        ResponseEntity<ResponseDto> response = cartController.resetCart();

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isResult()); // Accessing the field directly
        assertEquals("Cart reset successfully.", response.getBody().getMessage());
    }

    @Test
    public void testRemoveItemFromCart() {
        int itemId = 1;
        when(cartService.removeItemFromCart(itemId)).thenReturn(true);

        ResponseEntity<ResponseDto> response = cartController.removeItemFromCart(itemId);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isResult()); // Accessing the field directly
        assertEquals("Item removed successfully.", response.getBody().getMessage());
    }

    @Test
    public void testDisplayCart() {
        CartDisplayDto displayInfo = new CartDisplayDto(); // Initialize as necessary
        when(cartService.displayCart()).thenReturn(displayInfo);

        ResponseEntity<ResponseDto> response = cartController.displayCart();

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isResult());
        assertEquals(displayInfo, response.getBody().getMessage());
    }
}
