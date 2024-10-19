package com.ecommerce.application.controller;

import com.ecommerce.application.dto.CartDisplayDto;
import com.ecommerce.application.dto.item.ItemRequestDto;
import com.ecommerce.application.service.CartService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CartControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private MockMvc mockMvc;
    @Mock
    private CartService cartService;
    @InjectMocks
    private CartController cartController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    @Test
    void addItemTest() throws Exception {
        // Given
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        when(cartService.addItemToCart(any(ItemRequestDto.class))).thenReturn(true);

        // When & Then
        mockMvc.perform(post("/cart/addItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.message").value("Item added successfully."));

        verify(cartService, times(1)).addItemToCart(any(ItemRequestDto.class));
    }

    @Test
    void addVasItemToItemTest() throws Exception {
        // Given
        ItemRequestDto vasItemRequestDto = new ItemRequestDto(/* params */);
        when(cartService.addVasItemToItem(any(ItemRequestDto.class))).thenReturn(true);

        // When & Then
        mockMvc.perform(post("/cart/addVasItemToItem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vasItemRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.message").value("VAS item added successfully."));

        verify(cartService, times(1)).addVasItemToItem(any(ItemRequestDto.class));
    }

    @Test
    void resetCartTest() throws Exception {
        // Given
        when(cartService.resetCart()).thenReturn(true);

        // When & Then
        mockMvc.perform(post("/cart/resetCart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.message").value("Cart reset successfully."));

        verify(cartService, times(1)).resetCart();
    }

    @Test
    void removeItemFromCartTest() throws Exception {
        // Given
        int itemId = 1;
        when(cartService.removeItemFromCart(itemId)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/cart/removeItem/{itemId}", itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.message").value("Item removed successfully."));

        verify(cartService, times(1)).removeItemFromCart(itemId);
    }

    @Test
    void displayCartTest() throws Exception {
        // Given
        CartDisplayDto displayDto = new CartDisplayDto(/* params */);


        when(cartService.displayCart()).thenReturn(displayDto);

        // When & Then
        mockMvc.perform(get("/cart/displayCart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(true))
                .andExpect(jsonPath("$.message").isNotEmpty());

        verify(cartService, times(1)).displayCart();
    }
}

//TODO: how to add integration tests ? hiçbir şey mocklanmaz.
