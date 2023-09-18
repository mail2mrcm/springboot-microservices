package com.hello2chandan.paymentms.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="ms_payment")
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer studentId;
    private String description;
    private Double amount;
    private String status;
    public Integer getId() {
        return id;
    }
    public Integer getStudentId() {
        return studentId;
    }
    public String getDescription() {
        return description;
    }
    public Double getAmount() {
        return amount;
    }
    public String getStatus() {
        return status;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
