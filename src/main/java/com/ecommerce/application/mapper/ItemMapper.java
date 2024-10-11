package com.ecommerce.application.mapper;

import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.dto.ItemDto;

public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.itemId = item.getItemId();
        itemDto.categoryId = item.getCategoryId();
        itemDto.sellerId = item.getSellerId();
        itemDto.price = item.getPrice();
        itemDto.quantity = item.getQuantity();
        return itemDto;
    }

    public static void updateItemFromDto(ItemDto itemDto, Item item) {
        item.itemId = itemDto.itemId;
        item.categoryId = itemDto.categoryId;
        item.sellerId = itemDto.sellerId;
        item.price = itemDto.price;
        item.quantity = itemDto.quantity;
    }
}

