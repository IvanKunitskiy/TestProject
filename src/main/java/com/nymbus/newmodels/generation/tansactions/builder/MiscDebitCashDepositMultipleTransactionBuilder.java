package com.nymbus.newmodels.generation.tansactions.builder;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.generation.tansactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.tansactions.factory.SourceFactory;
import com.nymbus.newmodels.transaction.MultipleTransaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;

import java.util.ArrayList;

public class MiscDebitCashDepositMultipleTransactionBuilder implements MultipleTransactionBuilder {
    private MultipleTransaction multipleTransaction;

    public MiscDebitCashDepositMultipleTransactionBuilder() {
        multipleTransaction = new MultipleTransaction();
    }

    @Override
    public void setDate() {
        multipleTransaction.setTransactionDate(DateTime.getDateTodayPlusDaysWithFormat(0, "MM/dd/yyyy"));
    }

    @Override
    public void setSource() {
        multipleTransaction.setSources(getSources());
    }

    @Override
    public void setDestination() {
        multipleTransaction.setDestinations(getDestinations());
    }

    @Override
    public MultipleTransaction getResult() {
        return multipleTransaction;
    }

    private ArrayList<TransactionSource> getSources() {
        ArrayList<TransactionSource> sources = new ArrayList<>();
        sources.add(SourceFactory.getCashInSource());
        sources.add(SourceFactory.getMiscDebitSource());
        return sources;
    }

    private ArrayList<TransactionDestination> getDestinations() {
        ArrayList<TransactionDestination> destinations = new ArrayList<>();
        destinations.add(DestinationFactory.getCashOutDestination());
        destinations.add(DestinationFactory.getDepositDestination());
        destinations.add(DestinationFactory.getMiscCreditDestination());
        return destinations;
    }
}
