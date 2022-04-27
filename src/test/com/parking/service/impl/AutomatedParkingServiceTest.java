package com.parking.service.impl;

import com.parking.dto.TicketDto;
import com.parking.dto.VehicleDto;
import com.parking.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class AutomatedParkingServiceTest {

    @InjectMocks
    private AutomatedParkingService automatedParkingService;
    @Mock
    private RegistryService registryService;
    @Mock
    private LocatorService locatorService;
    @Mock
    private TicketService ticketService;
    @Mock
    private VehicleService vehicleService;

    @Test
    void allocateSlot() {
        String entrance = "E1";
        Floor floor = new Floor(1L, 5.0f, 50.0f, 10.0, 50.0f, "A", new ArrayList<>());
        Slot slot = new Slot(1L, false, "A1", floor);
        VehicleDto vehicleDto = new VehicleDto(123L, 4.0f, 20.0f, "car");

        Vehicle vehicle = new Car(vehicleDto.getVinNumber(), vehicleDto.getHeight(), vehicleDto.getWeight(),true);
        TicketDto expectedTicket = new TicketDto.TicketDtoBuilder(1L, 10.0).withGift("Gift: Car Wash").build();
        VehicleSlotMapping vehicleSlotMapping =  new VehicleSlotMapping(new EntranceExitMapping(), vehicle, floor, slot);

        Mockito.when(locatorService.findSlot(vehicleDto.getHeight(), vehicleDto.getWeight())).thenReturn(slot);
        Mockito.doNothing().when(locatorService).occupySlot(slot, vehicleDto.getWeight());
        Mockito.when(vehicleService.saveVehicle(vehicleDto)).thenReturn(vehicle);
        Mockito.when(registryService.registerVehicleInfo(vehicle, slot, entrance)).thenReturn(vehicleSlotMapping);
        Mockito.when(ticketService.createTicket(vehicleSlotMapping)).thenReturn(expectedTicket);

        TicketDto actualTicket = automatedParkingService.allocateSlot("E1", vehicleDto);
        Assertions.assertEquals(expectedTicket.getVehicleSlotId(), actualTicket.getVehicleSlotId());
        Assertions.assertEquals(expectedTicket.getAmount(), actualTicket.getAmount());
        Assertions.assertEquals(expectedTicket.getGift(), actualTicket.getGift());
    }


}