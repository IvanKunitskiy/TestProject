package com.nymbus.newmodels.generation.tansactions.factory;

import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.enums.Denominations;
import com.nymbus.newmodels.transaction.enums.DestinationType;
import com.nymbus.util.Random;

import java.util.HashMap;

public class DestinationFactory {

    public static TransactionDestination getCashOutDestination() {
        TransactionDestination destination = new TransactionDestination();
        destination.setSourceType(DestinationType.CASH_OUT);
        destination.setAmount(100);
        destination.setDenominationsHashMap(new HashMap<>());
        destination.getDenominationsHashMap().put(Denominations.FIFTIES, 100d);
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

    public static TransactionDestination getGLCreditDestination() {
        TransactionDestination destination = new TransactionDestination();
        destination.setSourceType(DestinationType.GL_CREDIT);
        destination.setAccountNumber("0-0-Dummy");
        destination.setAmount(100.00);
        destination.setNotes(Random.genString(20));
        return destination;
    }
}