package com.nymbus.newmodels.account.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountType {
    CHK("CHK"),
    REGULAR_SAVINGS("Regular savings"),
    CD("CD"),
    IRA("IRA");

    private final String accountType;
}
