package com.nymbus.newmodels.generation.tansactions.factory;

import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.Denominations;
import com.nymbus.newmodels.transaction.enums.SourceType;
import com.nymbus.newmodels.transaction.enums.TransactionCode;

import java.util.HashMap;

public class SourceFactory {

    public static TransactionSource getCashInSource() {
        TransactionSource source = new TransactionSource();
        source.setSourceType(SourceType.CASH_IN);
        source.setAmount(100);
        source.setDenominationsHashMap(new HashMap<>());
        source.getDenominationsHashMap().put(Denominations.HUNDREDS, 100.00);
        return source;
    }

    public static TransactionSource getMiscDebitSource() {
        TransactionSource source = new TransactionSource();
        source.setSourceType(SourceType.MISC_DEBIT);
        source.setAccountNumber("12400589946");
        source.setTransactionCode(TransactionCode.WITHDRAWAL_116.getTransCode());
        source.setAmount(100);
        return source;
    }

    public static TransactionSource getWithdrawalSource() {
        TransactionSource source = new TransactionSource();
        source.setSourceType(SourceType.WITHDRAWL);
        source.setAccountNumber("12400589946");
        source.setTransactionCode(TransactionCode.WITHDRAWAL_216.getTransCode());
        source.setAmount(100);
        return source;
    }

    public static TransactionSource getCheckSource() {
        TransactionSource source = new TransactionSource();
        source.setSourceType(SourceType.CHECK);
        source.setRoutingNumber("122105155");
        source.setAccountNumber("12400589946");
        source.setCheckNumber(Generator.getRandomStringNumber(6));
        source.setTransactionCode(TransactionCode.CHECK.getTransCode());
        source.setAmount(100);
        return source;
    }

    public static TransactionSource getGlDebitSource() {
        TransactionSource source = new TransactionSource();
        source.setSourceType(SourceType.GL_DEBIT);
        source.setAccountNumber("0-0-Dummy");
        source.setTransactionCode(TransactionCode.GL_DEBIT.getTransCode());
        source.setAmount(100);
        return source;
    }
}
