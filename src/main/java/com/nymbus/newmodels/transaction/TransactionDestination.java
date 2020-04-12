package com.nymbus.newmodels.transaction;

import com.nymbus.newmodels.transaction.enums.DestinationType;
import lombok.Data;

@Data
public class TransactionDestination {
    private DestinationType sourceType;
    private String accountNumber;
    private String transactionCode;
    private String notes;
    private double amount;
}
