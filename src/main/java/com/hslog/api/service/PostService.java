package com.hslog.api.service;

import com.hslog.api.domain.Post;
import com.hslog.api.mapper.PostMapper;
import com.hslog.api.message.ExceptionMessage;
import com.hslog.api.repository.PostRepository;
import com.hslog.api.request.PostCreate;
import com.hslog.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public Long write(PostCreate postCreate) {
        // postCreate 를 엔티티 클래스로 변환 필요
        Post post = postMapper.toPost(postCreate);
        Post saved = postRepository.save(post);
        return saved.getId();
    }

    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMessage.MSG_EX_NOT_FOUND_POST));

        return postMapper.toPostResponse(post);
    }

    public List<PostResponse> getPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toPostResponse)
                .collect(Collectors.toList());
    }
}
