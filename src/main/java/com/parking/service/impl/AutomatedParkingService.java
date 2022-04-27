package com.parking.service.impl;

import com.parking.dto.TicketDto;
import com.parking.dto.VehicleDto;
import com.parking.model.*;
import com.parking.service.ParkingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class AutomatedParkingService implements ParkingService {

    private final LocatorService locatorService;
    private final TicketService ticketService;
    private final VehicleService vehicleService;
    private final RegistryService registryService;
    private final ReentrantLock lock = new ReentrantLock();

    @Autowired
    public AutomatedParkingService(LocatorService locatorService,
                                   VehicleService vehicleService,
                                   TicketService ticketService,
                                   RegistryService registryService) {
        this.locatorService = locatorService;
        this.ticketService = ticketService;
        this.vehicleService = vehicleService;
        this.registryService = registryService;
    }

    /**
     * Allocate slot based on the availability and capacity. And generate ticket.
     * @return TicketDto
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TicketDto allocateSlot(String entrance, VehicleDto vehicleDto) {
        Slot slot;
        lock.lock();
        try {
            slot = locatorService.findSlot(vehicleDto.getHeight(), vehicleDto.getWeight());
            locatorService.occupySlot(slot, vehicleDto.getWeight());
        } finally {
            lock.unlock();
        }
        Vehicle vehicle = vehicleService.saveVehicle(vehicleDto);
        VehicleSlotMapping vehicleSlotMapping = registryService.registerVehicleInfo(vehicle, slot, entrance);
        return ticketService.createTicket(vehicleSlotMapping);
    }
}

