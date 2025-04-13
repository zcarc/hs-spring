package com.hslog.api.controller;

import com.hslog.api.message.ValidationMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("/api/posts 요청시 posts를 출력한다.")
    void test() throws Exception {
        // expected
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"글 제목\", \"content\": \"내용\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("posts"))
                .andDo(print());
    }

    @Test
    @DisplayName("/api/posts 요청시 title 값은 필수다.")
    void test2() throws Exception {
        // expected
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": null, \"content\": \"내용\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ValidationMessage.ERROR_CODE))
                .andExpect(jsonPath("$.message").value(ValidationMessage.ERROR_MESSAGE))
                .andExpect(jsonPath("$.errorValidations[0].errorMessage").value(ValidationMessage.TITLE_REQUIRED))
                .andDo(print());
    }
}