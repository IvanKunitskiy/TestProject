package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
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
public class C22594_ViewNewCDIRAAccountTest extends BaseTest {

    Client client;
    Account cdIRAAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        client.setSelectOfficer("autotest autotest"); // Controlling officer to validate 'Bank Branch' value

        // Set up IRA account
        cdIRAAccount = new Account().setCDIRAAccountData();
        cdIRAAccount.setBankBranch("Inspire - Langhorne"); // Branch of the 'autotest autotest' user

        // Login to the system and create a client with IRA account
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        AccountActions.createAccount().createCDAccount(cdIRAAccount);
        AccountActions.editAccount().editCDAccount(cdIRAAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22594, View New CD IRA Account")
    @Severity(SeverityLevel.CRITICAL)
    public void viewNewCDIRAAccount() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the Savings account from the precondition and open it on Details");
        Pages.clientsSearchPage().typeToClientsSearchInputField(cdIRAAccount.getAccountNumber());
        Assert.assertTrue(Pages.clientsSearchPage().getAllLookupResults().size() == 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(Pages.clientsSearchPage().getAllLookupResults(), cdIRAAccount.getAccountNumber()));
        Pages.clientsSearchPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
        Pages.clientDetailsPage().clickAccountsTab();
        Pages.clientDetailsPage().openAccountByNumber(cdIRAAccount.getAccountNumber());

        logInfo("Step 3: Click [Load More] button");
        Pages.accountDetailsPage().clickMoreButton();

        logInfo("Step 4: Pay attention to the fields on the page");
        Assert.assertEquals(Pages.accountDetailsPage().getProductValue(), cdIRAAccount.getProduct(), "'Product' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountNumberValue(), cdIRAAccount.getAccountNumber(), "'Account Number' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountTitleValue(), cdIRAAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), cdIRAAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), cdIRAAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestFrequency(), cdIRAAccount.getInterestFrequency(), "'Interest Frequency' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getApplyInterestTo(), cdIRAAccount.getApplyInterestTo(), "'Apply Interest To' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestType(), cdIRAAccount.getInterestType(), "'Interest Type' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), cdIRAAccount.getInterestRate(), "'Interest Rate' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCallClassCode(), cdIRAAccount.getCallClassCode(), "'Call Class' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getFederalWHReason(), cdIRAAccount.getFederalWHReason(), "'Federal WH Reason' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getFederalWHPercent(), cdIRAAccount.getFederalWHPercent(), "'Federal WH Percent' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_1(), cdIRAAccount.getUserDefinedField_1(), "'User Defined Field 1' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_2(), cdIRAAccount.getUserDefinedField_2(), "'User Defined Field 2' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_3(), cdIRAAccount.getUserDefinedField_3(), "'User Defined Field 3' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_4(), cdIRAAccount.getUserDefinedField_4(), "'User Defined Field 4' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getIraDistributionFrequency(), cdIRAAccount.getIraDistributionFrequency(), "' IRA Distribution Frequency' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getIraDistributionCode(), cdIRAAccount.getIraDistributionCode(), "' IRA Distribution Code' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateOpenedValue(), cdIRAAccount.getDateOpened(), "'Date Opened' value does not match");

        logInfo("Step 5: Click [Less] button");
        Pages.accountDetailsPage().clickLessButton();
        Assert.assertTrue(Pages.accountDetailsPage().isMoreButtonVisible(), "More button is not visible");
    }

}