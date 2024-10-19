package com.ecommerce.application.service;

import com.ecommerce.application.domain.cart.Cart;
import com.ecommerce.application.domain.promotion.Promotion;
import com.ecommerce.application.dto.PromotionDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PromotionService {
    private final List<Promotion> promotions;

    public PromotionService(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public double findDiscount(Cart cart) {
        PromotionDto bestPromotion = findBestPromotion(cart);
        cart.applyDiscount(bestPromotion.getDiscount());
        return bestPromotion.getDiscount();
    }

    public int getBestPromotionId(Cart cart) {
        return findBestPromotion(cart).getId();
    }

    private PromotionDto findBestPromotion(Cart cart) {
        double bestDiscount = 0;
        Promotion bestPromotion = null;

        for (Promotion promotion : promotions) {
            if (promotion.isApplicable(cart)) {
                double discount = promotion.applyDiscount(cart);
                if (discount > bestDiscount) {
                    bestDiscount = discount;
                    bestPromotion = promotion;
                }
            }
        }

        return new PromotionDto(bestPromotion, bestDiscount);
    }
}
