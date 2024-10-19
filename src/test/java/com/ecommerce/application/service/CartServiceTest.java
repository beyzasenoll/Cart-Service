package com.ecommerce.application.service;

import com.ecommerce.application.domain.cart.Cart;
import com.ecommerce.application.domain.item.DigitalItem;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.dto.CartDisplayDto;
import com.ecommerce.application.dto.item.ItemRequestDto;
import com.ecommerce.application.dto.item.ItemResponseDto;
import com.ecommerce.application.mapper.ItemMapper;
import com.ecommerce.application.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private Cart cart;

    @Mock
    private ItemMapper itemMapper;

    @Mock
    private PromotionService promotionService;


    @Mock
    private VasItemService vasItemService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenValidItem_whenAddItemToCart_thenItemAddedSuccessfully() {
        // Given
        //ItemRequestDto itemRequestDto = new ItemRequestDto();

        ItemRequestDto itemRequestDto = ItemRequestDto.builder()
                .itemId(1)
                .categoryId(7889) // Category for DigitalItem
                .sellerId(101)
                .price(200.0)
                .quantity(2)
                .build();

        Item item = new DigitalItem(1, 101, 200.0, 2);

        when(itemMapper.updateItemFromDto(itemRequestDto)).thenReturn(item);
        when(item.validateItem(item)).thenReturn(true);
        when(cart.findItemInCart(item.getItemId(), cart.getItems())).thenReturn(null);
        when(cart.addItem(item)).thenReturn(true);
        when(promotionService.findDiscount(cart)).thenReturn(10.0);

        // When
        boolean result = cartService.addItemToCart(itemRequestDto);

        // Then
        assertTrue(result);
        verify(cart, times(1)).addItem(item);
        verify(promotionService, times(1)).findDiscount(cart);
    }

    @Test
    public void givenInvalidQuantity_whenAddItemToCart_thenItemNotAdded() {
        // Given
        ItemRequestDto itemRequestDto = ItemRequestDto.builder()
                .itemId(1)
                .categoryId(7889)
                .sellerId(101)
                .price(200.0)
                .quantity(50) // Invalid quantity
                .build();

        Item item = new DigitalItem(1, 101, 200.0, 50);

        when(itemMapper.updateItemFromDto(itemRequestDto)).thenReturn(item);
        when(item.validateItem(item)).thenReturn(false);

        // When
        boolean result = cartService.addItemToCart(itemRequestDto);

        // Then
        assertFalse(result);
        verify(cart, never()).addItem(item);
        verify(promotionService, never()).findDiscount(cart);
    }

    @Test
    public void givenVasItem_whenAddVasItemToItem_thenVasItemAddedSuccessfully() {
        // Given
        ItemRequestDto vasItemRequestDto = ItemRequestDto.builder()
                .itemId(1)
                .vasItemId(101)
                .categoryId(3242)
                .sellerId(202)
                .price(50.0)
                .quantity(1)
                .build();

        when(vasItemService.addVasItemToItem(vasItemRequestDto)).thenReturn(true);

        // When
        boolean result = cartService.addVasItemToItem(vasItemRequestDto);

        // Then
        assertTrue(result);
        verify(vasItemService, times(1)).addVasItemToItem(vasItemRequestDto);
    }

    @Test
    public void givenCartWithItems_whenDisplayCart_thenReturnCartDetails() {
        //TODO: "should return cart details when display cart"
        // Given
        List<ItemResponseDto> items = new ArrayList<>();
        items.add(ItemResponseDto.builder()
                .itemId(1)
                .categoryId(7889)
                .sellerId(101)
                .price(200.0)
                .quantity(2)
                .build());

        when(cart.getItems()).thenReturn(new ArrayList<>());
        when(promotionService.findDiscount(cart)).thenReturn(20.0);
        when(promotionService.getBestPromotionId(cart)).thenReturn(1);
        when(cart.getTotalPrice()).thenReturn(400.0);

        // When
        CartDisplayDto cartDisplayDto = cartService.displayCart();

        // Then
        assertNotNull(cartDisplayDto);
        assertEquals(400.0, cartDisplayDto.getTotalPrice());
        assertEquals(20.0, cartDisplayDto.getTotalDiscount());
        assertEquals(1, cartDisplayDto.getAppliedPromotionId());
    }

    @Test
    public void givenItemInCart_whenRemoveItemFromCart_thenItemRemovedSuccessfully() {
        // Given
        int itemId = 1;
        when(cart.removeItem(itemId)).thenReturn(true);

        // When
        boolean result = cartService.removeItemFromCart(itemId);

        // Then
        assertTrue(result);
        verify(cart, times(1)).removeItem(itemId);
    }

    @Test
    public void whenResetCart_thenCartClearedSuccessfully() {
        // Given
        when(cart.clearCart()).thenReturn(true);

        // When
        boolean result = cartService.resetCart();

        // Then
        assertTrue(result);
        verify(cart, times(1)).clearCart();
    }
}
