package com.nymbus.frontoffice.safedepositboxmanagement;

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
@Feature("Box Accounts Management")
@Owner("Dmytro")
public class C22614_ViewNewSafeBoxAccountTest extends BaseTest {

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

        // Login to the system and create a client with Safe Deposit Box and CHK accounts
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Pages.accountDetailsPage().clickAccountsLink();
        AccountActions.createAccount().createSafeDepositBoxAccount(safeDepositBoxAccount);
        AccountActions.editAccount().editSafeDepositBoxAccount(safeDepositBoxAccount);
        Actions.loginActions().doLogOut();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "C22614, View New Safe Box Account")
    public void viewNewSafeBoxAccount() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Step 2: Search for the Safe Deposit Box Account from the precondition and open it on Details");
        Pages.clientsSearchPage().typeToClientsSearchInputField(safeDepositBoxAccount.getAccountNumber());
        Assert.assertTrue(Pages.clientsSearchPage().getAllLookupResults().size() == 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(Pages.clientsSearchPage().getAllLookupResults(), safeDepositBoxAccount.getAccountNumber()));
        Pages.clientsSearchPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
        Pages.clientDetailsPage().clickAccountsTab();

        logInfo("Step 3: Open the account");
        Pages.clientDetailsPage().openAccountByNumber(safeDepositBoxAccount.getAccountNumber());

        logInfo("Step 4: Verify the displayed fields in view mode");
        Assert.assertEquals(Pages.accountDetailsPage().getBoxSizeValue(), safeDepositBoxAccount.getBoxSize(), "'Box Size' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getRentalAmount(), safeDepositBoxAccount.getRentalAmount(), "'Rental Amount' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountTitleValue(), safeDepositBoxAccount.getAccountTitle(), "'Title' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDiscountPeriods(), safeDepositBoxAccount.getDiscountPeriods(), "'Discount Periods' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDateOpenedValue(), safeDepositBoxAccount.getDateOpened(), "'Date Opened' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getMailCodeValue(), safeDepositBoxAccount.getMailCode(), "'Mail Code' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCurrentOfficerValue(), safeDepositBoxAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getBankBranchValue(), safeDepositBoxAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getCorrespondingAccount(), safeDepositBoxAccount.getCorrespondingAccount(), "'Corresponding Account' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getDiscountReason(), safeDepositBoxAccount.getDiscountReason(), "'Discount Reason' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_1(), safeDepositBoxAccount.getUserDefinedField_1(), "'User Defined Field 1' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_2(), safeDepositBoxAccount.getUserDefinedField_2(), "'User Defined Field 2' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_3(), safeDepositBoxAccount.getUserDefinedField_3(), "'User Defined Field 3' value does not match");
        Assert.assertEquals(Pages.accountDetailsPage().getUserDefinedField_4(), safeDepositBoxAccount.getUserDefinedField_4(), "'User Defined Field 4' value does not match");
    }
}
