package com.parking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long vinNumber;
    private Float height;
    private Float weight;
    private String type;

    protected Vehicle(Long vinNumber, Float height, Float weight, String type) {
        this.vinNumber = vinNumber;
        this.height = height;
        this.weight = weight;
        this.type = type;
    }

    public abstract boolean isFreeWashAvailable();


}
