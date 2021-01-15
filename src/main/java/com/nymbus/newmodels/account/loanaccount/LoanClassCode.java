package com.nymbus.newmodels.account.loanaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoanClassCode {
    COMMERCIAL_LOAN("Commercial Loan"),
    CONSUMER_LOAN("Consumer Loan"),
    MORTGAGE_LOAN("Mortgage Loan");

    private final String loanClassCode;
}
