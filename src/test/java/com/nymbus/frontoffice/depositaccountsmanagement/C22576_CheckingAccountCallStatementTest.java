package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
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
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

public class C22576_CheckingAccountCallStatementTest extends BaseTest {

    private IndividualClient client;
    private Account chkAccount;
    private Transaction transaction;
    private File callStatementPdfFile;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up account
        chkAccount = new Account().setCHKAccountData();

        // Set up transaction with account number
        transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        transaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        transaction.getTransactionDestination().setTransactionCode("109 - Deposit");

        // Create client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCHKAccount(chkAccount);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create transaction and logout
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22576, Checking account call statement")
    @Severity(SeverityLevel.CRITICAL)
    public void viewClientLevelCallStatement() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CHK account from the precondition and open it on Transactions tab");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);
        AccountActions.callStatement().setDataForChkSavingsIraAccountCallStatementVerification(chkAccount);
        Pages.accountNavigationPage().clickTransactionsTab();

        logInfo("Step 3: Click [Call Statement] button");
        logInfo("Step 4: Look through the CHK Call Statement data and verify it contains correct data");
        callStatementPdfFile = AccountActions.callStatement().downloadCallStatementPdfFile();
        AccountActions.callStatement().verifyChkSavingsIraAccountCallStatementData(callStatementPdfFile, chkAccount, client, transaction);

        // Get account number with 'interestPaidYTD'
        String accountNumber = WebAdminActions.webAdminUsersActions().getAccountWithYtdDividendsPaidNotNull();
        System.out.println("ACCOUNT : " + accountNumber);

        // Open received account
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(accountNumber);

        // Get 'interestPaidYTD' value
        String interestPaidYTD = Pages.accountDetailsPage().getInterestPaidYTD();
        System.out.println("interestPaidYTD : " + interestPaidYTD);

        // Navigate to transactions tab and get the pdf
        Pages.accountNavigationPage().clickTransactionsTab();
        callStatementPdfFile = AccountActions.callStatement().downloadCallStatementPdfFile();

        // Check the 'interestPaidYTD' value presence in pdf
        AccountActions.callStatement().verifyInterestPaidYTDInPdf(callStatementPdfFile, interestPaidYTD);
    }

    @AfterMethod(description = "Delete the downloaded PDF.")
    public void postCondition() {
        logInfo("Deleting the downloaded PDF...");
        Functions.cleanDirectory(System.getProperty("user.dir") + "/screenshots/");
        Functions.deleteFile(System.getProperty("user.dir") + "/proxy.pdf"); // TODO: Discover reason of duplicating pdf file
    }
}
