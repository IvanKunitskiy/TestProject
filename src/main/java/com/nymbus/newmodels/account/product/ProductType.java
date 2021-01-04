package com.nymbus.newmodels.account.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductType {
    CD_ACCOUNT("CD Account"),
    SAVINGS_ACCOUNT("Savings Account"),
    CHK_ACCOUNT("CHK Account"),
    INCOMING_WIRE("incoming wire (g/l)"),
    OUTGOING_WIRE("outgoing wire (g/l)"),
    SAFE_DEPOSIT_BOX("Safe Deposit Box");

    public final String productType;
}
