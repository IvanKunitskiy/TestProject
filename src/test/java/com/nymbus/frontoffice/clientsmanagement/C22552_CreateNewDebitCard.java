package com.nymbus.frontoffice.clientsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.base.BaseTest;
import com.nymbus.model.client.Client;
import com.nymbus.pages.Pages;
import com.nymbus.util.Constants;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.qameta.allure.SeverityLevel.CRITICAL;

@Epic("Frontoffice")
@Feature("User Management")
@Owner("Dmytro")
public class C22552_CreateNewDebitCard extends BaseTest {
    private Client client;

    @BeforeMethod
    public void preCondition() {
        client = new Client();
        client.setLastName("ndjws");
//        individualClient.setBinControl(new BinControl().getDefaultBinControl());

        navigateToUrl(Constants.URL);

        LOG.info("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Pages.navigationPage().waitForUserMenuVisible();


    }

    @Severity(CRITICAL)
    @Test(description = "C22552, Clients Profile: Create new debit debitcard")
    public void createNewDebitCard() throws InterruptedException {
        LOG.info("Step 2: Search for any individualClient and open his profile on the Maintenance tab");
        Pages.clientsPage().typeToClientsSearchInputField(client.getLastName());

        Assert.assertEquals(Pages.clientsPage().getLookupResultsCount(), 1);
        Assert.assertFalse(Pages.clientsPage().isLoadMoreResultsButtonVisible());

        Pages.clientsPage().clickOnViewAccountButtonByValue(client.getLastName());

        Pages.clientDetailsPage().clickOnMaintenanceTab();
        Actions.maintenancePageActions().addNewDebitCard(client);

        Thread.sleep(3_000);
    }
}
