package com.nymbus.models.settings.bincontrol;

public enum ReplacementCardCode {
    ACCOUNT_ANALYSIS("Account Analysis"),
    ACCOUNT_FEE("Account Fee"),
    ACCOUNT_RECONCILIATION("Account Reconciliation"),
    ACTIVITY_FEE("Activity Fee");

    private final String replacementCardCode;

    ReplacementCardCode(String replacementCardCode) {
        this.replacementCardCode = replacementCardCode;
    }

    public String getReplacementCardCode() {
        return replacementCardCode;
    }
}
