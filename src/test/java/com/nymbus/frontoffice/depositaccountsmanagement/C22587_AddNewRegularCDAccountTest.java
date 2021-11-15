package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.account.InterestFrequency;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22587_AddNewRegularCDAccountTest extends BaseTest {

    private IndividualClient client;
    private Account cdAccount;

    @BeforeMethod
    public void preConditions() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up IRA account
        cdAccount = new Account().setCdAccountData();

        // Set up CHK account
        Account checkingAccount = new Account().setCHKAccountData();

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set accounts data
        cdAccount.setBankBranch(Actions.usersActions().getBankBranch());
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));
        cdAccount.setProduct(Actions.productsActions().getProduct(Products.CD_PRODUCTS, AccountType.CD, RateType.FIXED));
        cdAccount.setTermType(Actions.productsActions().getTermType(Products.CD_PRODUCTS, cdAccount));
        cdAccount.setMinTerm(Actions.productsActions().getMinTerm(Products.CD_PRODUCTS, cdAccount));
        cdAccount.setInterestFrequency(Actions.productsActions().getInterestFrequency(Products.CD_PRODUCTS, cdAccount));
        cdAccount.setInterestType(Actions.productsActions().getInterestType(Products.CD_PRODUCTS, cdAccount));

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create account and logout
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Deposit Accounts Management";

    @TestRailIssue(issueID = 22587, testRunName = TEST_RUN_NAME)
    @Test(description = "C22587, Add New Regular CD Account")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewRegularCDAccountTest() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Search for the client from the preconditions and open his profile on Accounts tab");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        Pages.clientDetailsPage().clickAccountsTab();

        logInfo("Step 3: Click 'Add New' drop down and select 'Account'");
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(cdAccount);

        logInfo("Step 4: Select 'Product Type' = 'CD Account'");
        AccountActions.createAccount().setProductType(cdAccount);

        logInfo("Step 5: Select any product not IRA (e.g. 3 month Regular Certificate)");
        AccountActions.createAccount().setProduct(cdAccount);

        logInfo("Step 6: Look through the fields. Check that fields are prefilled by default");
        AccountActions.createAccount().verifyRegularCdAccountPrefilledFields(cdAccount, client);

        logInfo("Step 7: Look at the field 'Account Title' and verify that such field is not a required field");
        TestRailAssert.assertFalse(Pages.addAccountPage().isAccountTitleFieldRequired(),
                new CustomStepResult("'Account Title' is required", "'Account Title' is not required"));

        logInfo("Step 8: Select values in such drop-down fields:");
        logInfo("Step 9: Fill in such text fields with valid data (except Account Number field):");
        logInfo("Step 10: Set switchers:\n" +
                "- Apply Seasonal Address- to NO\n" +
                "- Auto Renewable to NO\n" +
                "- Transactional Account to YES");
        logInfo("Step 11: Select Date Opened as any date < Current Date");
        cdAccount.setInterestFrequency(InterestFrequency.ONE_TIME_PAY.getInterestFrequency());
        cdAccount.setApplyInterestTo("CHK Acct");
        AccountActions.createAccount().selectValuesInFieldsRequiredForCDAccount(cdAccount);

        logInfo("Step 12: Submit the account creation by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 13: Check the fields that were filled in during account creation");
        AccountActions.accountDetailsActions().clickMoreButton();
        AccountActions.accountDetailsActions().verifyCDAccountRecords(cdAccount);

        logInfo("Step 14: Check the Maturity Date field value (verify that it's calculated based on Date Opened + Term of Account)");
        int minTerm = Integer.parseInt(cdAccount.getMinTerm());
        String maturityDate =  Actions.productsActions().getMaturityDateValue(cdAccount, minTerm);
        cdAccount.setMaturityDate(maturityDate);
        Assert.assertEquals(Pages.accountDetailsPage().getMaturityDate(), cdAccount.getMaturityDate(), "'Maturity Date' value does not match");

        logInfo("Step 15: Check the Date next interest field value (verify that it's calculated based on Date Opened + Interest Frequency)");
        String dateNextInterest =  Actions.productsActions().getDateNextInterestValue(cdAccount);
        cdAccount.setDateNextInterest(dateNextInterest);
        Assert.assertEquals(Pages.accountDetailsPage().getDateNextInterest(), cdAccount.getDateNextInterest(), "'Maturity Date' value does not match");

        logInfo("Step 16: Click [Edit] button and pay attention to the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickEditButton();
        AccountActions.editAccount().verifyCdAccountFieldsAfterCreationInEditMode(cdAccount);

        logInfo("Step 17: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 18: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        AccountActions.accountMaintenanceActions().verifyRegularCdAccountRecords(cdAccount);
    }
}
