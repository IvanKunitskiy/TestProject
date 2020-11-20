package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.UserCredentials;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Petro")
public class C22693_ResearchTransactionsInTellerJournalTest extends BaseTest {

    private UserCredentials userCredentials_1;
    private UserCredentials userCredentials_2;
    private IndividualClient client_1;
    private IndividualClient client_2;
    private Account checkingAccount_1;
    private Account checkingAccount_2;
    private Transaction chkTransaction_1;
    private Transaction chkTransaction_2;
    private String userName_2;
    private String transactionNumber_1;
    private String transactionNumber_2;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client_1 = individualClientBuilder.buildClient();
        client_2 = individualClientBuilder.buildClient();

        // Credentials for user 1 and 2
        userCredentials_1 = userCredentials;
        userCredentials_2 = Constants.USERS.pop();

        // Set up account data
        checkingAccount_1 = new Account().setCHKAccountData();
        checkingAccount_2 = new Account().setCHKAccountData();

        // Set up transaction data for increasing the accounts balances
        chkTransaction_1 = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        chkTransaction_1.getTransactionDestination().setAccountNumber(checkingAccount_1.getAccountNumber());

        chkTransaction_2 = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        chkTransaction_2.getTransactionDestination().setAccountNumber(checkingAccount_2.getAccountNumber());

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set the account data
        Pages.aSideMenuPage().clickSettingsMenuItem();
        SettingsPage.mainPage().clickViewProfile();
        String bankBranch_1 = SettingsPage.viewUserPage().getBankBranch();
        checkingAccount_1.setBankBranch(bankBranch_1);
        checkingAccount_1.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));
        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(userCredentials_1.getUserName(), userCredentials_1.getPassword());

        // Create client 1
        ClientsActions.individualClientActions().createClient(client_1);
        ClientsActions.individualClientActions().setClientDetailsData(client_1);
        ClientsActions.individualClientActions().setDocumentation(client_1);
        client_1.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create CHK account 1
        AccountActions.createAccount().createCHKAccount(checkingAccount_1);

        // Perform transaction to assign money to account
        Actions.transactionActions().performGLDebitMiscCreditTransaction(chkTransaction_1);
        Actions.loginActions().doLogOutProgrammatically();

        // Login to the system
        Actions.loginActions().doLogin(userCredentials_2.getUserName(), userCredentials_2.getPassword());

        // Set the account data
        Pages.aSideMenuPage().clickSettingsMenuItem();
        SettingsPage.mainPage().clickViewProfile();
        String bankBranch_2 = SettingsPage.viewUserPage().getBankBranch();
        String firstNameValue = SettingsPage.viewUserPage().getFirstNameValue();
        String lastNameValue = SettingsPage.viewUserPage().getLastNameValue();
        userName_2 = firstNameValue + " " + lastNameValue;
        checkingAccount_2.setBankBranch(bankBranch_2);
        checkingAccount_2.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));
        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(userCredentials_2.getUserName(), userCredentials_2.getPassword());

        // Create client 2
        ClientsActions.individualClientActions().createClient(client_2);
        ClientsActions.individualClientActions().setClientDetailsData(client_2);
        ClientsActions.individualClientActions().setDocumentation(client_2);
        client_2.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create CHK account 2
        AccountActions.createAccount().createCHKAccount(checkingAccount_2);

        // Perform transaction to assign the money to account
        Actions.transactionActions().performGLDebitMiscCreditTransaction(chkTransaction_2);
        Actions.loginActions().doLogOutProgrammatically();

        // Get transaction numbers
        transactionNumber_1 = WebAdminActions.webAdminTransactionActions().getTransactionNumber(userCredentials_1, checkingAccount_1);
        transactionNumber_2 = WebAdminActions.webAdminTransactionActions().getTransactionNumber(userCredentials_2, checkingAccount_2);
    }

    @Test(description = "C22693, Research transactions in teller journal")
    @Severity(SeverityLevel.CRITICAL)
    public void printTellerReceiptWithoutBalance() {

        logInfo("Step 1: Log in to the system as User1 from preconditions");
        Actions.loginActions().doLogin(userCredentials_1.getUserName(), userCredentials_1.getPassword());

        logInfo("Step 2: Go to the Journal page and log in to the proof date");
        Actions.journalActions().goToJournalPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Do not select any search filter and check transaction records on the page");
        Assert.assertTrue(Actions.journalActions().checkTransactionDataIsPresent(transactionNumber_1),
                "'Transaction 1' is not displayed on the screen by default");

        logInfo("Step 4: Try to search for Transaction1 by its Amount:\n" +
                "fill in 'Transaction Amount' field = amount of Transaction1 and check transaction records on the page");
        String transactionAmount_1 = Functions.getStringValueWithOnlyDigits(chkTransaction_1.getTransactionDestination().getAmount());
        Pages.journalPage().fillInTransactionAmount(transactionAmount_1);
        Assert.assertTrue(Actions.journalActions().checkTransactionDataIsPresent(transactionNumber_1),
                "'Transaction 1' is not displayed on the screen after query");

        logInfo("Step 5: Click the transaction");
        Actions.journalActions().clickLastTransaction();
        Assert.assertEquals(Pages.journalDetailsPage().getTransactionData(1), transactionNumber_1,
                "'Transaction 1' is not displayed on the screen after clicking");

        logInfo("Step 6: Go back to Journal page");
        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(userCredentials_1.getUserName(), userCredentials_1.getPassword());
        Actions.journalActions().goToJournalPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 7: Clear the value from the 'Transaction Amount' field\n" +
                "Click 'Teller' drop down field and search for User2\n" +
                "check transaction records on the page");
        Pages.journalPage().waitForProofDateSpan();
        Actions.journalActions().setTeller(userName_2);

        logInfo("Step 8: Look through the transactions and verify that there is NO Transaction1");
        Assert.assertFalse(Actions.journalActions().checkTransactionDataIsPresent(transactionNumber_1),
                "'Transaction 1' is not displayed on the screen after query");

        logInfo("Step 9: Search for transaction2");
        Assert.assertTrue(Actions.journalActions().checkTransactionDataIsPresent(transactionNumber_2),
                "'Transaction 2' is not displayed on the screen after query");
    }
}
