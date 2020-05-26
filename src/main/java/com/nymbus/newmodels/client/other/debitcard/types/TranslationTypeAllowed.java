package com.nymbus.newmodels.client.other.debitcard.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TranslationTypeAllowed {
    BOTH_PIN_AND_SIGNATURE("Both PIN & Signature"),
    PIN_TRANSACTIONS_ONLY("PIN transactions only"),
    SIGNATURE_TRANSACTIONS_ONLY("Signature transactions only");

    private final String translationTypeAllowed;
}
