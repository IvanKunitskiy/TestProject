package com.nymbus.newmodels.account.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RateType {
    TIER("Tier"),
    FIXED("Fixed");

    private final String rateType;
}
