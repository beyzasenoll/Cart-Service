package com.ecommerce.application.factory;

import com.ecommerce.application.domain.item.Item;

public interface ItemFactory {
     Item createItem(int itemId, int categoryId, int sellerId, double price, int quantity);
}
