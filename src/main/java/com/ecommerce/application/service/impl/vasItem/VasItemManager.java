package com.ecommerce.application.service.impl.vasItem;

import com.ecommerce.application.domain.cart.Cart;
import com.ecommerce.application.domain.item.DefaultItem;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.domain.item.VasItem;
import com.ecommerce.application.dto.VasItemDto;
import com.ecommerce.application.mapper.VasItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VasItemManager {
    Logger logger = LoggerFactory.getLogger(VasItemManager.class);
    private final Cart cart;

    public VasItemManager(Cart cart) {
        this.cart = cart;
    }

    public boolean addVasItemToItem(VasItemDto vasItemDto) {
        logger.info("Attempting to add VAS item to cart: {}", vasItemDto);

        // Seller ID kontrolü
        if (vasItemDto.getSellerId() != 5003) {
            logger.error("VasItem seller id is invalid: {}", vasItemDto.getSellerId());
            throw new IllegalArgumentException("VasItem seller id should be 5003.");
        }

        VasItem vasItem = VasItemMapper.toVasItem(vasItemDto);
        boolean isParentFound = findParentItem(vasItemDto.getItemId(), vasItem);

        if (isParentFound) {
            cart.addItem(vasItem);
            logger.info("VAS item added to parent item with ID: {}", vasItemDto.getItemId());
            return true;
        }

        logger.warn("Parent item not found or cannot add VAS item. Parent ID: {}", vasItemDto.getItemId());
        return false;
    }

    private boolean findParentItem(int parentId, VasItem vasItem) {
        logger.info("Searching for parent item with ID: {}", parentId);
        for (Item item : cart.getItems()) {
            logger.info("Item in cart: ID: {}, Type: {}", item.getItemId(), item.getClass().getSimpleName());
            if (item.getItemId() == parentId && item instanceof DefaultItem) {
                DefaultItem defaultItem = (DefaultItem) item;
                if (defaultItem.canAddVasItem()) {
                    logger.info("Parent item found and can accept VAS item. Parent ID: {}", parentId);
                    return true;
                } else {
                    logger.error("Parent category ID should be 1001 or 3004 your category id is: {}", item.getCategoryId());
                }
            }
        }

        logger.warn("Parent item with ID {} not found in cart.", parentId);
        return false;
    }
}
