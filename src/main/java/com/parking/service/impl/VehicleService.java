package com.parking.service.impl;

import com.parking.dto.VehicleDto;
import com.parking.model.Vehicle;
import com.parking.repository.VehicleRepository;
import com.parking.utils.VehicleFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    private final VehicleFactory vehicleFactory;


    @Autowired
    public VehicleService(VehicleRepository vehicleRepository,
                          VehicleFactory vehicleFactory) {

        this.vehicleRepository = vehicleRepository;
        this.vehicleFactory = vehicleFactory;
    }

    public Vehicle saveVehicle(VehicleDto vehicleDto) {
        Vehicle vehicleEntity = vehicleFactory.getVehicle(vehicleDto.getVinNumber(), vehicleDto.getHeight(),
                vehicleDto.getWeight(), vehicleDto.getType());
        return vehicleRepository.save(vehicleEntity);
    }
}
