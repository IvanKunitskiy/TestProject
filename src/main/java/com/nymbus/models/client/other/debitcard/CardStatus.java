package com.nymbus.models.client.other.debitcard;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardStatus {
    ACTIVE("Active"),
    WAITING_TO_BECOME_ACTIVE("Waiting to become active");

    private final String cardStatus;
}
