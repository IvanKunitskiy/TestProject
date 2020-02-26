package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.base.BaseTest;
import com.nymbus.models.account.Account;
import com.nymbus.models.client.Client;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.BeforeMethod;

@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C14918_CreateCheckingAccount extends BaseTest {

    private Client client;
    private Account depositAccount;

    @BeforeMethod
    public void preCondition() {
        client = new Client().setDefaultClientData();
        depositAccount = new Account().setCHKAccountData();
    }
}