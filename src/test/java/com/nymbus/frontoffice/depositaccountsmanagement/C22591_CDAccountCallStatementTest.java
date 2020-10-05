package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.basicinformation.address.Address;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.client.factory.basicinformation.AddressFactory;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitGLCreditTransactionBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Petro")
public class C22591_CDAccountCallStatementTest extends BaseTest {

    private IndividualClient client;
    private Account cdAccount;
    private Transaction creditTransaction;
    private Transaction debitTransaction;
    private Address seasonalAddress;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up account
        cdAccount = new Account().setCdAccountData();

        // Set up transaction
        creditTransaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        creditTransaction.getTransactionDestination().setAccountNumber(cdAccount.getAccountNumber());
        creditTransaction.getTransactionDestination().setTransactionCode("311 - New CD");

        debitTransaction = new TransactionConstructor(new MiscDebitGLCreditTransactionBuilder()).constructTransaction();
        debitTransaction.getTransactionSource().setAccountNumber(cdAccount.getAccountNumber());
        debitTransaction.getTransactionSource().setTransactionCode("319 - Debit Memo");

        // Create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set the product
        cdAccount.setProduct(Actions.productsActions().getProduct(Products.CD_PRODUCTS, AccountType.CD, RateType.FIXED));

        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Add seasonal address
        seasonalAddress = new AddressFactory().getSeasonalAddress();
        ClientsActions.clientDetailsActions().clickEditProfile();
        ClientsActions.clientDetailsActions().addSeasonalAddress(seasonalAddress, client);
        Pages.clientDetailsPage().clickSaveChangesButton();
        Pages.clientDetailsPage().waitForProfileNotEditable();

        // Create account
        AccountActions.createAccount().createCDAccount(cdAccount);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Commit GLDebitMiscCredit transaction to account
        Actions.transactionActions().performGLDebitMiscCreditTransaction(creditTransaction);

        // Re-login in system for updating teller session
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Commit MiscDebitGLCredit transaction to account
        Actions.transactionActions().performMiscDebitGLCreditTransaction(debitTransaction);
        Actions.loginActions().doLogOutProgrammatically();
    }

    @Test(description = "C22591, Edit CD account call statement")
    @Severity(SeverityLevel.CRITICAL)
    public void viewClientLevelCallStatement() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CD account from the precondition and open it on Transactions tab");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(cdAccount);
        AccountActions.callStatement().setDataForCDIRAAAccountCallStatementVerification(cdAccount);
        Pages.accountNavigationPage().clickTransactionsTab();

        logInfo("Step 3: Click [Call Statement] button");
        logInfo("Step 4: Look through the Certificate Call Statement data and verify it contains correct data");
        logInfo("Step 5: Look through the Call Statement for the selected CD account");
        AccountActions.callStatement().verifyCDIRAAccountCallStatementData(cdAccount, client, creditTransaction, debitTransaction, seasonalAddress);

        logInfo("Step 6: Go to the WebAdmin -> RulesUI and search for active CD IRA account,"
                + "where YTD Interest Paid field is not null.\n"
                + "Using the Query open it on Transactions tab and click [Call Statement] button.\n"
                + "SKIP STEP if QUERY DOES NOT RETURN AT LEAST ONE ACCOUNT\n");
        logInfo("Step 7: Verify YTD Interest Paid field value.\n" +
                "SKIP STEP if THERE ARE NO SUCH ACCOUNTS");
        AccountActions.callStatement().verifyYtdInterestPaidValue();

        logInfo("Step 8: Go to the WebAdmin->RulesUI and search for active CD IRA account,"
                + "where Interest Paid Last Year field is not null.\n"
                + "Using the Query open it on Transactions tab and click [Call Statement] button\n"
                + "SKIP STEP if QUERY DOES NOT RETURN AT LEAST ONE ACCOUNT\n");
        logInfo("Step 9: Verify Interest Paid Last Year field value.\n" +
                "SKIP STEP if THERE ARE NO SUCH ACCOUNTS");
        AccountActions.callStatement().verifyInterestPaidLastYearValue();

        logInfo("Step 10: Go to the WebAdmin->RulesUI and search for active CD IRA account,"
                + "where YTD Taxes withheld field is not null.\n"
                + "Using the Query open it on Transactions tab and click [Call Statement] button\n"
                + "SKIP STEP if QUERY DOES NOT RETURN AT LEAST ONE ACCOUNT\n");
        logInfo("Step 11: Verify YTD Taxes withheld field value.\n" +
                "SKIP STEP if THERE ARE NO SUCH ACCOUNTS");
        AccountActions.callStatement().verifyYtdTaxesWithheldValue();
    }

    @AfterMethod(description = "Delete the downloaded PDF.")
    public void postCondition() {
        logInfo("Deleting the downloaded PDF...");
        Functions.cleanDirectory(System.getProperty("user.dir") + "/screenshots/");
    }
}
