package com.nymbus.newmodels.generation.tansactions.factory;

import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.enums.DestinationType;

public class DestinationFactory {

    public static TransactionDestination getCashOutDestination() {
        TransactionDestination destination = new TransactionDestination();
        destination.setSourceType(DestinationType.CASH_OUT);
        destination.setAmount(100);
        return destination;
    }

    public static TransactionDestination getDepositDestination() {
        TransactionDestination destination = new TransactionDestination();
        destination.setSourceType(DestinationType.DEPOSIT);
        destination.setAccountNumber("12400590175");
        destination.setTransactionCode("209 - Deposit");
        destination.setAmount(50);
        return destination;
    }

    public static TransactionDestination getMiscCreditDestination() {
        TransactionDestination destination = new TransactionDestination();
        destination.setSourceType(DestinationType.MISC_CREDIT);
        destination.setAccountNumber("12400590522");
        destination.setTransactionCode("303 - Credit Memo");
        destination.setAmount(50);
        return destination;
    }
}
