package com.example.joinpratice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATED_USERNAME(HttpStatus.CONFLICT, "username이 중복되었습니다."),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, ""),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "");

    private HttpStatus httpStatus;
    private String message;

}
