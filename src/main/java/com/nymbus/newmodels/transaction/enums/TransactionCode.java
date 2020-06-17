package com.nymbus.newmodels.transaction.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionCode {
    WITHDRAW_AND_CLOSE("127 - Withdraw&Close");
    private final String transCode;
}