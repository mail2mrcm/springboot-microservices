package com.hello2chandan.studentms.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hello2chandan.studentms.model.Payment;

@FeignClient(name = "payment-service")
public interface PaymentClient {
  
    @GetMapping("/student/{studentId}")
    Payment getPaymentDetailsByStudentId(@PathVariable Integer studentId);

    @PostMapping("/")
    Payment createPayment(@RequestBody Payment payment);
}
