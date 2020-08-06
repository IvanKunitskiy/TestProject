package com.nymbus.newmodels.client.other.debitcard.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardDesign {
    CRD("CRD"),
    IBM("IBM");

    private final String cardDesign;
}
