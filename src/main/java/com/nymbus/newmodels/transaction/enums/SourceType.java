package com.nymbus.newmodels.transaction.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SourceType {
    CASH_IN("Cash-In"),
    GL_DEBIT("GL Debit"),
    MISC_DEBIT("Misc Debit"),
    WITHDRAWL("Withdrawl"),
    UNVERIFIED("Unverified"),
    CHECK("Check");

    private final String sourceType;
}
