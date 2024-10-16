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
    public int itemId;
    public int categoryId;
    public int sellerId;
    public double price;
    public int quantity;
    public List<VasItemResponseDto> vasItems = new ArrayList<>();

    public void addVasItem(VasItemResponseDto VasItemResponseDto) {
        vasItems.add(VasItemResponseDto);
    }

    public List<VasItemResponseDto> getVasItems() {
        return vasItems;
    }
}
