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
public class C22594_ViewNewCDIRAAccountTest extends BaseTest {

    private Account cdIRAAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up IRA account
        cdIRAAccount = new Account().setCdIraAccountData();

        // Login to the system
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set the bank branch of the user to account
        cdIRAAccount.setBankBranch(Actions.usersActions().getBankBranch());

        // Create a client with IRA account
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCDAccount(cdIRAAccount);

        // Edit Account and logout
        AccountActions.editAccount().editCDAccount(cdIRAAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22594, View New CD IRA Account")
    @Severity(SeverityLevel.CRITICAL)
    public void viewNewCDIRAAccount() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the Savings account from the precondition and open it on Details");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(cdIRAAccount);

        logInfo("Step 3: Click [Load More] button");
        AccountActions.accountDetailsActions().clickMoreButton();

        logInfo("Step 4: Pay attention to the fields on the page");
        AccountActions.accountDetailsActions().verifyCdIraAccountRecords(cdIRAAccount);

        logInfo("Step 5: Click [Less] button");
        AccountActions.accountDetailsActions().clickLessButtonAndVerifyMoreIsVisible();
    }

}