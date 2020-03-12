package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.account.Account;
import com.nymbus.models.client.Client;
import com.nymbus.util.Constants;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22579_ViewNewSavingsAccount extends BaseTest {

    private Client client;
    private Account savingsAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        client.setSelectOfficer("autotest autotest"); // Controlling officer to validate 'Bank Branch' value

        // Set up savings account
        savingsAccount = new Account().setSavingsAccountData();
        savingsAccount.setBankBranch("Inspire - Langhorne"); // Branch of the 'autotest autotest' user

        // Login to the system and create a client with savings account
        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        AccountActions.createAccount().createSavingsAccount(savingsAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22579, View New Savings Account")
    @Severity(SeverityLevel.CRITICAL)
    public void viewNewSavingsAccount() {

        LOG.info("Step 1: Log in to the system as the user from the precondition");
        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

    }
}
