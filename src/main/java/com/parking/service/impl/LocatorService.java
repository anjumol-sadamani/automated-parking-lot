package com.parking.service.impl;

import com.parking.exceptionhandler.SlotNotAvailableException;
import com.parking.model.Floor;
import com.parking.model.Slot;
import com.parking.repository.FloorRepository;
import com.parking.repository.SlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class LocatorService {
    private final SlotRepository slotRepository;

    private final FloorRepository floorRepository;


    @Autowired
    public LocatorService(SlotRepository slotRepository, FloorRepository floorRepository) {
        this.slotRepository = slotRepository;
        this.floorRepository = floorRepository;
    }


    /**
     * Method to find a suitable slot for parking the vehicle. The available floors are
     * fetched from the database, then below logic is applied to find matching slot.
     * 1 - filter the stream of floors for height > vehicle height and has remaining capacity >= vehicle weight
     * 2 - get available slots from filtered floors
     * 3 - choose a slot of minimum height and capacity
     * @param height Height of the vehicle
     * @param weight Weight of the vehicle
     * @return Suitable slot to park the car
     * @exception SlotNotAvailableException
     */
    public Slot findSlot(Float height, Float weight) {
        List<Floor> floors = floorRepository.findAllBy();

        Optional<Slot> opSlot = floors.stream()
                .filter(f -> f.getHeight() > height
                        && f.getRemainingCapacity() >= weight)
                .flatMap(floor -> floor.getSlots().stream())
                .filter(s -> !s.getOccupied())
                .min(Comparator.comparing((Slot s) -> s.getFloor().getHeight())
                        .thenComparing(s -> s.getFloor().getRemainingCapacity()));


        if (opSlot.isEmpty()) {
            throw new SlotNotAvailableException("Slot not available");
        }
        return opSlot.get();
    }

    public void occupySlot(Slot slot, Float weight) {
        Floor floor = slot.getFloor();
        floor.setRemainingCapacity(floor.getRemainingCapacity() - weight);
        slot.setOccupied(true);
        slotRepository.save(slot);
    }
}
