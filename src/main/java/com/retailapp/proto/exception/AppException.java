package com.retailapp.proto.exception;

public class AppException extends Exception {
    
    private String message;

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }

    public AppException(String message) {
        this.message = message;
    }

}
