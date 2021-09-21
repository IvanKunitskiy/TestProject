package com.nymbus.newmodels.client.other.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AchBankAccountType {
    EXT_CHECKING("Ext. Checking"),
    EXT_LOAN("Ext. Loan"),
    EXT_SAVINGS("Ext. Savings");

    private final String transferType;
}
