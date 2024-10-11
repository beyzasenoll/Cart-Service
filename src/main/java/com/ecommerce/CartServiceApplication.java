package com.ecommerce;

import com.ecommerce.application.domain.item.DefaultItem;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.domain.promotion.SameSellerPromotion;
import com.ecommerce.application.domain.promotion.TotalPricePromotion;
import com.ecommerce.application.service.CartService;
import com.ecommerce.application.service.PromotionService;
import com.ecommerce.application.domain.Cart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class CartServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartServiceApplication.class, args);
	}
}

