package com.retailapp.proto.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ApiResponse<T> {
    
    public static final String OK = "ok";
    public static final String ERROR = "error";
    private String status;

    @JsonInclude(Include.NON_NULL)
    private String errorCode;

    @JsonInclude(Include.NON_NULL)
    private String errorMessage;

    private T payload;

    public String getStatus() {
        return this.status;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public T getPayload() {
        return this.payload;
    }

    private ApiResponse(String status, String errorCode, String errorMessage, T payload) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.payload = payload;
    }

    public static <P> ApiResponse<P> ok(P payload) {
        return new ApiResponse<P>(OK, null, null, payload);
    }

    public static <P> ApiResponse<P> error(String errorCode, String errorMessage) {
        return new ApiResponse<P>(ERROR, errorCode, errorMessage, null);
    }

    public static <P> ApiResponse<P> error(String errorCode, String errorMessage, P payload) {
        return new ApiResponse<P>(ERROR, errorCode, errorMessage, payload);
    }
}
