package com.example.edutopia_res.Services;

import com.example.edutopia_res.Iservices.IpaymentService;
import com.example.edutopia_res.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class PaymentService implements IpaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
/*
        public void savePayment(PaymentRequest paymentRequest, Charge charge) {
            Payment payment = new Payment();
            payment.setCurrency(charge.getCurrency());
            payment.setAmount(charge.getAmount());
            payment.setStripeChargeId(charge.getId());
            payment.setPaymentSourceToken(paymentRequest.getToken());
            paymentRepository.save(payment);
        }*/
}
