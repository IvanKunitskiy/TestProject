package com.nymbus.frontoffice.clientsmanagement.createclients;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.FinancialInstitutionType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.clientdetails.type.ClientStatus;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Backoffice")
@Feature("Create clients")
@Owner("Dmytro")
public class C28143_CreateClientIndividualCustomerTest extends BaseTest {
    private IndividualClient individualClient;

    @BeforeMethod
    public void prepareClientData() {
        String currentFinancialType = WebAdminActions.loginActions().getFinancialType();
        if (!currentFinancialType.equals(FinancialInstitutionType.BANK.getFinancialInstitutionType())) {
            throw new SkipException("Financial Institution Type is incorrect!");
        }

        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());

        individualClient = individualClientBuilder.buildClient();
        individualClient.getIndividualType().setClientStatus(ClientStatus.CUSTOMER);
    }

    private final String TEST_RUN_NAME = "Create clients";

    @TestRailIssue(issueID = 28143, testRunName = TEST_RUN_NAME)
    @Test(description = "C28143, Create Client - Individual - Customer (FI type=Bank)")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyIndividualClientCreation() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Clients page and click [Add new Client] button");
        logInfo("Step 3: Select Client type == Individual");
        logInfo("Step 4: Select Client Status == Customer");
        logInfo("Step 5: Fill in such text fields with valid data: \n" +
                "- First Name - any alphabetical value \n" +
                "- Middle Name - any alphabetical value \n" +
                "- Last Name - any alphabetical value \n" +
                "* Tax ID - UNIQUE numeric value with length = 9 \n" +
                "* Address Line 1 - any alphanumeric value \n" +
                "* Address Line 2- any alphanumeric value \n" +
                "* City- any alphanumeric value \n" +
                "* Zip Code - 5 digits, + additional field - 4 digits \n" +
                "* Years in this address - any numeric value");
        logInfo("Step 6: Select data for such drop-down fields: \n" +
                "- Tax Payer ID Type- Individual SSN \n" +
                "- Issued By \n" +
                "- ID Type - Passport \n" +
                "- Country - any (e.g. United States) \n" +
                "- State - any \n" +
                "- Address Type - Physical");
        logInfo("Step 7: Fill in such calendar fields with valid data: \n" +
                "- Birth Date - any date ≤ Current Date \n" +
                "- Expiration Date - any date ≥ Current Date");
        logInfo("Step 8: Click [Save and Continue] button and Submit OFAC check popup");
        ClientsActions.individualClientActions().createClient(individualClient);

        logInfo("Step 9: Select data for such drop-down fields: \n" +
                "- Gender - any \n" +
                "- Education - any \n" +
                "- Income - any \n" +
                "- Marital Status \n" +
                "- Consumer Information Indicator - any \n" +
                "- Own or Rent- any \n" +
                "- Mail Code- any \n" +
                "- Select Officer - any \n" +
                "- Email - primary");
        logInfo("Step 10: Fill in such text fields with valid data: \n" +
                "- Suffix - any alphabetical value \n" +
                "- Maiden/Family Name- any alphabetical value \n" +
                "- AKA #1 - any alphanumeric value \n" +
                "- Occupation - any alphanumeric value \n" +
                "- Job Title - any alphanumeric value \n" +
                "- User Defined Fields 1- 4 - any alphanumeric value \n" +
                "- Phone - 10 digit value \n" +
                "- Email - valid email");
        logInfo("Step 11: Click [Save and Continue] button");
        ClientsActions.individualClientActions().setClientDetailsData(individualClient);

        logInfo("Step 12: Upload some document (e.g. passport) and fill in all the fields for that document \n" +
                "Click [Save Changes] button");
        logInfo("Step 13: Click [Save and Continue] button");
        logInfo("Step 14: Upload the signature or use signature pad and click [Save and Continue] button");
        ClientsActions.individualClientActions().setDocumentation(individualClient);
        logInFile("Create client - " + individualClient.getFullName());

        logInfo("Step 15: Click [View Customer Profile] button");
        ClientsActions.individualClientActions().verifyClientData(individualClient);
    }
}