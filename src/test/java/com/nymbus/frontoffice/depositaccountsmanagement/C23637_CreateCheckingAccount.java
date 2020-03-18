package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.account.Account;
import com.nymbus.models.client.Client;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C23637_CreateCheckingAccount extends BaseTest {

    private Client client;
    private Account checkingAccount;

    @BeforeMethod
    public void preCondition() {
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        checkingAccount = new Account().setCHKAccountData();
    }

    @Test(description = "C23637, Create checking account")
    @Severity(SeverityLevel.CRITICAL)
    public void createCheckingAccount() {

        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        final String clientID = Pages.clientDetailsPage().getClientID();
        client.setClientID(clientID);
        Pages.aSideMenuPage().clickClientMenuItem();

        logInfo("Step 2: Search for any client and open his profile on Accounts tab");
        Pages.clientsPage().typeToClientsSearchInputField(clientID);
        Assert.assertTrue(Pages.clientsPage().getAllLookupResults().size() == 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsPage().isSearchResultsRelative(Pages.clientsPage().getAllLookupResults(), clientID), "Search results are not relevant");
        Pages.clientsPage().clickOnSearchButton();
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.now();
        Assert.assertEquals(Pages.addAccountPage().getDateOpened(), dtf.format(localDate), "'Date' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getOriginatingOfficer(), client.getSelectOfficer(), "'Originating officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getCurrentOfficer(), client.getSelectOfficer(), "'Current officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getBankBranch(), "Select", "'Bank branch' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getOptInOutStatus(), "Client Has Not Responded", "'DBC ODP Opt In/Out Status' is prefilled with wrong value");

        logInfo("Step 6: Select any values in drop-down fields");
        AccountActions.createAccount().setProduct(checkingAccount);
        Pages.addAccountPage().setDateOpenedValue(checkingAccount.getDateOpened());
        AccountActions.createAccount().setCurrentOfficer(checkingAccount);
        AccountActions.createAccount().setBankBranch(checkingAccount);
        AccountActions.createAccount().setStatementCycle(checkingAccount);
        AccountActions.createAccount().setCallClassCode(checkingAccount);
        AccountActions.createAccount().setChargeOrAnalyze(checkingAccount);
        AccountActions.createAccount().setAccountAnalysis(checkingAccount);

        logInfo("Step 7: Fill in text fields with valid data. NOTE: do not fill in Account Number field");
        Pages.addAccountPage().setAccountTitleValue(checkingAccount.getAccountTitle());
        checkingAccount.setStatementFlag(Pages.addAccountPage().generateStatementFlagValue());
        Pages.addAccountPage().setStatementFlag(checkingAccount.getStatementFlag());
        checkingAccount.setInterestRate(Pages.addAccountPage().generateInterestRateValue());
        Pages.addAccountPage().setInterestRate(checkingAccount.getInterestRate());
        checkingAccount.setEarningCreditRate(Pages.addAccountPage().generateEarningCreditRateValue());
        Pages.addAccountPage().setEarningCreditRate(checkingAccount.getEarningCreditRate());

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
        Assert.assertEquals(Pages.accountDetailsPage().getStatementFlagValue(), checkingAccount.getStatementFlag(), "'Statement Flag' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), checkingAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getEarningCreditRate(), checkingAccount.getEarningCreditRate(), "'Earning Rate' value does not match");

        logInfo("Step 11: Click [Edit] button and pay attention to the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickEditButton();
        Assert.assertEquals(Pages.accountDetailsPage().getProductValueInEditMode(), checkingAccount.getProduct(), "'Product' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateOpenedValueInEditMode(), checkingAccount.getDateOpened(), "'Date Opened' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValueInEditMode(), checkingAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValueInEditMode(), checkingAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getStatementCycleValueInEditMode(), checkingAccount.getStatementCycle(), "'Statement Cycle' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCallClassCodeValueInEditMode(), checkingAccount.getCallClassCode(), "'Call Class' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getChargeOrAnalyzeInEditMode(), checkingAccount.getChargeOrAnalyze(), "'Charge or Analyze' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountAnalysisValueInEditMode(), checkingAccount.getAccountAnalysis(), "'Account Analysis' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountTitleValueInEditMode(), checkingAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getStatementFlagValueInEditMode(), checkingAccount.getStatementFlag(), "'Statement Flag' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValueInEditMode(), checkingAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getEarningCreditRateInEditMode(), checkingAccount.getEarningCreditRate(), "'Earning Rate' value does not match");

        logInfo("Step 12: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountDetailsPage().clickMaintenanceTab();
        Pages.accountDetailsPage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 13: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        // TODO: Implement verification at Maintenance History page
    }
}