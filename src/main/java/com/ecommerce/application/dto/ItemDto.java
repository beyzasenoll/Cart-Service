package com.ecommerce.application.dto;

import com.ecommerce.application.domain.item.VasItem;
import lombok.Getter;

import java.util.List;

@Getter
public class ItemDto {
    public int itemId;
    public int categoryId;
    public int sellerId;
    public double price;
    public int quantity;
    protected List<VasItem> vasItems = null;
}
