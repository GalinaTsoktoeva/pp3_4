package com.example.demo.exceptions;

import java.util.Date;

public class AppError {
    private int status;
    private String message;
    private Date timestamp;

    public AppError(int status, String message) {
        this.timestamp = new Date();
        this.status = status;
        this.message = message;


    }
}
