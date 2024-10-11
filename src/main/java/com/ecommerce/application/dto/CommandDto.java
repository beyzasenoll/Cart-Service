package com.ecommerce.application.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CommandDto {
    private String command;
    private Object payload;
}
