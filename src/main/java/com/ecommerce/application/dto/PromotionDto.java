package com.ecommerce.application.dto;


import com.ecommerce.application.domain.promotion.Promotion;
import lombok.Builder;

@Builder
public class PromotionDto {
    private final Promotion promotion;
    private final double discount;

    public PromotionDto(Promotion promotion, double discount) {
        this.promotion = promotion;
        this.discount = discount;
    }

    public int getId() {
        return promotion != null ? promotion.getId() : 0;
    }

    public double getDiscount() {
        return discount;
    }
}
