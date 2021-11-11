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
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22582_AddNewSavingsIRAAccountTest extends BaseTest {

    private Account checkingAccount;
    private IndividualClient client;
    private Account savingsIRAAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up CHK account (required as Corresponding Account)
        checkingAccount = new Account().setCHKAccountData();

        // Set up IRA account
        savingsIRAAccount = new Account().setSavingsIraAccountData();
        savingsIRAAccount.setApplyInterestTo("Remain in Account");

        // Login to the system
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set the missing CHK account data
        checkingAccount.setBankBranch(Actions.usersActions().getBankBranch());
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));
        Pages.aSideMenuPage().clickClientMenuItem();

        // Set the bank branch of the user to account
        savingsIRAAccount.setBankBranch(Actions.usersActions().getBankBranch());

        // Set the product of the user to account
        savingsIRAAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.IRA, RateType.TIER));

        // Create a client and logout
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create CHK account
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Deposit Accounts Management";

    @TestRailIssue(issueID = 22582, testRunName = TEST_RUN_NAME)
    @Test(description = "C22582, Add New Savings IRA Account")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewIRAAccount() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the client from the preconditions and open his profile on Accounts tab");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        Pages.accountNavigationPage().clickAccountsTab();

        logInfo("Step 3: Click 'Add New' drop down and select 'Account'");
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(savingsIRAAccount);

        logInfo("Step 4: Select 'Product Type' = 'Savings Account'");
        AccountActions.createAccount().setProductType(savingsIRAAccount);

        logInfo("Step 5: Select any Savings IRA product (product with Account Type= Ira, Roth IRA, Coverdell ESA)");
        AccountActions.createAccount().setProduct(savingsIRAAccount);

        logInfo("Step 6: Look through the fields. Check that fields are prefilled by default");
        AccountActions.createAccount().verifySavingsIraAccountPrefilledFields(savingsIRAAccount, client);

        logInfo("Step 7: Select any values in drop-down fields");
        logInfo("Step 8: Fill in such text fields with valid data (except Account Number field):");
        logInfo("Step 9: Select Date Opened as any date < Current Date and Select Date next IRA distribution as any date > Current Date" +
                "Select Date next IRA distribution as any date > Current Date" +
                "Select Date Of First Deposit as any date");
        savingsIRAAccount.setIraDistributionCode("CHK Acct");
        savingsIRAAccount.setIraDistributionAccountNumber(checkingAccount.getAccountNumber());
        AccountActions.createAccount().selectValuesInDropdownFieldsRequiredForSavingsIRAAccount(savingsIRAAccount);

        logInfo("Step 10: Set 'Apply Seasonal Address' switcher - to NO");
        if (Pages.addAccountPage().getApplySeasonalAddressSwitchValue().equals("yes")) {
            Pages.addAccountPage().clickApplySeasonalAddressSwitch();
        }

        logInfo("Step 11: Submit the account creation by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 12: Pay attention to the fields that were filled in during account creation");
        AccountActions.accountDetailsActions().clickMoreButton();
        AccountActions.accountDetailsActions().verifySavingsIRAAccountRecords(savingsIRAAccount);

        logInfo("Step 13: Click [Edit] button and pay attention to the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickEditButton();
        AccountActions.editAccount().verifySavingsIraFieldsAfterCreationInEditMode(savingsIRAAccount);

        logInfo("Step 14: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 15: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        AccountActions.accountMaintenanceActions().verifySavingsIraAccountRecords(savingsIRAAccount);

        logInfo("Step 16: Repeat steps 2-6\n" +
                "Fill in all the same fields as in Step7\n" +
                "BUT\n" +
                "- IRA Distribution Code = Check\n" +
                "and verify IRA Distribution Account Number");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        Pages.accountNavigationPage().clickAccountsTab();
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(savingsIRAAccount);
        AccountActions.createAccount().setProductType(savingsIRAAccount);
        AccountActions.createAccount().setProduct(savingsIRAAccount);
        savingsIRAAccount.setIraDistributionCode("Check");
        savingsIRAAccount.setIraDistributionAccountNumber(null);
        AccountActions.createAccount().selectValuesInDropdownFieldsRequiredForSavingsIRAAccount(savingsIRAAccount);
        Pages.addAccountPage().checkIRADistributionAccountNumberSelectorButtonIsDisabled();

        logInfo("Step 17: Fill in all other required fields and click [Save] button");
        if (Pages.addAccountPage().getApplySeasonalAddressSwitchValue().equals("yes")) {
            Pages.addAccountPage().clickApplySeasonalAddressSwitch();
        }
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
        AccountActions.accountDetailsActions().clickMoreButton();
        AccountActions.accountDetailsActions().verifySavingsIRAAccountRecords(savingsIRAAccount);
        String savAcc = Pages.accountDetailsPage().getAccountNumberValue();

        logInfo("Step 18: Repeat steps 2-6\n" +
                "Fill in all the same fields as in Step7\n" +
                "BUT\n" +
                "- IRA Distribution Code = Savings Account\n" +
                "- IRA Distribution Account Number = Any");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        Pages.accountNavigationPage().clickAccountsTab();
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(savingsIRAAccount);
        AccountActions.createAccount().setProductType(savingsIRAAccount);
        AccountActions.createAccount().setProduct(savingsIRAAccount);
        savingsIRAAccount.setIraDistributionCode("Savings Acct");
        savingsIRAAccount.setIraDistributionAccountNumber(savAcc);
        AccountActions.createAccount().selectValuesInDropdownFieldsRequiredForSavingsIRAAccount(savingsIRAAccount);

        logInfo("Step 19: Fill in all other required fields and click [Save] button");
        if (Pages.addAccountPage().getApplySeasonalAddressSwitchValue().equals("yes")) {
            Pages.addAccountPage().clickApplySeasonalAddressSwitch();
        }
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
        AccountActions.accountDetailsActions().clickMoreButton();
        AccountActions.accountDetailsActions().verifySavingsIRAAccountRecords(savingsIRAAccount);

        logInfo("Repeat steps 2-6\n" +
                "Fill in all the same fields as in Step7\n" +
                "BUT\n" +
                "- IRA Distribution Code = No Dist\n" +
                "and verify IRA Distribution Account Number");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        Pages.accountNavigationPage().clickAccountsTab();
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(savingsIRAAccount);
        AccountActions.createAccount().setProductType(savingsIRAAccount);
        AccountActions.createAccount().setProduct(savingsIRAAccount);
        savingsIRAAccount.setIraDistributionCode("No dist");
        savingsIRAAccount.setIraDistributionAccountNumber(null);
        AccountActions.createAccount().selectValuesInDropdownFieldsRequiredForSavingsIRAAccount(savingsIRAAccount);
        Pages.addAccountPage().checkIRADistributionAccountNumberSelectorButtonIsDisabled();

        logInfo("Step 21: Fill in all other required fields and click [Save] button");
        if (Pages.addAccountPage().getApplySeasonalAddressSwitchValue().equals("yes")) {
            Pages.addAccountPage().clickApplySeasonalAddressSwitch();
        }
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
        AccountActions.accountDetailsActions().clickMoreButton();
        AccountActions.accountDetailsActions().verifySavingsIRAAccountRecords(savingsIRAAccount);
    }
}
