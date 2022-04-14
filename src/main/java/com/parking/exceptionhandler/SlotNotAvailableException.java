package com.parking.exceptionhandler;

public class SlotNotAvailableException extends RuntimeException{
    public SlotNotAvailableException(String message) {
        super(message);
    }
}
