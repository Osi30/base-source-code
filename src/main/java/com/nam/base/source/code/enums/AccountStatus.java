package com.nam.base.source.code.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountStatus {
    ACTIVE("active"),
    INACTIVE("inactive"),
    BANNED("banned"),
    DELETED("deleted");

    private final String name;
}
