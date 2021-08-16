package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C23637_CreateCheckingAccountTest extends BaseTest {

    private IndividualClient client;
    private Account checkingAccount;

    @BeforeMethod
    public void preCondition() {
        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up account
        checkingAccount = new Account().setCHKAccountData();
        checkingAccount.setDateOpened(DateTime.getDateMinusDays(checkingAccount.getDateOpened(),-Constants.DAYS_BEFORE_SYSTEM_DATE));

        // Login to the system
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set the bank branch of the user to account
        checkingAccount.setBankBranch(Actions.usersActions().getBankBranch());
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create a client and logout
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Deposit Accounts Management";

    @TestRailIssue(issueID = 23637, testRunName = TEST_RUN_NAME)
    @Test(description = "C23637, Create checking account")
    @Severity(SeverityLevel.CRITICAL)
    public void createCheckingAccount() {
        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for any client and open his profile on Accounts tab");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        Pages.accountNavigationPage().clickAccountsTab();

        logInfo("Step 3: Click 'Add New' drop down and select 'Account'");
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(checkingAccount);

        logInfo("Step 4: Select 'Product Type' = 'CHK Account'");
        AccountActions.createAccount().setProductType(checkingAccount);

        logInfo("Step 5: Select any product (e.g. CHK Weiland Account)");
        AccountActions.createAccount().setProduct(checkingAccount);

        logInfo("Step 6: Look through the fields. Check that fields are prefilled by default");
        AccountActions.createAccount().verifyChkAccountPrefilledFields(checkingAccount, client);

        logInfo("Step 7: Look at the field 'Account Title' and verify that such field is not a required field");
        TestRailAssert.assertFalse(Pages.addAccountPage().isAccountTitleFieldRequired(),
                new CustomStepResult("'Account Title' is required", "'Account Title' is not required"));

        logInfo("Step 8: Select any values in drop-down fields");
        logInfo("Step 9: Fill in text fields with valid data. NOTE: do not fill in Account Number field");
        logInfo("Step 10: Select any date ≤ Current Date in DBC ODP Opt In/Out Status Date field");
        logInfo("Step 11: Set 'Apply Seasonal Address' switcher - to NO");
        AccountActions.createAccount().setValuesInFieldsRequiredForCheckingAccount(checkingAccount);

        logInfo("Step 12: Submit the account creation by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 13: Pay attention to the fields that were filled in during account creation");
        AccountActions.accountDetailsActions().clickMoreButton();
        AccountActions.accountDetailsActions().verifyChkAccountRecords(checkingAccount);

        logInfo("Step 14: Click [Edit] button and pay attention to the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickEditButton();
        AccountActions.editAccount().verifyChkAccountFieldsAfterCreationInEditMode(checkingAccount);

        logInfo("Step 15: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 16: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        AccountActions.accountMaintenanceActions().verifyChkAccountRecords(checkingAccount);
    }
}