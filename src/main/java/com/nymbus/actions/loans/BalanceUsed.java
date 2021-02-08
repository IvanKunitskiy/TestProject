package com.nymbus.actions.loans;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BalanceUsed {
    COMMITMENT_AMOUNT("Commitment Amount"),
    CURRENT_BALANCE("Current Balance"),
    ORIGINAL_BALANCE("Original Balance");

    private final String balanceUsed;
}
