package com.nymbus.newmodels.account.loanaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum InterestMethod {
    SIMPLE_INTEREST("Simple Interest"),
    AMORTIZED("Amortized"),
    RULE_OF_78("rule of 78's");

    private final String interestMethod;
}
