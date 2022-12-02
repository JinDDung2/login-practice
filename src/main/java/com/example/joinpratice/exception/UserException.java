package com.example.joinpratice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException{

    private ErrorCode errorCode;
    private String message;

    @Override
    public String toString() {
        if(message== null) return errorCode.getMessage();
        return errorCode.getMessage() + message;
    }
}
