package com.nymbus.newmodels.client.other.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Frequency {
    ANNUAL("Annual"),
    BI_MONTHLY("Bi-monthly"),
    BI_WEEKLY("Bi-weekly"),
    DAILY("Daily"),
    MONTHLY("Monthly"),
    ONE_TIME_ONLY("One Time Only"),
    QUARTERLY("Quarterly"),
    SEMI_ANNUAL("Semi-annual"),
    SEMI_MONTHLY("Semi-monthly"),
    WEEKLY("Weekly");

    private final String frequencyType;
}