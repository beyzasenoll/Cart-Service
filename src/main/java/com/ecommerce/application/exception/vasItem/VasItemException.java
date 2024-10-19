package com.ecommerce.application.exception.vasItem;

public class VasItemException extends RuntimeException {

    public VasItemException(String message) {
        super(message);
    }

    public static class CannotAddedVasItem extends VasItemException {
        public CannotAddedVasItem(String message) {
            super(message);
        }
    }

    public static class ParentItemNotFoundException extends VasItemException {
        public ParentItemNotFoundException(String message) {
            super(message);
        }
    }
}
