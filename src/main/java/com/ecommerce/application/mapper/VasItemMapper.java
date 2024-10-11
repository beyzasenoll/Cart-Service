package com.ecommerce.application.mapper;


import com.ecommerce.application.domain.item.VasItem;
import com.ecommerce.application.dto.VasItemDto;

public class VasItemMapper {

    public static VasItem toVasItem(VasItemDto vasItemDto) {
        if (vasItemDto == null) {
            return null;
        }
        return new VasItem(vasItemDto.getVasItemId(), vasItemDto.getCategoryId(), vasItemDto.getPrice(), vasItemDto.getQuantity());
    }

    public static VasItemDto toVasItemDto(VasItem VasItem) {
        if (VasItem == null) {
            return null;
        }

        VasItemDto dto = new VasItemDto();
        dto.setItemId(VasItem.getItemId());
        dto.setVasItemId(VasItem.getItemId());
        dto.setCategoryId(VasItem.getCategoryId());
        dto.setSellerId(VasItem.getSellerId());
        dto.setPrice(VasItem.getPrice());
        dto.setQuantity(VasItem.getQuantity());

        return dto;
    }
}

