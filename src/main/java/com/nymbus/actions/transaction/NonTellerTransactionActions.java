package com.nymbus.actions.transaction;

import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.transaction.enums.ATMTransactionType;
import com.nymbus.newmodels.transaction.verifyingModels.NonTellerTransactionData;
import com.nymbus.pages.webadmin.WebAdminPages;

import java.util.Map;

public class NonTellerTransactionActions {
    public void performDepositTransactions(int count, NonTellerTransactionData transactionData) {
        for (int i = 0; i < count; i++) {
            Actions.nonTellerTransaction().generateDebitPurchaseTransaction(transactionData.getCardNumber(),
                    transactionData.getExpirationDate(), transactionData.getAmount());
            SelenideTools.sleep(1);
        }
    }

    public void performATMWithdrawalONUSTransaction(NonTellerTransactionData transactionData) {
        Actions.nonTellerTransaction().generateWithdrawalONUSTransaction(transactionData.getCardNumber(),
                transactionData.getExpirationDate(), transactionData.getAmount(), transactionData.getTerminalId());
    }


    public void performATMWDepositONUSTransaction(NonTellerTransactionData transactionData) {
        Actions.nonTellerTransaction().generateDepositONUSTransaction(transactionData.getCardNumber(),
                transactionData.getExpirationDate(), transactionData.getAmount(), transactionData.getTerminalId());
    }

    public void performMixDepCashTransaction(NonTellerTransactionData transactionData, ATMTransactionType type) {
        Actions.nonTellerTransaction().generateATMTransaction(transactionData, type);
    }

    public void performMixDepCashTransaction(Map<String, String> fields) {
        Actions.nonTellerTransaction().generateMixDepCashTransaction(fields);
    }

    public void performATMTransaction(Map<String, String> fields) {
        Actions.nonTellerTransaction().generateATMTransaction(fields);
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