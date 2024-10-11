package com.ecommerce.application.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class CartDisplayDto {
    private List<ItemDto> items;
    private double totalPrice;
    private int appliedPromotionId;
    private double totalDiscount;
}
