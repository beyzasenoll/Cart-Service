package com.ecommerce.application.utils;

import com.ecommerce.application.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class ValidationUtil {

    private static final Logger logger = LoggerFactory.getLogger(ValidationUtil.class);

    public static ResponseEntity<ResponseDto> getResponseDtoResponseEntity(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
            logger.error("Validation error: {}", errorMessage);
            return ResponseEntity.badRequest().body(new ResponseDto(false, errorMessage));
        }
        return null;
    }
}
