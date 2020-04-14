package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceData;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22634_GLDebit_MiscCredit_SavingsAcc_Test extends BaseTest {
    private Transaction transaction;
    private BalanceData balanceData;
    private TransactionData transactionData;
    Client client;
    Account savingsAccount;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        savingsAccount = new Account().setSavingsAccountData();
        transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.createClient().createClient(client);

        // Create account
        AccountActions.createAccount().createSavingsAccount(savingsAccount);

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());

        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);
        balanceData = AccountActions.retrievingAccountData().getBalanceData();

        transactionData = new TransactionData(transaction.getTransactionDate(), transaction.getTransactionDate(), "+", balanceData.getCurrentBalance(),
                 transaction.getTransactionDestination().getAmount());
    }

    @Test(description = "C22634, Commit transaction GL Debit -> Misc Credit")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTransactionGLDebitMiscCredit() {

        Actions.transactionActions().goToTellerPage();

        Actions.transactionActions().doLoginTeller();

        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);

        Actions.transactionActions().clickCommitButton();

        Assert.assertEquals(Pages.tellerPage().getModalText(),
                    "Transaction was successfully completed.",
                    "Transaction doesn't commit");

        Pages.tellerPage().closeModal();

        balanceData.addAmount(transaction.getTransactionDestination().getAmount());

        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);

        BalanceData actualBalanceData = AccountActions.retrievingAccountData().getBalanceData();

        Assert.assertEquals(actualBalanceData, balanceData, "Balance data doesn't match!");

        Assert.assertEquals(Pages.accountDetailsPage().getDateLastDepositValue(), transaction.getTransactionDate(),
                "Date Last deposit  doesn't match");

        Assert.assertEquals(Pages.accountDetailsPage().getDateLastActivityValue(), transaction.getTransactionDate(),
                "Date Last activity  doesn't match");


        transactionData.setBalance(balanceData.getCurrentBalance());

        AccountActions.retrievingAccountData().goToTransactionsTab();

        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();

        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");

        // TODO add verification for webadmin
    }
}
