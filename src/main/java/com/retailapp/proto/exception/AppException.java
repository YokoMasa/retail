package com.retailapp.proto.exception;

public class AppException extends Exception {

    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }
    
    @Override
    public String toString() {
        return getMessage();
    }

    public AppException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}