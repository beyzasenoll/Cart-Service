package com.ecommerce.application.dto;

import com.ecommerce.application.dto.item.ItemResponseDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
public class CartDisplayDto {
    private List<ItemResponseDto> items;
    private double totalPrice;
    private int appliedPromotionId;
    private double totalDiscount;

    public CartDisplayDto(List<ItemResponseDto> itemResponseDto, double totalPrice, double bestDiscount, int bestPromotionId) {
        this.items = itemResponseDto;
        this.totalPrice = totalPrice;
        this.totalDiscount = bestDiscount;
        this.appliedPromotionId = bestPromotionId;
    }

}
