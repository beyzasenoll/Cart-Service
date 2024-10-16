package com.ecommerce.application.domain.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DigitalItemTest {

    private DigitalItem digitalItem;

    @BeforeEach
    public void setUp() {
        digitalItem = new DigitalItem(2, 345, 1000, 3);  // DigitalItem örneği oluşturuluyor
    }

    @Test
    public void givenDigitalItem_whenGetTotalPrice_thenReturnsCorrectTotalPrice() {
        // When
        double totalPrice = digitalItem.getTotalPrice();

        // Then
        assertEquals(3000, totalPrice);
    }

    @Test
    public void givenValidQuantity_whenIsValidQuantity_thenReturnsTrue() {
        // Given
        int validQuantity = 5;

        // When
        boolean isValid = digitalItem.isValidQuantity(validQuantity);

        // Then
        assertTrue(isValid);
    }

    @Test
    public void givenInvalidQuantity_whenIsValidQuantity_thenReturnsFalse() {
        // Given
        int invalidQuantity = 6;

        // When
        boolean isValid = digitalItem.isValidQuantity(invalidQuantity);

        // Then
        assertFalse(isValid);
    }
}
