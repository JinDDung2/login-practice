package com.example.joinpratice.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    private String resultCode;
    private T result;

    public static Response<Void> error(String errorCode) {
        return new Response<>(errorCode, null);
    }
    // 제네릭 선언 방식은 2가지 선언하는거야. 1. 타입파라미터 2. 리턴타입
                // 1번
                            // 2번
    public static <T> Response<T> success(T result) {
        return new Response<>("SUCCESS", result);
    }
}
