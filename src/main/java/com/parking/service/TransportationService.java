package com.parking.service;

public interface TransportationService {
    // transport vehicle to destination. on enter destination will be slot. On exit destination would be gate.
    void transportVehicleToSlot(Long vehicleSlotMappingId);
    void transportVehicleToEntrance(Long vehicleSlotMappingId);


}
