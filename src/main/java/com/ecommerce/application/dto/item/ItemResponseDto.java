package com.ecommerce.application.dto.item;

import com.ecommerce.application.dto.vasItem.VasItemResponseDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponseDto {
    public List<VasItemResponseDto> vasItems = new ArrayList<>();
    private int itemId;
    private int categoryId;
    private int sellerId;
    private double price;
    private int quantity;

    public void addVasItem(VasItemResponseDto VasItemResponseDto) {
        vasItems.add(VasItemResponseDto);
    }

    public List<VasItemResponseDto> getVasItems() {
        return vasItems;
    }
}
