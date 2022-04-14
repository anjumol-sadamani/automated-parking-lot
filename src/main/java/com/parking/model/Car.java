package com.parking.model;

import com.parking.utils.Constant;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


@NoArgsConstructor
@Entity
public class Car extends Vehicle{
    private boolean freeWash;

    public Car(Long vinNumber, Float height, Float weight,boolean freeWash){
        super(vinNumber, height, weight, Constant.VehicleType.CAR.toString());
        this.freeWash = freeWash;
    }

    @Override
    public boolean isFreeWashAvailable() {
        return freeWash;
    }
}
