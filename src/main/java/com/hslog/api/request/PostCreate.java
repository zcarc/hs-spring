package com.hslog.api.request;

import com.hslog.api.message.ValidationMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PostCreate {

    @NotBlank(message = ValidationMessage.MSG_VAL_TITLE_REQUIRED)
    private final String title;

    @NotBlank(message = ValidationMessage.MSG_VAL_CONTENT_REQUIRED)
    private final String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
