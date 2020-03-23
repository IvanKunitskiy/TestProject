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

        // Login to the system and create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        client.setClientID(Pages.clientDetailsPage().getClientID());
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
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderRelationship(), "Owner", "'Relationship' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderClientType(), client.getClientType(), "'Client type' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getAccountHolderTaxID(), client.getTaxID(), "'Tax ID' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getDateOpened(), DateTime.getLocalDateTimeByPattern("MM/dd/yyyy"), "'Date' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getOriginatingOfficer(), client.getSelectOfficer(), "'Originating officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getCurrentOfficer(), client.getSelectOfficer(), "'Current officer' is prefilled with wrong value");
        Assert.assertEquals(Pages.addAccountPage().getBankBranch(), cdAccount.getBankBranch(), "'Bank branch' is prefilled with wrong value");
//        - Term Type (Min Term for selected Product)
//        - Auto Renewable (default to YES)
//        - Interest Frequency (from selected Product)
//        - Interest Rate
//        - Interest Type

        logInfo("Step 7: Select values in such drop-down fields:");
//        - Account Type
//        - Current Officer (any value that differs from the default Officer)
//        - Bank Branch
//        - Interest Frequency
//        - Apply Interest To (Check or Remain in account)
//        - Interest Type
//        - Statement Cycle
//        - Corresponding Account (if another active CHK/Savings account exists for selected Client)
//        - Call Class Code - (if the drop-down is not blank)
//        NOTE: if Apply Interest To= CHK or Savings account -> Corresponding Account field becomes required

        logInfo("Step 8: Fill in such text fields with valid data (except Account Number field):");
        Pages.addAccountPage().setAccountTitleValue(cdAccount.getAccountTitle());
//        - Statement Flag (R or S)
//        - Interest Rate - min - 4 digits pass the decimal, max - 100%

        logInfo("Step 9: Set 'Transactional Account' switcher to YES):");
        // ...
        // Set 'Transactional Account' switcher to YES
        // ...

        logInfo("Step 10: Select Date Opened as any date < Current Date");
        Pages.addAccountPage().setDateOpenedValue(cdAccount.getDateOpened());

        logInfo("Step 11: Submit the account creation by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 12: Pay attention to the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickMoreButton();



    }
}
