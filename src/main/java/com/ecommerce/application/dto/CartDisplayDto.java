package com.ecommerce.application.dto;

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
    private List<ItemDto> items;
    private double totalPrice;
    private int appliedPromotionId;
    private double totalDiscount;

    public CartDisplayDto(List<ItemDto> itemDtoList, double totalPrice, double bestDiscount, int bestPromotionId) {
    }
}
