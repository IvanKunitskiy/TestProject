package com.nymbus.frontoffice.safedepositboxmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Box Accounts Management")
@Owner("Dmytro")
public class C22612_AddNewSafeDepositBoxAccountTest extends BaseTest {

    private Client client;
    private Account safeDepositBoxAccount;
    private Account checkingAccount;

    @BeforeMethod
    public void preConditions() {

        // Set up Client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        client.setSelectOfficer("autotest autotest"); // Controlling officer to validate 'Bank Branch' value

        // Set up Safe Deposit Box Account
        safeDepositBoxAccount = new Account().setSafeDepositBoxData();
        safeDepositBoxAccount.setBankBranch("Inspire - Langhorne"); // Branch of the 'autotest autotest' user
        safeDepositBoxAccount.setBoxSize("10");
        safeDepositBoxAccount.setRentalAmount("100.00");

        // Set up CHK account (required to point the 'Corresponding Account')
        checkingAccount = new Account().setCHKAccountData();

        // Login to the system and create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        client.setClientID(Pages.clientDetailsPage().getClientID());
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Actions.loginActions().doLogOut();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "C22612, Add New 'Safe Deposit Box' Account")
    public void createSafeBoxAccount() {

        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Clients screen and search for client from preconditions");
        Pages.clientsSearchPage().typeToClientsSearchInputField(client.getClientID());
        Assert.assertTrue(Pages.clientsSearchPage().getAllLookupResults().size() == 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(Pages.clientsSearchPage().getAllLookupResults(), client.getClientID()), "Search results are not relevant");
        Pages.clientsSearchPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
        Pages.clientDetailsPage().clickAccountsTab();

        logInfo("Step 3: Click 'Add New' drop down and select 'Account'");
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(safeDepositBoxAccount);

        logInfo("Step 4: Select 'Product Type' = 'Safe Deposit Box'");
        AccountActions.createAccount().setProductType(safeDepositBoxAccount);

        logInfo("Step 5: Look through the fields. Check that fields are prefilled by default");
        Assert.assertEquals(Pages.addAccountPage().getAccountType(), client.getClientType(), "'Account type' is prefilled with wrong value");
        final String accountHolderName = client.getFirstName() + " " + client.getLastName() + " (" + client.getClientID() + ")";
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderName(), accountHolderName, "'Name' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderRelationship(), safeDepositBoxAccount.getAccountHolder(), "'Relationship' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderClientType(), client.getClientType(), "'Client type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderTaxID(), client.getTaxID(), "'Tax ID' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getMailCode(), client.getMailCode(), "'Mail code' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getDateOpened(), DateTime.getLocalDateTimeByPattern("MM/dd/yyyy"), "'Date' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getOriginatingOfficer(), client.getSelectOfficer(), "'Originating officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getCurrentOfficer(), client.getSelectOfficer(), "'Current officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getBankBranch(), safeDepositBoxAccount.getBankBranch(), "'Bank branch' is prefilled with wrong value");

        logInfo("Step 6: Select any value from Box Size drop-down and check the Rental Amount field");
        AccountActions.createAccount().setBoxSize(safeDepositBoxAccount);
        Assert.assertEquals(Pages.addAccountPage().getRentalAmount(), safeDepositBoxAccount.getRentalAmount(), "'Rental Amount' is prefilled with wrong value");

        logInfo("Step 7: Select values in such drop-down fields:");
        AccountActions.createAccount().selectValuesInDropdownFieldsRequiredForSafeDepositBoxAccount(safeDepositBoxAccount);

        logInfo("Step 8: Fill in such text fields with valid data (except Account Number field):");
        AccountActions.createAccount().fillInInputFieldsRequiredForSafeDepositBoxAccount(safeDepositBoxAccount);

        logInfo("Step 9: Select Date Opened as any date < Current Date and Select Date next IRA distribution as any date > Current Date");
        Pages.addAccountPage().setDateOpenedValue(safeDepositBoxAccount.getDateOpened());

        logInfo("Step 10: Submit the account creation by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 11: Pay attention to the fields that were filled in during account creation");
        Assert.assertEquals(Pages.accountDetailsPage().getBoxSizeValue(), safeDepositBoxAccount.getBoxSize(), "'Box Size' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getRentalAmount(), safeDepositBoxAccount.getRentalAmount(), "'Rental Amount' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), safeDepositBoxAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), safeDepositBoxAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCorrespondingAccount(), safeDepositBoxAccount.getCorrespondingAccount(), "'Corresponding Account' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDiscountReason(), safeDepositBoxAccount.getDiscountReason(), "'Discount Reason' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountTitleValue(), safeDepositBoxAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_1(), safeDepositBoxAccount.getUserDefinedField_1(), "'User defined field 1' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_2(), safeDepositBoxAccount.getUserDefinedField_2(), "'User defined field 2' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_3(), safeDepositBoxAccount.getUserDefinedField_3(), "'User defined field 3' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_4(), safeDepositBoxAccount.getUserDefinedField_4(), "'User defined field 4' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDiscountPeriods(), safeDepositBoxAccount.getDiscountPeriods(), "'Discount Periods' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateOpenedValue(), safeDepositBoxAccount.getDateOpened(), "'Date Opened' value does not match");

        logInfo("Step 12: Check the 'Next Billing Date' field value");
        Assert.assertEquals(Pages.accountDetailsPage().getBillingNextDate(), safeDepositBoxAccount.getDateOpened(), "'Date Opened' value does not match");

        logInfo("Step 13: Click [Edit] button and pay attention to the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickEditButton();
        Assert.assertEquals(Pages.editAccountPage().getBoxSize(), safeDepositBoxAccount.getBoxSize(), "'Box Size' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getRentalAmount(), safeDepositBoxAccount.getRentalAmount(), "'Rental Amount' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCurrentOfficerValueInEditMode(), safeDepositBoxAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBankBranchValueInEditMode(), safeDepositBoxAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCorrespondingAccount(), safeDepositBoxAccount.getCorrespondingAccount(), "'Corresponding Account' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDiscountReason(), safeDepositBoxAccount.getDiscountReason(), "'Discount Reason' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getAccountTitleValueInEditMode(), safeDepositBoxAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField1(), safeDepositBoxAccount.getUserDefinedField_1(), "'User defined field 1' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField2(), safeDepositBoxAccount.getUserDefinedField_2(), "'User defined field 2' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField3(), safeDepositBoxAccount.getUserDefinedField_3(), "'User defined field 3' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField4(), safeDepositBoxAccount.getUserDefinedField_4(), "'User defined field 4' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDiscountPeriods(), safeDepositBoxAccount.getDiscountPeriods(), "'Discount Periods' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDateOpenedValueInEditMode(), safeDepositBoxAccount.getDateOpened(), "'Date Opened' value does not match");

        logInfo("Step 14: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 15: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        // TODO: Implement verification at Maintenance History page
    }
}
