package com.nymbus.actions.account;

import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.account.verifyingmodels.ClosedAccountData;
import com.nymbus.newmodels.accountinstructions.verifyingModels.InstructionBalanceData;
import com.nymbus.newmodels.transaction.verifyingModels.*;
import com.nymbus.pages.Pages;

public class RetrievingAccountData {
    public String getDateLastInterestPaid(String dateOpened) {
        String result = Pages.accountDetailsPage().getDateLastInterestPaid();
        return result.equals("") ? dateOpened : result;
    }

    public BalanceDataForCDAcc getBalanceDataForCDAcc() {
        BalanceDataForCDAcc balanceDataForCDAcc = new BalanceDataForCDAcc();
        balanceDataForCDAcc.setCurrentBalance(getCurrentBalance());
        balanceDataForCDAcc.setOriginalBalance(getOriginalBalance());
        // balanceDataForCDAcc.setTotalContributionForLifeOfAcc(getTotalContribution());

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

    public double getAverageBalance() {
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
        //balanceData.setAggregateBalanceYearToDate(getAggregateBalanceYearToDate());
        // balanceData.setTotalContributionsForLifeOfAccount(getTotalContributionsForLifeOfAccount());

        return balanceData;
    }

    public ClosedAccountData getClosedAccountData() {
        ClosedAccountData closedAccountData = new ClosedAccountData();
        closedAccountData.setCurrentBalance(getCurrentBalance());
        closedAccountData.setAvailableBalance(getAvailableBalance());
        closedAccountData.setAccruedInterest(getAccruedInterest());
        closedAccountData.setAccountStatus(getAccountStatus());
        closedAccountData.setDateClosed(Pages.accountDetailsPage().getDateClosed());

        return closedAccountData;
    }

    public ClosedAccountData getClosedAccountDataForCDAccount() {
        ClosedAccountData closedAccountData = new ClosedAccountData();
        closedAccountData.setCurrentBalance(getCurrentBalance());
        closedAccountData.setAvailableBalance(getAvailableBalanceFromHeaderMenu());
        closedAccountData.setAccruedInterest(Double.parseDouble(Pages.accountDetailsPage().getAccruedInterest()));
        closedAccountData.setAccountStatus(getAccountStatus());
        closedAccountData.setDateClosed(Pages.accountDetailsPage().getDateClosed());

        return closedAccountData;
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

    public double getAvailableBalance() {
        String value = Pages.accountDetailsPage().getAvailableBalance();
        return Double.parseDouble(value);
    }

    private double getAvailableBalanceFromHeaderMenu() {
        String value = Pages.accountDetailsPage().getAvailableBalanceFromHeaderMenu();
        return Double.parseDouble(value);
    }

    public double getCurrentBalance() {
        String value = Pages.accountDetailsPage().getCurrentBalance();
        return Double.parseDouble(value);
    }

    public double getCurrentBalanceWithAccruedInterest() {
        return  getCurrentBalance() + Double.parseDouble(Pages.accountDetailsPage().getAccruedInterest());
    }

    public double getCurrentBalanceWithAccruedInterestForCycle() {
        return  getCurrentBalance() + Double.parseDouble(Pages.accountDetailsPage().getAccruedInterestThisStatementCycle());
    }

    public double getAccruedInterest() {
        String accruedInterestValue = Pages.accountDetailsPage().getAccruedInterestThisStatementCycle();
        return Double.parseDouble(accruedInterestValue);
    }

    public void goToTransactionsTab() {
        Pages.accountDetailsPage().clickTransactionsTab();
    }

    public TransactionData getTransactionData() {
        int tempIndex = 1;
        TransactionData transactionData = new TransactionData();

        transactionData.setPostingDate(Pages.accountTransactionPage().getPostingDateValue(tempIndex));
        transactionData.setEffectiveDate(Pages.accountTransactionPage().getEffectiveDateValue(tempIndex));
        transactionData.setAmount(getAmountValue(tempIndex));
        transactionData.setBalance(getBalanceValue(tempIndex));
        transactionData.setAmountSymbol(Pages.accountTransactionPage().getAmountSymbol(tempIndex));

        return transactionData;
    }

    public TransactionData getTransactionDataWithBalanceSymbol() {
        int tempIndex = 1;
        TransactionData transactionData = new TransactionData();

        transactionData.setPostingDate(Pages.accountTransactionPage().getPostingDateValue(tempIndex));
        transactionData.setEffectiveDate(Pages.accountTransactionPage().getEffectiveDateValue(tempIndex));
        transactionData.setAmount(getAmountValue(tempIndex));
        transactionData.setBalance(getBalanceValueWithSymbol(tempIndex));
        transactionData.setAmountSymbol(Pages.accountTransactionPage().getAmountSymbol(tempIndex));

        return transactionData;
    }

    public TransactionData getTransactionDataWithBalanceSymbolForMinus() {
        int tempIndex = 1;
        TransactionData transactionData = new TransactionData();

        transactionData.setPostingDate(Pages.accountTransactionPage().getPostingDateValue(tempIndex));
        transactionData.setEffectiveDate(Pages.accountTransactionPage().getEffectiveDateValue(tempIndex));
        transactionData.setAmount(getAmountMinusValue(tempIndex));
        transactionData.setBalance(getAmountValue(tempIndex));
        transactionData.setAmountSymbol(Pages.accountTransactionPage().getAmountSymbol(tempIndex));

        return transactionData;
    }

    public TransactionData getTransactionDataWithBalanceSymbolForMinusAmount() {
        int tempIndex = 1;
        TransactionData transactionData = new TransactionData();

        transactionData.setPostingDate(Pages.accountTransactionPage().getPostingDateValue(tempIndex));
        transactionData.setEffectiveDate(Pages.accountTransactionPage().getEffectiveDateValue(tempIndex));
        transactionData.setAmount(getAmountMinusValue(tempIndex));
        transactionData.setBalance(getAmountValue(tempIndex));
        transactionData.setAmountSymbol(Pages.accountTransactionPage().getAmountMinusSymbol(tempIndex));

        return transactionData;
    }


    public TransactionData getSecondTransactionDataWithBalanceSymbol() {
        int tempIndex = 2;
        TransactionData transactionData = new TransactionData();

        transactionData.setPostingDate(Pages.accountTransactionPage().getPostingDateValue(tempIndex));
        transactionData.setEffectiveDate(Pages.accountTransactionPage().getEffectiveDateValue(tempIndex));
        transactionData.setAmount(getAmountValue(tempIndex));
        transactionData.setBalance(getBalanceValueWithSymbol(tempIndex));
        transactionData.setAmountSymbol(Pages.accountTransactionPage().getAmountSymbol(tempIndex));

        return transactionData;
    }

    public TransactionData getTransactionDataWithOffset(int offset) {
        int tempIndex = 1;
        TransactionData transactionData = new TransactionData();

        transactionData.setPostingDate(Pages.accountTransactionPage().getPostingDateValue(tempIndex, offset));
        transactionData.setEffectiveDate(Pages.accountTransactionPage().getEffectiveDateValue(tempIndex, offset));
        transactionData.setAmount(getAmountValue(tempIndex, offset));
        transactionData.setBalance(getBalanceValue(tempIndex, offset));
        transactionData.setAmountSymbol(Pages.accountTransactionPage().getAmountSymbol(tempIndex, offset));

        return transactionData;
    }

    public TransactionData getTransactionDataWithOffset(int offset, int tempIndex) {
        TransactionData transactionData = new TransactionData();

        transactionData.setPostingDate(Pages.accountTransactionPage().getPostingDateValue(tempIndex, offset));
        transactionData.setEffectiveDate(Pages.accountTransactionPage().getEffectiveDateValue(tempIndex, offset));
        transactionData.setAmount(getAmountValue(tempIndex, offset));
        transactionData.setBalance(getBalanceValue(tempIndex, offset));
        transactionData.setAmountSymbol(Pages.accountTransactionPage().getAmountSymbol(tempIndex, offset));

        return transactionData;
    }

    public TransactionData getTransactionDataForATM() {
        int tempIndex = 1;
        TransactionData transactionData = new TransactionData();

        transactionData.setPostingDate(Pages.accountTransactionPage().getPostingDateValue1(tempIndex));
        transactionData.setEffectiveDate(Pages.accountTransactionPage().getEffectiveDateValue1(tempIndex));
        transactionData.setAmount(getATMAmountValue(tempIndex));
        transactionData.setBalance(getATMBalanceValue(tempIndex));
        transactionData.setAmountSymbol(Pages.accountTransactionPage().getAmountSymbol1(tempIndex));

        return transactionData;
    }

    public double getAmountValue(int index) {
        double amountIntegerPart = getAmount(index);
        double amountFractionalPart = getAmountFractionalPart(index);
        return amountIntegerPart + amountFractionalPart;
    }

    public double getAmountMinusValue(int index) {
        double amountIntegerPart = Double.parseDouble(Pages.accountTransactionPage().getAmountValue(index));
        double amountFractionalPart = Double.parseDouble(Pages.accountTransactionPage().getAmountMinusFractionalValue(index))/100;
        return amountIntegerPart + amountFractionalPart;
    }

    public double getInterestMinusValue(int index) {
        double interest = Double.parseDouble(Pages.accountTransactionPage().getInterestValue(index));
        double interestFractionalPart = Double.parseDouble(Pages.accountTransactionPage().getInterestMinusFractionalValue(index))/100;
        return interest + interestFractionalPart;
    }

    public double getEscrowMinusValue(int index) {
        double escrow = Double.parseDouble(Pages.accountTransactionPage().getEscrowValue(index));
        double escrowFractionalPart = Double.parseDouble(Pages.accountTransactionPage().getEscrowMinusFractionalValue(index))/100;
        return escrow + escrowFractionalPart;
    }

    public double getAmountValue(int index, int offset) {
        double amountIntegerPart = getAmount(index, offset);
        double amountFractionalPart = getAmountFractionalPart(index, offset);
        return amountIntegerPart + amountFractionalPart;
    }

    public double getATMAmountValue(int index) {
        double amountIntegerPart = getATMAmount(index);
        double amountFractionalPart = getATMAmountFractionalPart(index);
        return amountIntegerPart + amountFractionalPart;
    }

    public double getBalanceValue(int index) {
        double balanceIntegerPart = getBalance(index);
        double balanceFractionalPart = getBalanceFractional(index);
        return  balanceIntegerPart + balanceFractionalPart;
    }

    public double getPrincipalValue(int index) {
        double principalIntegerPart = getPrincipal(index);
        double principalFractionalPart = getPrincipalFractional(index);
        return  principalIntegerPart + principalFractionalPart;
    }

    public double getBalanceValueWithSymbol(int index) {
        double balanceIntegerPart = getBalanceWithSymbol(index);
        double balanceFractionalPart = getBalanceFractionalWithSymbol(index);
        return  balanceIntegerPart + balanceFractionalPart;
    }

    public double getBalanceValue(int index, int offset) {
        double balanceIntegerPart = getBalance(index, offset);
        double balanceFractionalPart = getBalanceFractional(index, offset);
        return  balanceIntegerPart + balanceFractionalPart;
    }

    public double getATMBalanceValue(int index) {
        double balanceIntegerPart = getATMBalance(index);
        double balanceFractionalPart = getATMBalanceFractional(index);
        return  balanceIntegerPart + balanceFractionalPart;
    }

    private double getBalance(int tempIndex) {
        String value = Pages.accountTransactionPage().getBalanceValue(tempIndex);
        return Double.parseDouble(value);
    }

    private double getBalanceWithSymbol(int tempIndex) {
        String value = Pages.accountTransactionPage().getBalanceValueWithSymbol(tempIndex);
        return Double.parseDouble(value);
    }

    private double getBalance(int tempIndex, int offset) {
        String value = Pages.accountTransactionPage().getBalanceValue(tempIndex, offset);
        return Double.parseDouble(value);
    }

    private double getATMBalance(int tempIndex) {
        String value = Pages.accountTransactionPage().getBalanceValue1(tempIndex);
        return Double.parseDouble(value);
    }

    public double getAmount(int tempIndex) {
        String value = Pages.accountTransactionPage().getAmountValue(tempIndex);
        return Double.parseDouble(value);
    }

    public double getAmount(int tempIndex, int offset) {
        String value = Pages.accountTransactionPage().getAmountValue(tempIndex, offset);
        return Double.parseDouble(value);
    }

    public double getATMAmount(int tempIndex) {
        String value = Pages.accountTransactionPage().getAmountValue1(tempIndex);
        return Double.parseDouble(value);
    }

    private double getBalanceFractional(int tempIndex) {
        String value = Pages.accountTransactionPage().getBalanceFractionalValue(tempIndex);
        return Double.parseDouble(value) / 100;
    }

    private double getBalanceFractionalWithSymbol(int tempIndex) {
        String value = Pages.accountTransactionPage().getBalanceFractionalValueWithSymbol(tempIndex);
        return Double.parseDouble(value) / 100;
    }

    private double getBalanceFractional(int tempIndex, int offset) {
        String value = Pages.accountTransactionPage().getBalanceFractionalValue(tempIndex, offset);
        return Double.parseDouble(value) / 100;
    }

    private double getPrincipal(int tempIndex) {
        String value = Pages.accountTransactionPage().getPrincipalValue(tempIndex);
        return Double.parseDouble(value);
    }

    private double getPrincipalFractional(int tempIndex) {
        String value = Pages.accountTransactionPage().getPrincipalFractionalPartValue(tempIndex);
        return Double.parseDouble(value) / 100;
    }

    private double getATMBalanceFractional(int tempIndex) {
        String value = Pages.accountTransactionPage().getBalanceFractionalValue1(tempIndex);
        return Double.parseDouble(value) / 100;
    }

    private double getAmountFractionalPart(int tempIndex) {
        String value = Pages.accountTransactionPage().getAmountFractionalValue(tempIndex);
        return Double.parseDouble(value) / 100;
    }

    private double getATMAmountFractionalPart(int tempIndex) {
        String value = Pages.accountTransactionPage().getAmountFractionalValue1(tempIndex);
        return Double.parseDouble(value) / 100;
    }

    private double getAmountFractionalPart(int tempIndex, int offset) {
        String value = Pages.accountTransactionPage().getAmountFractionalValue(tempIndex, offset);
        return Double.parseDouble(value) / 100;
    }

    private String getAccountStatus() {
        return Pages.accountDetailsPage().getAccountStatus();
    }

    public int getOffset() {
        int tableHeaders = Pages.accountTransactionPage().getTableHeaderCols();
        switch (tableHeaders) {
            default:
            case 9:
                return 0;
            case 10:
                return 1;
        }
    }

    public double getInstructionAmount() {
        String value = Pages.accountInstructionsPage().getHoldAmount();
        return Double.parseDouble(value);
    }

    public double getDeletedInstructionAmount(int index) {
        String value = Pages.accountInstructionsPage().getAmountValueByIndex(index);
        return Double.parseDouble(value);
    }

    public String calculateNextInterestAmount(double currentBalance, String rate, String fromDate, String toDate, boolean includeToDate, String interestType) {
        double parsedRate = Double.parseDouble(rate);
        if (interestType.equals("Compound")) {
            return Functions.getCompoundCalculatedInterestAmount(currentBalance, parsedRate, fromDate, toDate, includeToDate);
        }
        else {
            return Functions.getCalculatedInterestAmount(currentBalance, parsedRate, fromDate, toDate, includeToDate);
        }
    }
}