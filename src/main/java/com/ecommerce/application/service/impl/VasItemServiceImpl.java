package com.ecommerce.application.service.impl;

import com.ecommerce.application.domain.cart.Cart;
import com.ecommerce.application.domain.item.DefaultItem;
import com.ecommerce.application.domain.item.Item;
import com.ecommerce.application.domain.item.VasItem;
import com.ecommerce.application.dto.item.ItemRequestDto;
import com.ecommerce.application.exception.vasItem.VasItemException;
import com.ecommerce.application.mapper.VasItemMapper;
import com.ecommerce.application.service.VasItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VasItemServiceImpl implements VasItemService {
    private final Cart cart;
    Logger logger = LoggerFactory.getLogger(VasItemServiceImpl.class);

    public VasItemServiceImpl(Cart cart) {
        this.cart = cart;
    }

    @Override
    public boolean addVasItemToItem(ItemRequestDto vasItemRequestDto) {
        logger.info("Attempting to add VAS item to cart: {}", vasItemRequestDto);

        validateSellerId(vasItemRequestDto.getSellerId());

        VasItem vasItem = VasItemMapper.toVasItem(vasItemRequestDto);
        DefaultItem parentItem = findParentItem(vasItemRequestDto.getItemId());

        if (parentItem != null) {
            return addVasItem(vasItem, parentItem);
        }

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
            if (parentItem.addOrUpdateVasItem(vasItem)) {
                cart.calculateTotalPrice();
                logger.info("VAS item added/updated to parent item with ID: {}", parentItem.getItemId());
                return true;
            }
        }
        String errorMessage = "cannot add VAS item. Parent ID: " + vasItem.getItemId();
        logger.warn(errorMessage);
        throw new VasItemException.CannotAddedVasItem(errorMessage);
    }

    private DefaultItem findParentItem(int parentId) {
        logger.info("Searching for parent item with ID: {}", parentId);
        for (Item item : cart.getItems()) {
            if (item.getItemId() == parentId && item instanceof DefaultItem) {
                return (DefaultItem) item;
            }
        }
        String errorMessage = "Parent item not found";
        logger.warn(errorMessage);
        throw new VasItemException.ParentItemNotFoundException(errorMessage);
    }
}
