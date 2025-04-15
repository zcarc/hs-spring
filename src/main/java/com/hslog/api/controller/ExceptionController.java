package com.hslog.api.controller;

import com.hslog.api.message.ValidationMessage;
import com.hslog.api.response.ErrorResponse;
import com.hslog.api.response.ErrorValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        List<ErrorValidation> errorValidations = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            ErrorValidation errorValidation = ErrorValidation.builder()
                    .fieldName(fieldError.getField())
                    .errorMessage(fieldError.getDefaultMessage())
                    .build();
            errorValidations.add(errorValidation);
        }

        return ErrorResponse.builder()
                .code(ValidationMessage.MSG_VAL_ERROR_CODE)
                .message(ValidationMessage.MSG_VAL_ERROR_MESSAGE)
                .errorValidations(errorValidations)
                .build();
    }
}
