package com.ecommerce.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VasItemDto {
    private int itemId;
    private int vasItemId;
    private int categoryId;
    private int sellerId;
    private double price;
    private int quantity;
}

