package com.nymbus.actions.account;

import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.accountinstructions.verifyingModels.InstructionBalanceData;
import com.nymbus.newmodels.transaction.verifyingModels.*;
import com.nymbus.pages.Pages;

public class RetrievingAccountData {
    public String getDateLastInterestPaid() {
        String result = Pages.accountDetailsPage().getDateLastInterestPaid();
        return result.equals("") ? DateTime.getDateTodayPlusDaysWithFormat(0, "MM/dd/yyyy") : result;
    }

    public BalanceDataForCDAcc getBalanceDataForCDAcc() {
        BalanceDataForCDAcc balanceDataForCDAcc = new BalanceDataForCDAcc();
        balanceDataForCDAcc.setCurrentBalance(getCurrentBalance());
        balanceDataForCDAcc.setOriginalBalance(getOriginalBalance());
        balanceDataForCDAcc.setTotalContributionForLifeOfAcc(getTotalContribution());

        return  balanceDataForCDAcc;
    }

    private double getTotalContribution() {
        String totalContribution = Pages.accountDetailsPage().getTotalContributionsForLifeOfAccount();
        return Double.parseDouble(totalContribution);
    }

    private double getOriginalBalance() {
        String originalResult = Pages.accountDetailsPage().getOriginalBalanceValue();
        return originalResult.equals("") ? 0.00 : Double.parseDouble(originalResult);
    }

    public BalanceDataForCHKAcc getBalanceDataForCHKAcc() {
        BalanceDataForCHKAcc balanceDataForCHKAcc = new BalanceDataForCHKAcc();
        balanceDataForCHKAcc.setCurrentBalance(getCurrentBalance());
        balanceDataForCHKAcc.setAvailableBalance(getAvailableBalance());

        return balanceDataForCHKAcc;
    }

    public ExtendedBalanceDataForCHKAcc getExtendedBalanceDataForCHKAcc() {
        ExtendedBalanceDataForCHKAcc balanceDataForCHKAcc = new ExtendedBalanceDataForCHKAcc();
        balanceDataForCHKAcc.setCurrentBalance(getCurrentBalance());
        balanceDataForCHKAcc.setAvailableBalance(getAvailableBalance());
        balanceDataForCHKAcc.setCollectedBalance(getCollectedBalance());
        balanceDataForCHKAcc.setAverageBalance(getAverageBalance());
        return balanceDataForCHKAcc;
    }

    private double getAverageBalance() {
        String value = Pages.accountDetailsPage().getAverageBalanceValue();
        return Double.parseDouble(value);
    }

    private double getCollectedBalance() {
        String value = Pages.accountDetailsPage().getCollectedBalanceValue();
        return Double.parseDouble(value);
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
        // balanceData.setTotalContributionsForLifeOfAccount(getTotalContributionsForLifeOfAccount());

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