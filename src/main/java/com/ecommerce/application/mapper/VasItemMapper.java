package com.ecommerce.application.mapper;


import com.ecommerce.application.domain.item.VasItem;
import com.ecommerce.application.dto.vasItem.VasItemRequestDto;
import com.ecommerce.application.dto.vasItem.VasItemResponseDto;
import org.springframework.stereotype.Component;

@Component

public class VasItemMapper {

    public static VasItem toVasItem(VasItemRequestDto vasItemRequestDto) {
        if (vasItemRequestDto == null) {
            return null;
        }
        return new VasItem(vasItemRequestDto.getVasItemId(), vasItemRequestDto.getPrice(), vasItemRequestDto.getQuantity());
    }

    public static VasItemResponseDto toResponseDto(VasItem vasItem) {
        if (vasItem == null) {
            return null;
        }
        VasItemResponseDto responseDto = new VasItemResponseDto();
        responseDto.setVasItemId(vasItem.getItemId());
        responseDto.setCategoryId(vasItem.getCategoryId());
        responseDto.setSellerId(vasItem.getSellerId());
        responseDto.setPrice(vasItem.getPrice());
        responseDto.setQuantity(vasItem.getQuantity());
        return responseDto;
    }
}

