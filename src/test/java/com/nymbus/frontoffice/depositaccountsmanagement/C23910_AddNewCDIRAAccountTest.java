package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C23910_AddNewCDIRAAccountTest extends BaseTest {

    private IndividualClient client;
    private Account cdIRAAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up CD IRA account
        cdIRAAccount = new Account().setCdIraAccountData();

        // Login to the system
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set account data
        cdIRAAccount.setBankBranch(Actions.usersActions().getBankBranch());
        cdIRAAccount.setProduct(Actions.productsActions().getProduct(Products.CD_PRODUCTS, AccountType.IRA, RateType.FIXED));
        cdIRAAccount.setInterestType(Actions.productsActions().getInterestType(Products.CD_PRODUCTS, cdIRAAccount));
        cdIRAAccount.setTermType(Actions.productsActions().getTermType(Products.CD_PRODUCTS, cdIRAAccount));
        cdIRAAccount.setMinTerm(Actions.productsActions().getMinTerm(Products.CD_PRODUCTS, cdIRAAccount));
        cdIRAAccount.setMaturityDate(Actions.productsActions().getMaturityDateValue(cdIRAAccount, Integer.parseInt(cdIRAAccount.getMinTerm())));

        // Create a client and logout
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C23910, Add New CD IRA Account")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewCDIRAAccount() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the client from the preconditions and open his profile on Accounts tab");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        Pages.clientDetailsPage().clickAccountsTab();

        logInfo("Step 3: Click 'Add New' drop down and select 'Account'");
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(cdIRAAccount);

        logInfo("Step 4: Select 'Product Type' = 'CD Account'");
        AccountActions.createAccount().setProductType(cdIRAAccount);

        logInfo("Step 5: Select any CD IRA product (product with Account Type= Ira, Roth IRA, Coverdell ESA)");
        AccountActions.createAccount().setProduct(cdIRAAccount);

        logInfo("Step 6: Look through the fields. Check that fields are prefilled by default");
        AccountActions.createAccount().verifyCdIraAccountPrefilledFields(cdIRAAccount, client);

        logInfo("Step 7: Select values in such drop-down fields:");
        logInfo("Step 8: Fill in such text fields with valid data (except Account Number field):");
        logInfo("Step 9: Select Date Opened as any date < Current Date\n" +
                "Select Date next IRA distribution as any date > Current Date\n" +
                "Select Date Of First Deposit as any date");
        logInfo("Step 10: Set switchers:\n" +
                "- Apply Seasonal Address- to NO\n" +
                "- Auto Renewable to NO\n" +
                "- Transactional Account to YES");
        AccountActions.createAccount().setValuesInFieldsRequiredForCDIRAAccount(cdIRAAccount);

        logInfo("Step 11: Submit the account creation by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 12: Pay attention to the fields that were filled in during account creation");
        AccountActions.accountDetailsActions().clickMoreButton();
        AccountActions.accountDetailsActions().verifyCdIraAccountRecords(cdIRAAccount);

        logInfo("Step 13: Check the Maturity Date field value (verify that it's calculated based on Date Opened + Term of Account)");
        Assert.assertEquals(Pages.accountDetailsPage().getMaturityDate(), cdIRAAccount.getMaturityDate(), "'Maturity Date' value does not match");

        logInfo("Step 14: Click [Edit] button and pay attention to the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickEditButton();
        AccountActions.editAccount().verifyCdIraFieldsAfterCreationInEditMode(cdIRAAccount);

        logInfo("Step 15: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 16: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        AccountActions.accountMaintenanceActions().verifyCdIraAccountRecords(cdIRAAccount);
    }
}

