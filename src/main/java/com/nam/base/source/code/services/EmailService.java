package com.nam.base.source.code.services;

import com.nam.base.source.code.dtos.request.EmailRequest;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async
    void sendHtmlEmail(EmailRequest request);
}
