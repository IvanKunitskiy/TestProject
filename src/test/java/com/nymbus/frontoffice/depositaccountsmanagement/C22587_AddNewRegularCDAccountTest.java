package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.models.account.Account;
import com.nymbus.models.client.Client;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22587_AddNewRegularCDAccountTest extends BaseTest {

    Client client;
    Account cdAccount;
    Account checkingAccount;

    @BeforeMethod
    public void preConditions() {

        // Set up Client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        client.setSelectOfficer("autotest autotest"); // Controlling officer to validate 'Bank Branch' value

        // Set up IRA account
        cdAccount = new Account().setCDAccountData();
        cdAccount.setBankBranch("Inspire - Langhorne"); // Branch of the 'autotest autotest' user
        cdAccount.setApplyInterestTo("CHK Acct");
        cdAccount.setTermType("3");
        cdAccount.setMaturityDate(DateTime.getDateWithNMonthAdded(cdAccount.getDateOpened(), "MM/dd/yyyy", Integer.parseInt(cdAccount.getTermType())));
        cdAccount.setDateNextInterest(DateTime.getDateWithNMonthAdded(cdAccount.getDateOpened(), "MM/dd/yyyy", 3)); // 3 month added as 'Interest Frequency' is set to 'Quarterly'

        // Set up CHK account (required to point the 'Corresponding Account')
        checkingAccount = new Account().setCHKAccountData();

        // Login to the system and create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        client.setClientID(Pages.clientDetailsPage().getClientID());
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22587, Add New Regular CD Account")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewRegularCDAccountTest() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the client from the preconditions and open his profile on Accounts tab");
        Pages.clientsPage().typeToClientsSearchInputField(client.getClientID());
        Assert.assertTrue(Pages.clientsPage().getAllLookupResults().size() == 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsPage().isSearchResultsRelative(Pages.clientsPage().getAllLookupResults(), client.getClientID()), "Search results are not relevant");
        Pages.clientsPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
        Pages.clientDetailsPage().clickAccountsTab();

        logInfo("Step 3: Click 'Add New' drop down and select 'Account'");
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(cdAccount);

        logInfo("Step 4: Select 'Product Type' = 'CD Account'");
        AccountActions.createAccount().setProductType(cdAccount);

        logInfo("Step 5: Select any product not IRA (e.g. 3 month Regular Certificate)");
        AccountActions.createAccount().setProduct(cdAccount);

        logInfo("Step 6: Look through the fields. Check that fields are prefilled by default");
        Assert.assertEquals(Pages.addAccountPage().getAccountType(), client.getClientType(), "'Account type' is prefilled with wrong value");
        final String accountHolderName = client.getFirstName() + " " + client.getLastName() + " (" + client.getClientID() + ")";
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderName(), accountHolderName, "'Name' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderRelationship(), cdAccount.getAccountHolder(), "'Relationship' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderClientType(), client.getClientType(), "'Client type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderTaxID(), client.getTaxID(), "'Tax ID' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getOriginatingOfficer(), client.getSelectOfficer(), "'Originating officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getCurrentOfficer(), client.getSelectOfficer(), "'Current officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getBankBranch(), cdAccount.getBankBranch(), "'Bank branch' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getTermType(), cdAccount.getTermType(), "'Term Type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAutoRenewable(), cdAccount.getAutoRenewable(), "'Auto Renewable' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getInterestFrequency(), cdAccount.getInterestFrequency(), "'Interest Frequency' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getInterestType(), cdAccount.getInterestType(), "'Interest Type' is prefilled with wrong value");

        logInfo("Step 7: Select values in such drop-down fields:");
        AccountActions.createAccount().selectValuesInDropdownFieldsRequiredForCDAccount(cdAccount);

        logInfo("Step 8: Fill in such text fields with valid data (except Account Number field):");
        Pages.addAccountPage().setAccountTitleValue(cdAccount.getAccountTitle());
        Pages.addAccountPage().setInterestRate(cdAccount.getInterestRate());

        logInfo("Step 9: Set 'Transactional Account' switcher to YES):");
        Pages.addAccountPage().clickTransactionalAccountSwitch();

        logInfo("Step 10: Select Date Opened as any date < Current Date");
        Pages.addAccountPage().setDateOpenedValue(cdAccount.getDateOpened());

        logInfo("Step 11: Submit the account creation by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 12: Check the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickMoreButton();
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), cdAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), cdAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestFrequency(), cdAccount.getInterestFrequency(), "'Interest Frequency' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getApplyInterestTo(), cdAccount.getApplyInterestTo(), "'Apply Interest To' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestType(), cdAccount.getInterestType(), "'Interest Type' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCorrespondingAccount(), cdAccount.getCorrespondingAccount(), "'Corresponding Account' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCallClassCode(), cdAccount.getCallClassCode(), "'Call Class' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountTitleValue(), cdAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), cdAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateOpenedValue(), cdAccount.getDateOpened(), "'Date Opened' value does not match");

        logInfo("Step 13: Check the Maturity Date field value (verify that it's calculated based on Date Opened + Term of Account)");
        Assert.assertEquals(Pages.accountDetailsPage().getMaturityDate(), cdAccount.getMaturityDate(), "'Maturity Date' value does not match");

        logInfo("Step 14: Check the Date next interest field value (verify that it's calculated based on Date Opened + Interest Frequency)");
        Assert.assertEquals(Pages.accountDetailsPage().getDateNextInterest(), cdAccount.getDateNextInterest(), "'Maturity Date' value does not match");

        logInfo("Step 15: Click [Edit] button and pay attention to the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickEditButton();
        Assert.assertEquals(Pages.editAccountPage().getCurrentOfficerValueInEditMode(), cdAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBankBranchValueInEditMode(), cdAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestFrequency(), cdAccount.getInterestFrequency(), "'Interest Frequency' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getApplyInterestTo(), cdAccount.getApplyInterestTo(), "'Apply Interest To' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestType(), cdAccount.getInterestType(), "'Interest Type' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCorrespondingAccount(), cdAccount.getCorrespondingAccount(), "'Corresponding Account' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCallClassCodeValueInEditMode(), cdAccount.getCallClassCode(), "'Call Class' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getAccountTitleValueInEditMode(), cdAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestRateValueInEditMode(), cdAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDateOpenedValueInEditMode(), cdAccount.getDateOpened(), "'Date Opened' value does not match");

        logInfo("Step 16: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 17: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        // TODO: Implement verification at Maintenance History page
    }
}
