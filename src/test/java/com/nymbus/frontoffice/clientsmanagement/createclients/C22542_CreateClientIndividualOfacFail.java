package com.nymbus.frontoffice.clientsmanagement.createclients;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.client.Client;
import com.nymbus.pages.Pages;
import com.nymbus.pages.webadmin.WebAdminPages;
import com.nymbus.util.Constants;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

@Epic("Frontoffice")
@Feature("Create clients")
public class C22542_CreateClientIndividualOfacFail extends BaseTest {

    private Client client;

    @BeforeMethod
    public void prepareClientData(){
        client = new Client().setDefaultClientData();

        navigateToUrl(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        navigateToUrl(Constants.WEB_ADMIN_URL + "RulesUIQuery.ct?" +
                "waDbName=nymbusdev6DS&" +
                "dqlQuery=count%3A+100%0D%0A" +
                "from%3A+security.ofac.entry%0D%0A" +
                "where%3A+%0D%0A" +
                "-+.sdntype->name%3A+Individual&source=");

        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();

        int index = (new Random().nextInt(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult())) + 1;

        client.setClientType("Individual");
        client.setClientStatus("Member");
        client.setFirstName(WebAdminPages.rulesUIQueryAnalyzerPage().getFirstNameByIndex(index));
        client.setMiddleName("");
        client.setLastName(WebAdminPages.rulesUIQueryAnalyzerPage().getLastNameByIndex(index));
    }

    @Test(description = "C22542, Create Client - Individual - Ofac check fail")
    @Severity(SeverityLevel.CRITICAL)
    public void firstTest() {
        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        ClientsActions.createClient().openClientPage();

        Pages.clientsPage().clickAddNewClient();

        ClientsActions.createClient().setBasicInformation(client);

        Assert.assertFalse(Pages.addClientPage().isVerificationSuccess(),
                "Client data verification is success");
    }
}
