package com.nymbus.newmodels.transaction;

import com.nymbus.newmodels.transaction.enums.Denominations;
import com.nymbus.newmodels.transaction.enums.DestinationType;
import lombok.Data;

import java.util.HashMap;

@Data
public class TransactionDestination {
    private DestinationType sourceType;
    private String accountNumber;
    private String transactionCode;
    private String notes;
    private double amount;
    private HashMap<Denominations, Double> denominationsHashMap;
}
