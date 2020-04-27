package com.nymbus.frontoffice.clientsmanagement.createclients;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.client.OrganisationClient;
import com.nymbus.newmodels.client.basicinformation.address.Address;
import com.nymbus.newmodels.client.other.document.IDType;
import com.nymbus.newmodels.generation.client.OrganisationClientSettings;
import com.nymbus.newmodels.generation.client.builder.OrganisationClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.organisation.CorporationBuilder;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Epic("Frontoffice")
@Feature("Create clients")
@Owner("Petro")
public class C23576_CreateCorporationClientTest extends BaseTest {

    OrganisationClient organisationClient;

    @BeforeMethod
    public void preConditions() {
        OrganisationClientBuilder organisationClientBuilder = new  OrganisationClientBuilder();
        organisationClientBuilder.setOrganisationTypeBuilder(new CorporationBuilder());

        organisationClient = organisationClientBuilder.buildClient();
    }

    @Test(description = "C23576, Create Client - Corporation")
    @Severity(SeverityLevel.CRITICAL)
    public void createCorporationTest() {
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        ClientsActions.organisationClientActions().createClient(organisationClient);
        ClientsActions.organisationClientActions().setClientDetailsData(organisationClient);
        ClientsActions.organisationClientActions().setDocumentation(organisationClient);

        ClientsActions.organisationClientActions().verifyClientData(organisationClient);
    }
}
