package com.nymbus.actions.transaction;

import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.apirequest.NonTellerTransaction;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.transaction.enums.ATMTransactionType;
import com.nymbus.newmodels.transaction.verifyingModels.NonTellerTransactionData;
import com.nymbus.pages.webadmin.WebAdminPages;

public class NonTellerTransactionActions {
    public void performDepositTransactions(int count, NonTellerTransactionData transactionData) {
        for (int i = 0; i < count; i++) {
            NonTellerTransaction.generateDebitPurchaseTransaction(transactionData.getCardNumber(),
                    transactionData.getExpirationDate(), transactionData.getAmount());
            SelenideTools.sleep(1);
        }
    }

    public void performATMWithdrawalONUSTransaction(NonTellerTransactionData transactionData) {
        NonTellerTransaction.generateWithdrawalONUSTransaction(transactionData.getCardNumber(),
                transactionData.getExpirationDate(), transactionData.getAmount(), transactionData.getTerminalId());
    }


    public void performATMWDepositONUSTransaction(NonTellerTransactionData transactionData) {
        NonTellerTransaction.generateDepositONUSTransaction(transactionData.getCardNumber(),
                transactionData.getExpirationDate(), transactionData.getAmount(), transactionData.getTerminalId());
    }

    public void performATMTransaction(NonTellerTransactionData transactionData, ATMTransactionType type) {
        NonTellerTransaction.generateATMTransaction(transactionData, type);
    }

    public String getTerminalID(int index) {
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminTransactionActions().goToTerminalIdUrl();
        String result = WebAdminPages.rulesUIQueryAnalyzerPage().getTerminalIdValue(index);
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();

        return result;
    }

    public double getForeignFee(int index) {
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminTransactionActions().goToForeignFeeUrl();
        String result = WebAdminPages.rulesUIQueryAnalyzerPage().getForeignFeeValue(index);
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();

        return Double.parseDouble(result);
    }
}