package com.nymbus.frontoffice.clientsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.transfers.TransfersActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.aspectj.lang.annotation.Before;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Clients Management")
@Owner("Petro")
public class C22560_ViewClientLevelCallStatementTest extends BaseTest {

    private Client client;
    private Account chkAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");

        // Set up account
        chkAccount = new Account().setCHKAccountData();

        // Create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        client.setClientID(Pages.clientDetailsPage().getClientID());
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Actions.tellerActions().assignAmountToAccount(chkAccount, "2", "20000");
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22560, View client level 'Call Statement'")
    @Severity(SeverityLevel.CRITICAL)
    public void viewClientLevelCallStatement() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Clients and search for the client from the precondition");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);

        logInfo("Step 3: Open Clients Profile on the Transfers tab");
        Pages.accountNavigationPage().clickAccountsTab();

        logInfo("Step 4: Click [Call Statement] button");
        Pages.accountsPage().clickCallStatementButton();

        logInfo("Step 5: Select address and AKA and click [OK] button");
        AccountActions.callStatement().setAddress(client);
        Pages.callStatementModalPage().clickOkButton();

        logInfo("Step 6: Look at the generated report");
        // TODO: parse pdf file

    }
}
