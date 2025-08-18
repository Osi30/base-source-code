package com.nam.base.source.code.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailTemplate {
    VERIFY_EMAIL("verify-email", "Verify User Account"),
    RESET_PASSWORD("reset-password", "Reset Account Password"),
    ;
    private final String code;
    private final String subject;
}
