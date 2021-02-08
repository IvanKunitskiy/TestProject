package com.nymbus.actions.loans;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PenaltyCalculationType {
    FIXED_AMOUNT("Fixed Amount"),
    FIXED_PERCENTAGE("Fixed Percentage"),
    TIER_PERCENTAGE("Tier Percentage");

    private final String penaltyCalculationType;
}
