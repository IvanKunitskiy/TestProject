package com.nymbus.frontoffice.transactions;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
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
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Petro")
public class C22672_TellerCommitTransactionOnDormantAccountTest extends BaseTest {

    private Account chkAccount;
    private Transaction transaction;
    private TransactionData transactionData;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up account
        chkAccount = new Account().setCHKAccountData();

        // Set up transaction
        transaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        transaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());

        // Create a client
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set the product
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create the client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        AccountActions.createAccount().createCHKAccountForDormantPurpose(chkAccount);

        // Get root id
        String[] url = WebDriverRunner.url().split("/");
        String clientRootId = url[url.length - 2];

        // Set account as 'Dormant'
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminUsersActions().setDormantAccount(clientRootId, chkAccount);
        WebAdminActions.loginActions().doLogout();

        transactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "+", transaction.getTransactionDestination().getAmount(), transaction.getTransactionDestination().getAmount());
    }

    @Test(description = "C22672, Teller: Commit transaction on dormant account")
    @Severity(SeverityLevel.CRITICAL)
    public void tellerCommitTransactionOnDormantAccount() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);
        Assert.assertEquals(Pages.accountDetailsPage().getAccountStatus(), "Dormant", "Account status is not 'Dormant'");

        logInfo("Step 2: Go to Teller page and log in to the proof date");
        logInfo("Step 3: Select any source / destination fund types so that at least one is related to regular account\n." +
                "e.g. : - Source: Gl/Debit, - Destination: Deposit");
        logInfo("Step 4: Select dormant account from preconditions in any of the added line items\n" +
                "which are related to regular account (e.g. Deposit) and specify some amount (e.g. $100) for it");
        logInfo("Step 5: Specify fields for opposite line item with correct values:\n" +
                "- search for any GL account\n" +
                "- specify same amount (e.g. $100)\n" +
                "- expand line item and specify Note");
        logInfo("Step 6: Click [Commit Transaction] button");
        logInfo("Step 7: Specify credentials of the user with supervisor override permissions from preconditions in the popup and submit it");
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction);

        logInfo("Step 8: Go to dormant account used in transaction and verify its:\n" +
                "- current balance\n" +
                "- available balance\n" +
                "- account status");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);
        Assert.assertEquals(AccountActions.retrievingAccountData().getCurrentBalance(),
                transaction.getTransactionDestination().getAmount(), "CHK account current balance is not correct!");
        Assert.assertEquals(AccountActions.retrievingAccountData().getAvailableBalance(),
                transaction.getTransactionDestination().getAmount(), "CHK account available balance is not correct!");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountStatus(), "Active", "Account status is not 'Active'");

        logInfo("Step 9: Open Account on the Transactions History tab and verify the committed transaction");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset);
        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");
        Assert.assertEquals(Pages.accountTransactionPage().getTransactionItemsCount(), 1,
                "Transactions count on 'Transactions' tab is incorrect");

        logInfo("Step 10: Go to Account Maintenance-> Maintenance History page.\n" +
                " Check that there is record about changing Account status from 'Dormant' to 'Active'");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();
        AccountActions.accountMaintenanceActions().expandAllRows();

        // TODO: Waiting for response from Mariya. The record is not available about changing status to 'Active' from 'Dormant'
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Account Status") >= 1,
                "'Account Status' row count is incorrect!");
    }
}
