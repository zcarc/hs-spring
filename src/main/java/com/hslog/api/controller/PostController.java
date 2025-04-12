package com.hslog.api.controller;

import com.hslog.api.request.PostCreate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/api")
@RestController
public class PostController {

    @PostMapping("/posts")
    public Map<String, String> posts(@RequestBody @Valid PostCreate params,
                                     BindingResult bindingResult) {
        log.info("params={}", params);

        if(bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            FieldError firstFieldError = fieldErrors.getFirst();
            String fieldName = firstFieldError.getField(); // title
            String errorMessage = firstFieldError.getDefaultMessage(); // 에러 메세지

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            return error;
        }

         return Map.of();
    }
}
