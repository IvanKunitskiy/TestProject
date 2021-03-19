package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22584_ViewNewSavingsIRAAccountTest extends BaseTest {

    private Account savingsIRAAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up IRA account
        savingsIRAAccount = new Account().setSavingsIraAccountData();

        // Login to the system
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set the bank branch of the user to account
        savingsIRAAccount.setBankBranch(Actions.usersActions().getBankBranch());

        // Set the product of the user to account
        savingsIRAAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.IRA, RateType.TIER));

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create and edit IRA account
        AccountActions.createAccount().createIRAAccount(savingsIRAAccount);
        AccountActions.editAccount().editSavingsAccount(savingsIRAAccount);
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Deposit Accounts Management";

    @TestRailIssue(issueID = 22584, testRunName = TEST_RUN_NAME)
    @Test(description = "C22584, View new savings IRA account", enabled = false)
    @Severity(SeverityLevel.CRITICAL)
    public void viewNewSavingsIRAAccount() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the Savings account from the precondition and open it on Details");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(savingsIRAAccount);

        logInfo("Step 3: Click [Load More] button");
        AccountActions.accountDetailsActions().clickMoreButton();

        logInfo("Step 4: Pay attention to the fields that were filled in during account creation");
        AccountActions.accountDetailsActions().verifySavingsIRAAccountRecords(savingsIRAAccount);

        logInfo("Step 5: Click [Less] button");
        AccountActions.accountDetailsActions().clickLessButtonAndVerifyMoreIsVisible();
    }
}
