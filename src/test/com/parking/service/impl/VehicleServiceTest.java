package com.parking.service.impl;

import com.parking.dto.VehicleDto;
import com.parking.model.Car;
import com.parking.model.Vehicle;
import com.parking.repository.VehicleRepository;
import com.parking.utils.VehicleFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private  VehicleFactory vehicleFactory;

    @Test
    void saveVehicle() {
        Vehicle expectedVehicle = new Car(123L, 4.0f, 20.0f,true);

        Mockito.when(vehicleFactory.getVehicle(expectedVehicle.getVinNumber(), expectedVehicle.getHeight(),
               expectedVehicle.getWeight(), expectedVehicle.getType())).thenReturn(expectedVehicle);
        Mockito.when(vehicleRepository.save(expectedVehicle)).thenReturn(expectedVehicle);
        Vehicle actualVehicle = vehicleService.saveVehicle(new VehicleDto(123L, 4.0f, 20.0f, "car"));

        Mockito.verify(vehicleRepository, Mockito.times(1)).save(expectedVehicle);
        assertSame(expectedVehicle, actualVehicle);
    }
}