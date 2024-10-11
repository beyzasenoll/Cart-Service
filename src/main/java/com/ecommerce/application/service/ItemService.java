package com.ecommerce.application.service;

import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.factory.ItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemFactory itemFactory;

    @Autowired
    public ItemService(ItemFactory itemFactory) {
        this.itemFactory = itemFactory;
    }

    public Item createItem(int itemId, int categoryId, int sellerId, double price, int quantity) {
        return itemFactory.createItem(itemId, categoryId, sellerId, price, quantity);
    }
}
