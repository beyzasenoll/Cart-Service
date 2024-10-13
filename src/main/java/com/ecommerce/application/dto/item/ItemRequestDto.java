package com.ecommerce.application.dto.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequestDto {
    public int itemId;
    public int categoryId;
    public int sellerId;
    public double price;
    public int quantity;
}
