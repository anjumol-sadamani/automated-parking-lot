package com.parking.service.impl;

import com.parking.model.EntranceExitMapping;
import com.parking.model.Slot;
import com.parking.model.Vehicle;
import com.parking.model.VehicleSlotMapping;
import com.parking.repository.EntranceRepository;
import com.parking.repository.VehicleSlotMappingRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistryService {
    private final EntranceRepository entranceRepository;
    private final VehicleSlotMappingRepository vehicleSlotMappingRepository;

    public RegistryService(EntranceRepository entranceRepository,
                           VehicleSlotMappingRepository vehicleSlotMappingRepository) {
        this.entranceRepository = entranceRepository;
        this.vehicleSlotMappingRepository = vehicleSlotMappingRepository;
    }

    public VehicleSlotMapping registerVehicleInfo(Vehicle vehicle, Slot slot, String entrance) {
        EntranceExitMapping entranceExitMapping = entranceRepository.findByEntrance(entrance);
        VehicleSlotMapping vehicleSlotMapping = new VehicleSlotMapping(entranceExitMapping, vehicle,
                slot.getFloor(), slot);
        return vehicleSlotMappingRepository.save(vehicleSlotMapping);
    }
}
