package com.ecommerce.application.service.impl;

import com.ecommerce.application.domain.Cart;
import com.ecommerce.application.domain.item.DefaultItem;
import com.ecommerce.application.domain.item.DigitalItem;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.domain.item.VasItem;
import com.ecommerce.application.domain.promotion.Promotion;
import com.ecommerce.application.dto.ItemDto;
import com.ecommerce.application.mapper.ItemMapper;
import com.ecommerce.application.service.CartService;
import com.ecommerce.application.service.PromotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private final Cart cart;
    private List<Promotion> promotions;
    ItemMapper itemMapper;

    private static final int MAX_UNIQUE_ITEMS = 10; // Maksimum benzersiz item sayısı
    private static final int MAX_TOTAL_ITEMS = 30; // Maksimum toplam ürün adedi
    private static final double MAX_TOTAL_PRICE = 500000; // Maksimum toplam tutar


    public CartServiceImpl(Cart cart, List<Promotion> promotions) {
        this.cart = cart;
        this.promotions = promotions;
    }

    public boolean addItemToCart(ItemDto itemDto) {
        logger.info("Adding item to cart: {}", itemDto);

        Item item = itemMapper.updateItemFromDto(itemDto);
        logger.debug("Mapped Item: {}", item);

        if (!isValidItem(item)) {
            logger.error("Invalid item: {}", item);
            return false;
        }

        // Sepetteki mevcut ürünü kontrol et
        Item existingItem = findExistingItemInCart(item);
        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + item.getQuantity();
            logger.info("Existing item found in cart. Current quantity: {}, New quantity: {}", existingItem.getQuantity(), newQuantity);

            if (newQuantity > 10) {
                logger.error("Cannot add more than 10 of the same item. Current quantity: {}", newQuantity);
                return false;
            }

            existingItem.setQuantity(newQuantity);
            logger.info("Quantity updated for item: {}", existingItem);
            return true;
        }

        if (!isCartValid(item)) {
            logger.error("Cart validation failed for item: {}", item);
            return false;
        }

        boolean isAdded = cart.addItem(item);
        if (isAdded) {
            logger.info("Item added to cart: {}", item);
        } else {
            logger.error("Failed to add item: {}", item);
        }

        return isAdded;
    }

    private Item findExistingItemInCart(Item item) {
        logger.info("Finding existing item in cart: {}", item);
        for (Item cartItem : cart.getItems()) {
            if (cartItem.getItemId() == item.getItemId()) {
                logger.info("Found existing item in cart: {}", cartItem);
                return cartItem;
            }
        }
        logger.warn("No existing item found in cart for item: {}", item);
        return null;
    }

    private boolean isValidItem(Item item) {
        logger.info("Validating item: {}", item);

        if (item instanceof DigitalItem) {
            boolean isValid = ((DigitalItem) item).isValidQuantity(item.getQuantity());
            logger.info("Digital item validation result: {}", isValid);
            return isValid;
        } else if (item instanceof DefaultItem) {
            boolean isValid = ((DefaultItem) item).isValidQuantity(item.getQuantity());
            logger.info("Default item validation result: {}", isValid);
            return isValid;
        } else if (item instanceof VasItem) {
            DefaultItem parentItem = findParentItem((VasItem) item);
            if (parentItem == null) {
                logger.warn("No parent item found for VasItem: {}", item);
                return false;
            }
            boolean isValid = ((VasItem) item).isValidQuantity(item.getQuantity()) && item.getPrice() <= parentItem.getPrice();
            logger.info("VasItem validation result: {}", isValid);
            return isValid;
        }

        logger.warn("Invalid item type: {}", item);
        return false;
    }

    private boolean isCartValid(Item item) {
        logger.info("Validating cart for item: {}", item);
        int uniqueItemCount = 0; // Benzersiz öğe sayısı
        int totalQuantity = 0; // Toplam ürün adedi
        double totalPrice = 0.0; // Toplam fiyat

        for (Item cartItem : cart.getItems()) {
            // Benzersiz öğe sayısını kontrol et
            if (!(cartItem instanceof VasItem)) {
                uniqueItemCount++;
            }
            // Toplam ürün adedini ve fiyatını güncelle
            totalQuantity += cartItem.getQuantity();
            totalPrice += cartItem.getPrice() * cartItem.getQuantity();
        }

        // Kurallara göre kontrol
        if (uniqueItemCount >= MAX_UNIQUE_ITEMS) {
            logger.error("Maximum unique items in cart exceeded. Current count: {}", uniqueItemCount);
            return false;
        }

        if (totalQuantity + item.getQuantity() > MAX_TOTAL_ITEMS) {
            logger.error("Total quantity in cart would exceed 30. Current total: {}", totalQuantity);
            return false;
        }

        if (totalPrice + (item.getPrice() * item.getQuantity()) > MAX_TOTAL_PRICE) {
            logger.error("Total price in cart would exceed 500.000 TL. Current total: {}", totalPrice);
            return false;
        }

        logger.info("Cart validation successful for item: {}", item);
        return true;
    }

    private DefaultItem findParentItem(VasItem vasItem) {
        logger.info("Finding parent item for VasItem: {}", vasItem);
        for (Item item : cart.getItems()) {
            if (item instanceof DefaultItem) {
                DefaultItem defaultItem = (DefaultItem) item;
                if (defaultItem.canAddVasItem(vasItem)) {
                    logger.info("Found parent item for VasItem: {}", defaultItem);
                    return defaultItem;
                }
            }
        }
        logger.warn("No parent item found for VasItem: {}", vasItem);
        return null;
    }

    @Override
    public boolean resetCart() {
        boolean isCleared = cart.clearCart();
        if (isCleared) {
            logger.info("Cart reset successfully.");
        } else {
            logger.error("Failed to reset cart");
        }
        return isCleared;
    }


    @Override
    public boolean removeItemFromCart(int itemId) {
        boolean isRemove = cart.removeItem(itemId);
        if (isRemove) {
            logger.info("Item removed from cart");
        } else {
            logger.info("Failed to remove item");
        }
        return isRemove;
    }

    @Override
    public void displayCart() {
        cart.getItems().forEach((item) -> {
            System.out.printf("Item ID: %d, Price: %.2f\n",
                    item.getItemId(), item.getTotalPrice());
        });
    }

    @Override
    public int getCartItemCount() {
        return cart.getItemCount();
    }


    @Override
    public double getDiscountedPrice() {
        double totalPrice = cart.getTotalPrice();
        double discount = getDiscount();
        double finalPrice = totalPrice - discount;
        return finalPrice;
    }

    @Override
    public double getDiscount() {
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
