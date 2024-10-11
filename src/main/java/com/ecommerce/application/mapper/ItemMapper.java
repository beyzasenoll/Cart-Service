package com.ecommerce.application.mapper;

import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.dto.ItemDto;
import com.ecommerce.application.factory.ItemFactory;

public class ItemMapper {
    private static ItemFactory itemFactory ;

    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.itemId = item.getItemId();
        itemDto.categoryId = item.getCategoryId();
        itemDto.sellerId = item.getSellerId();
        itemDto.price = item.getPrice();
        itemDto.quantity = item.getQuantity();
        return itemDto;
    }

    public static Item updateItemFromDto(ItemDto itemDto) {

        Item item = itemFactory.createItem(
                itemDto.itemId,
                itemDto.categoryId,
                itemDto.sellerId,
                itemDto.price,
                itemDto.quantity);

        return item;
    }
}

