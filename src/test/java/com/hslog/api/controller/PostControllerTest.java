package com.hslog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hslog.api.domain.Post;
import com.hslog.api.message.ValidationMessage;
import com.hslog.api.repository.PostRepository;
import com.hslog.api.request.PostCreate;
import com.hslog.api.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/api/posts 글작성시 요청시 void를 반환한다.")
    void apiPosts_Print_String() throws Exception {

        // given
        PostCreate postCreate = PostCreate.builder()
                .title("글 제목")
                .content("내용")
                .build();

        String jsonString = new ObjectMapper().writeValueAsString(postCreate);

        // expected
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("/api/posts 요청시 title이 없으면 커스텀 에러메세지를 반환한다.")
    void apiPosts_Required_Title() throws Exception {

        // given
        PostCreate postCreate = PostCreate.builder()
                .content("내용")
                .build();

        String jsonString = new ObjectMapper().writeValueAsString(postCreate);

        // expected
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ValidationMessage.MSG_VAL_ERROR_CODE))
                .andExpect(jsonPath("$.message").value(ValidationMessage.MSG_VAL_ERROR_MESSAGE))
                .andExpect(jsonPath("$.errorValidations[0].errorMessage").value(ValidationMessage.MSG_VAL_TITLE_REQUIRED))
                .andDo(print());
    }

    @Test
    @DisplayName("/api/posts 요청시 DB에 값이 저장된다.")
    void apiPosts_Save_DB() throws Exception {

        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        String jsonString = new ObjectMapper().writeValueAsString(postCreate);

        // when
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().getFirst();
        assertEquals("제목", post.getTitle());
        assertEquals("내용", post.getContent());

    }

    @Test
    @DisplayName("/api/posts/{postId} 게시글 1개 조회")
    void apiPosts_get_post() throws Exception {

        // given
        PostCreate postCreate = PostCreate.builder()
                .title("123451234512345")
                .content("내용123 bar")
                .build();

        Post clientPost = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        Post savedPost = postRepository.save(clientPost);

        // expected (when + then)
        mockMvc.perform(get("/api/posts/{postId}", savedPost.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedPost.getId()))
                .andExpect(jsonPath("$.title").value("1234512345"))
                .andExpect(jsonPath("$.content").value("내용123 bar"))
                .andDo(print());
    }

    @Test
    @DisplayName("/api/posts 게시글 전체 조회")
    void getPosts() throws Exception {

        //given
        PostCreate postCreate1 = PostCreate.builder()
                .title("제목123 foo")
                .content("내용123 bar")
                .build();

        PostCreate postCreate2 = PostCreate.builder()
                .title("제목222 foo")
                .content("내용222 bar")
                .build();

        // expected
        Long savedId1 = postService.write(postCreate1);
        Long savedId2 = postService.write(postCreate2);

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(savedId1))
                .andExpect(jsonPath("$[0].title").value("제목123 foo"))
                .andExpect(jsonPath("$[0].content").value("내용123 bar"))
                .andExpect(jsonPath("$[1].id").value(savedId2))
                .andExpect(jsonPath("$[1].title").value("제목222 foo"))
                .andExpect(jsonPath("$[1].content").value("내용222 bar"))
                .andDo(print());

    }
}