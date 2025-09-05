package com.nam.base.source.code.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    AWAITING_PAYMENT("Waiting Payment"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled"),
    ;

    private final String detail;
}
