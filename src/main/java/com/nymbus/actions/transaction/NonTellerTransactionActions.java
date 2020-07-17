package com.nymbus.actions.transaction;

import com.nymbus.apirequest.NonTellerTransaction;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.transaction.verifyingModels.NonTellerTransactionData;

public class NonTellerTransactionActions {
    public void performDepositTransactions(int count, NonTellerTransactionData transactionData) {
        for (int i = 0; i < count; i++) {
            NonTellerTransaction.generateDebitPurchaseTransaction(transactionData.getCardNumber(),
                    transactionData.getExpirationDate(), transactionData.getAmount());
            SelenideTools.sleep(1);
        }
    }

    public void performATMWithdrawalONUSTransaction(NonTellerTransactionData transactionData, String onusTerminalId) {
        NonTellerTransaction.generateWithdrawalONUSTransaction(transactionData.getCardNumber(),
                transactionData.getExpirationDate(), transactionData.getAmount(), onusTerminalId);
    }
}