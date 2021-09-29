package com.nymbus.newmodels.generation.transactions.builder;

import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.DestinationType;
import com.nymbus.newmodels.transaction.enums.SourceType;
import com.nymbus.newmodels.transaction.enums.TransactionCode;

public class TransitCheckDepositCHKAccBuilder implements TransactionBuilder {
    private Transaction transaction;

    public TransitCheckDepositCHKAccBuilder() {
        this.transaction = new Transaction();
    }


    @Override
    public void setDate() {
        transaction.setTransactionDate(WebAdminActions.loginActions().getSystemDate());
    }

    @Override
    public void setSource() {
        transaction.setTransactionSource(new TransactionSource());
        transaction.getTransactionSource().setRoutingNumber("122105155");
        transaction.getTransactionSource().setSourceType(SourceType.CHECK);
        transaction.getTransactionSource().setAccountNumber("83460855747");
        transaction.getTransactionSource().setAmount(4000.00);
        transaction.getTransactionSource().setTransactionCode(TransactionCode.TRANSIT_CHECK.getTransCode());
        transaction.getTransactionSource().setCheckNumber("2222");
    }

    @Override
    public void setDestination() {
        transaction.setTransactionDestination(new TransactionDestination());
        transaction.getTransactionDestination().setSourceType(DestinationType.DEPOSIT);
        transaction.getTransactionDestination().setAccountNumber("83460855747");
        transaction.getTransactionDestination().setAmount(4000.00);
        transaction.getTransactionDestination().setTransactionCode("109 - Deposit");
    }

    @Override
    public Transaction getResult() {
        return transaction;
    }
}
