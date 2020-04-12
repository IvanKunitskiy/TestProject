package com.nymbus.actions.account;

import com.nymbus.newmodels.accountinstructions.verifyingModels.InstructionBalanceData;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceData;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;

public class RetrievingAccountData {
    public BalanceData getBalanceData() {
        BalanceData balanceData = new BalanceData();

        balanceData.setCurrentBalance(getCurrentBalance());
        balanceData.setAvailableBalance(getAvailableBalance());
        balanceData.setAggregateBalanceYearToDate(getAggregateBalanceYearToDate());
        balanceData.setTotalContributionsForLifeOfAccount(getTotalContributionsForLifeOfAccount());

        return balanceData;
    }

    public InstructionBalanceData getInstructionBalanceData() {
        InstructionBalanceData instructionBalanceData = new InstructionBalanceData();

        instructionBalanceData.setCurrentBalance(getCurrentBalance());
        instructionBalanceData.setAvailableBalance(getAvailableBalance());

        return instructionBalanceData;
    }

    private double getTotalContributionsForLifeOfAccount() {
        String value = Pages.accountDetailsPage().getTotalContributionsForLifeOfAccount();
        return Double.parseDouble(value);
    }

    private double getAggregateBalanceYearToDate() {
        String value = Pages.accountDetailsPage().getAggregateBalanceYearToDate();
        return Double.parseDouble(value);
    }

    private double getAvailableBalance() {
        String value = Pages.accountDetailsPage().getAvailableBalance();
        return Double.parseDouble(value);
    }

    private double getCurrentBalance() {
        String value = Pages.accountDetailsPage().getCurrentBalance();
        return Double.parseDouble(value);
    }

    public void goToTransactionsTab() {
        Pages.accountDetailsPage().clickTransactionsTab();
    }

    public TransactionData getTransactionData() {
        int tempIndex = 1;
        TransactionData transactionData = new TransactionData();

        transactionData.setPostingDate(Pages.accountTransactionPage().getPostingDateValue(tempIndex));
        transactionData.setEffectiveDate(Pages.accountTransactionPage().getEffectiveDateValue(tempIndex));
        transactionData.setAmount(getAmount(tempIndex));
        transactionData.setBalance(getBalance(tempIndex));

        return transactionData;
    }

    private double getBalance(int tempIndex) {
        String value = Pages.accountTransactionPage().getBalanceValue(tempIndex);
        return Double.parseDouble(value);
    }

    private double getAmount(int tempIndex) {
        String value = Pages.accountTransactionPage().getAmountValue(tempIndex);
        return Double.parseDouble(value);
    }
}
