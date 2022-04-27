package com.parking.service.impl;

import com.parking.model.*;
import com.parking.repository.EntranceRepository;
import com.parking.repository.VehicleSlotMappingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertSame;
@ExtendWith(MockitoExtension.class)
class RegistryServiceTest {
    @InjectMocks
    private RegistryService registryService;
    @Mock
    private  EntranceRepository entranceRepository;
    @Mock
    private  VehicleSlotMappingRepository vehicleSlotMappingRepository;

    @Test
    void registerVehicleInfo() {
        Vehicle vehicle = new Car(123L, 4.0f, 20.0f,true);
        String entrance = "E1";
        Floor floor = new Floor(1L, 5.0f, 50.0f, 10.0, 50.0f, "A", new ArrayList<>());
        Slot slot = new Slot(1L, false, "A1", floor);
        EntranceExitMapping entranceExitMapping = new EntranceExitMapping(1l,"E1", "E1");
        VehicleSlotMapping expectedVehicleSlotMapping =  new VehicleSlotMapping(entranceExitMapping, vehicle, floor, slot);

        Mockito.when(entranceRepository.findByEntrance("E1")).thenReturn(entranceExitMapping);
        Mockito.when(vehicleSlotMappingRepository.save(Mockito.any(VehicleSlotMapping.class))).thenReturn(expectedVehicleSlotMapping);

        VehicleSlotMapping actualVehicleSlotInfo = registryService.registerVehicleInfo(vehicle, slot, entrance);

        Mockito.verify(entranceRepository, Mockito.times(1)).findByEntrance("E1");
        assertSame(expectedVehicleSlotMapping, actualVehicleSlotInfo);
    }
}