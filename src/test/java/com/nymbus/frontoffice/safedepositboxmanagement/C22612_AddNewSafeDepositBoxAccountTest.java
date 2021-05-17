package com.nymbus.frontoffice.safedepositboxmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.account.verifyingmodels.SafeDepositKeyValues;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;


@Epic("Frontoffice")
@Feature("Box Accounts Management")
@Owner("Dmytro")
public class C22612_AddNewSafeDepositBoxAccountTest extends BaseTest {

    private Account safeDepositBoxAccount;
    private String clientID;

    @BeforeMethod
    public void preConditions() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up CHK account (required to point the 'Corresponding Account')
        Account checkingAccount = new Account().setCHKAccountData();

        // Set up Safe Deposit Box Account
        safeDepositBoxAccount = new Account().setSafeDepositBoxData();
        safeDepositBoxAccount.setCurrentOfficer(Constants.FIRST_NAME + " " + Constants.LAST_NAME);

        // Login to the system and create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set product
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Get safeDepositKeyValues
        Actions.usersActions().openSafeDepositBoxSizesPage();
        List<SafeDepositKeyValues> safeDepositKeyValues = Actions.usersActions().getSafeDepositBoxValues();
        Assert.assertTrue(safeDepositKeyValues.size() > 0, "Safe deposits values are not set!");

        // Set box size and amount
        AccountActions.verifyingAccountDataActions().setSafeDepositBoxSizeAndRentalAmount(safeDepositBoxAccount, safeDepositKeyValues);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        clientID = Pages.clientDetailsPage().getClientID();

        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkingAccount);
        safeDepositBoxAccount.setCorrespondingAccount(checkingAccount.getAccountNumber());
        safeDepositBoxAccount.setApplyInterestTo("CHK Acct");
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Safe Deposit Box Management";

    @TestRailIssue(issueID = 22612, testRunName = TEST_RUN_NAME)
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "C22612, Add New 'Safe Deposit Box' Account")
    public void createSafeBoxAccount() {
        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Clients screen and search for client from preconditions");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientID);
        Pages.clientDetailsPage().clickAccountsTab();
        Actions.clientPageActions().closeAllNotifications();

        logInfo("Step 3: Click 'Add New' drop down and select 'Account'");
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(safeDepositBoxAccount);

        logInfo("Step 4: Select 'Product Type' = 'Safe Deposit Box'");
        AccountActions.createAccount().setProductType(safeDepositBoxAccount);

        logInfo("Step 5: Look through the fields. Check that fields are prefilled by default");
        AccountActions.verifyingAccountDataActions().verifyPredefinedFields();

        logInfo("Step 6: Select any value from Box Size drop-down and check the Rental Amount field");
        AccountActions.createAccount().setBoxSize(safeDepositBoxAccount);
        Assert.assertEquals(Pages.addAccountPage().getRentalAmount(), safeDepositBoxAccount.getRentalAmount(),
                "'Rental Amount' is prefilled with wrong value");

        logInfo("Step 7: Select values in such drop-down fields:");
        AccountActions.createAccount().selectValuesInDropdownFieldsRequiredForSafeDepositBoxAccount(safeDepositBoxAccount);

        logInfo("Step 8: Fill in such text fields with valid data (except Account Number field):");
        AccountActions.createAccount().fillInInputFieldsRequiredForSafeDepositBoxAccount(safeDepositBoxAccount);

        logInfo("Step 9: Select Date Opened as any date < Current Date");
        Pages.addAccountPage().setDateOpenedValue(safeDepositBoxAccount.getDateOpened());

        logInfo("Step 10: Submit the account creation by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 11: Pay attention to the fields that were filled in during account creation");
        AccountActions.verifyingAccountDataActions().verifyFieldsInViewMode(safeDepositBoxAccount);

        logInfo("Step 12: Check the 'Next Billing Date' field value");
        Assert.assertEquals(Pages.accountDetailsPage().getBillingNextDate(), safeDepositBoxAccount.getDateOpened(), "'Date Opened' value does not match");

        logInfo("Step 13: Click [Edit] button and pay attention to the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickEditButton();
        AccountActions.verifyingAccountDataActions().verifyFieldsInEditMode(safeDepositBoxAccount);

        logInfo("Step 14: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();
        AccountActions.accountMaintenanceActions().expandAllRows();

        logInfo("Step 15: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        AccountActions.verifyingAccountDataActions().verifySafeDepositBoxAccountInformationOnMaintenanceTab(safeDepositBoxAccount, 26);
    }
}