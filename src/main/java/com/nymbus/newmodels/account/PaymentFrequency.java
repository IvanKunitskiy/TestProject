package com.nymbus.newmodels.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentFrequency {
    QUARTERLY("Quarterly"),
    MONTHLY("Monthly"),
    ANNUAL("Annual"),
    BI_MONTHLY("Bi-monthly"),
    BI_WEEKLY("Bi-weekly"),
    AT_MATURITY("At Maturity");

    private final String paymentFrequency;
}
