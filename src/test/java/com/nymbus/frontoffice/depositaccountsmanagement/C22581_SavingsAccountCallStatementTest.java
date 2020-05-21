package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.LoginActions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Petro")
public class C22581_SavingsAccountCallStatementTest extends BaseTest {

    private String savingsAccountNumber = "";

    @BeforeMethod
    public void prepareData() {
        // Set up client
        Client client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");

        // Set iup account
        Account savingsAccount = new Account().setSavingsAccountData();

        // Set up transaction with account number
        Transaction transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        transaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());

        // Log in and create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);

        // Create account
        AccountActions.createAccount().createSavingsAccount(savingsAccount);
        savingsAccountNumber = savingsAccount.getAccountNumber();

        // Create transaction
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22581, Client Accounts: Savings account call statement")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyCallStatementOnSavingsAccount() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the Savings account from the precondition and open it on Transactions tab");
        AccountActions.editAccount().navigateToAccountDetails(savingsAccountNumber, false);
        Pages.accountDetailsPage().clickTransactionsTab();

        logInfo("Step 3: Click 'Call Statement' button");
        Pages.accountTransactionPage().clickStatementButton();

        Actions.mainActions().switchToTab(1);

        Pages.accountStatementPage().switchToFrame();

        Pages.accountStatementPage().switchToDefaultContent();

        // TODO parse pdf file
    }
}