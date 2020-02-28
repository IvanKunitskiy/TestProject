package com.nymbus.models.client.other.debitcard;

public enum CardStatus {
    ACTIVE("Active"),
    WAITIBG_TO_BECOME_ACTIVE("Waiting to become active");

    private final String cardStatus;

    CardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getCardStatus() {
        return cardStatus;
    }
}
