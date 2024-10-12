package com.ecommerce.application.service;

import com.ecommerce.application.dto.vasItem.VasItemRequestDto;

public interface VasItemService {

    boolean addVasItemToItem(VasItemRequestDto vasItemRequestDto);
}
