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

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22579_ViewNewSavingsAccount extends BaseTest {

    private Client client;
    private Account savingsAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        client.setSelectOfficer("autotest autotest"); // Controlling officer to validate 'Bank Branch' value

        // Set up savings account
        savingsAccount = new Account().setSavingsAccountData();
        savingsAccount.setBankBranch("Inspire - Langhorne"); // Branch of the 'autotest autotest' user

        // Login to the system and create a client with savings account
//        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        AccountActions.createAccount().createSavingsAccount(savingsAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22579, View New Savings Account")
    @Severity(SeverityLevel.CRITICAL)
    public void viewNewSavingsAccount() {

//        LOG.info("Step 1: Log in to the system as the user from the precondition");
//        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

//        LOG.info("Step 2: Search for the Savings account from the precondition and open it on Details");
        Pages.clientsPage().typeToClientsSearchInputField(savingsAccount.getAccountNumber());
        Assert.assertTrue(Pages.clientsPage().getAllLookupResults().size() == 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsPage().isSearchResultsRelative(Pages.clientsPage().getAllLookupResults(), savingsAccount.getAccountNumber()));
        Pages.clientsPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
        Pages.clientDetailsPage().clickAccountsTab();
        Pages.clientDetailsPage().openAccountByNumber(savingsAccount.getAccountNumber());

//        LOG.info("Step 3: Click [Load More] button");
        Pages.accountDetailsPage().clickMoreButton();

//        LOG.info("Step 4: Pay attention to the fields on the page");
        Assert.assertEquals(Pages.accountDetailsPage().getProductValue(), savingsAccount.getProduct(), "'Product' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountNumberValue(), savingsAccount.getAccountNumber(), "'Account Number' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountTitleValue(), savingsAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateOpenedValue(), savingsAccount.getDateOpened(), "'Date Opened' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), savingsAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), savingsAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getStatementCycle(), savingsAccount.getStatementCycle(), "'Statement Cycle' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCallClassCode(), savingsAccount.getCallClassCode(), "'Call Class' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getInterestRateValue(), savingsAccount.getInterestRate(), "'Interest Rate' value does not match");

//        LOG.info("Step 5: Click [Less] button");
        Pages.accountDetailsPage().clickLessButton();
        Assert.assertTrue(Pages.accountDetailsPage().isMoreButtonVisible(), "More button is not visible");
    }
}
