package com.nymbus.newmodels.transaction;

import com.nymbus.newmodels.transaction.enums.Denominations;
import com.nymbus.newmodels.transaction.enums.SourceType;
import lombok.Data;

import java.util.HashMap;

@Data
public class TransactionSource {
    private SourceType sourceType;
    private String routingNumber;
    private String accountNumber;
    private String transactionCode;
    private String checkNumber;
    private String notes;
    private double amount;
    private HashMap<Denominations, Double> denominationsHashMap;
}