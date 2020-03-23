package com.nymbus.frontoffice.depositaccountsmanagement;

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
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C23637_CreateCheckingAccountTest extends BaseTest {
    private Client client;
    private Account checkingAccount;

    @BeforeMethod
    public void preCondition() {
        // Set up Client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        client.setSelectOfficer("autotest autotest"); // Controlling officer to validate 'Bank Branch' value
        checkingAccount = new Account().setCHKAccountData();
        checkingAccount.setBankBranch("Inspire - Langhorne"); // Branch of the 'autotest autotest' user

        // Login to the system and create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        client.setClientID(Pages.clientDetailsPage().getClientID());
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C23637, Create checking account")
    @Severity(SeverityLevel.CRITICAL)
    public void createCheckingAccount() {

        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for any client and open his profile on Accounts tab");
        Pages.clientsSearchPage().typeToClientsSearchInputField(client.getClientID());
        Assert.assertTrue(Pages.clientsSearchPage().getAllLookupResults().size() == 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(Pages.clientsSearchPage().getAllLookupResults(), client.getClientID()), "Search results are not relevant");
        Pages.clientsSearchPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
        Pages.clientDetailsPage().clickAccountsTab();

        logInfo("Step 3: Click 'Add New' drop down and select 'Account'");
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(checkingAccount);

        logInfo("Step 4: Select 'Product Type' = 'CHK Account'");
        AccountActions.createAccount().setProductType(checkingAccount);

        logInfo("Step 5: Look through the fields. Check that fields are prefilled by default");
        Assert.assertEquals(Pages.addAccountPage().getAccountType(), client.getClientType(), "'Account type' is prefilled with wrong value");
        final String accountHolderName = client.getFirstName() + " " + client.getLastName() + " (" + client.getClientID() + ")";
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderName(), accountHolderName, "'Name' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderRelationship(), "Owner", "'Relationship' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderClientType(), client.getClientType(), "'Client type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderTaxID(), client.getTaxID(), "'Tax ID' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getDateOpened(), DateTime.getLocalDateTimeByPattern("MM/dd/yyyy"), "'Date' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getOriginatingOfficer(), client.getSelectOfficer(), "'Originating officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getCurrentOfficer(), client.getSelectOfficer(), "'Current officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getBankBranch(), checkingAccount.getBankBranch(), "'Bank branch' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getOptInOutStatus(), "Client Has Not Responded", "'DBC ODP Opt In/Out Status' is prefilled with wrong value");

        logInfo("Step 6: Select any values in drop-down fields");
        AccountActions.createAccount().selectValuesInDropdownFieldsRequiredForCheckingAccount(checkingAccount);

        logInfo("Step 7: Fill in text fields with valid data. NOTE: do not fill in Account Number field");
        AccountActions.createAccount().fillInInputFieldsRequiredForCheckingAccount(checkingAccount);

        logInfo("Step 8: Select any date ≤ Current Date in DBC ODP Opt In/Out Status Date field");
        Pages.addAccountPage().setOptInOutDateValue(checkingAccount.getOptInOutDate());

        logInfo("Step 9: Submit the account creation by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 10: Pay attention to the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickMoreButton();
        Assert.assertEquals(Pages.accountDetailsPage().getProductValue(), checkingAccount.getProduct(), "'Product' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateOpenedValue(), checkingAccount.getDateOpened(), "'Date Opened' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), checkingAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), checkingAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getStatementCycle(), checkingAccount.getStatementCycle(), "'Statement Cycle' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCallClassCode(), checkingAccount.getCallClassCode(), "'Call Class' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getChargeOrAnalyze(), checkingAccount.getChargeOrAnalyze(), "'Charge or Analyze' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountAnalysisValue(), checkingAccount.getAccountAnalysis(), "'Account Analysis' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountTitleValue(), checkingAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), checkingAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getEarningCreditRate(), checkingAccount.getEarningCreditRate(), "'Earning Rate' value does not match");

        logInfo("Step 11: Click [Edit] button and pay attention to the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickEditButton();
        Assert.assertEquals(Pages.editAccountPage().getProductValueInEditMode(), checkingAccount.getProduct(), "'Product' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDateOpenedValueInEditMode(), checkingAccount.getDateOpened(), "'Date Opened' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCurrentOfficerValueInEditMode(), checkingAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBankBranchValueInEditMode(), checkingAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getStatementCycleValueInEditMode(), checkingAccount.getStatementCycle(), "'Statement Cycle' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCallClassCodeValueInEditMode(), checkingAccount.getCallClassCode(), "'Call Class' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getChargeOrAnalyzeInEditMode(), checkingAccount.getChargeOrAnalyze(), "'Charge or Analyze' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getAccountAnalysisValueInEditMode(), checkingAccount.getAccountAnalysis(), "'Account Analysis' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getAccountTitleValueInEditMode(), checkingAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getInterestRateValueInEditMode(), checkingAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getEarningCreditRateInEditMode(), checkingAccount.getEarningCreditRate(), "'Earning Rate' value does not match");

        logInfo("Step 12: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 13: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        // TODO: Implement verification at Maintenance History page
    }
}