package com.hslog.api.controller;

import com.hslog.api.request.PostCreate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequestMapping("/api")
@RestController
public class PostController {

    @PostMapping("/posts")
    public Map<String, String> posts(@RequestBody @Valid PostCreate params) {
        log.info("params={}", params);
         return Map.of();
    }
}
