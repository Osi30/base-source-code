package com.nam.base.source.code.services;

import com.nam.base.source.code.dtos.request.EmailRequest;

public interface EmailService {
    void sendHtmlEmail(EmailRequest request);
}
