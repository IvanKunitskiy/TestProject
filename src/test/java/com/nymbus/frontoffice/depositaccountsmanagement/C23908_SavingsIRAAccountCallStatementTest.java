package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
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
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Petro")
public class C23908_SavingsIRAAccountCallStatementTest extends BaseTest {

    private Client client;
    private Account iraAccount;
    private Transaction transaction;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");

        // Set up account
        iraAccount = new Account().setIRAAccountData();

        // Set up transaction
        transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();

        // Create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);

        // Create account
        AccountActions.createAccount().createIRAAccount(iraAccount);

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(iraAccount.getAccountNumber());
        transaction.getTransactionDestination().setTransactionCode("2330 - Cur Yr Contrib");

        // Commit transaction to account
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C23908, Savings IRA account call statement")
    @Severity(SeverityLevel.CRITICAL)
    public void savingsIRAAccountCallStatement() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CHK account from the precondition and open it on Transactions tab");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(iraAccount);
        Pages.accountNavigationPage().clickTransactionsTab();

        logInfo("Step 3: Click [Call Statement] button");
        Pages.transactionsPage().clickTheCallStatementButton();

        logInfo("Step 4: Look through the CHK Call Statement data and verify it contains correct data");
        // TODO: parse statement file
    }
}