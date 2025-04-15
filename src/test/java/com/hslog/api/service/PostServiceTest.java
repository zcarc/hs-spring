package com.hslog.api.service;

import com.hslog.api.domain.Post;
import com.hslog.api.mapper.PostMapper;
import com.hslog.api.repository.PostRepository;
import com.hslog.api.request.PostCreate;
import com.hslog.api.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void write() {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목111")
                .content("내용111")
                .build();

        //when
        postService.write(postCreate);

        //then
        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().getFirst();
        assertEquals("제목111", post.getTitle());
        assertEquals("내용111", post.getContent());

    }

    @Test
    @DisplayName("게시글 1개 조회")
    void getPost() {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목123 foo")
                .content("내용123 bar")
                .build();

        Post requestPost = postMapper.toPost(postCreate);
        postRepository.save(requestPost);

        //when
        PostResponse postResponse = postService.getPost(requestPost.getId());

        //then
        assertEquals(1L, postRepository.count());
        assertEquals("제목123 foo", postResponse.getTitle());
        assertEquals("내용123 bar", postResponse.getContent());
    }

    @Test
    @DisplayName("게시글 여러개 조회")
    void getPosts() {
        //given
        PostCreate postCreate1 = PostCreate.builder()
                .title("제목123 foo")
                .content("내용123 bar")
                .build();

        PostCreate postCreate2 = PostCreate.builder()
                .title("제목222 foo")
                .content("내용222 bar")
                .build();

        Post requestPost1 = postMapper.toPost(postCreate1);
        Post requestPost2 = postMapper.toPost(postCreate2);

        postRepository.saveAll(
                List.of(
                        requestPost1,
                        requestPost2
                )
        );

        //when
        List<PostResponse> posts = postService.getPosts();

        //then
        assertEquals(2, posts.size());
    }
}