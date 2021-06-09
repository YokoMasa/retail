package com.retailapp.proto.dto;

import java.util.List;

public class ValidationResultDTO {
    
    private List<FormErrorDTO> errors;
    private boolean hasError;

    public List<FormErrorDTO> getErrors() {
        return this.errors;
    }

    public boolean hasError() {
        return this.hasError;
    }

    public ValidationResultDTO(boolean hasError, List<FormErrorDTO> errors) {
        this.errors = errors;
        this.hasError = hasError;
    }

}
