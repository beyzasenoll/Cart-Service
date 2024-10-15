package com.ecommerce.application.domain.item;

import com.ecommerce.application.domain.item.VasItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VasItemTest {

    private VasItem vasItem;

    @BeforeEach
    public void setUp() {
        vasItem = new VasItem(3, 200, 3);
    }

    @Test
    public void givenVasItem_whenGetTotalPrice_thenReturnsCorrectTotalPrice() {


        // When
        double totalPrice = vasItem.getTotalPrice();

        // Then
        assertEquals(600, totalPrice);
    }

    @Test
    public void givenValidQuantity_whenIsValidQuantity_thenReturnsTrue() {
        // Given
        int validQuantity = 3;

        // When
        boolean isValid = vasItem.isValidQuantity(validQuantity);

        // Then
        assertTrue(isValid);
    }

    @Test
    public void givenInvalidQuantity_whenIsValidQuantity_thenReturnsFalse() {
        // Given
        int invalidQuantity = 5;

        // When
        boolean isValid = vasItem.isValidQuantity(invalidQuantity);

        // Then
        assertFalse(isValid);
    }
}
