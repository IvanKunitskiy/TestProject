package com.nymbus.newmodels.client.other.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransferType {
    BOX_RENTAL_PAYMENT("Box Rental Payment"),
    HIGH_BALANCE_TRANSFER("High Balance Transfer"),
    INSUFFICIENT_FUNDS_TRANSFER("Insufficient Funds Transfer"),
    LOAN_PAYMENT("Loan Payment"),
    LOW_BALANCE_TRANSFER("Low Balance Transfer"),
    TRANSFER("Transfer"),
    EXTERNAL_LOAN_PAYMENT("External Loan Payment");

    private final String transferType;
}