package com.ecommerce.application.service;

import com.ecommerce.application.dto.item.ItemRequestDto;

public interface VasItemService {

    boolean addVasItemToItem(ItemRequestDto itemRequestDto);
}
