package com.ecommerce.application.service.impl.vasItem;

import com.ecommerce.application.domain.cart.Cart;
import com.ecommerce.application.domain.item.DefaultItem;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.domain.item.VasItem;
import com.ecommerce.application.dto.vasItem.VasItemRequestDto;
import com.ecommerce.application.mapper.VasItemMapper;
import com.ecommerce.application.service.impl.cart.ItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VasItemManager {
    Logger logger = LoggerFactory.getLogger(VasItemManager.class);
    private final Cart cart;
    private final ItemManager itemManager;

    public VasItemManager(Cart cart, ItemManager itemManager) {
        this.cart = cart;
        this.itemManager = itemManager;
    }

    public boolean addVasItemToItem(VasItemRequestDto vasItemRequestDto) {
        logger.info("Attempting to add VAS item to cart: {}", vasItemRequestDto);

        validateSellerId(vasItemRequestDto.getSellerId());

        VasItem vasItem = VasItemMapper.toVasItem(vasItemRequestDto);
        DefaultItem parentItem = findParentItem(vasItemRequestDto.getItemId());

        if (parentItem != null) {
            return addVasItem(vasItem, parentItem);
        }

        logger.warn("Parent item not found or cannot add VAS item. Parent ID: {}", vasItemRequestDto.getItemId());
        return false;
    }

    private void validateSellerId(int sellerId) {
        if (sellerId != 5003) {
            logger.error("VasItem seller id is invalid: {}", sellerId);
            throw new IllegalArgumentException("VasItem seller id should be 5003.");
        }
    }

    private boolean addVasItem(VasItem vasItem, DefaultItem parentItem) {
        if (vasItem != null && parentItem.canAddVasItem()) {

            if (!itemManager.updateExistingItemQuantity(vasItem, cart)) {
                return false;
            }
            if (parentItem.addVasItem(vasItem)) {
                cart.calculateTotalPrice(); // Recalculate total price
                logger.info("VAS item added to parent item with ID: {}", parentItem.getItemId());
                return true;
            }
        }
        return false;
    }
    private DefaultItem findParentItem(int parentId) {
        logger.info("Searching for parent item with ID: {}", parentId);
        for (Item item : cart.getItems()) {
            if (item.getItemId() == parentId && item instanceof DefaultItem) {
                return (DefaultItem) item; // Return found parent item
            }
        }

        logger.warn("Parent item with ID {} not found in cart.", parentId);
        return null;
    }
}

