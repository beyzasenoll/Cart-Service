package com.ecommerce.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemDto {
    public int itemId;
    public int categoryId;
    public int sellerId;
    public double price;
    public int quantity;
    protected List<VasItemDto> vasItems = new ArrayList<>();
}
