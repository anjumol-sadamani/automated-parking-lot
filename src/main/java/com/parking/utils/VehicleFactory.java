package com.parking.utils;

import com.parking.exceptionhandler.VehicleNotSupportedException;
import com.parking.model.Car;
import com.parking.model.Vehicle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Eligibility for a free wash can be configured in application.properties file
 */
@Component
public class VehicleFactory {

    @Value("${free.car-wash}")
    private boolean carFreeWash;

    public Vehicle getVehicle(Long vinNumber, Float height, Float weight, String type){
        Constant.VehicleType vType = Constant.VehicleType.getVehicleType(type);
        switch (Objects.requireNonNull(vType)){
            case CAR: return new Car(vinNumber, height, weight,carFreeWash);
        }
        throw new VehicleNotSupportedException("Vehicle Type Not Supported!");
    }
}
