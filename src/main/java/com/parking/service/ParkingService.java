package com.parking.service;

import com.parking.dto.TicketDto;
import com.parking.dto.VehicleDto;
import org.springframework.stereotype.Service;

@Service
public interface ParkingService {
    TicketDto allocateSlot(String entrance, VehicleDto vehicleDto);
}
