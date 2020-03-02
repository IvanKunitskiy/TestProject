package com.nymbus.models.settings.bincontrol;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReplacementCardCode {
    ACCOUNT_ANALYSIS("Account Analysis"),
    ACCOUNT_FEE("Account Fee"),
    ACCOUNT_RECONCILIATION("Account Reconciliation"),
    ACTIVITY_FEE("Activity Fee");

    private final String replacementCardCode;
}
