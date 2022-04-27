package com.parking.service.impl;

import com.parking.dto.TicketDto;
import com.parking.dto.VehicleDto;
import com.parking.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;
    @Test
    void createTicket() {
        Floor floor = new Floor(1L, 5.0f, 50.0f, 10.0, 50.0f, "A", new ArrayList<>());
        Slot slot = new Slot(1L, false, "A1", floor);
        VehicleDto vehicleDto = new VehicleDto(123L, 4.0f, 20.0f, "car");

        Vehicle vehicle = new Car(vehicleDto.getVinNumber(), vehicleDto.getHeight(), vehicleDto.getWeight(),true);
        TicketDto expectedTicket = new TicketDto.TicketDtoBuilder(1L, 10.0).withGift("Gift: Car Wash").build();
        VehicleSlotMapping vehicleSlotMapping =  new VehicleSlotMapping(new EntranceExitMapping(), vehicle, floor, slot);

        TicketDto actualTicket = ticketService.createTicket(vehicleSlotMapping);

        assertEquals(expectedTicket.getAmount(), actualTicket.getAmount());
        assertEquals(expectedTicket.getVehicleSlotId(), actualTicket.getVehicleSlotId());

    }
}