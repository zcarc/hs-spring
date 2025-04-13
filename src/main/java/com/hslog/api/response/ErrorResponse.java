package com.hslog.api.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * code : 400
 * message : "잘못된 요청입니다."
 * validation: {
 * title: "값을 입력해주세요"
 * }
 */

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final int code;
    private final String message;

    private final List<ErrorValidation> errorValidations = new ArrayList<>();

    public void addValidation(final String fieldName, final String errorMessage) {
        errorValidations.add(new ErrorValidation(fieldName, errorMessage));
    }
}
