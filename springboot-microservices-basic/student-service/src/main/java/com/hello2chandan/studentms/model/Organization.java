package com.hello2chandan.studentms.model;


public class Organization {
    private Integer id;
    private String name;
    private String location;
    private String owner;
    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getLocation() {
        return location;
    }
    public String getOwner() {
        return owner;
    }
}
