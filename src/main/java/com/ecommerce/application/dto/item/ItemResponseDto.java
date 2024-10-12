package com.ecommerce.application.dto.item;

import com.ecommerce.application.dto.vasItem.VasItemResponseDto;

import java.util.ArrayList;
import java.util.List;

public class ItemResponseDto {
    public int itemId;
    public int categoryId;
    public int sellerId;
    public double price;
    public int quantity;
    protected List<VasItemResponseDto> vasItems = new ArrayList<>();

    public void addVasItem(VasItemResponseDto VasItemResponseDto) {
        vasItems.add(VasItemResponseDto);
    }
    public List<VasItemResponseDto> getVasItems() {
        return vasItems;
    }
}
