package com.hello2chandan.studentms.model;

public class Error implements Result {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
