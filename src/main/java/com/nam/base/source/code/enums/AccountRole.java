package com.nam.base.source.code.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountRole {
    CUSTOMER(1),
    EMPLOYEE(2),
    ADMINISTRATOR(3);

    private final int value;
}
