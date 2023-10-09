package com.hello2chandan.studentms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<Result<Student>> addStudent(@RequestBody Student student) {
        Student newStudent = studentRepo.saveAndFlush(student);
        Result<Student> result = new Result<>();
        result.setStatus(HttpStatus.OK.value());
        result.setData(newStudent);
        result.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result<Student>> getStudentbyId(@PathVariable Integer id) {
        Optional<Student> student = studentRepo.findById(id);
        Result<Student> result = new Result<>();
        if (student.isPresent()) {
            Student newStudent = student.get();
            result.setData(newStudent);
            result.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok().body(result);
        }
        result.setStatus(HttpStatus.NOT_FOUND.value());
        result.setMessage("Data Not Found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);

    }

    @GetMapping("/")
    public ResponseEntity<Result<List<Student>>> getAllStudent() {
        List<Student> students = studentRepo.findAll();
        Result<List<Student>> result = new Result<>();
        if (students.size() > 0) {
            result.setData(students);
            result.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok(result);

        }
        result.setStatus(HttpStatus.NOT_FOUND.value());
        result.setMessage("Data Not Found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/{id}/with-payments")
    @CircuitBreaker(name = "student-service", fallbackMethod = "fallbackMethod")
    public ResponseEntity<Result<Student>> findById(@PathVariable Integer id) {
        Optional<Student> studentBuck = studentRepo.findById(id);
        Result<Student> result = new Result<>();
        if (studentBuck.isPresent()) {
            Student student = studentBuck.get();
            List<Payment> payments = paymentClient.getPaymentDetailsByStudentId(id);
            student.setPayments(payments);
            result.setData(student);
            result.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok(result);
        }
        result.setStatus(HttpStatus.NOT_FOUND.value());
        result.setMessage("Data Not Found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @GetMapping("/with-payments")
    @CircuitBreaker(name = "student-service", fallbackMethod = "fallbackMethod")
    public ResponseEntity<Result<List<Student>>> findAll() {
        Result<List<Student>> result = new Result<>();
        List<Student> studentList = studentRepo.findAll();
        studentList.forEach(student -> {
            int studentId = student.getId();
            List<Payment> payments = paymentClient.getPaymentDetailsByStudentId(studentId);
            student.setPayments(payments);
            studentList.add(student);
        });
        if (studentList.size() > 0) {
            result.setData(studentList);
            result.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok(result);
        }
        result.setStatus(HttpStatus.NOT_FOUND.value());
        result.setMessage("Data Not Found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "student-service", fallbackMethod = "fallbackMethod")
    public ResponseEntity<Result<Integer>> deleteById(@PathVariable Integer id) {
        Result<Integer> result = new Result<>();
        paymentClient.deleteByStudentId(id);
        studentRepo.deleteById(id);
        result.setMessage("Deleted Successfuly");
        result.setData(id);
        result.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Result<Student>> updateStudent(@RequestBody Student student, @PathVariable Integer id) {
        Result<Student> result = new Result<>();
        Optional<Student> currentStudent = studentRepo.findById(id);
        if (currentStudent.isPresent()) {
            Student currentStudentData = currentStudent.get();
            currentStudentData.setFirstName(student.getFirstName());
            currentStudentData.setLastName(student.getLastName());
            currentStudentData.setGender(student.getGender());
            currentStudentData.setOrgId(student.getOrgId());
            studentRepo.saveAndFlush(currentStudentData);

            result.setData(currentStudentData);
            result.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok(result);
        }
        result.setStatus(HttpStatus.BAD_REQUEST.value());
        result.setMessage("Unable to Update");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    private com.hello2chandan.studentms.model.Error fallbackMethod(Exception e) {
        com.hello2chandan.studentms.model.Error error = new com.hello2chandan.studentms.model.Error();
        error.setMessage("Payment service is not responding properly");
        return error;
    }

}
