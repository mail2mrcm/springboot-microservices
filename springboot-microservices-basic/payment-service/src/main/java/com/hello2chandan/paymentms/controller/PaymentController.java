package com.hello2chandan.paymentms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hello2chandan.paymentms.model.Payment;
import com.hello2chandan.paymentms.repository.PaymentRepository;

@RestController
public class PaymentController {

    @Autowired
    private PaymentRepository payentRepo;


    @PostMapping("/")
    public Payment createPayment(@RequestBody Payment payment) {
        return payentRepo.saveAndFlush(payment);        
    }

    @PutMapping("/{id}")
    public Payment makePayment(@RequestBody Payment newPayment, @PathVariable Integer id) {
        Optional<Payment> paymentBucket =  payentRepo.findById(id);
        if(paymentBucket.isPresent()){
          Payment payment =  paymentBucket.get();
          payment.setStatus("Paid");
          return payentRepo.saveAndFlush(payment);
        }
        return new Payment();
    }
    
    @GetMapping("/student/{studentId}")
    public Payment getPaymentDetailsByStudentId(@PathVariable Integer studentId) {
        Optional<Payment> students = payentRepo.findById(studentId);
        if (students.isPresent())
            return students.get();
        return new Payment();
    }
}
