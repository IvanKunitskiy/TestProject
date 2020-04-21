package com.nymbus.newmodels.generation.tansactions.factory;

import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.SourceType;

public class SourceFactory {

    public static TransactionSource getCashInSource() {
        TransactionSource source = new TransactionSource();
        source.setSourceType(SourceType.CASH_IN);
        source.setAmount(100);
        return source;
    }

    public static TransactionSource getMiscDebitSource() {
        TransactionSource source = new TransactionSource();
        source.setSourceType(SourceType.MISC_DEBIT);
        source.setAccountNumber("12400589946");
        source.setTransactionCode("116 - Withdrawal");
        source.setAmount(100);
        return source;
    }
}
