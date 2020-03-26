package com.nymbus.frontoffice.clientsmanagement.createclients;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.client.OrganisationClient;
import com.nymbus.newmodels.generation.client.builder.OrganisationClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.organisation.CorporationBuilder;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Create clients")
@Owner("Petro")
public class C23576_CreateCorporationClientTest extends BaseTest {

    OrganisationClient client;

    @BeforeMethod
    public void preConditions() {
        OrganisationClientBuilder organisationClientBuilder = new OrganisationClientBuilder();
        organisationClientBuilder.setOrganisationTypeBuilder(new CorporationBuilder());
        client = organisationClientBuilder.buildClient();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
    }

    @Test(description = "C23576, Create Client - Corporation")
    @Severity(SeverityLevel.CRITICAL)
    public void createCorporationTest() {
        ClientsActions.createClient().openClientPage();
        Pages.clientsSearchPage().clickAddNewClient();
        ClientsActions.createOrganisationClientActions().setBasicInformation(client);
       /* Assert.assertTrue(Pages.addClientPage().isVerificationSuccess(),
                "Client data verification is not success");
        Pages.addClientPage().clickOkModalButton();

        Pages.addClientPage().clickViewMemberProfileButton();

        Assert.assertEquals(ClientsActions.verifyClientDataActions().getIndividualConsumerClientData(), client,
                "Client's data is not equals");*/
    }
}
