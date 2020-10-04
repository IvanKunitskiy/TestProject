package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22579_ViewNewSavingsAccountTest extends BaseTest {

    private IndividualClient client;
    private Account savingsAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up savings account
        savingsAccount = new Account().setSavingsAccountData();

        // Login to the system
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set the bank branch of the user to account
        savingsAccount.setBankBranch(Actions.usersActions().getBankBranch());

        // Create a client with savings account
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        AccountActions.createAccount().createSavingsAccount(savingsAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22579, View New Savings Account")
    @Severity(SeverityLevel.CRITICAL)
    public void viewNewSavingsAccount() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the Savings account from the precondition and open it on Details");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(savingsAccount);

        logInfo("Step 3: Click [Load More] button");
        AccountActions.accountDetailsActions().clickMoreButton();

        logInfo("Step 4: Pay attention to the fields on the page");
        AccountActions.accountDetailsActions().verifySavingsAccountRecords(savingsAccount);

        logInfo("Step 5: Click [Less] button");
        AccountActions.accountDetailsActions().clickLessButtonAndVerifyMoreIsVisible();
    }
}