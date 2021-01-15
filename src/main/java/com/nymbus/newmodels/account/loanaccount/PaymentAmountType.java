package com.nymbus.newmodels.account.loanaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentAmountType {
    INTEREST_ONLY("Interest Only (Bill)"),
    PRIN_AND_INT("Prin & Int (Bill)"),
    PRINCIPAL_AND_INTEREST("Principal & Interest");

    private final String paymentAmountType;
}
