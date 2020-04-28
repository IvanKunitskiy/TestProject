package com.nymbus.newmodels.transaction.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GLFunctionValue {
    DEPOSIT_ITEM("2.-1.0");

    private final String glFunctionValue;
}