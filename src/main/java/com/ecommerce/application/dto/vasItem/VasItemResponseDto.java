package com.ecommerce.application.dto.vasItem;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VasItemResponseDto {
    public int vasItemId;
    public int categoryId;
    public int sellerId;
    public double price;
    public int quantity;

}
