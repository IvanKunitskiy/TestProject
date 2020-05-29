package com.nymbus.frontoffice.clientsmanagement.createclients;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.client.OrganisationClient;
import com.nymbus.newmodels.client.clientdetails.contactinformation.phone.Phone;
import com.nymbus.newmodels.generation.client.builder.OrganisationClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.organisation.TrustAccountBuilder;
import com.nymbus.newmodels.generation.client.factory.clientdetails.contactinformation.PhoneFactory;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class C24987_ViewEditNewTrustAccountTest extends BaseTest {
    private OrganisationClient organisationClient;
    private String clientId;

    @BeforeMethod
    public void prepareClientData() {
        OrganisationClientBuilder organisationClientBuilder = new  OrganisationClientBuilder();
        organisationClientBuilder.setOrganisationTypeBuilder(new TrustAccountBuilder());
        organisationClient = organisationClientBuilder.buildClient();

        // Login
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.organisationClientActions().createClient(organisationClient);
        ClientsActions.organisationClientActions().setClientDetailsData(organisationClient);
        ClientsActions.organisationClientActions().setDocumentation(organisationClient);

        clientId = Pages.clientDetailsPage().getClientID();
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C24987, View/edit new Trust Account client")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTrustAccountEdition() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Clients page and search for the client from preconditions");
        logInfo("Step 3: Open selected Client Profile");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientId);

        logInfo("Step 4: Check such fields and verify that they are not blank in the View mode: \n" +
                "- Client Status \n" +
                "- Name \n" +
                "- Tax Payer ID Type \n" +
                "- Tax ID \n" +
                "- Mail Code \n" +
                "- Tax ID \n" +
                "- Select Officer \n" +
                "- Phone Home \n" +
                "- Address1 section for Physical address");
        ClientsActions.clientDetailsActions().verifyNotBlankFields();

        logInfo("Step 5: Click [Edit Profile] button");
        ClientsActions.clientDetailsActions().clickEditProfile();

        logInfo("Step 6: Update several required fields with any valid date");
        ClientsActions.clientDetailsActions().deleteAllPhoneRows();
        ClientsActions.clientDetailsActions().setPhones(getPhoneListForUpdatePurpose());
    }

    private List<Phone> getPhoneListForUpdatePurpose() {
        return new PhoneFactory().getPhoneListForUpdatePurpose();
    }
}