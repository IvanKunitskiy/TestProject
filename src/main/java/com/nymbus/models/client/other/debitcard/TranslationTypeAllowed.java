package com.nymbus.models.client.other.debitcard;

public enum TranslationTypeAllowed {
    BOTH_PIN_AND_SIGNATURE("Both PIN & Signature"),
    PIN_TRANSACTIONS_ONLY("PIN transactions only"),
    SIGNATURE_TRANSACTIONS_ONLY("Signature transactions only");

    private final String translationTypeAllowed;

    TranslationTypeAllowed(String translationTypeAllowed) {
        this.translationTypeAllowed = translationTypeAllowed;
    }

    public String getTranslationTypeAllowed() {
        return translationTypeAllowed;
    }
}
