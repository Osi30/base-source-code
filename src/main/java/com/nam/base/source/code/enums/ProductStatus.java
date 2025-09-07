package com.nam.base.source.code.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductStatus {
    INACTIVE("Not selling"),
    ACTIVE("Is selling"),
    DELETED("Is deleted"),
    BANNED("Is banned"),
    OUT_OF_STOCK("Is out of stock");
    ;

    private final String detail;
}
