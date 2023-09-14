package com.hello2chandan.studentms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hello2chandan.studentms.client.PaymentClient;
import com.hello2chandan.studentms.model.Payment;
import com.hello2chandan.studentms.model.Student;

@RestController
public class StudentController {

    @Autowired
    private PaymentClient paymentClient;

    @GetMapping("/{id}")
    public Student addStudent(@PathVariable Integer id) {
        Student student = new Student();
        student.setId(1);
        student.setName("chandan");
        return student;
    }
    @GetMapping("/{id}/with-payments")
    public Student findById(@PathVariable Integer id) {
        Student student = new Student();
        student.setId(1);
        student.setName("chandan");
        List<Payment> paymentList = paymentClient.getPaymentDetailsByStudentId(student.getId());
        student.setPayment(paymentList);
        return student;
    }

}
