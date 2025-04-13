package com.hslog.api.message;

import org.springframework.http.HttpStatus;

public class ValidationMessage {
    public static final int ERROR_CODE = HttpStatus.BAD_REQUEST.value();
    public static final String ERROR_MESSAGE = "잘못된 요청입니다.";
    public static final String TITLE_REQUIRED = "타이틀을 입력해주세요.";

}
