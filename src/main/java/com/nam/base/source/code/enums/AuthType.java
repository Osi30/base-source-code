package com.nam.base.source.code.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthType {
    PASSWORD("password"),
    GOOGLE("google")
    ;
    private final String type;
}
