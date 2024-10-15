package com.ecommerce.application.domain.item;

import com.ecommerce.application.domain.item.DefaultItem;
import com.ecommerce.application.domain.item.VasItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultItemTest {

    private DefaultItem defaultItem;
    private VasItem vasItem1;
    private VasItem vasItem2;

    @BeforeEach
    public void setUp() {
        defaultItem = new DefaultItem(1, 1001, 5001, 50.0, 5);
        vasItem1 = new VasItem(101, 10.0, 1);
        vasItem2 = new VasItem(101, 5.0, 2);
    }

    @Test
    public void givenDefaultItem_whenGetTotalPrice_thenReturnsCorrectTotalPrice() {
        // Given - created default item
        // When
        double totalPrice = defaultItem.getTotalPrice();

        // Then
        assertEquals(250.0, totalPrice);
    }

    @Test
    public void givenValidQuantity_whenIsValidQuantity_thenReturnsTrue() {
        // When
        boolean isValid = defaultItem.isValidQuantity(defaultItem.getQuantity());

        // Then
        assertTrue(isValid);
    }

    @Test
    public void givenInvalidQuantity_whenIsValidQuantity_thenReturnsFalse() {
        // When
        boolean isValid = defaultItem.isValidQuantity(11);

        // Then
        assertFalse(isValid);
    }

    @Test
    public void givenDefaultItem_whenCanAddVasItem_thenReturnsTrue() {
        // When
        boolean canAdd = defaultItem.canAddVasItem();

        // Then
        assertTrue(canAdd);
    }

    @Test
    public void givenNewVasItem_whenAddOrUpdateVasItem_thenReturnsTrue() {
        // Given
        // When
        boolean result = defaultItem.addOrUpdateVasItem(vasItem1);

        // Then
        assertTrue(result);
        assertEquals(1, defaultItem.getVasItems().size());
        assertEquals(vasItem1.getQuantity(), defaultItem.getVasItems().get(0).getQuantity());
    }

    @Test
    public void givenExistingVasItem_whenAddOrUpdateVasItem_thenUpdatesQuantity() {
        // Given
        defaultItem.addOrUpdateVasItem(vasItem1);

        // When
        boolean result = defaultItem.addOrUpdateVasItem(vasItem2);

        // Then
        assertTrue(result);
        assertEquals(3, defaultItem.getVasItems().get(0).getQuantity());
    }

    @Test
    public void givenInvalidQuantity_whenAddOrUpdateVasItem_thenReturnsFalse() {
        // Given
        for (int i = 0; i < 3; i++) {
            defaultItem.addOrUpdateVasItem(new VasItem(102, 5.0, 1));
        }

        // When
        boolean result = defaultItem.addOrUpdateVasItem(new VasItem(102, 5.0, 2));

        // Then
        assertFalse(result);
    }
}
