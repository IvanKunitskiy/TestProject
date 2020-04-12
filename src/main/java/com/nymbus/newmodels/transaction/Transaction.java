package com.nymbus.newmodels.transaction;


import lombok.Data;

@Data
public class Transaction {
    private String transactionDate;
    private TransactionSource transactionSource;
    private TransactionDestination transactionDestination;
}
