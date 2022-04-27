package com.parking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class VehicleSlotMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "entrance_mapping_id")
    private EntranceExitMapping entranceExitMapping;
    @OneToOne
    @JoinColumn(name = "vehicle_id")
    Vehicle vehicle;
    @ManyToOne
    @JoinColumn(name = "floor_id")
    private Floor floor;
    @ManyToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;
    private LocalDateTime entranceTime;
    private LocalDateTime exitTime;
    private Double rate;

    public VehicleSlotMapping(EntranceExitMapping entranceExitMapping, Vehicle vehicle, Floor floor, Slot slot) {
        this.entranceExitMapping = entranceExitMapping;
        this.vehicle = vehicle;
        this.floor = floor;
        this.slot = slot;
        this.entranceTime = LocalDateTime.now();
    }
}
