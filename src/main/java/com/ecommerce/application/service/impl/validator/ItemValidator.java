package com.ecommerce.application.service.impl.validator;

import com.ecommerce.application.domain.item.DefaultItem;
import com.ecommerce.application.domain.item.DigitalItem;
import com.ecommerce.application.domain.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ItemValidator {
    Logger logger = LoggerFactory.getLogger(ItemValidator.class);

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
