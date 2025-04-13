package com.hslog.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorValidation {

    private final String fieldName;
    private final String errorMessage;

    @Builder
    public ErrorValidation(String fieldName, String errorMessage) {
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }
}
