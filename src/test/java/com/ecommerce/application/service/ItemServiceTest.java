package com.ecommerce.application.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.ecommerce.application.domain.cart.Cart;
import com.ecommerce.application.domain.item.DefaultItem;
import com.ecommerce.application.domain.item.DigitalItem;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.dto.item.ItemRequestDto;
import com.ecommerce.application.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ItemServiceTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private Cart cart;

    private ItemRequestDto itemRequestDto;
    private DefaultItem item; // Changed from @Mock to DefaultItem
    private DefaultItem existingItem; // Changed from @Mock to DefaultItem

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        itemRequestDto = ItemRequestDto.builder()
                .itemId(1)
                .categoryId(3242)
                .sellerId(5002)
                .price(100.0)
                .quantity(10)
                .build();

        item = new DefaultItem(itemRequestDto.getItemId(), itemRequestDto.getCategoryId(),
                itemRequestDto.getSellerId(), itemRequestDto.getPrice(),
                itemRequestDto.getQuantity());

        existingItem = new DefaultItem(itemRequestDto.getItemId(), itemRequestDto.getCategoryId(),
                itemRequestDto.getSellerId(), itemRequestDto.getPrice(),
                5);
    }

        @Test
        public void whenSellerNotAllowed_thenReturnFalse() {
            //Given
            itemRequestDto.setSellerId(5003);

            //When
            boolean result = itemService.isItemAddable(itemRequestDto, item);

            //Then
            assertFalse(result);
        }

        @Test
        public void whenItemInvalid_thenReturnFalse() {
            // Given
            itemRequestDto.setQuantity(11);

            item = new DefaultItem(itemRequestDto.getItemId(), itemRequestDto.getCategoryId(),
                    itemRequestDto.getSellerId(), itemRequestDto.getPrice(),
                    itemRequestDto.getQuantity());

            // When
            boolean result = itemService.isItemAddable(itemRequestDto, item);

            // Then
            assertFalse(result);
        }

        @Test
        public void whenItemNotFoundInCart_thenReturnFalse() {
            // Given
            when(cart.findItemInCart(item.getItemId(), cart)).thenReturn(null);

            // When
            boolean result = itemService.updateExistingItemQuantity(item, cart);

            // Then
            assertFalse(result);
        }

        @Test
        public void whenAttributesDifferent_thenReturnFalse() {
            // Given
            Item differentItem = new DefaultItem(1, 1234, 5002, 100.0, 10);
            when(cart.findItemInCart(differentItem.getItemId(), cart)).thenReturn(existingItem);

            // When
            boolean result = itemService.updateExistingItemQuantity(differentItem, cart);

            // Then
            assertFalse(result);
        }

        @Test
        public void whenValidUpdate_thenReturnTrue() {
            // Given
            when(cart.findItemInCart(item.getItemId(),cart)).thenReturn(existingItem);
            item.setQuantity(10);

            when(existingItem.isValidQuantity(existingItem.getQuantity() + item.getQuantity())).thenReturn(true);

            // When
            boolean result = itemService.updateExistingItemQuantity(item, cart);

            // Then
            assertTrue(result);
            assertEquals(15, existingItem.getQuantity());
        }

        @Test
        public void whenAttributesAreDifferent_thenReturnTrue() {
            // Given
            Item newItem = new DefaultItem(1, 3242, 5002, 100.0, 10);

            // When
            boolean result = itemService.hasDifferentAttributes(existingItem, newItem);

            // Then
            assertTrue(result);
        }

        @Test
        public void whenValidDigitalItem_thenReturnTrue() {
            // Given
            DigitalItem digitalItem = mock(DigitalItem.class);
            when(digitalItem.isValidQuantity(anyInt())).thenReturn(true);

            // When
            boolean result = itemService.isValidItem(digitalItem);

            // Then
            assertTrue(result);
        }

        @Test
        public void whenValidDefaultItem_thenReturnTrue() {
            // Given
            DefaultItem defaultItem = mock(DefaultItem.class);
            when(defaultItem.isValidQuantity(anyInt())).thenReturn(true);

            // When
            boolean result = itemService.isValidItem(defaultItem);

            // Then
            assertTrue(result);
        }

        @Test
        public void whenInvalidItemType_thenReturnFalse() {
            // Given
            Object notAnItem = new Object();

            // When
            boolean result = itemService.isValidItem((Item) notAnItem);

            // Then
            assertFalse(result);
        }
    }
