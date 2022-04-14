package com.parking.service.impl;

import com.parking.dto.TicketDto;
import com.parking.dto.VehicleDto;
import com.parking.exceptionhandler.SlotNotAvailableException;
import com.parking.model.*;
import com.parking.repository.EntranceRepository;
import com.parking.repository.SlotRepository;
import com.parking.repository.VehicleRepository;
import com.parking.repository.VehicleSlotMappingRepository;
import com.parking.utils.VehicleFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class SlotAllocationServiceImplTest {

    @BeforeEach
    void setUp() {
    }

    @InjectMocks
    SlotAllocationServiceImpl slotAllocationService;

    @Mock
    SlotRepository slotRepository;
    @Mock
    EntranceRepository entranceRepository;
    @Mock
    VehicleRepository vehicleRepository;
    @Mock
    VehicleSlotMappingRepository vehicleSlotMappingRepository;

    @Mock
    VehicleFactory vehicleFactory;
    List<Slot> slotList;
    VehicleDto vehicleDto;

    @BeforeEach
    public void init() {
        slotList = new ArrayList<>();
        vehicleDto = new VehicleDto(123L, 4.0f, 20.0f, "car");
    }


    @Test
    void allocateSlot() {
        Floor floor = new Floor(1L, 5.0f, 50.0f, 10.0, 50.0f, "A");
        Slot slot1 = new Slot(1L, false, "A1", floor);
        Slot slot2 = new Slot(2L, false, "A2", floor);
        slotList.add(slot1);
        slotList.add(slot2);
        EntranceExitMapping entranceExitMapping = new EntranceExitMapping(1L, "E1", "E2");
        Vehicle vehicle = new Car(vehicleDto.getVinNumber(), vehicleDto.getHeight(), vehicleDto.getWeight(),true);
//        ReflectionTestUtils.setField(vehicle, "freeWash", true);
        TicketDto expectedTicket = new TicketDto.TicketDtoBuilder(1L, 10.0).withGift("Gift: Car Wash").build();
        Mockito.when(slotRepository.getSuitableSlot(vehicleDto.getHeight(), vehicleDto.getWeight())).thenReturn(slotList);
        Mockito.when(slotRepository.save(slot1)).thenReturn(slot1);
        Mockito.when(entranceRepository.findByEntrance("E1")).thenReturn(entranceExitMapping);
        Mockito.when(vehicleFactory.getVehicle(vehicleDto.getVinNumber(), vehicleDto.getHeight(),
                vehicleDto.getWeight(), vehicleDto.getType())).thenReturn(vehicle);
        Mockito.when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        Mockito.when(vehicleSlotMappingRepository.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);

        TicketDto actualTicket = slotAllocationService.allocateSlot("E1", vehicleDto);
        Assertions.assertEquals(expectedTicket.getVehicleSlotId(), actualTicket.getVehicleSlotId());
        Assertions.assertEquals(expectedTicket.getAmount(), actualTicket.getAmount());
        Assertions.assertEquals(expectedTicket.getGift(), actualTicket.getGift());
    }


    @Test
    void testVehicleNotSupportedException() {
        Vehicle vehicle = new Car(vehicleDto.getVinNumber(), vehicleDto.getHeight(), vehicleDto.getWeight(),true);
        ReflectionTestUtils.setField(vehicle, "freeWash", true);
        Mockito.when(slotRepository.getSuitableSlot(vehicleDto.getHeight(), vehicleDto.getWeight())).thenReturn(slotList);
        Assertions.assertThrows(SlotNotAvailableException.class, () -> slotAllocationService.allocateSlot("E1", vehicleDto));
    }
}