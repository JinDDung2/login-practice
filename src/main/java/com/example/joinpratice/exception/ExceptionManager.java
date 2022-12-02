package com.example.joinpratice.exception;

import com.example.joinpratice.controller.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> userExceptionHandler(UserException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(Response.error(e.getErrorCode().getMessage()));
    }

}
