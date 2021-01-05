package com.nymbus.newmodels.account.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductType {
    CD_ACCOUNT("CD Account"),
    SAVINGS_ACCOUNT("Savings Account"),
    CHK_ACCOUNT("CHK Account"),
    SAFE_DEPOSIT_BOX("Safe Deposit Box"),
    LOAN_ACCOUNT("Loan Account");

    public final String productType;
}
