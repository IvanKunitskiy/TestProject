package com.nymbus.frontoffice.safedepositboxmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22613_EditSafeDepositBoxAccountTest extends BaseTest {

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
        AccountActions.createAccount().createSafeDepositBoxAccount(safeDepositBoxAccount);
        Pages.accountDetailsPage().clickAccountsLink();
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Actions.loginActions().doLogOut();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "C22612, Add New 'Safe Deposit Box' Account")
    public void createSafeBoxAccount() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the client from the preconditions and open his profile on Accounts tab");
        Pages.clientsSearchPage().typeToClientsSearchInputField(safeDepositBoxAccount.getAccountNumber());
        Assert.assertTrue(Pages.clientsSearchPage().getAllLookupResults().size() == 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsSearchPage().isSearchResultsRelative(Pages.clientsSearchPage().getAllLookupResults(), safeDepositBoxAccount.getAccountNumber()), "Search results are not relevant");
        Pages.clientsSearchPage().clickOnSearchButton();
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
        Pages.clientDetailsPage().clickAccountsTab();
        Pages.clientDetailsPage().openAccountByNumber(safeDepositBoxAccount.getAccountNumber());

        logInfo("Step 3: Click [Edit] button");
        Pages.accountDetailsPage().clickEditButton();

        logInfo("Step 4: Look at the fields and verify that such fields are disabled for editing");
        Assert.assertTrue(Pages.editAccountPage().isProductTypeFieldDisabledInEditMode(), "'Product Type' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccountNumberFieldDisabledInEditMode(), "'Account Number' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccountTypeFieldDisabledInEditMode(), "'Account Type' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isOriginatingOfficerFieldDisabledInEditMode(), "'Originating Officer' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAccountStatusFieldDisabledInEditMode(), "'Account Status' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateOpenedFieldDisabledInEditMode(), "'Date Opened' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateClosedFieldDisabledInEditMode(), "'Date Closed field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isCurrentBalanceDisabledInEditMode(), "'Current Balance' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateNextBillingDisabledInEditMode(), "'Date Next Billing' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isDateLastPaidDisabledInEditMode(), "'Date Last Paid' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAmountLastPaidDisabledInEditMode(), "'Amount Last Paid' field is not disabled");

        logInfo("Step 5: Select any other value in such drop-down fields:");
        AccountActions.editAccount().selectValuesInDropdownFieldsRequiredForSafeDepositBoxAccount(safeDepositBoxAccount);

        logInfo("Step 6: Make some changes in such fields:");
        Pages.editAccountPage().setUserDefinedField_1(safeDepositBoxAccount.getUserDefinedField_1());
        Pages.editAccountPage().setUserDefinedField_2(safeDepositBoxAccount.getUserDefinedField_2());
        Pages.editAccountPage().setUserDefinedField_3(safeDepositBoxAccount.getUserDefinedField_3());
        Pages.editAccountPage().setUserDefinedField_4(safeDepositBoxAccount.getUserDefinedField_4());

        logInfo("Step 7: Select current date from the 'Date Last Access' calendar");
        Pages.editAccountPage().setDateLastAccess(safeDepositBoxAccount.getDateLastAccess());

        logInfo("Step 8: Submit the account editing by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 9: Click [Edit] button and pay attention to the fields");
        Pages.accountDetailsPage().clickEditButton();
        Assert.assertEquals(Pages.editAccountPage().getMailCode(), safeDepositBoxAccount.getMailCode(), "'Mail Code' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCurrentOfficerValueInEditMode(), safeDepositBoxAccount.getCurrentOfficer(), "'Current Officer' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getBankBranchValueInEditMode(), safeDepositBoxAccount.getBankBranch(), "'Bank Branch' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getCorrespondingAccount(), safeDepositBoxAccount.getCorrespondingAccount(), "'Corresponding Account' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getDiscountReason(), safeDepositBoxAccount.getDiscountReason(), "'Discount Reason' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField1(), safeDepositBoxAccount.getUserDefinedField_1(), "'User Defined Field 1' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField2(), safeDepositBoxAccount.getUserDefinedField_2(), "'User Defined Field 2' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField3(), safeDepositBoxAccount.getUserDefinedField_3(), "'User Defined Field 3' value does not match");
        Assert.assertEquals(Pages.editAccountPage().getUserDefinedField4(), safeDepositBoxAccount.getUserDefinedField_4(), "'User Defined Field 4' value does not match");

        logInfo("Step 10: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 11: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        // TODO: Implement verification at Maintenance History page

    }

}