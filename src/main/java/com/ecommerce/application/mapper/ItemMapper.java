package com.ecommerce.application.mapper;

import com.ecommerce.application.domain.item.DefaultItem;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.domain.item.VasItem;
import com.ecommerce.application.dto.item.ItemRequestDto;
import com.ecommerce.application.dto.item.ItemResponseDto;
import com.ecommerce.application.dto.vasItem.VasItemResponseDto;
import com.ecommerce.application.factory.ItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemMapper {

    private final ItemFactory itemFactory;
    private final VasItemMapper vasItemMapper;

    @Autowired
    public ItemMapper(ItemFactory itemFactory, VasItemMapper vasItemMapper) {
        this.itemFactory = itemFactory;
        this.vasItemMapper = vasItemMapper;
    }

    public ItemResponseDto toItemDto(Item item) {
        if (item == null) {
            return null;
        }

        ItemResponseDto itemResponseDto = new ItemResponseDto();
        itemResponseDto.itemId = item.getItemId();
        itemResponseDto.categoryId = item.getCategoryId();
        itemResponseDto.sellerId = item.getSellerId();
        itemResponseDto.price = item.getPrice();
        itemResponseDto.quantity = item.getQuantity();

        if (item instanceof DefaultItem) {
            DefaultItem defaultItem = (DefaultItem) item;

            List<VasItem> vasItems = defaultItem.getVasItems();
            for (VasItem vasItem : vasItems) {
               VasItemResponseDto vasItemResponseDto= VasItemMapper.toResponseDto(vasItem);
                itemResponseDto.getVasItems().add(vasItemResponseDto);
            }
        }
        return itemResponseDto;
    }



    public Item updateItemFromDto(ItemRequestDto itemRequestDto) {
        if (itemRequestDto == null) {
            return null;
        }

        return itemFactory.createItem(
                itemRequestDto.itemId,
                itemRequestDto.categoryId,
                itemRequestDto.sellerId,
                itemRequestDto.price,
                itemRequestDto.quantity
        );
    }
}

