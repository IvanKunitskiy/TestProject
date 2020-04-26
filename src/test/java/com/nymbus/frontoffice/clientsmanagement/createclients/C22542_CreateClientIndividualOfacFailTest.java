package com.nymbus.frontoffice.clientsmanagement.createclients;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import com.nymbus.pages.webadmin.WebAdminPages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

@Epic("Frontoffice")
@Feature("Create clients")
@Owner("Petro")
public class C22542_CreateClientIndividualOfacFailTest extends BaseTest {

    private IndividualClient individualClient;

    @BeforeMethod
    public void prepareClientData(){
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());

        individualClient = individualClientBuilder.buildClient();

        Selenide.open(Constants.WEB_ADMIN_URL);

        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Selenide.open(Constants.WEB_ADMIN_URL + "RulesUIQuery.ct?" +
                "waDbName=nymbusdev6DS&" +
                "dqlQuery=count%3A+100%0D%0A" +
                "from%3A+security.ofac.entry%0D%0A" +
                "where%3A+%0D%0A" +
                "-+.sdntype->name%3A+IndividualType&source=");

        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();

        int index = (new Random().nextInt(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult())) + 1;

        individualClient.getIndividualType().setFirstName(WebAdminPages.rulesUIQueryAnalyzerPage().getFirstNameByIndex(index));
        individualClient.getIndividualType().setMiddleName("");
        individualClient.getIndividualType().setLastName(WebAdminPages.rulesUIQueryAnalyzerPage().getLastNameByIndex(index));
    }

    @Test(description = "C22542, Create Client - IndividualType - Ofac check fail")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyOfacValidation() {
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        ClientsActions.createClient().openClientPage();

        Pages.clientsSearchPage().clickAddNewClient();

        ClientsActions.individualClientActions().setBasicInformation(individualClient);

        Assert.assertFalse(Pages.addClientPage().isVerificationSuccess(),
                "Client data verification is success");
    }
}
