package com.parking.exceptionhandler;

public class VehicleNotSupportedException extends RuntimeException{
    public VehicleNotSupportedException(String message) {
        super(message);
    }

}
