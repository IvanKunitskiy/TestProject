package com.nymbus.newmodels.client.other.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BankBranch {
    INSPIRE_BRISTOL("Inspire - Bristol"),
    INSPIRE_LANGHORNE("Inspire - Langhorne"),
    INSPIRE_NEWTOWN("Inspire - Newtown");

    private final String bankBranch;
}
