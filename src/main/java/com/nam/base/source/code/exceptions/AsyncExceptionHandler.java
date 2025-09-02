package com.nam.base.source.code.exceptions;

import com.nam.base.source.code.dtos.request.EmailRequest;
import com.nam.base.source.code.exceptions.exceptions.EmailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... params) {
        // Log exception overall
        log.error("Async method '{}' with parameters {} threw an exception.",
                method.getName(), Arrays.toString(params), throwable);

        // Log exception details
        if (throwable instanceof EmailException) {
            logEmailException(params);
        }
    }

    private void logEmailException(Object... params) {
        if (params.length > 0 && params[0] instanceof EmailRequest emailRequest) {
            log.error("Email Destination: {}, Template: {}",
                    emailRequest.getTo(), emailRequest.getEmailTemplate());
        }
    }
}
