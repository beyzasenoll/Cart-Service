package com.ecommerce.application.exception;

import com.ecommerce.application.dto.ResponseDto;
import com.ecommerce.application.exception.cart.CartValidationException;
import com.ecommerce.application.exception.item.ItemException;
import com.ecommerce.application.exception.vasItem.VasItemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VasItemException.CannotAddedVasItem.class)
    public ResponseEntity<ResponseDto> handleCannotAddedVasItem(VasItemException.CannotAddedVasItem ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(false, errorMessage));
    }

    @ExceptionHandler(VasItemException.ParentItemNotFoundException.class)
    public ResponseEntity<ResponseDto> handleParentItemNotFoundException(VasItemException.ParentItemNotFoundException ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(false, errorMessage));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(false, errorMessage));
    }

    @ExceptionHandler(CartValidationException.class)
    public ResponseEntity<ResponseDto> handleCartValidationException(CartValidationException ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(false, errorMessage));
    }
    @ExceptionHandler(ItemException.class)
    public ResponseEntity<ResponseDto> handleItemException(ItemException ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto(false, errorMessage));
    }
    @ExceptionHandler(ItemException.ItemNotFoundException.class)
    public ResponseEntity<ResponseDto> handleItemNotFoundException(ItemException.ItemNotFoundException ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(false, ex.getMessage()));
    }

    @ExceptionHandler(ItemException.ItemRemoveException.class)
    public ResponseEntity<ResponseDto> handleItemRemoveException(ItemException.ItemRemoveException ex) {
        String errorMessage = ex.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(false, ex.getMessage()));
    }

}
