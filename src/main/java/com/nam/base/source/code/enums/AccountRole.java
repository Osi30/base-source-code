package com.nam.base.source.code.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountRole {
    CUSTOMER(0),
    EMPLOYEE(1),
    ADMINISTRATOR(2);

    private final int value;
}
