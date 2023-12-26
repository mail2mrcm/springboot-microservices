package com.hello2chandan.schoolms.model;

import java.io.Serializable;

public class Result<T> implements Serializable {

    private String message;
    private Integer status;
    private T data;


    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
