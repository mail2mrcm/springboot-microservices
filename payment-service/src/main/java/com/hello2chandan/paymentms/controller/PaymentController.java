package com.hello2chandan.paymentms.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hello2chandan.paymentms.model.Payment;
import com.hello2chandan.paymentms.model.Result;
import com.hello2chandan.paymentms.repository.PaymentRepository;

@RestController
public class PaymentController {

    @Autowired
    private PaymentRepository payentRepo;

    private Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping("/")
    // @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Result<Payment>> createPayment(@RequestBody Payment payment) {
        logger.info("Start of createPayment");
        Result<Payment> result = new Result<>();
        Payment newPayment = payentRepo.saveAndFlush(payment);
        result.setData(newPayment);
        result.setStatus(HttpStatus.OK.value());
        logger.info("End of createPayment");
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    // @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Result<Payment>> makePayment(@RequestBody Payment newPayment, @PathVariable Integer id) {
        Optional<Payment> paymentBucket = payentRepo.findById(id);
        Result<Payment> result = new Result<>();
        if (paymentBucket.isPresent()) {
            Payment payment = paymentBucket.get();
            payment.setStatus(newPayment.getStatus());
            payentRepo.saveAndFlush(payment);
            result.setData(newPayment);
            result.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok().body(result);
        }
        result.setStatus(HttpStatus.BAD_REQUEST.value());
        result.setMessage("Bad request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }

    @GetMapping("/")
    // @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Result<List<Payment>>> getPaymensByStudentIdAndStatus(
            @RequestParam("studentid") Integer studentId,
            @RequestParam("status") String status) {
        Result<List<Payment>> result = new Result<>();
        List<Payment> payments = payentRepo.findByStudentId(studentId);
        List<Payment> paidList = payments//
                .stream()//
                .filter(p -> p.getStatus().equalsIgnoreCase(status))//
                .collect(Collectors.toList());
        if (paidList.size() > 0) {
            result.setData(paidList);
            return ResponseEntity.ok().body(result);
        }
        result.setMessage("Data not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result<String>> deleteByStudentId(@PathVariable Integer id) {
        payentRepo.deleteById(id);
        Result<String> result = new Result<>();
        result.setMessage("Deleted successfuly");
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/student/{studentId}")
    public List<Payment> getPaymentDetailsByStudentId(@PathVariable Integer studentId) {
        logger.info("Start of getPaymentDetailsByStudentId");
        List<Payment> payments = payentRepo.findByStudentId(studentId);
        logger.info("End of getPaymentDetailsByStudentId");
        return payments;
    }
}
