package com.nam.base.source.code.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DefaultRole {
    CUSTOMER("customer"),
    EMPLOYEE("employee"),
    ADMIN("admin");

    private final String detail;
}
