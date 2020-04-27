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
public class C22596_CDIRAAccountCallStatementTest extends BaseTest {

    private Client client;
    private Account cdIraAccount;
    private Transaction transaction;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");

        // Set up account
        cdIraAccount = new Account().setCDIRAAccountData();

        // Set up transaction
        transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();

        // Create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);

        // Create account
        AccountActions.createAccount().createCDAccount(cdIraAccount);

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(cdIraAccount.getAccountNumber());
        transaction.getTransactionDestination().setTransactionCode("330 - Cur Yr Contrib");

        // Commit transaction to account
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22596, CD IRA account call statement")
    @Severity(SeverityLevel.CRITICAL)
    public void cdIraBalanceInquiryTest() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CD IRA account from the precondition and open it on Transactions tab");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(cdIraAccount);
        Pages.accountNavigationPage().clickTransactionsTab();

        logInfo("Step 3: Click [Call Statement] button");
        Pages.transactionsPage().clickTheCallStatementButton();

        logInfo("Step 4: Look through the Savings Call Statement data and verify it contains correct data");
        // TODO: parse statement file
    }
}
