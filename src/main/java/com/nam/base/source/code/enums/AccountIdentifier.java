package com.nam.base.source.code.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountIdentifier {
    EMAIL("email"),
    USERNAME("username"),
    PHONE("phone"),
    ALL("all"),
    ;

    private final String value;
}
