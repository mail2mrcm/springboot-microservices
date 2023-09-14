package com.hello2chandan.paymentms.model;

public class Payment {
    private Integer id;
    private String name;
    private Integer studentId;

    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Integer getStudentId() {
        return studentId;
    }
}
