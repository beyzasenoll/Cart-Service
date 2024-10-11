package com.ecommerce.application.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public abstract class Item {

    public int itemId;
    public int categoryId;
    public int sellerId;
    public double price;
    public int quantity;
    public abstract double getTotalPrice();

    abstract boolean isValidQuantity(int quantity);
}

