package com.hello2chandan.paymentms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import com.hello2chandan.paymentms.model.Payment;

@Component
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    
}
