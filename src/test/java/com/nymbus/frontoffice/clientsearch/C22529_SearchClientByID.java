package com.nymbus.frontoffice.clientsearch;

import com.nymbus.base.BaseTest;
import com.nymbus.models.client.Client;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("User Managenment")
@Owner("Dmytro")
public class C22529_SearchClientByID extends BaseTest {
    private Client client;

    @BeforeMethod
    public void preCondition() {
        client = new Client();
    }

    @Test(description = "C22529, Search client by ID")
    public void SearchByID() {

    }


}
