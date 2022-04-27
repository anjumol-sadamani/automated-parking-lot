package com.parking.controller;

import com.parking.service.TransportationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@Slf4j
@RestController
@RequestMapping("v1/automatic-parking")
public class TransportationController {

    private final TransportationService transportationService;

    @Autowired
    public TransportationController( TransportationService transportationService) {
        this.transportationService = transportationService;
    }

    @PostMapping(value = "/enter/{Id}")
    public void transportVehicleToSlot(@PathParam("Id") Long vehicleSlotMappingId){
        log.info("TransportationController:transportVehicleToSlot:Init...");
        transportationService.transportVehicleToSlot(vehicleSlotMappingId);
    }

    @PostMapping(value = "/exit/{Id}")
    public void transportVehicleToEntrance(@PathParam("Id") Long vehicleSlotMappingId){
        log.info("TransportationController:transportVehicleToEntrance:Init...");
        transportationService.transportVehicleToEntrance(vehicleSlotMappingId);
    }
}
