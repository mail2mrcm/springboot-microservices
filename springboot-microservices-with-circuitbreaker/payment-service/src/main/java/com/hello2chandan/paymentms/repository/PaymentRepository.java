package com.hello2chandan.paymentms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.hello2chandan.paymentms.model.Payment;

import jakarta.transaction.Transactional;

@Component
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByStudentId(Integer studentId);
    
    @Transactional
    List<Payment> deleteByStudentId(Integer studentId);
}
