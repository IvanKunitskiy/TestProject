package com.nymbus.newmodels.transaction.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ATMTransactionType {
    ATM_DEPOSIT_ONUS_108("108 ATM Deposit ONUS"),
    ATM_WITHDRAWAL_224("224 ATM Withdrawal ONUS");
    private final String atmTransactionType;
}
