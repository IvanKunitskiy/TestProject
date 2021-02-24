package com.nymbus.newmodels.account.loanaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RateChangeFrequency {
    NONE("None"),
    MONTHLY("Monthly"),
    DAILY("Daily"),
    ANNUAL("Annual");

    private final String rateChangeFrequency;
}
