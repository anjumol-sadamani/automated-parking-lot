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
    @Column(name = "entrance_mapping_id",nullable = false)
    private Long entranceExitMappingId;
    @ManyToOne
    @JoinColumn(name = "entrance_mapping_id", insertable = false,updatable = false)
    private EntranceExitMapping entranceExitMapping;
    @Column(name = "vehicle_id",nullable = false)
    private Long vehicleId;
    @OneToOne
    @JoinColumn(name = "vehicle_id",insertable = false,updatable = false)
    Vehicle vehicle;
    @Column(name = "floor_id", nullable = false)
    private Long floorId;
    @ManyToOne
    @JoinColumn(name = "floor_id", insertable = false, updatable = false)
    private Floor floor;
    @Column(name = "slot_id",nullable = false)
    private Long slotId;
    @ManyToOne
    @JoinColumn(name = "slot_id",insertable = false,updatable = false)
    private Slot slot;
    private LocalDateTime entranceTime;
    private LocalDateTime exitTime;
    private Double rate;

    public VehicleSlotMapping(Long entranceExitMappingId, Long vehicleId, Long floorId, Long slotId) {
        this.entranceExitMappingId = entranceExitMappingId;
        this.vehicleId = vehicleId;
        this.floorId = floorId;
        this.slotId = slotId;
        this.entranceTime = LocalDateTime.now();
    }


}
