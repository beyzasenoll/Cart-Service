package com.ecommerce.application.dto.item;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemRequestDto {
    private int itemId;
    private int categoryId;
    private int sellerId;
    private double price;
    private int quantity;
}
