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

    OrganisationClient client;
    OrganisationClientSettings clientSettings;

    @BeforeMethod
    public void preConditions() {
        clientSettings = getClientSettings();
        OrganisationClientBuilder organisationClientBuilder = new OrganisationClientBuilder();
        organisationClientBuilder.setOrganisationTypeBuilder(new CorporationBuilder());
        client = organisationClientBuilder.buildClient(clientSettings);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
    }

    @Test(description = "C23576, Create Client - Corporation")
    @Severity(SeverityLevel.CRITICAL)
    public void createCorporationTest() {
        ClientsActions.createClient().openClientPage();
        Pages.clientsSearchPage().clickAddNewClient();
        ClientsActions.createOrganisationClientActions().setBasicInformation(client);

        Assert.assertTrue(Pages.addClientPage().isVerificationSuccess(),
                "Client data verification is not success");

        Pages.addClientPage().clickOkModalButton();

        ClientsActions.createOrganisationClientActions().setClientDetails(client);

        ClientsActions.createOrganisationClientActions().setDocumentation(client);

        ClientsActions.createOrganisationClientActions().setSignature(client);

        Pages.addClientPage().clickViewMemberProfileButton();

        verifyClientBasicInformation(client);

        verifyClientAddressInformation(client.getOrganisationType().getAddresses());

        //TODO: add other verifications
    }

    private OrganisationClientSettings getClientSettings() {
        OrganisationClientSettings clientSettings = new OrganisationClientSettings();
        Map<IDType, Integer> documents = new HashMap<>();
        documents.put(IDType.COMPANY_ID, 1);
        clientSettings.setAddressCount(1);
        clientSettings.setPhonesCount(2);
        clientSettings.setEmailCount(1);
        clientSettings.setDocuments(documents);

        return clientSettings;
    }

    private void verifyClientBasicInformation(OrganisationClient client) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(client.getOrganisationType().getName(),
                                Pages.clientDetailsPage().getLastName(),
                                "client name doesn't match!" );
        softAssert.assertEquals(client.getOrganisationType().getClientStatus().getClientStatus(),
                                Pages.clientDetailsPage().getStatus(),
                                "client status doesn't match!");
        softAssert.assertEquals(client.getOrganisationType().getTaxPayerIDType().getTaxPayerIDType(),
                                Pages.clientDetailsPage().getTaxPairIdType(),
                                "client Tax Payer ID Type doesn't match!");
        softAssert.assertEquals(client.getOrganisationType().getTaxID(),
                                Pages.clientDetailsPage().getTaxID(),
                                "client Tax Id doesn't match!");
        softAssert.assertAll();
    }

    private void verifyClientAddressInformation(Set<Address> addresses) {
        Address [] addressArr = addresses.stream().toArray(Address[]::new);
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < addressArr.length; i++) {
            int tempIterator = i + 1;
            softAssert.assertEquals(addressArr[i].getType().getAddressType(),
                                    Pages.clientDetailsPage().getAddressType1(tempIterator),
                                    String.format("Address type %s doesn't match", i));
            softAssert.assertEquals(addressArr[i].getCountry().getCountry(),
                                    Pages.clientDetailsPage().getAddressCountry1(tempIterator),
                                    String.format("Address country %s doesn't match", i));
            softAssert.assertEquals(addressArr[i].getAddress(),
                                    Pages.clientDetailsPage().getAddress1(tempIterator),
                                    String.format("Address address line 1 %s doesn't match", i));
            softAssert.assertEquals(addressArr[i].getCity(),
                                     Pages.clientDetailsPage().getAddressCity1(tempIterator),
                                     String.format("Address city %s doesn't match", i));
            softAssert.assertEquals(addressArr[i].getState().getState(),
                                     Pages.clientDetailsPage().getAddressState1(tempIterator),
                                     String.format("Address state %s doesn't match", i));
            softAssert.assertEquals(addressArr[i].getZipCode(),
                                    Pages.clientDetailsPage().getAddressZipCode1(tempIterator),
                                    String.format("Address zipCode %s doesn't match", i));
        }
        softAssert.assertAll();
    }
}
