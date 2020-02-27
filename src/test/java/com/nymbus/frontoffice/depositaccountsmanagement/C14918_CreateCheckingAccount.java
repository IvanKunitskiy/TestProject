package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.account.Account;
import com.nymbus.models.client.Client;
import com.nymbus.pages.Pages;
import com.nymbus.util.Constants;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C14918_CreateCheckingAccount extends BaseTest {

    private Client client;
    private Account checkingAccount;

    @BeforeMethod
    public void preCondition() {
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        checkingAccount = new Account().setCHKAccountData();
    }

    @Test(description = "C14918, Create checking account")
    @Severity(SeverityLevel.CRITICAL)
    public void createCheckingAccount() {

        LOG.info("Step 1: Log in to the system as the User from the precondition");
        navigateToUrl(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        final String clientID = Pages.clientDetailsPage().getClientID();
        client.setClientID(clientID);
        Pages.aSideMenuPage().clickClientMenuItem();

        LOG.info("Step 2: Search for any client and open his profile on Accounts tab");
        Pages.clientsPage().typeToClientsSearchInputField(clientID);
        Assert.assertTrue(Pages.clientsPage().getAllLookupResults().size() == 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsPage().isSearchResultsRelative(Pages.clientsPage().getAllLookupResults(), clientID));
        Pages.clientsPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
        Pages.clientDetailsPage().clickAccountsTab();

        LOG.info("Step 3: Click 'Add New' drop down and select 'Account'");
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(checkingAccount);

        LOG.info("Step 4: Select 'Product Type' = 'CHK Account'");
        AccountActions.createAccount().setProductType(checkingAccount);

        LOG.info("Step 5: Look through the fields. Check that fields are prefilled by default");

        // Account type
        Assert.assertEquals(Pages.addAccountPage().getAccountType(), client.getClientType());

        // Account Holders and Signers section
        final String accountHolderName = client.getFirstName() + " " + client.getLastName() + " (" + client.getClientID() + ")";
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderName(), accountHolderName);
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderRelationship(), "Owner");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderClientType(), client.getClientType());
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderTaxID(), client.getTaxID());

        // Date Opened
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.now();
        Assert.assertEquals(Pages.addAccountPage().getDateOpened(), dtf.format(localDate));

        // Originating Officer And Current Officer
        Assert.assertEquals(Pages.addAccountPage().getCurrentOfficer(), client.getSelectOfficer());
        Assert.assertEquals(Pages.addAccountPage().getOriginatingOfficer(), client.getSelectOfficer());

        // Bank Branch ??

        // DBC ODP Opt In/Out Status
        Assert.assertEquals(Pages.addAccountPage().getOptInOutStatus(), "Client Has Not Responded");

        LOG.info("Step 6: Select any values in drop-down fields");
        AccountActions.createAccount().setProduct(checkingAccount);
        AccountActions.createAccount().setBankBranch(checkingAccount);
        AccountActions.createAccount().setStatementCycle(checkingAccount);

        LOG.info("Step 7: Fill in text fields with valid data. NOTE: do not fill in Account Number field");
        // Clarify fields to fill in
        Pages.addAccountPage().setAccountTitleValue(checkingAccount.getAccountTitle());

        LOG.info("Step 8: Select any values in drop-down fields)");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        LOG.info("Step 9: Pay attention to the fields that were filled in during account creation");
        // Check all fields filled in steps 6 and 7
        Assert.assertEquals(Pages.accountDetailsPage().getAccountTitleValue(), checkingAccount.getAccountTitle(), "Account title is not relevant");

        // TODO: Proceed implementing the test case

    }
}