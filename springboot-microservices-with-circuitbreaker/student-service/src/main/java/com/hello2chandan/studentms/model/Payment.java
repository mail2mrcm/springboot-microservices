package com.hello2chandan.studentms.model;

public class Payment {
   
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
