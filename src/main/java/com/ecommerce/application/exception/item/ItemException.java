package com.ecommerce.application.exception.item;

public class ItemException extends RuntimeException {

    public ItemException(String message) {
        super(message);
    }
    public static class ItemNotFoundException extends RuntimeException {
        public ItemNotFoundException(String message) {
            super(message);
        }
    }

    public static class ItemRemoveException extends RuntimeException {
        public ItemRemoveException(String message) {
            super(message);
        }
    }


    public static class InvalidItemRequestException extends ItemException {
        public InvalidItemRequestException(String message) {
            super(message);
        }
    }

    public static class ItemAttributeMismatchException extends ItemException {
        public ItemAttributeMismatchException(String message) {
            super(message);
        }
    }

    public static class ItemQuantityExceededException extends ItemException {
        public ItemQuantityExceededException(String message) {
            super(message);
        }
    }

    public static class ItemValidationException extends ItemException {
        public ItemValidationException(String message) {
            super(message);
        }
    }
}
