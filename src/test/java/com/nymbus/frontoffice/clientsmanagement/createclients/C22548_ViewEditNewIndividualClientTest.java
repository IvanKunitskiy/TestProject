package com.nymbus.frontoffice.clientsmanagement.createclients;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.basicinformation.address.Address;
import com.nymbus.newmodels.client.clientdetails.contactinformation.phone.Phone;
import com.nymbus.newmodels.client.verifyingmodels.TrustAccountUpdateModel;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.client.factory.basicinformation.AddressFactory;
import com.nymbus.newmodels.generation.client.factory.clientdetails.contactinformation.PhoneFactory;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Backoffice")
@Feature("Create clients")
@Owner("Dmytro")
public class C22548_ViewEditNewIndividualClientTest extends BaseTest {
    private String clientId;
    private TrustAccountUpdateModel updateModel;

    @BeforeMethod
    public void prepareClientData() {
        // Setup updated data
        updateModel = new TrustAccountUpdateModel();
        updateModel.setUpdatedPhones(getPhoneListForUpdatePurpose());
        updateModel.setAdditionalAddress(getAdditionalAddress());

        // Setup individual client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient individualClient = individualClientBuilder.buildClient();

        // Login
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.individualClientActions().createClient(individualClient);
        ClientsActions.individualClientActions().setClientDetailsData(individualClient);
        ClientsActions.individualClientActions().setDocumentation(individualClient);
        logInFile("Create client - " + individualClient.getFullName());

        clientId = Pages.clientDetailsPage().getClientID();
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Create clients";

    @TestRailIssue(issueID = 22548, testRunName = TEST_RUN_NAME)
    @Test(description = "C22548, View / edit new individual client (member)")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyIndividualClientEdition() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Clients page and search for the client from preconditions");
        logInfo("Step 3: Open selected Client Profile");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientId);

        logInfo("Step 4: Check such fields and verify that they are not blank in the View mode: \n" +
                "- Client type \n" +
                "- Client Status \n" +
                "- First Name \n" +
                "- Last Name \n" +
                "- Tax Payer ID Type \n" +
                "- Tax ID \n" +
                "- Birth Date \n" +
                "- Occupation \n" +
                "- Select Officer \n" +
                "- Phone Home \n" +
                "- Address1 section for Physical address");
        ClientsActions.clientDetailsActions().verifyNotBlankFieldsForIndividualClient();

        logInfo("Step 5: Click [Edit Profile] button" +
                "Verify that asterisks are placed before mandatory fields");
        ClientsActions.clientDetailsActions().clickEditProfile();

        logInfo("Step 6: Check all required fields, make sure that they are not blank");
        ClientsActions.clientDetailsActions().verifyNotBlankFieldsForIndividualClient();

        logInfo("Step 7: Update several required fields with any valid date \n" +
                "- Fill in optional fields with another data \n" +
                "- Add one more Address (e.g. Type = Mailing) and fill in Address specific fields: \n" +
                "Address line1 - any alphanumeric value \n" +
                "Address line2- any alphanumeric value \n" +
                "City - any alphanumeric value \n" +
                "State - any value from the drop-down \n" +
                "zip code - 5 digit value + additional field 4 digit value \n" +
                "- Switch 'Ignore OFAC Matching' - 'Yes' position" +
                "and click [Save Changes] button");
        // Set user defined fields
        Pages.addClientPage().setUserDefinedField1Value(updateModel.getUserDefinedField1());
        Pages.addClientPage().setUserDefinedField2Value(updateModel.getUserDefinedField2());
        Pages.addClientPage().setUserDefinedField3Value(updateModel.getUserDefinedField3());
        Pages.addClientPage().setUserDefinedField4Value(updateModel.getUserDefinedField4());

        // Update phones
        ClientsActions.clientDetailsActions().deleteAllPhoneRows();
        ClientsActions.clientDetailsActions().setPhones(updateModel.getUpdatedPhones());

        // Add one more address
        ClientsActions.clientDetailsActions().addAddress(updateModel.getAdditionalAddress());

        //Update switch Ignore OFAC Matching
        ClientsActions.clientDetailsActions().clickIgnoreOFACMatching();

        // Save changes
        ClientsActions.clientDetailsActions().clickSaveChangesButton();

        // Verify data
        ClientsActions.clientDetailsActions().verifyTrustAccountUpdatedFields(updateModel);
    }

    private List<Phone> getPhoneListForUpdatePurpose() {
        return new PhoneFactory().getPhoneListForUpdatePurpose();
    }

    private Address getAdditionalAddress() {
        return new AddressFactory().getAdditionalAddress();
    }
}