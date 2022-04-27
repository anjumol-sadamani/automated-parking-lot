package com.parking.service.impl;

import com.parking.exceptionhandler.SlotNotAvailableException;
import com.parking.model.Floor;
import com.parking.model.Slot;
import com.parking.repository.FloorRepository;
import com.parking.repository.SlotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LocatorServiceTest {

    @InjectMocks
    private LocatorService locatorService;

    @Mock
    private FloorRepository floorRepository;

    @Mock
    private SlotRepository slotRepository;

    @Test
    void findSlot() {
        Floor floor = new Floor(1L, 5.0f, 50.0f, 10.0, 50.0f, "A", null);
        Slot expectedSlot = new Slot(1L, false, "A1", floor);
        Floor floor2 = new Floor(1L, 10.0f, 100.0f, 10.0, 50.0f, "B", null);
        Slot slot = new Slot(1L, false, "B1", floor2);
        floor.setSlots(List.of(expectedSlot));
        floor2.setSlots(List.of(slot));
        List<Floor> floors = Arrays.asList(floor, floor2);

        var height = 4.0f;
        var weight = 30.0f;

        Mockito.when(floorRepository.findAllBy()).thenReturn(floors);

        Slot actualSlot = locatorService.findSlot(height, weight);
        assertSame(expectedSlot, actualSlot);
    }

    @Test
    void suitableSlotNotAvailable() {
        Floor floor = new Floor(1L, 5.0f, 50.0f, 10.0, 50.0f, "A", null);
        Slot slot = new Slot(1L, false, "A1", floor);
        Floor floor2 = new Floor(1L, 10.0f, 100.0f, 10.0, 50.0f, "B", null);
        Slot slot2 = new Slot(1L, false, "B1", floor2);
        floor.setSlots(List.of(slot));
        floor2.setSlots(List.of(slot2));
        List<Floor> floors = Arrays.asList(floor, floor2);
        var height = 12.0f;
        var weight = 30.0f;

        Mockito.when(floorRepository.findAllBy()).thenReturn(floors);
        assertThrows(
                SlotNotAvailableException.class,
                () -> {
                    locatorService.findSlot(height, weight);
                }
        );
    }

    @Test
    void occupySlot() {
        Floor floor = new Floor(1L, 5.0f, 50.0f, 10.0, 50.0f, "A", new ArrayList<>());
        Slot expectedSlot = new Slot(1L, false, "A1", floor);
        Mockito.when(slotRepository.save(expectedSlot)).thenAnswer(i -> i.getArguments()[0]);
        locatorService.occupySlot(expectedSlot, 40.0f);

        Mockito.verify(slotRepository, Mockito.times(1)).save(expectedSlot);
    }

}