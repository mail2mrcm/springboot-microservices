package com.hello2chandan.studentms.controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.client.RestTemplate;

import com.hello2chandan.studentms.model.Payment;
import com.hello2chandan.studentms.model.Result;
import com.hello2chandan.studentms.model.School;
import com.hello2chandan.studentms.model.Student;
import com.hello2chandan.studentms.repository.StudentRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class StudentController {
    Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepo;

    @PostMapping("/")
    public ResponseEntity<Result<Student>> addStudent(@RequestBody Student student) {
        logger.debug("Start of addStudent");
        Student newStudent = studentRepo.saveAndFlush(student);
        Result<Student> result = new Result<>();
        result.setStatus(HttpStatus.OK.value());
        result.setData(newStudent);
        result.setStatus(HttpStatus.OK.value());
        logger.debug("End of addStudent");
        return ResponseEntity.ok(result);
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

    @GetMapping("/{id}")
    @CircuitBreaker(name = "student-service", fallbackMethod = "getSystemFailure")
    public ResponseEntity<Result<Student>> findById(@PathVariable Integer id) {
        logger.debug("Start of findById");
        Optional<Student> studentBuck = studentRepo.findById(id);
        Result<Student> result = new Result<>();
        if (studentBuck.isPresent()) {
            Student student = studentBuck.get();
            List<Payment> paymentResult = restTemplate.getForObject("http://payment-service/student/{id}",
                    List.class, id);
            student.setPayments(paymentResult);
            int schoolId = student.getSchoolId();
            ResponseEntity<School> schoolResult = restTemplate.getForEntity("http://school-service/{schoolId}",
                    School.class, schoolId);
            if (null != schoolResult) {
                /*School school = schoolResult.getData();*/
                student.setSchool(schoolResult.getBody());
            }
            result.setData(student);
            result.setStatus(HttpStatus.OK.value());
            logger.debug("End of findById");
            return ResponseEntity.ok(result);
        }
        result.setStatus(HttpStatus.NOT_FOUND.value());
        result.setMessage("Data Not Found");
        logger.debug("End of findById");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    /*
     * @GetMapping("/with-payments")
     * 
     * @CircuitBreaker(name = "student-service", fallbackMethod = "fallbackMethod")
     * public ResponseEntity<Result<List<Student>>> findAll() {
     * Result<List<Student>> result = new Result<>();
     * List<Student> studentList = studentRepo.findAll();
     * studentList.forEach(student -> {
     * int studentId = student.getId();
     * List<Payment> payments =
     * restTemplate.getForObject("http://payment-service/student/{studentId}",
     * List.class, studentId);
     * student.setPayments(payments);
     * studentList.add(student);
     * });
     * if (studentList.size() > 0) {
     * result.setData(studentList);
     * result.setStatus(HttpStatus.OK.value());
     * return ResponseEntity.ok(result);
     * }
     * result.setStatus(HttpStatus.NOT_FOUND.value());
     * result.setMessage("Data Not Found");
     * return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
     * }
     */

    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "student-service", fallbackMethod = "getSystemFailure")
    public ResponseEntity<Result<Integer>> deleteById(@PathVariable Integer id) {
        Result<Integer> result = new Result<>();
        restTemplate.delete("http://payment-service/student/{id}", List.class, id);
        studentRepo.deleteById(id);
        result.setMessage("Deleted Successfuly");
        result.setData(id);
        result.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    @CircuitBreaker(name = "student-service", fallbackMethod = "getSystemFailure")
    public ResponseEntity<Result<Student>> updateStudent(@RequestBody Student student, @PathVariable Integer id) {
        Result<Student> result = new Result<>();
        Optional<Student> currentStudent = studentRepo.findById(id);
        if (currentStudent.isPresent()) {
            Student currentStudentData = currentStudent.get();
            currentStudentData.setFirstName(student.getFirstName());
            currentStudentData.setLastName(student.getLastName());
            currentStudentData.setGender(student.getGender());
            currentStudentData.setSchoolId(student.getSchoolId());
            studentRepo.saveAndFlush(currentStudentData);

            result.setData(currentStudentData);
            result.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok(result);
        }
        result.setStatus(HttpStatus.BAD_REQUEST.value());
        result.setMessage("Unable to Update");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    public ResponseEntity<String> getSystemFailure() {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Please try after sometime");
    }    

}
