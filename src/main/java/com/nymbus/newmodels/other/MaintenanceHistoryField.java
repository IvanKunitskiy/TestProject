package com.nymbus.newmodels.other;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MaintenanceHistoryField {
    ALLOW_FOREIGN_TRANSACTIONS("Allow Foreign Transactions"),
    TRANSACTION_TYPE_ALLOWED("Transaction Type Allowed"),
    ATM_DAILY_LIMIT_AMOUNT("ATM Daily Limit Amount"),
    ATM_DAILY_LIMIT_NUMBER("ATM Daily Limit Number"),
    BIN_NUMBER("Bin Number"),
    CARD_NUMBER("Card Number"),
    CARD_TRANSMITTED_TO_PROCESSORS("Card Transmitted To Processors"),
    CARD_TYPE("Card Type"),
    CHARGE_FOR_CARD_REPLACEMENT("Charge for Card Replacement"),
    DATE_CREATED("Date Created"),
    DATE_TRANSMITTED_TO_PROCESSORS("Date Transmitted To Processors"),
    DATE_EFFECTIVE("Date Effective"),
    EXPIRATION_DATE("Expiration Date"),
    INSTANT_ISSUE("Instant Issue"),
    NAME_ON_CARD("Name on Card"),
    CLIENT_NUMBER("Client Number"),
    PIN_OFFSET("Pin Offset"),
    DEBIT_PURCHASE_DAILY_LIMIT_AMOUNT("Debit Purchase Daily Limit Amount"),
    DEBIT_PURCHASE_DAILY_LIMIT_NUMBER("Debit Purchase Daily Limit Number"),
    CARD_STATUS("Card Status");

    private final String fieldName;
}
