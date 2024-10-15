package com.ecommerce.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ecommerce.application.domain.cart.Cart;
import com.ecommerce.application.domain.item.DefaultItem;
import com.ecommerce.application.domain.item.VasItem;
import com.ecommerce.application.dto.vasItem.VasItemRequestDto;
import com.ecommerce.application.service.impl.VasItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VasItemServiceTest {

    @InjectMocks
    private VasItemServiceImpl vasItemService;

    @Mock
    private Cart cart;

    @Mock
    private DefaultItem parentItem;

    private VasItemRequestDto vasItemRequestDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        vasItemRequestDto = new VasItemRequestDto();
        vasItemRequestDto.setSellerId(5003);
        vasItemRequestDto.setItemId(1);
    }

    @Test
    public void whenValidAddVasItem_thenReturnTrue() {
        // Given
        VasItem vasItem = new VasItem(2, 300, 1);
        when(cart.getItems()).thenReturn(Collections.singletonList(parentItem));
        when(parentItem.canAddVasItem()).thenReturn(true);
        when(parentItem.addOrUpdateVasItem(any(VasItem.class))).thenReturn(true);

        vasItemRequestDto = VasItemRequestDto.builder()
                .itemId(parentItem.getItemId()) // Match parent item's ID
                .sellerId(5003) // Ensure this is valid
                .build();

        // When
        boolean result = vasItemService.addVasItemToItem(vasItemRequestDto);

        // Then
        assertTrue(result, "Expected to add VAS item successfully");
        verify(cart).calculateTotalPrice();
        verify(parentItem).addOrUpdateVasItem(any(VasItem.class));
    }


    @Test
    public void whenInvalidSellerId_thenThrowException() {
        // Given
        vasItemRequestDto.setSellerId(5004); // Invalid seller ID

        // When / Then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            vasItemService.addVasItemToItem(vasItemRequestDto);
        });

        assertEquals("VasItem seller id should be 5003.", exception.getMessage());
    }

    @Test
    public void whenParentItemNotFound_thenReturnFalse() {
        // Given
        when(cart.getItems()).thenReturn(new ArrayList<>()); // No items in the cart

        // When
        boolean result = vasItemService.addVasItemToItem(vasItemRequestDto);

        // Then
        assertFalse(result);
        verify(cart, never()).calculateTotalPrice(); // Verify that total price was not recalculated
    }

    @Test
    public void whenParentItemCannotAddVasItem_thenReturnFalse() {
        // Given
        when(cart.getItems()).thenReturn(List.of(parentItem)); // Return the parent item in the cart
        when(parentItem.canAddVasItem()).thenReturn(false); // The parent item cannot add a VAS item

        // When
        boolean result = vasItemService.addVasItemToItem(vasItemRequestDto);

        // Then
        assertFalse(result);
        verify(cart, never()).calculateTotalPrice(); // Verify that total price was not recalculated
    }
}
