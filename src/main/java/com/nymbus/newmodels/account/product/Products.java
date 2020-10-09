package com.nymbus.newmodels.account.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Products {
    CHK_PRODUCTS("CHK Products"),
    CD_PRODUCTS("CD Products"),
    SAVINGS_PRODUCTS("Savings Products");

    private String product;
}