package com.hslog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hslog.api.domain.Post;
import com.hslog.api.message.ValidationMessage;
import com.hslog.api.repository.PostRepository;
import com.hslog.api.request.PostCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/api/posts 요청시 String을 출력한다.")
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
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    @DisplayName("/api/posts 요청시 title 값은 필수다.")
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
                .andExpect(jsonPath("$.code").value(ValidationMessage.ERROR_CODE))
                .andExpect(jsonPath("$.message").value(ValidationMessage.ERROR_MESSAGE))
                .andExpect(jsonPath("$.errorValidations[0].errorMessage").value(ValidationMessage.TITLE_REQUIRED))
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
}