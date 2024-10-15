package com.ecommerce.application.factory;

import com.ecommerce.application.domain.item.DefaultItem;
import com.ecommerce.application.domain.item.DigitalItem;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.domain.item.VasItem;
import com.ecommerce.application.factory.impl.ItemFactoryImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemFactoryTest {

    private final ItemFactoryImpl itemFactory = new ItemFactoryImpl();

    @Test
    public void testCreateDigitalItem() {
        Item digitalItem = itemFactory.createItem(1, 7889, 1001, 99.99, 1);

        assertTrue(digitalItem instanceof DigitalItem);
        assertEquals(1, digitalItem.getItemId());
        assertEquals(1001, digitalItem.getSellerId());
        assertEquals(99.99, digitalItem.getPrice());
        assertEquals(1, digitalItem.getQuantity());
    }

    @Test
    public void testCreateVasItem() {
        // given
        Item vasItem = itemFactory.createItem(2, 3242, 5002, 49.99, 1);

        //then
        assertTrue(vasItem instanceof VasItem);
        assertEquals(2, vasItem.getItemId());
        assertEquals(1002, vasItem.getSellerId());
        assertEquals(49.99, vasItem.getPrice());
        assertEquals(1, vasItem.getQuantity());
    }

    @Test
    public void testCreateDefaultItem() {
        // given
        Item defaultItem = itemFactory.createItem(3, 1001, 1003, 19.99, 1);

        //then
        assertTrue(defaultItem instanceof DefaultItem);
        assertEquals(3, defaultItem.getItemId());
        assertEquals(1003, defaultItem.getSellerId());
        assertEquals(19.99, defaultItem.getPrice());
        assertEquals(1, defaultItem.getQuantity());
    }

    @Test
    public void testCreateItemWithUnknownCategory() {
        // given
        Item unknownCategoryItem = itemFactory.createItem(4, 9999, 1004, 29.99, 1);

        //then
        assertTrue(unknownCategoryItem instanceof DefaultItem);
        assertEquals(4, unknownCategoryItem.getItemId());
        assertEquals(1004, unknownCategoryItem.getSellerId());
        assertEquals(29.99, unknownCategoryItem.getPrice());
        assertEquals(1, unknownCategoryItem.getQuantity());
    }
}
