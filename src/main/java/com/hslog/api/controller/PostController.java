package com.hslog.api.controller;

import com.hslog.api.request.PostCreate;
import com.hslog.api.response.PostResponse;
import com.hslog.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void posts(@RequestBody @Valid PostCreate postCreate) {
        log.info("param postCreate={}", postCreate);
        postService.write(postCreate);
    }

    @GetMapping("/posts/{postId}")
    public PostResponse getPost(@PathVariable Long postId) {
        log.info("param postId={}", postId);
        return postService.getPost(postId);
    }

    @GetMapping("/posts")
    public List<PostResponse> getPosts() {
        return postService.getPosts();
    }

}
