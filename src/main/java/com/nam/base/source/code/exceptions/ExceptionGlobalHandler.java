package com.nam.base.source.code.exceptions;

import com.nam.base.source.code.dtos.BaseResponse;
import com.nam.base.source.code.exceptions.exceptions.AccountException;
import com.nam.base.source.code.exceptions.exceptions.AuthException;
import com.nam.base.source.code.exceptions.exceptions.EmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionGlobalHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<BaseResponse> handleAuthException(AuthException ex, WebRequest request) {
        BaseResponse exceptionResponse = BaseResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .data(request.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<BaseResponse> handleAccountException(AccountException ex, WebRequest request) {
        BaseResponse exceptionResponse = BaseResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .data(request.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<BaseResponse> handleEmailException(EmailException ex, WebRequest request) {
        BaseResponse exceptionResponse = BaseResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .data(request.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
