package com.ecommerce.application.service.impl.vasItem;

import com.ecommerce.application.dto.vasItem.VasItemRequestDto;
import com.ecommerce.application.service.VasItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VasItemServiceImpl implements VasItemService {
    Logger logger = LoggerFactory.getLogger(VasItemServiceImpl.class);
    private final VasItemManager vasItemManager;

    public VasItemServiceImpl(VasItemManager vasItemManager) {
        this.vasItemManager = vasItemManager;
    }

    @Override
    public boolean addVasItemToItem(VasItemRequestDto vasItemRequestDto) {
        logger.info("Delegating the VAS item addition to manager.");
        return vasItemManager.addVasItemToItem(vasItemRequestDto);
    }
}
