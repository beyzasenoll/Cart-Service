package com.ecommerce.application.dto.vasItem;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VasItemResponseDto {
    private int vasItemId;
    private int categoryId;
    private int sellerId;
    private double price;
    private int quantity;

}
