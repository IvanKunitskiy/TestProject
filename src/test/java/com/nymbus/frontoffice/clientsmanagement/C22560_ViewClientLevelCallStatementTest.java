package com.nymbus.frontoffice.clientsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Clients Management")
@Owner("Petro")
public class C22560_ViewClientLevelCallStatementTest extends BaseTest {

    private IndividualClient client;
    private Account chkAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up account
        chkAccount = new Account().setCHKAccountData();

        // Set up transaction
        Transaction transaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        transaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());

        // Create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set product
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        AccountActions.createAccount().createCHKAccount(chkAccount);

        // Create transaction
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction);
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Clients Management";

    @TestRailIssue(issueID = 22560, testRunName = TEST_RUN_NAME)
    @Test(description = "C22560, View client level 'Call Statement'")
    @Severity(SeverityLevel.CRITICAL)
    public void viewClientLevelCallStatement() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Clients and search for the client from the precondition");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        logInfo("Step 3: Open Clients Profile on the Accounts tab");
        Pages.accountNavigationPage().clickAccountsTab();

        logInfo("Step 4: Click [Call Statement] button");
        Pages.accountsPage().clickCallStatementButton();

        logInfo("Step 5: Select address and AKA and click [OK] button");
        AccountActions.callStatement().setAddress(client);
        Pages.callStatementModalPage().clickOkButton();

        logInfo("Step 6: Look at the generated report");
        AccountActions.callStatement().verifyCallStatementData(client,chkAccount,client.getIndividualType().getAddresses().get(0),
                WebAdminActions.loginActions().getSystemDate());
    }
}
