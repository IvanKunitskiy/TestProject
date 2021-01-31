package com.nymbus.frontoffice.clientsmanagement.createclients;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.FinancialInstitutionType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Create clients")
@Owner("Petro")
public class C22541_CreateIndividualMemberClientTest extends BaseTest {

    private IndividualClient individualClient;

    @BeforeMethod
    public void prepareClientData() {
        String currentFinancialType = WebAdminActions.loginActions().getFinancialType();
        if (!currentFinancialType.equals(FinancialInstitutionType.FEDERAL_CREDIT_UNION.getFinancialInstitutionType())) {
            throw new SkipException("Financial Institution Type is incorrect!");
        }

        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());

        individualClient = individualClientBuilder.buildClient();
    }

    private final String TEST_RUN_NAME = "Create clients";

    @TestRailIssue(issueID = 22541, testRunName = TEST_RUN_NAME)
    @Test(description = "C22541, Create Client - IndividualType - Member")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyIndividualClientCreation() {
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        ClientsActions.individualClientActions().createClient(individualClient);
        ClientsActions.individualClientActions().setClientDetailsData(individualClient);
        ClientsActions.individualClientActions().setDocumentation(individualClient);

        ClientsActions.individualClientActions().verifyClientData(individualClient);

    }
}
