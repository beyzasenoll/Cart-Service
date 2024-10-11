package com.ecommerce.application.service;

import com.ecommerce.application.domain.cart.Cart;
import com.ecommerce.application.domain.promotion.Promotion;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PromotionService {

    private List<Promotion> promotions;

    public PromotionService(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public double applyBestPromotion(Cart cart) {
        double bestDiscount = 0;
        Promotion bestPromotion = null;

        for (Promotion promotion : promotions) {
            double discount = promotion.applyDiscount(cart);
            if (discount > bestDiscount) {
                bestDiscount = discount;
                bestPromotion = promotion;
            }
        }

        return bestDiscount; // Return the best discount found
    }
}
