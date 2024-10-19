package com.ecommerce.application.dto.item;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemRequestDto {
    @NotNull(message = "Item name cannot be null")
    private Integer itemId;
    @NotNull(message = "Category Id cannot be null")
    private Integer categoryId;

    private Integer vasItemId = null;

    @NotNull(message = "Seller Id cannot be null")
    private Integer sellerId;
    @NotNull(message = "Price cannot be null")
    private Double price;
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
}
