package com.parking.service.impl;

import com.parking.dto.TicketDto;
import com.parking.dto.VehicleDto;
import com.parking.exceptionhandler.SlotNotAvailableException;
import com.parking.model.*;
import com.parking.repository.EntranceRepository;
import com.parking.repository.SlotRepository;
import com.parking.repository.VehicleRepository;
import com.parking.repository.VehicleSlotMappingRepository;
import com.parking.service.SlotAllocationService;
import com.parking.utils.VehicleFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class SlotAllocationServiceImpl implements SlotAllocationService {

    private final SlotRepository slotRepository;

    private final EntranceRepository entranceRepository;

    private final VehicleRepository vehicleRepository;

    private final VehicleSlotMappingRepository vehicleSlotMappingRepository;

    private VehicleFactory vehicleFactory;

    private final ReentrantLock lock = new ReentrantLock();

    @Autowired
    public SlotAllocationServiceImpl(SlotRepository slotRepository,
                                     EntranceRepository entranceRepository,
                                     VehicleRepository vehicleRepository,
                                     VehicleSlotMappingRepository vehicleSlotMappingRepository,
                                     VehicleFactory vehicleFactory) {
        this.slotRepository = slotRepository;
        this.entranceRepository = entranceRepository;
        this.vehicleRepository = vehicleRepository;
        this.vehicleSlotMappingRepository = vehicleSlotMappingRepository;
        this.vehicleFactory = vehicleFactory;
    }
    /**
     * Allocate slot based on the availability and capacity. And generate ticket.
     * @return Ticket
     */
    @Override
    @Transactional
    public TicketDto allocateSlot(String entrance, VehicleDto vehicleDto) {
        log.info("Started allocateSlot with Vin Number {}",vehicleDto.getVinNumber());
        Slot slot;
        lock.lock();
        try {
            List<Slot> slots = slotRepository.getSuitableSlot(vehicleDto.getHeight(), vehicleDto.getWeight());
            Optional<Slot> opSlot = slots.stream().findFirst();
            if (opSlot.isEmpty()) {
                throw new SlotNotAvailableException("Slot not available");
            }
            slot = opSlot.get();
            Floor floor = slot.getFloor();
            floor.setRemainingCapacity(floor.getRemainingCapacity() - vehicleDto.getWeight());
            slot.setOccupied(true);
            slot = slotRepository.save(slot);
        } finally {
            lock.unlock();
        }
        EntranceExitMapping entranceExitMapping = entranceRepository.findByEntrance(entrance);
        Vehicle vehicleEntity = vehicleFactory.getVehicle(vehicleDto.getVinNumber(), vehicleDto.getHeight(),
                vehicleDto.getWeight(), vehicleDto.getType());
        vehicleEntity = vehicleRepository.save(vehicleEntity);
        VehicleSlotMapping vehicleSlotMapping
                = new VehicleSlotMapping(entranceExitMapping.getId(), vehicleEntity.getId(),
                slot.getFloor().getId(), slot.getId());
        vehicleSlotMapping = vehicleSlotMappingRepository.save(vehicleSlotMapping);
        TicketDto.TicketDtoBuilder ticketBuilder = new TicketDto.TicketDtoBuilder(vehicleSlotMapping.getSlotId(), slot.getFloor().getRate());
        if (vehicleEntity.isFreeWashAvailable()) {
            ticketBuilder.withGift("Gift: Car Wash");
        }
        return ticketBuilder.build();
    }
}
