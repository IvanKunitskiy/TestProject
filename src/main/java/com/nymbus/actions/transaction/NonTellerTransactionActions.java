package com.nymbus.actions.transaction;

import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.transaction.enums.ATMTransactionType;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.NonTellerTransactionData;
import com.nymbus.pages.webadmin.WebAdminPages;
import org.testng.Assert;

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

    public void performZeroDollarAtmTransaction(Map<String, String> fields, String[] actions) {
        Actions.nonTellerTransaction().generateATMTransaction(fields, actions);
    }

    public void performBalanceInquiryCrdOnusTransaction(Map<String, String> fields, String responseCodeOfField39) {
        Actions.nonTellerTransaction().generateATMTransaction(fields, responseCodeOfField39);
    }

    public String getFieldValueFromATMTransaction(Map<String, String> fields, String field) {
        return Actions.nonTellerTransaction().getFiledValue(fields, field);
    }

    public BalanceDataForCHKAcc getBalanceDataFromField54(String field54Value) {
        BalanceDataForCHKAcc balanceDataForCHKAcc = new BalanceDataForCHKAcc();
        String currentPart = field54Value.substring(0, field54Value.length()/2);
        String availablePart = field54Value.substring(field54Value.length()/2);
        balanceDataForCHKAcc.setCurrentBalance(Double.parseDouble(currentPart.replace("100184", ""))/100);
        balanceDataForCHKAcc.setAvailableBalance(Double.parseDouble(availablePart.replace("100284", ""))/100);

        return balanceDataForCHKAcc;
    }

    public String getTerminalID(int index) {
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminTransactionActions().goToTerminalIdUrl();
        String result = WebAdminPages.rulesUIQueryAnalyzerPage().getTerminalIdValue(index);
        WebAdminActions.loginActions().doLogoutProgrammatically();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();

        return result;
    }

    public double getForeignFee(int index) {
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminTransactionActions().goToForeignFeeUrl();
        String result = WebAdminPages.rulesUIQueryAnalyzerPage().getForeignFeeValue(index);
        WebAdminActions.loginActions().doLogoutProgrammatically();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();

        return Double.parseDouble(result);
    }

    public double getForeignATMFeeBalanceInquiry(int index) {
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminTransactionActions().goToForeignATMFeeBalanceInquiryUrl();
        String result = WebAdminPages.rulesUIQueryAnalyzerPage().getForeignFeeValue(index);
        WebAdminActions.loginActions().doLogoutProgrammatically();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();

        return Double.parseDouble(result);
    }

    public boolean isATMFeeBalanceInquiryValuePresent() {
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminTransactionActions().goToForeignATMFeeBalanceInquiryUrl();
        String foreignFeeValue = WebAdminPages.rulesUIQueryAnalyzerPage().getForeignFeeValue(1);
        WebAdminActions.loginActions().doLogoutProgrammatically();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();

        return foreignFeeValue != null && !foreignFeeValue.isEmpty();
    }

    public boolean isWaiveATUsageFeeAcronymValuePresent() {
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminTransactionActions().goToWaiveATUsageFeeAcronymUrl();
        String waiveATUsageFeeAcronymValue = WebAdminPages.rulesUIQueryAnalyzerPage().getWaiveATUsageFeeAcronymValue(1);
        WebAdminActions.loginActions().doLogoutProgrammatically();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();

        return waiveATUsageFeeAcronymValue != null && !waiveATUsageFeeAcronymValue.isEmpty();
    }

    public void verifyCurrentAndAvailableBalance(String field54Value, double transactionAmount) {
        String currentBalancePart = field54Value.substring(0, field54Value.length()/2);
        String availableBalancePart = field54Value.substring(field54Value.length()/2);
        String formattedAccountBalance = String.format("%.2f", transactionAmount).replaceAll("[^0-9]", "");
        Assert.assertTrue(currentBalancePart.endsWith(formattedAccountBalance),
                "'Current Balance' is not returned in DE54 field");
        Assert.assertTrue(availableBalancePart.endsWith(formattedAccountBalance),
                "'Available Balance' is not returned in DE54 field");
    }
}