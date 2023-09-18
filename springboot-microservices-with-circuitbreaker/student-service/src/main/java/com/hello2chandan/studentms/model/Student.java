package com.hello2chandan.studentms.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "ms_student")
public class Student implements Result {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer orgId;
    private String firstName;
    private String lastName;
    private String gender;
    @Transient
    private List<Payment> payments;

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
    public List<Payment> getPayments() {
        return payments;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Integer getId() {
        return id;
    }
    public Integer getOrgId() {
        return orgId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getGender() {
        return gender;
    }
}
