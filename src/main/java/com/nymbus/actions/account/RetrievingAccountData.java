package com.nymbus.actions.account;

import com.nymbus.newmodels.accountinstructions.verifyingModels.InstructionBalanceData;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceData;
import com.nymbus.newmodels.transaction.verifyingModels.AccountDates;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;

public class RetrievingAccountData {
    public BalanceDataForCHKAcc getBalanceDataForCHKAcc() {
        BalanceDataForCHKAcc balanceDataForCHKAcc = new BalanceDataForCHKAcc();
        balanceDataForCHKAcc.setCurrentBalance(getCurrentBalance());
        balanceDataForCHKAcc.setAvailableBalance(getAvailableBalance());

        return balanceDataForCHKAcc;
    }



    public AccountDates getAccountDates() {
        AccountDates accountDates = new AccountDates();
        accountDates.setLastDepositDate(getLastDepositDate());
        accountDates.setLastActivityDate(getLastActivityDate());
        accountDates.setNumberOfDeposits(getNumberOfDeposits());
        accountDates.setLastDepositAmount(getDepositsAmount());

        return accountDates;
    }

    private double getDepositsAmount() {
        String depositsAmount = Pages.accountDetailsPage().getLastDepositAmountValue();
        return depositsAmount.equals("") ? 0 : Double.parseDouble(depositsAmount);
    }

    private int getNumberOfDeposits() {
        String depositsCount = Pages.accountDetailsPage().getNumberOfDepositsThisStatementCycleValue();
        return depositsCount.equals("") ? 0 : Integer.parseInt(depositsCount);
    }

    private String getLastActivityDate() {
        return Pages.accountDetailsPage().getDateLastActivityValue();
    }

    private String getLastDepositDate() {
        return Pages.accountDetailsPage().getDateLastDepositValue();
    }


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
        transactionData.setBalance(getBalanceValue(tempIndex));
        transactionData.setAmountSymbol(Pages.accountTransactionPage().getAmountSymbol(tempIndex));

        return transactionData;
    }

    private double getAmountValue(int index) {
        double amountIntegerPart = getAmount(index);
        double amountFractionalPart = getAmountFractionalPart(index);
        return amountIntegerPart + amountFractionalPart;
    }

    private double getBalanceValue(int index) {
        double balanceIntegerPart = getBalance(index);
        double balanceFractionalPart = getBalanceFractional(index);
        return  balanceIntegerPart + balanceFractionalPart;
    }

    private double getBalance(int tempIndex) {
        String value = Pages.accountTransactionPage().getBalanceValue(tempIndex);
        return Double.parseDouble(value);
    }

    private double getAmount(int tempIndex) {
        String value = Pages.accountTransactionPage().getAmountValue(tempIndex);
        return Double.parseDouble(value);
    }

    private double getBalanceFractional(int tempIndex) {
        String value = Pages.accountTransactionPage().getBalanceFractionalValue(tempIndex);
        return Double.parseDouble(value) / 100;
    }

    private double getAmountFractionalPart(int tempIndex) {
        String value = Pages.accountTransactionPage().getAmountFractionalValue(tempIndex);
        return Double.parseDouble(value) / 100;
    }
}