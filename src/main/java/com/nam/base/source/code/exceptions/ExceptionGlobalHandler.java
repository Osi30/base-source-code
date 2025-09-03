package com.nam.base.source.code.exceptions;

import com.nam.base.source.code.dtos.BaseResponse;
import com.nam.base.source.code.exceptions.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<BaseResponse> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        BaseResponse exceptionResponse = BaseResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .data(request.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        BaseResponse exceptionResponse = BaseResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .data(request.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        BaseResponse exceptionResponse = BaseResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .data(request.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RoleException.class)
    public ResponseEntity<BaseResponse> handleRoleException(RoleException ex, WebRequest request) {
        BaseResponse exceptionResponse = BaseResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.BAD_REQUEST.value())
                .data(request.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<BaseResponse> handleAuthorizationDeniedException(AuthorizationDeniedException ex, WebRequest request) {
        BaseResponse exceptionResponse = BaseResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.FORBIDDEN.value())
                .data(request.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BaseResponse> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        BaseResponse exceptionResponse = BaseResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.FORBIDDEN.value())
                .data(request.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<BaseResponse> handleNullPointerException(NullPointerException ex, WebRequest request) {
        BaseResponse exceptionResponse = BaseResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.FORBIDDEN.value())
                .data(request.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<BaseResponse> handleProductException(ProductException ex, WebRequest request) {
        BaseResponse exceptionResponse = BaseResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.FORBIDDEN.value())
                .data(request.getDescription(false))
                .build();
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }
}
