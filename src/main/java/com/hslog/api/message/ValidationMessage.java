package com.hslog.api.message;

import org.springframework.http.HttpStatus;

public class ValidationMessage {
    public static final int MSG_VAL_ERROR_CODE = HttpStatus.BAD_REQUEST.value();
    public static final String MSG_VAL_ERROR_MESSAGE = "잘못된 요청입니다.";
    public static final String MSG_VAL_TITLE_REQUIRED = "타이틀을 입력해주세요.";
    public static final String MSG_VAL_CONTENT_REQUIRED = "컨텐츠를 입력해주세요.";

}
