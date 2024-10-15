package com.ecommerce.application.dto.item;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemRequestDto {
    public int itemId;
    public int categoryId;
    public int sellerId;
    public double price;
    public int quantity;
}
