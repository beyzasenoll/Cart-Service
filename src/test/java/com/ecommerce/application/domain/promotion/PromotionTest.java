package com.ecommerce.application.domain.promotion;

import com.ecommerce.application.domain.cart.Cart;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.factory.impl.ItemFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PromotionTest {

    private CategoryPromotion categoryPromotion;
    private SameSellerPromotion sameSellerPromotion;
    private TotalPricePromotion totalPricePromotion;
    private ItemFactoryImpl itemFactory;
    private Cart cart;

    @BeforeEach
    public void setUp() {
        categoryPromotion = new CategoryPromotion();
        sameSellerPromotion = new SameSellerPromotion();
        totalPricePromotion = new TotalPricePromotion();
        itemFactory = new ItemFactoryImpl();
        cart = Mockito.mock(Cart.class);
    }

    @Test
    public void testCategoryPromotionApplicable() {
        // Given
        List<Item> items = new ArrayList<>();
        Item item = itemFactory.createItem(1, 3003, 100, 50.0, 1);
        items.add(item);
        Mockito.when(cart.getItems()).thenReturn(items);

        // When
        boolean result = categoryPromotion.isApplicable(cart);

        // Then
        assertTrue(result);
    }

    @Test
    public void testCategoryPromotionDiscount() {
        // Given
        List<Item> items = new ArrayList<>();
        Item item1 = itemFactory.createItem(1, 3003, 100, 50.0, 1);
        Item item2 = itemFactory.createItem(2, 1001, 200, 30.0, 1); // Not applicable
        items.add(item1);
        items.add(item2);

        Mockito.when(cart.getItems()).thenReturn(items);
        Mockito.when(cart.getTotalPrice()).thenReturn(80.0);

        // When
        double discount = categoryPromotion.applyDiscount(cart);

        // Then
        assertEquals(2.5, discount); // %5 of 50
        assertEquals(77.5, cart.getTotalPrice() - discount); // 80 - 2.5
    }

    @Test
    public void testSameSellerPromotionApplicable() {
        // Given
        Mockito.when(cart.hasSameSellerItems()).thenReturn(true);

        // When
        boolean result = sameSellerPromotion.isApplicable(cart);

        // Then
        assertTrue(result);
    }

    @Test
    public void testSameSellerPromotionDiscount() {
        // Given
        List<Item> items = new ArrayList<>();
        Item item1 = itemFactory.createItem(1, 12, 100, 50.0, 1);
        Item item2 = itemFactory.createItem(2, 12, 100, 30.0, 1);
        items.add(item1);
        items.add(item2);

        // Cart mock object
        Mockito.when(cart.getTotalPrice()).thenReturn(80.0);
        Mockito.when(cart.hasSameSellerItems()).thenReturn(true);

        // When
        double discount = sameSellerPromotion.applyDiscount(cart);

        // Then
        assertEquals(8, discount);
    }


    @Test
    public void testTotalPricePromotionApplicable() {
        // Given
        Mockito.when(cart.getTotalPrice()).thenReturn(6000.0);

        // When
        boolean result = totalPricePromotion.isApplicable(cart);

        // Then
        assertTrue(result);
    }

    @Test
    public void testTotalPricePromotionDiscount() {
        // Given
        Mockito.when(cart.getTotalPrice()).thenReturn(6000.0);

        // When
        double discount = totalPricePromotion.applyDiscount(cart);

        // Then
        assertEquals(500.0, discount); // 6000 falls in DISCOUNT_2_THRESHOLD
    }

    @Test
    public void testCalculateDiscount() {
        // Given
        Cart cart1 = createMockCart(3000.0);
        Cart cart2 = createMockCart(7000.0);
        Cart cart3 = createMockCart(15000.0);
        Cart cart4 = createMockCart(60000.0);

        // When & Then
        assertEquals(250.0, totalPricePromotion.applyDiscount(cart1)); // DISCOUNT_1_AMOUNT
        assertEquals(500.0, totalPricePromotion.applyDiscount(cart2)); // DISCOUNT_2_AMOUNT
        assertEquals(1000.0, totalPricePromotion.applyDiscount(cart3)); // DISCOUNT_3_AMOUNT
        assertEquals(2000.0, totalPricePromotion.applyDiscount(cart4)); // DISCOUNT_4_AMOUNT
    }

    private Cart createMockCart(double totalPrice) {
        Cart cart = Mockito.mock(Cart.class);
        Mockito.when(cart.getTotalPrice()).thenReturn(totalPrice);
        return cart;
    }
}
