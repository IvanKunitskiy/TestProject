package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.account.Account;
import com.nymbus.models.client.Client;
import com.nymbus.pages.Pages;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.BeforeMethod;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22582_AddNewSavingsIRAAccountTest {

    Client client;
    Account savingsIRAAccount;

    @BeforeMethod
    public void preCondition() {
        // Set up Client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");

        // Set up Savings account
//        savingsIRAAccount = new Account().setIRAAccountData();

        // Login to the system and create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        client.setClientID(Pages.clientDetailsPage().getClientID());
//        AccountActions.createAccount().createIRAAccount(savingsIRAAccount);
        Actions.loginActions().doLogOut();
    }
}
