package com.ecommerce.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto {
    private boolean result;
    private Object message;

    public ResponseDto(boolean result, Object message) {
        this.result = result;
        this.message = message;
    }
}
