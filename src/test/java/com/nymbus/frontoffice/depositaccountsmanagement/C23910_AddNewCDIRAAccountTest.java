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
public class C23910_AddNewCDIRAAccountTest extends BaseTest {

    private Account checkingAccount;
    private IndividualClient client;
    private Account cdIRAAccount;
    private Account savingsIRAAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up CHK and Savings account (required as Corresponding Account)
        checkingAccount = new Account().setCHKAccountData();
        savingsIRAAccount = new Account().setSavingsAccountData();
        savingsIRAAccount.setApplyInterestTo("Remain in Account");

        // Set up CD IRA account
        cdIRAAccount = new Account().setCdIraAccountData();

        // Login to the system
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set the missing CHK account data
        checkingAccount.setBankBranch(Actions.usersActions().getBankBranch());
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Set the product of the user to account
        savingsIRAAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));

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

        // Create CHK account
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        Pages.accountNavigationPage().clickAccountsTab();
        AccountActions.createAccount().createSavingsAccount(savingsIRAAccount);
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Deposit Accounts Management";

    @TestRailIssue(issueID = 23910, testRunName = TEST_RUN_NAME)
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

        logInfo("Step 6: Look through the fields.\n" +
                "Check that such fields are prefilled by default:\n" +
                "- Account Type\n" +
                "- Mail Code (if this field was filled in on Client level)\n" +
                "- Account Holders and Signers section\n" +
                "- Date Opened\n" +
                "- Originating Officer and Current Officer\n" +
                "- Bank Branch\n" +
                "- Term Type\n" +
                "- Auto Renewable switcher (Default=YES)\n" +
                "- Interest Frequency - frequency from the selected product)\n" +
                "- Interest Rate - Rate from the selected product\n" +
                "- Apply Interest To - (remain in Account default value)\n" +
                "- Interest Type - (Simple default value)\n" +
                "- Transactional Account - Default= NO\n" +
                "- Apply Seasonal Address- Default = YES\n" +
                "- Date of Birth - Client's Date of Birth\n" +
                "- IRA Distribution Frequency (No dist by default)\n" +
                "- IRA Distribution Code (No Dist by default)");
        AccountActions.createAccount().verifyCdIraAccountPrefilledFields(cdIRAAccount, client);

        logInfo("Step 7: Look at the field 'Account Title' and verify that such field is not a required field");
        TestRailAssert.assertTrue(Pages.addAccountPage().isAccountTitleFieldRequired(),
                new CustomStepResult("'Account Title' is required", "'Account Title' is not required"));

        logInfo("Step 8: Select values in such drop-down fields:\n" +
                "- Current Officer (any value that differs from the default Officer)\n" +
                "- Bank Branch\n" +
                "- Corresponding Account (if another active CHK/Savings account exists for selected Client)\n" +
                "- Interest Frequency\n" +
                "- Apply Interest To\n" +
                "- Interest Type\n" +
                "- Call Class Code - (if the drop-down is not blank)\n" +
                "- IRA Distribution Frequency\n" +
                "- IRA Distribution Code = CHK Acct\n" +
                "- IRA Distribution Account Number = CHK account from precondition#2");
        logInfo("Step 9: Fill in such text fields with valid data (except Account Number field):\n" +
                "- Account Title - alphanumeric value\n" +
                "- IRA distribution amount - numeric value (12 char, max)");
        logInfo("Step 10: Select Date Opened as any date < Current Date\n" +
                "Select Date next IRA distribution as any date > Current Date\n" +
                "Select Date Of First Deposit as any date");
        logInfo("Step 11: Set switchers:\n" +
                "- Apply Seasonal Address- to NO\n" +
                "- Auto Renewable to NO\n" +
                "- Transactional Account to YES");
        cdIRAAccount.setIraDistributionCode("CHK Acct");
        cdIRAAccount.setIraDistributionAccountNumber(checkingAccount.getAccountNumber());
        AccountActions.createAccount().setValuesInFieldsRequiredForCDIRAAccount(cdIRAAccount);
        AccountActions.createAccount().setIRADistributionFrequency(cdIRAAccount);

        logInfo("Step 12: Submit the account creation by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 13: Pay attention to the fields that were filled in during account creation");
        AccountActions.accountDetailsActions().clickMoreButton();
        AccountActions.accountDetailsActions().verifyCdIraAccountRecords(cdIRAAccount);

        logInfo("Step 14: Check the Maturity Date field value (verify that it's calculated based on Date Opened + Term of Account)");
        Assert.assertEquals(Pages.accountDetailsPage().getMaturityDate(), cdIRAAccount.getMaturityDate(), "'Maturity Date' value does not match");

        logInfo("Step 15: Click [Edit] button and pay attention to the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickEditButton();
        AccountActions.editAccount().verifyCdIraFieldsAfterCreationInEditMode(cdIRAAccount);

        logInfo("Step 16: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 17: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        AccountActions.accountMaintenanceActions().verifyCdIraAccountRecords(cdIRAAccount);

        logInfo("Step 18: Repeat steps 2-6\n" +
                "Fill in all the same fields as in Step7\n" +
                "BUT\n" +
                "- IRA Distribution Code = Check\n" +
                "and verify IRA Distribution Account Number");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        Pages.accountNavigationPage().clickAccountsTab();
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(cdIRAAccount);
        AccountActions.createAccount().setProductType(cdIRAAccount);
        AccountActions.createAccount().setProduct(cdIRAAccount);
        cdIRAAccount.setIraDistributionCode("Check");
        cdIRAAccount.setIraDistributionAccountNumber(null);
        AccountActions.createAccount().setValuesInFieldsRequiredForCDIRAAccount(cdIRAAccount);

        logInfo("Step 19: Fill in all other required fields and click [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
        AccountActions.accountDetailsActions().clickMoreButton();
        AccountActions.accountDetailsActions().verifyCdIraAccountRecords(cdIRAAccount);

        logInfo("Step 20: Repeat steps 2-6\n" +
                "Fill in all the same fields as in Step7\n" +
                "BUT\n" +
                "- IRA Distribution Code = Savings Account\n" +
                "- IRA Distribution Account Number = Any");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        Pages.accountNavigationPage().clickAccountsTab();
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(cdIRAAccount);
        AccountActions.createAccount().setProductType(cdIRAAccount);
        AccountActions.createAccount().setProduct(cdIRAAccount);
        cdIRAAccount.setIraDistributionCode("Savings Acct");
        cdIRAAccount.setIraDistributionAccountNumber(savingsIRAAccount.getAccountNumber());
        AccountActions.createAccount().setValuesInFieldsRequiredForCDIRAAccount(cdIRAAccount);
        AccountActions.createAccount().setIRADistributionFrequency(cdIRAAccount);

        logInfo("Step 21: Fill in all other required fields and click [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
        AccountActions.accountDetailsActions().clickMoreButton();
        AccountActions.accountDetailsActions().verifyCdIraAccountRecords(cdIRAAccount);

        logInfo("Step 22: Repeat steps 2-6\n" +
                "Fill in all the same fields as in Step7\n" +
                "BUT\n" +
                "- IRA Distribution Code = No Dist\n" +
                "and verify IRA Distribution Account Number");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        Pages.accountNavigationPage().clickAccountsTab();
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(cdIRAAccount);
        AccountActions.createAccount().setProductType(cdIRAAccount);
        AccountActions.createAccount().setProduct(cdIRAAccount);
        cdIRAAccount.setIraDistributionCode("No dist");
        cdIRAAccount.setIraDistributionAccountNumber(null);
        AccountActions.createAccount().setValuesInFieldsRequiredForCDIRAAccount(cdIRAAccount);

        logInfo("Step 23: Fill in all other required fields and click [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
        AccountActions.accountDetailsActions().clickMoreButton();
        AccountActions.accountDetailsActions().verifyCdIraAccountRecords(cdIRAAccount);
    }
}

