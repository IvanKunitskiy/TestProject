package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.account.Account;
import com.nymbus.models.client.Client;
import com.nymbus.pages.Pages;
import com.nymbus.util.Constants;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22574_ViewNewCheckingAccount extends BaseTest {

    private Client client;
    private Account checkingAccount;

    @BeforeMethod
    public void preCondition() {
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        checkingAccount = new Account().setCHKAccountData();
    }

    @Test(description = "C22574, View new checking account")
    @Severity(SeverityLevel.CRITICAL)
    public void viewNewCheckingAccount() {

        LOG.info("Step 1: Log in to the system");
        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        AccountActions.createAccount().createCHKAccount(checkingAccount);

        LOG.info("Step 2: Search for the CHK account from the precondition and open it on Details");
        Pages.aSideMenuPage().clickClientMenuItem();
        Pages.clientsPage().typeToClientsSearchInputField(checkingAccount.getAccountNumber());
        Assert.assertTrue(Pages.clientsPage().getAllLookupResults().size() == 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsPage().isSearchResultsRelative(Pages.clientsPage().getAllLookupResults(), checkingAccount.getAccountNumber()));
        Pages.clientsPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
        Pages.clientDetailsPage().clickAccountsTab();
        Pages.clientDetailsPage().openAccountByNumber(checkingAccount.getAccountNumber());

        LOG.info("Step 3: Click [Load More] button");
        Pages.accountDetailsPage().clickMoreButton();

        LOG.info("Step 4: Pay attention to the fields on the page");
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

        LOG.info("Step 5: Click [Less] button");
        Pages.accountDetailsPage().clickLessButton();
        Assert.assertTrue(Pages.accountDetailsPage().isMoreButtonVisible(), "More button is not visible");
    }
}
