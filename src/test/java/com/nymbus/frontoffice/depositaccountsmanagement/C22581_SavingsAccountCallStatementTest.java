package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Petro")
public class C22581_SavingsAccountCallStatementTest extends BaseTest {

    private Transaction transaction;
    private Account savingsAccount;
    private IndividualClient client;
    private File callStatementPdfFile;

    @BeforeMethod
    public void prepareData() {
        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up account
        savingsAccount = new Account().setSavingsAccountData();

        // Set up transaction with account number
        transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        transaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());

        // Log in and create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createSavingsAccount(savingsAccount);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

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
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(savingsAccount);
        Pages.accountDetailsPage().clickTransactionsTab();

        logInfo("Step 3: Click 'Call Statement' button");
        logInfo("Step 4: Look through the Savings Call Statement data and verify it contains correct data");
        callStatementPdfFile = AccountActions.callStatement().downloadCallStatementPdfFile();
        AccountActions.callStatement().verifySavingsAccountCallStatementData(callStatementPdfFile, savingsAccount, client, transaction);
    }

    @AfterMethod(description = "Delete the downloaded PDF.")
    public void postCondition() {
        logInfo("Deleting the downloaded PDF...");
        Functions.cleanDirectory(System.getProperty("user.dir") + "/screenshots/");
        Functions.deleteFile(System.getProperty("user.dir") + "/proxy.pdf"); // TODO: Discover reason of duplicating pdf file
    }
}