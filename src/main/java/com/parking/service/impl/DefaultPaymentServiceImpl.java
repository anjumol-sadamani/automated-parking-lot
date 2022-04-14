package com.parking.service.impl;

import com.parking.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class DefaultPaymentServiceImpl implements PaymentService {
    @Override
    public void makePayment(Long vehicleSlotMappingId) {
        throw new UnsupportedOperationException("MakePayment method not implemented!!");
    }
}
