package com.nam.base.source.code.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentMethod {
    CASH("cash"),
    VNPAY("vnpay"),
    MOMO("momo"),
    STRIPE("stripe")
    ;

    private final String code;
}
