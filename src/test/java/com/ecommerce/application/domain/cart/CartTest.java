package com.ecommerce.application.domain.cart;

import com.ecommerce.application.domain.item.DefaultItem;
import com.ecommerce.application.domain.item.DigitalItem;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.domain.item.VasItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest {

    private Cart cart;
    private Item digitalItem;
    private Item defaultItem;

    @BeforeEach
    public void setUp() {
        cart = new Cart();
        digitalItem = new DigitalItem(1, 5001, 100.0, 2);  // Örnek DigitalItem
        defaultItem = new DefaultItem(2, 1234, 5004, 50.0, 1); // Örnek DefaultItem
    }

    @Test
    public void givenNewCart_whenAddItem_thenItemShouldBeAddedToCart() {
        // When
        boolean result = cart.addItem(digitalItem);

        // Then
        assertTrue(result, "Item should be added to the cart.");
        assertEquals(1, cart.getItems().size(), "Cart should contain 1 item.");
    }

    @Test
    public void givenItemInCart_whenRemoveItem_thenItemShouldBeRemovedFromCart() {
        // Given
        cart.addItem(digitalItem);

        // When
        boolean result = cart.removeItem(1);

        // Then
        assertTrue(result, "Item should be removed from the cart.");
        assertEquals(0, cart.getItems().size(), "Cart should be empty after removal.");
    }

    @Test
    public void givenNoItemInCart_whenRemoveNonExistentItem_thenShouldReturnFalse() {
        // Given -there are no item ın the basket
        // When
        boolean result = cart.removeItem(9999);

        // Then
        assertFalse(result, "Removing non-existent item should return false.");
    }

    @Test
    public void givenItemsInCart_whenClearCart_thenShouldReturnTrueIfNotEmpty() {
        // Given
        cart.addItem(digitalItem);
        cart.addItem(defaultItem);

        // When
        boolean result = cart.clearCart();

        // Then
        assertTrue(result, "Clearing the cart should return true since it was not empty.");
        assertEquals(0, cart.getItems().size(), "Cart should be empty after clearing.");
    }

    @Test
    public void givenEmptyCart_whenClearCart_thenShouldReturnFalse() {
        // Given - there is no item ın the basket
        // When
        boolean result = cart.clearCart();

        // Then
        assertFalse(result, "Clearing the cart should return false since it was empty.");
    }

    @Test
    public void givenSameSellerItems_whenHasSameSellerItems_thenShouldReturnTrue() {
        // Given
        cart.addItem(new DefaultItem(3, 1234, 5004, 30.0, 1));
        cart.addItem(new DefaultItem(4, 1234, 5004, 20.0, 1));

        // When
        boolean result = cart.hasSameSellerItems();

        // Then
        assertTrue(result, "Expected true for items from the same seller");
    }

    @Test
    public void givenDifferentSellerItems_whenHasSameSellerItems_thenShouldReturnFalse() {
        // Given
        cart.addItem(new DefaultItem(3, 1234, 5004, 30.0, 1));
        cart.addItem(new DefaultItem(4, 1234, 500, 20.0, 1));

        // When
        boolean result = cart.hasSameSellerItems();

        // Then
        assertFalse(result, "Expected false for items from different sellers");
    }

    @Test
    public void givenItemsInCart_whenCalculateTotalQuantity_thenShouldReturnCorrectSum() {
        // Given
        cart.addItem(digitalItem);
        cart.addItem(defaultItem);

        // When
        int totalQuantity = cart.calculateTotalQuantity();

        // Then
        assertEquals(3, totalQuantity, "Total quantity should be the sum of item quantities.");
    }

    @Test
    public void givenVariousItemsInCart_whenCalculateUniqueItems_thenShouldExcludeVasItemsAndDuplicates() {
        // Given
        cart.addItem(new DefaultItem(1, 1234, 5001, 100.0, 1));
        cart.addItem(new DefaultItem(2, 1234, 5002, 50.0, 1));
        cart.addItem(new VasItem(3, 200.0, 1));
        cart.addItem(new DefaultItem(1, 1234, 5001, 100.0, 1));

        // When
        int uniqueItems = cart.calculateUniqueItems(cart);

        // Then
        assertEquals(2, uniqueItems, "Unique items count should exclude VasItem and duplicates.");
    }
}

