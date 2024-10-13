package com.ecommerce.application.service.impl;

import com.ecommerce.application.domain.cart.Cart;
import com.ecommerce.application.domain.item.DefaultItem;
import com.ecommerce.application.domain.item.DigitalItem;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.dto.item.ItemRequestDto;
import com.ecommerce.application.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ItemServiceImpl implements ItemService {

    private final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);


    public ItemServiceImpl() {
    }
    @Override
    public boolean isItemAddable(ItemRequestDto itemRequestDto, Item item) {
        if (itemRequestDto.getSellerId() == 5003) {
            logger.error("Item cannot be added from seller with ID 5003: {}", itemRequestDto);
            return false;
        }

        if (!isValidItem(item)) {
            logger.error("Invalid item: {}", item);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateExistingItemQuantity(Item item, Cart cart) {
        Item existingItem = cart.findItemInCart(item.getItemId(), cart);

        if (existingItem == null) {
            return false; // Eğer item sepette yoksa false döndür
        }

        if (hasDifferentAttributes(existingItem, item)) {
            logger.error("Cannot add item with the same itemId but different attributes. ItemId: {}", item.getItemId());
            return false; // Farklı özelliklere sahip item varsa false döndür
        }

        // Yeni miktarı hesapla
        int newQuantity = existingItem.getQuantity() + item.getQuantity();

        // Yeni miktarın geçerliliğini kontrol et
        if (!existingItem.isValidQuantity(newQuantity)) {
            logger.error("Cannot update item: quantity exceeds maximum limit. New quantity: {}", newQuantity);
            return false; // Eğer yeni miktar geçerli değilse false döndür
        }

        existingItem.setQuantity(newQuantity); // Miktarı güncelle
        logger.info("Quantity updated for item: {}", existingItem);
        return true; // Miktar başarılı bir şekilde güncellendi
    }

    @Override
    public boolean hasDifferentAttributes(Item existingItem, Item newItem) {
        return existingItem.getCategoryId() != newItem.getCategoryId() ||
                existingItem.getSellerId() != newItem.getSellerId() ||
                Double.compare(existingItem.getPrice(), newItem.getPrice()) != 0;
    }

    @Override
    public boolean isValidItem(Item item) {
        logger.info("Validating item: {}", item);

        if (item instanceof DigitalItem) {
            boolean isValid = ((DigitalItem) item).isValidQuantity(item.getQuantity());
            logger.info("Digital item validation result: {}", isValid);
            return isValid;
        } else if (item instanceof DefaultItem) {
            boolean isValid = ((DefaultItem) item).isValidQuantity(item.getQuantity());
            logger.info("Default item validation result: {}", isValid);
            return isValid;
        }

        logger.warn("Invalid item type: {}", item);
        return false;
    }
}
