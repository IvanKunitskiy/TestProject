package com.nymbus.frontoffice.clientsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
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
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Clients Management")
@Owner("Dmytro")
public class C40009_StatementProcessingPageAddingSecondaryAccount extends BaseTest {

    private IndividualClient client;
    private Account chkAccount;
    private Account savingsAccount;
    private String clientID;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up account
        chkAccount = new Account().setCHKAccountData();
        savingsAccount = new Account().setSavingsAccountData();

        // Set up transaction
        Transaction transaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        transaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());

        // Create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set product
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));

        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        clientID = Pages.clientDetailsPage().getClientID();
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createSavingsAccount(savingsAccount);


        // Create transaction
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction);
        Actions.loginActions().doLogOut();
    }


    private final String TEST_RUN_NAME = "Clients Management";

    @TestRailIssue(issueID = 40009, testRunName = TEST_RUN_NAME)
    @Test(description = "C40009, View client level 'Call Statement'")
    @Severity(SeverityLevel.CRITICAL)
    public void statementProcessingPageAddingSecondaryAccount() {

        logInfo("Step 1: Log in to the system as the User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the client owner of CHK/Savings account from the precondition#2 and open" +
                " his profile on Maintenance tab");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientID);
        Pages.clientDetailsPage().clickOnMaintenanceTab();

        logInfo("Step 3: Click [View all] button in the Statement Processing widget");
        Pages.maintenancePage().clickOnViewAllStatementProcessing();
        TestRailAssert.assertTrue(Pages.maintainceStatementProcessingPage().isAccountPresent(chkAccount.getAccountNumber()),
                new CustomStepResult("Account " + chkAccount.getAccountNumber() + " is present", "Account " +
                        chkAccount.getAccountNumber() + " is not present"));
        TestRailAssert.assertTrue(Pages.maintainceStatementProcessingPage().isAccountPresent(savingsAccount.getAccountNumber()),
                new CustomStepResult("Account " + savingsAccount.getAccountNumber() + " is present", "Account " +
                        savingsAccount.getAccountNumber() + " is not present"));

        logInfo("Step 4: Click [Edit] button");
        Pages.maintainceStatementProcessingPage().clickEditButton();

        logInfo("Step 5: Click [+] icon next to CHK account from the Precondition#2 and click [Add Secondary Account] button");
        int index = 1;
        Pages.maintainceStatementProcessingPage().clickPlusForAccount(index);
        Pages.maintainceStatementProcessingPage().clickAddSecondaryAccount();

        logInfo("Step 6: Click on this field and Search for the Savings account from Precondition#2, select it");
        Pages.maintainceStatementProcessingPage().clickSelectSecondaryAccount();
        Pages.maintainceStatementProcessingPage().clickAccountByNumber(savingsAccount.getAccountNumber());

        logInfo("Step 7: Click [Save] button");
        Pages.maintainceStatementProcessingPage().clickSaveButton();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);

        logInfo("Step 8: Try to search for Savings account from Precondition#2 that was selected as Secondary account");
        TestRailAssert.assertFalse(Pages.maintainceStatementProcessingPage().isAccountPresent(savingsAccount.getAccountNumber()),
                new CustomStepResult("Account " + savingsAccount.getAccountNumber() + " is present", "Account " +
                        savingsAccount.getAccountNumber() + " is not present"));

        logInfo("Step 9: Search for CHK account from Precondition#2 and verify Has Secondary column value");
        TestRailAssert.assertTrue(Pages.maintainceStatementProcessingPage().hasSecondaryAccount(index).contains("Yes"),
                new CustomStepResult("Account " + chkAccount.getAccountNumber() + " has secondary account", "Account " +
                        chkAccount.getAccountNumber() + " has not secondary account"));

        logInfo("Step 10: Click [+] icon next to the CHK account and verify that Savings account appears below the CHK account as the Secondary");
        Pages.maintainceStatementProcessingPage().clickPlusForAccount(index);
        boolean isSavIsVisible = Pages.maintainceStatementProcessingPage().checkSecondaryInfo(savingsAccount.getAccountNumber());
        TestRailAssert.assertTrue(isSavIsVisible,new CustomStepResult("Account " + savingsAccount.getAccountNumber() + " is secondary account", "Account " +
                savingsAccount.getAccountNumber() + " is not secondary account"));

    }
}
