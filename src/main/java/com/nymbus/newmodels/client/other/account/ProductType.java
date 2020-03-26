package com.nymbus.newmodels.client.other.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductType {
    CD_ACCOUNT("CD Account"),
    CHK_ACCOUNT("CHK Account"),
    LOAN_ACCOUNT("Loan Account"),
    SAFE_DEPOSIT_BOX("Safe Deposit Box"),
    SAVINGS_ACCOUNT("Savings Account");

    private final String productType;
}
