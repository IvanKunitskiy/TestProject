package com.nymbus.newmodels.transaction.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionCode {
    WITHDRAW_AND_CLOSE("127 - Withdraw&Close"),
    WITHDRAW_AND_CLOSE_SAVINGS("227 - Withdraw&Close"),
    INT_DEPOSIT("107 - Int Deposit"),
    INT_DEPOSIT_SAVINGS("207 - Int Deposit");
    private final String transCode;
}