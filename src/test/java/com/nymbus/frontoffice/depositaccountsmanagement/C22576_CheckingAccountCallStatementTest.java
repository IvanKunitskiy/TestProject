package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.account.Account;
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
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22576_CheckingAccountCallStatementTest extends BaseTest {

    private IndividualClient client;
    private Account chkAccount;
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
        chkAccount = new Account().setCHKAccountData();

        // Set up credit and debit transactions
        creditTransaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        creditTransaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        creditTransaction.getTransactionDestination().setTransactionCode("109 - Deposit");

        debitTransaction = new TransactionConstructor(new MiscDebitGLCreditTransactionBuilder()).constructTransaction();
        debitTransaction.getTransactionSource().setAccountNumber(chkAccount.getAccountNumber());

        // Create client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
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
        AccountActions.createAccount().createCHKAccount(chkAccount);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create transaction and logout
        Actions.transactionActions().performGLDebitMiscCreditTransaction(creditTransaction);
        Actions.transactionActions().performMiscDebitGLCreditTransaction(debitTransaction);
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
        AccountActions.callStatement().verifyChkSavingsIraAccountCallStatementData(chkAccount, client, creditTransaction, debitTransaction, seasonalAddress);

        logInfo("Step 5: Go to the WebAdmin -> RulesUI and search for active CHK account,"
                + "where YTD Interest Paid field is not null.\n"
                + "Using the Query open it on Transactions tab and click [Call Statement] button.\n"
                + "SKIP STEP if QUERY DOES NOT RETURN AT LEAST ONE ACCOUNT\n");
        logInfo("Step 6: Verify YTD Interest Paid field value.\n" +
                "SKIP STEP if THERE ARE NO SUCH ACCOUNTS");
        AccountActions.callStatement().verifyYtdInterestPaidValue();

        logInfo("Step 7: Go to the WebAdmin->RulesUI and search for active CHK account,"
                + "where Interest Paid Last Year field is not null.\n"
                + "Using the Query open it on Transactions tab and click [Call Statement] button\n"
                + "SKIP STEP if QUERY DOES NOT RETURN AT LEAST ONE ACCOUNT\n");
        logInfo("Step 8: Verify Interest Paid Last Year field value.\n" +
                "SKIP STEP if THERE ARE NO SUCH ACCOUNTS");
        AccountActions.callStatement().verifyInterestPaidLastYearValue();

        logInfo("Step 9: Go to the WebAdmin->RulesUI and search for active CHK account,"
                + "where YTD Taxes withheld field is not null.\n"
                + "Using the Query open it on Transactions tab and click [Call Statement] button\n"
                + "SKIP STEP if QUERY DOES NOT RETURN AT LEAST ONE ACCOUNT\n");
        logInfo("Step 10: Verify YTD Taxes withheld field value.\n" +
                "SKIP STEP if THERE ARE NO SUCH ACCOUNTS");
        AccountActions.callStatement().verifyYtdTaxesWithheldValue();

        logInfo("Step 11: Go to the WebAdmin->RulesUI and search for active CHK account,"
                + "where Overdraft Charge Off > 0.\n"
                + "Using the Query open it on Transactions tab and click [Call Statement] button\n"
                + "SKIP STEP if QUERY DOES NOT RETURN AT LEAST ONE ACCOUNT\n");
        logInfo("Step 12: Verify 'overdraft was charged off' field value.\n" +
                "SKIP STEP if THERE ARE NO SUCH ACCOUNTS");
        AccountActions.callStatement().verifyOverdraftWasChargedOffValue();
    }

    @AfterMethod(description = "Delete the downloaded PDF.")
    public void postCondition() {
        logInfo("Deleting the downloaded PDF...");
        Functions.cleanDirectory(System.getProperty("user.dir") + "/screenshots/");
        Functions.deleteFile(System.getProperty("user.dir") + "/proxy.pdf"); // TODO: Discover reason of duplicating pdf file
    }
}
