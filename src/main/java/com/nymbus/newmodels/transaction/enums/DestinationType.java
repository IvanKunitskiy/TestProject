package com.nymbus.newmodels.transaction.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DestinationType {
    CASH_OUT("Cash-Out"),
    GL_CREDIT("GL Credit"),
    MISC_CREDIT("Misc Credit"),
    DEPOSIT("Deposit"),
    LOAN_PAYMENTS("Loan Payments");

    private final String destinationType;
}
