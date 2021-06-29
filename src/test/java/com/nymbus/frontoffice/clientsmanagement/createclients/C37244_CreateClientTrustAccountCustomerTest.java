package com.nymbus.frontoffice.clientsmanagement.createclients;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.FinancialInstitutionType;
import com.nymbus.newmodels.client.OrganisationClient;
import com.nymbus.newmodels.client.basicinformation.address.AddressType;
import com.nymbus.newmodels.client.basicinformation.address.Country;
import com.nymbus.newmodels.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.newmodels.client.clientdetails.contactinformation.phone.PhoneType;
import com.nymbus.newmodels.client.clientdetails.type.ClientStatus;
import com.nymbus.newmodels.client.clientdetails.type.organisation.MailCode;
import com.nymbus.newmodels.client.verifyingmodels.ClientDetailsPredefinedFields;
import com.nymbus.newmodels.client.verifyingmodels.TrustAccountPredefinedField;
import com.nymbus.newmodels.generation.client.builder.OrganisationClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.organisation.TrustAccountBuilder;
import com.nymbus.newmodels.generation.client.factory.clientdetails.contactinformation.PhoneFactory;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Backoffice")
@Feature("Create clients")
@Owner("Dmytro")
public class C37244_CreateClientTrustAccountCustomerTest extends BaseTest {
    private OrganisationClient organisationClient;
    private TrustAccountPredefinedField trustAccountPredefinedField;

    @BeforeMethod
    public void prepareClientData() {
        String currentFinancialType = WebAdminActions.loginActions().getFinancialType();
        if (!currentFinancialType.equals(FinancialInstitutionType.BANK.getFinancialInstitutionType())) {
            throw new SkipException("Financial Institution Type is incorrect!");
        }

        OrganisationClientBuilder organisationClientBuilder = new  OrganisationClientBuilder();
        organisationClientBuilder.setOrganisationTypeBuilder(new TrustAccountBuilder());
        organisationClient = organisationClientBuilder.buildClient();
        organisationClient.getOrganisationType().setClientStatus(ClientStatus.CUSTOMER);

        trustAccountPredefinedField = getTrustAccountPredefinedField();
    }

    private final String TEST_RUN_NAME = "Create clients";

    @TestRailIssue(issueID = 37244, testRunName = TEST_RUN_NAME)
    @Test(description = "C37244, Create Client - Trust account (for FI type = Bank)")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyIndividualClientCreation() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        String currentOfficer = String.format("%s %s", userCredentials.getUserName(), userCredentials.getUserName());

        logInfo("Step 2: Go to Clients page and click [Add new Client] button");
        logInfo("Step 3: Select Client type == Trust Account");
        logInfo("Step 4: Select Client Status == Customer");
        logInfo("Step 5: Verify the fields that are displayed prefilled: \n" +
                "- Tax Payer ID Type (Business TIN) \n" +
                "- Address Type (Physical) \n" +
                "- Country (United States) ");
        logInfo("Step 6: Fill in such text fields with valid data: \n" +
                "- Name - any alphabetical value \n" +
                "- Tax ID - UNIQUE numeric value with length = 9 \n" +
                "- Address - any alphanumeric value \n" +
                "- Address Line 2- any alphanumeric value \n" +
                "- City- any alphanumeric value \n" +
                "- Zip Code - 5 digits, + additional field - 4 digits \n" +
                "- Years in this address - any alphanumeric value");
        logInfo("Step 7: Select data for such drop-down field: \n" +
                "- State - any");
        logInfo("Step 8: Click [Save and Continue] button and Submit OFAC check popup");
        ClientsActions.organisationClientActions().createClient(organisationClient, trustAccountPredefinedField);

        logInfo("Step 9: Verify the fields that are displayed prefilled: \n" +
                "- Mail CoDE (mail) \n" +
                "- Select Officer (Current User) \n" +
                "- Phone (Home, United States)");
        ClientsActions.organisationClientActions().verifyClientDetailsPredefinedFields(getDetailsPredefinedField(currentOfficer));

        logInfo("Step 10: Select data for such drop-down field: \n" +
                "- Email - primary");
        logInfo("Step 11: Fill in such text fields with valid data: \n" +
                "- AKA #1 - any alphanumeric value \n" +
                "- User Defined Fields 1- 4 - any alphanumeric value \n" +
                "- Phone - 10 digit value \n" +
                "- Email - valid email format");
        logInfo("Step 12: Click [Browse] button in Profile Photo section and upload any image");
        logInfo("Step 13: Click [Save and Continue] button");
        ClientsActions.organisationClientActions().setClientDetailsData(organisationClient);

        logInfo("Step 14: Upload some document and fill in all the fields for that document \n" +
                "Click [Save Changes] button");
        logInfo("Step 15: Click [Save and Continue] button");
        logInfo("Step 16: Upload the signature or use signature pad and click [Save and Continue] button");
        ClientsActions.organisationClientActions().setDocumentation(organisationClient);

        logInfo("Step 17: Click [View Customer Profile] button");
        ClientsActions.organisationClientActions().verifyClientData(organisationClient);
    }

    private TrustAccountPredefinedField getTrustAccountPredefinedField() {
        return new TrustAccountPredefinedField(TaxPayerIDType.BUSINESS_TIN, AddressType.PHYSICAL, Country.UNITED_STATES);
    }

    private ClientDetailsPredefinedFields getDetailsPredefinedField(String currentUser) {
        return ClientDetailsPredefinedFields.builder()
                .mailCode(MailCode.MAIL)
                .selectOfficer(currentUser)
                .phone(new PhoneFactory().getPhoneWithType(PhoneType.HOME))
                .build();
    }
}