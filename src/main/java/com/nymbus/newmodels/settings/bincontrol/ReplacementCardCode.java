package com.nymbus.newmodels.settings.bincontrol;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReplacementCardCode {
    ACCOUNT_ANALYSIS("Account Analysis"),
    ACCOUNT_FEE("Account Fee"),
    ACCOUNT_RECONCILIATION("Account Reconciliation"),
    ATM_CARD_REPLACEMENT("ATM Card Replacement"),
    ACTIVITY_FEE("Activity Fee");

    private final String replacementCardCode;
}
