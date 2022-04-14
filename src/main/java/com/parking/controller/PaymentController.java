package com.parking.controller;

import com.parking.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
@Slf4j
@RequestMapping("v1/automatic/parking")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(value = "/payment/{Id}")
    public void makePayment(@PathParam("Id") Long vehicleSlotMappingId){
        log.info("PaymentController:makePayment:Init...");
        paymentService.makePayment(vehicleSlotMappingId);
    }

}
