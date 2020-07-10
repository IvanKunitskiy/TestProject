package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
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
public class C23908_SavingsIRAAccountCallStatementTest extends BaseTest {

    private IndividualClient client;
    private Account iraAccount;
    private Transaction transaction;
    private File callStatementPdfFile;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up account
        iraAccount = new Account().setIRAAccountData();

        // Set up transaction
        transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        transaction.getTransactionDestination().setAccountNumber(iraAccount.getAccountNumber());
        transaction.getTransactionDestination().setTransactionCode("2330 - Cur Yr Contrib");

        // Create a client
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createIRAAccount(iraAccount);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Commit transaction to account
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C23908, Savings IRA account call statement")
    @Severity(SeverityLevel.CRITICAL)
    public void savingsIRAAccountCallStatement() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CHK account from the precondition and open it on Transactions tab");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(iraAccount);
        iraAccount.setAccruedInterest(Pages.accountDetailsPage().getAccruedInterest());
        iraAccount.setInterestRate(Pages.accountDetailsPage().getInterestRateValue());
        iraAccount.setInterestPaidYTD(Pages.accountDetailsPage().getInterestPaidYTD());
        iraAccount.setInterestPaidLastYear(Pages.accountDetailsPage().getInterestPaidLastYear());
        iraAccount.setTaxesWithheldYTD(Pages.accountDetailsPage().getTaxesWithheldYTD());
        Pages.accountNavigationPage().clickTransactionsTab();

        logInfo("Step 3: Click [Call Statement] button");
        logInfo("Step 4: Look through the CHK Call Statement data and verify it contains correct data");
        callStatementPdfFile = AccountActions.callStatement().downloadCallStatementPdfFile();
        AccountActions.callStatement().verifySavingsIraAccountCallStatementData(callStatementPdfFile, iraAccount, client, transaction);
    }

    @AfterMethod(description = "Delete the downloaded PDF.")
    public void postCondition() {
        logInfo("Deleting the downloaded PDF...");
        Functions.cleanDirectory(System.getProperty("user.dir") + "/screenshots/");
        Functions.deleteFile(System.getProperty("user.dir") + "/proxy.pdf"); // TODO: Discover reason of duplicating pdf file
    }
}