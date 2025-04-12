package com.hslog.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class PostController {

    @GetMapping("/posts")
    public String posts() {
        return "posts";
    }
}
