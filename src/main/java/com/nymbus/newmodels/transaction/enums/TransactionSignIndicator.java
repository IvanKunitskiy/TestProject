package com.nymbus.newmodels.transaction.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionSignIndicator {
    ZERO("0"),
    CREDIT("C"),
    DEBIT("D");
    private final String signIndicator;
}