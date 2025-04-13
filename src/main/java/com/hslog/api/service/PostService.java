package com.hslog.api.service;

import com.hslog.api.domain.Post;
import com.hslog.api.repository.PostRepository;
import com.hslog.api.request.PostCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        // postCreate 를 엔티티 클래스로 변환 필요
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }
}
