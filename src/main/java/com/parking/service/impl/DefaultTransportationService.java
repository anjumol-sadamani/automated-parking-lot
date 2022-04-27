package com.parking.service.impl;

import com.parking.service.TransportationService;
import org.springframework.stereotype.Service;

@Service
public class DefaultTransportationService implements TransportationService {
    @Override
    public void transportVehicleToSlot(Long vehicleSlotMappingId) {
        throw new UnsupportedOperationException("transportVehicleToSlot method not implemented!!");
    }

    @Override
    public void transportVehicleToEntrance(Long vehicleSlotMappingId) {
        throw new UnsupportedOperationException("transportVehicleToEntrance method not implemented!!");
    }
}
