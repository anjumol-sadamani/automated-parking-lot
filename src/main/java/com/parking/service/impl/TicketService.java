package com.parking.service.impl;

import com.parking.dto.TicketDto;
import com.parking.model.VehicleSlotMapping;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    public TicketDto createTicket(VehicleSlotMapping vehicleSlotMapping) {
        TicketDto.TicketDtoBuilder ticketBuilder = new TicketDto.TicketDtoBuilder(vehicleSlotMapping.getSlot().getId(),
                vehicleSlotMapping.getFloor().getRate());

        if (vehicleSlotMapping.getVehicle().isFreeWashAvailable()) {
            ticketBuilder.withGift("Gift: Car Wash");
        }
        return ticketBuilder.build();
    }
}
