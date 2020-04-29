package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitGLCreditTransactionBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class C22601_ViewTransactionHistoryTest extends BaseTest {
    private List<Transaction> transactions;

    @BeforeMethod
    public void prepareTransactionData() {
        transactions = getTransactionList("12400593203");

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.transactionActions().performTransactionList(transactions);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "22601, Account Transactions: View transaction history")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTransactionHistory() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Actions.clientPageActions().searchAndOpenClientByName("12400593203");

        double currentBalance = AccountActions.retrievingAccountData().getCurrentBalance();

        AccountActions.retrievingAccountData().goToTransactionsTab();

        double actualCurrentBalance =  AccountActions.retrievingAccountData().getBalanceValue(1);

        Assert.assertEquals(Pages.accountTransactionPage().getTransactionItemsCount(), 10,
                        "Transaction items count don't match!");

        Assert.assertTrue(AccountActions.accountTransactionActions().isTransactionsSymbolRight("12400593203", transactions));
    }

    private List<Transaction> getTransactionList(String accountNumber) {
        List<Transaction> transactions = new ArrayList<>();
        TransactionConstructor constructor = new TransactionConstructor(new GLDebitMiscCreditBuilder());
        for (int i = 0; i < 5; i++) {
            Transaction transaction = constructor.constructTransaction();
            transaction.getTransactionDestination().setAccountNumber(accountNumber);
            transactions.add(transaction);
        }
        constructor.setBuilder(new MiscDebitGLCreditTransactionBuilder());
        for (int i = 0; i < 5; i++) {
            Transaction transaction = constructor.constructTransaction();
            transaction.getTransactionSource().setAccountNumber(accountNumber);
            transactions.add(transaction);
        }
        return transactions;
    }
}