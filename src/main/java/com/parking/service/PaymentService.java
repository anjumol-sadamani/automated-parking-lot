package com.parking.service;

import org.springframework.stereotype.Service;

@Service
public interface PaymentService {

     void makePayment(Long vehicleSlotMappingId);
}
