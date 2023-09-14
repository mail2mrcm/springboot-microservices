package com.hello2chandan.paymentms.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hello2chandan.paymentms.model.Payment;

@RestController
public class PaymentController {
    
    @GetMapping("/student/{studentId}")
    public List<Payment> getPaymentDetailsByStudentId(@PathVariable Integer studentId) {
        List<Payment> payments = new ArrayList<>();
        Payment payment = new Payment();
        payment.setId(1);
        payment.setName("School Fees");
        payment.setStudentId(1);
        payments.add(payment);

        payment = new Payment();
        payment.setId(1);
        payment.setName("School Fees");
        payment.setStudentId(1);
        payments.add(payment);
        return payments;
    }
}
