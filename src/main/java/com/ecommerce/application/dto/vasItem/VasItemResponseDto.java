package com.ecommerce.application.dto.vasItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VasItemResponseDto {
        private int vasItemId;
        private int categoryId;
        private int sellerId;
        private double price;
        private int quantity;

    }
