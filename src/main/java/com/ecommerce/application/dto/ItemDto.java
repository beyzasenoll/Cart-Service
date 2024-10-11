package com.ecommerce.application.dto;

import com.ecommerce.application.domain.item.VasItem;

import java.util.List;

public class ItemDto {
    public int itemId;
    public int categoryId;
    public int sellerId;
    public double price;
    public int quantity;
    protected List<VasItem> vasItems = null;
}
