package com.nymbus.frontoffice.clientsmanagement.createclients;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.FinancialInstitutionType;
import com.nymbus.newmodels.client.OrganisationClient;
import com.nymbus.newmodels.client.basicinformation.address.AddressType;
import com.nymbus.newmodels.client.basicinformation.address.Country;
import com.nymbus.newmodels.client.basicinformation.type.TaxPayerIDType;
import com.nymbus.newmodels.client.clientdetails.type.ClientStatus;
import com.nymbus.newmodels.client.verifyingmodels.TrustAccountPredefinedField;
import com.nymbus.newmodels.generation.client.builder.OrganisationClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.organisation.TrustAccountBuilder;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C37244_CreateClientTrustAccountCustomerTest extends BaseTest {
    private OrganisationClient organisationClient;
    private TrustAccountPredefinedField trustAccountPredefinedField;

    @BeforeMethod
    public void prepareClientData() {
        String currentFinancialType = WebAdminActions.loginActions().getFinancialType();
        Assert.assertEquals(currentFinancialType, FinancialInstitutionType.BANK.getFinancialInstitutionType(),
                "Financial Institution Type is incorrect!");

        OrganisationClientBuilder organisationClientBuilder = new  OrganisationClientBuilder();
        organisationClientBuilder.setOrganisationTypeBuilder(new TrustAccountBuilder());
        organisationClient = organisationClientBuilder.buildClient();
        organisationClient.getOrganisationType().setClientStatus(ClientStatus.CUSTOMER);

        trustAccountPredefinedField = getTrustAccountPredefinedField();
    }

    @Test(description = "C37244, Create Client - Trust account (for FI type = Bank)")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyIndividualClientCreation() {
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        ClientsActions.organisationClientActions().createClient(organisationClient, trustAccountPredefinedField);
        ClientsActions.organisationClientActions().setClientDetailsData(organisationClient);
        ClientsActions.organisationClientActions().setDocumentation(organisationClient);

        ClientsActions.organisationClientActions().verifyClientData(organisationClient);
    }

    private TrustAccountPredefinedField getTrustAccountPredefinedField() {
        return new TrustAccountPredefinedField(TaxPayerIDType.BUSINESS_TIN, AddressType.PHYSICAL, Country.UNITED_STATES);
    }
}