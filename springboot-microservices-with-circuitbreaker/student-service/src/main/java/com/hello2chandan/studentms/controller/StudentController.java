package com.hello2chandan.studentms.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hello2chandan.studentms.client.PaymentClient;
import com.hello2chandan.studentms.model.Payment;
import com.hello2chandan.studentms.model.Result;
import com.hello2chandan.studentms.model.Student;
import com.hello2chandan.studentms.repository.StudentRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class StudentController {

    @Autowired
    private PaymentClient paymentClient;

    @Autowired
    private StudentRepository studentRepo;

    @PostMapping("/")
    public Student addStudent(@RequestBody Student student) {
        studentRepo.saveAndFlush(student);
        return student;
    }

    @GetMapping("/{id}/with-payments")
    @CircuitBreaker(name ="student-service", fallbackMethod ="fallbackMethod")
    public Result findById(@PathVariable Integer id) {
        Optional<Student> studentBuck = studentRepo.findById(id);
        if(studentBuck.isPresent()){
            Student student = studentBuck.get();
            Payment payment = paymentClient.getPaymentDetailsByStudentId(id);
            List<Payment> payments = new ArrayList<>();
            payments.add(payment);
            student.setPayments(payments);
            return student;
        }
        return new Student();        
    }

    private com.hello2chandan.studentms.model.Error fallbackMethod(Exception e) {
        com.hello2chandan.studentms.model.Error error = new com.hello2chandan.studentms.model.Error();
        error.setMessage("Payment service is not responding properly");
        return error;
    }

}
