package com.hslog.api.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorValidation {

    private final String fieldName;
    private final String errorMessage;
}
