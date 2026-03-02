package com.example.barber.config;

import org.springframework.http.HttpStatus;

public class ApiResponse {
    private Object data;
    private HttpStatus status;
    private String message;
    private boolean error;

    private int total;

    public ApiResponse(int total, HttpStatus status, String message) {
        this.total = total;
        this.status = status;
        this.message = message;
    }

    public ApiResponse(Object data, HttpStatus status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public ApiResponse(HttpStatus status, String message, boolean error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
