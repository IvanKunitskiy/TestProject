package com.nymbus.newmodels.client.other.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InterestFrequency {
    ANNUAL("Annual"),
    MONTHLY("Monthly"),
    ONE_TIME_PAY("One Time Pay"),
    QUARTER_END("Quarter End"),
    QUARTERLY("Quarterly"),
    SEMI_ANNUAL("Semi-Annual");

    private final String interestFrequency;
}
