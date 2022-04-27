package com.parking.controller;

import com.parking.dto.TicketDto;
import com.parking.dto.VehicleDto;
import com.parking.exceptionhandler.BadRequestException;
import com.parking.service.ParkingService;
import com.parking.validation.InputValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/automatic-parking")
public class ParkingController {

    private final ParkingService parkingService;

    private final InputValidation inputValidation;

    @Autowired
    public ParkingController(ParkingService parkingService, InputValidation inputValidation) {
        this.parkingService = parkingService;
        this.inputValidation = inputValidation;
    }

    @PostMapping(value = "/allocate/{entrance}")
    public ResponseEntity<TicketDto> allocateSlot(@PathVariable("entrance") String entrance, @Valid @RequestBody VehicleDto vehicle) {
       if(!inputValidation.isValidEntrance(entrance))
           throw new BadRequestException("Invalid Entrance");
        TicketDto ticketDto = parkingService.allocateSlot(entrance, vehicle);
        return new ResponseEntity<>(ticketDto, HttpStatus.OK);
    }

}
