
package com.ecommerce.application.factory.impl;

import com.ecommerce.application.domain.item.DefaultItem;
import com.ecommerce.application.domain.item.DigitalItem;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.domain.item.VasItem;
import com.ecommerce.application.factory.ItemFactory;
import org.springframework.stereotype.Component;

@Component
public class ItemFactoryImpl implements ItemFactory {

    @Override
     public Item createItem(int itemId, int categoryId, int sellerId, double price, int quantity) {
        if (categoryId == 7889) {
            return new DigitalItem(itemId, categoryId, sellerId, price, quantity);
        } else if (categoryId == 3242) {
            return new VasItem(itemId, categoryId, sellerId, price, quantity);
        } else {
            return new DefaultItem(itemId, categoryId, sellerId, price, quantity);
        }
    }
}
