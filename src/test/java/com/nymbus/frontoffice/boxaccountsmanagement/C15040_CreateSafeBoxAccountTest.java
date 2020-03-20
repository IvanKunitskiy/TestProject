package com.nymbus.frontoffice.boxaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.Pages;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("Box Accounts Management")
@Owner("Dmytro")
public class C15040_CreateSafeBoxAccountTest extends BaseTest {

    private Client client;
    private Account safeDepositBoxAccount;

    @BeforeMethod
    public void preConditions() {
        client = new Client().setDefaultClientData();
        safeDepositBoxAccount = new Account().setSafeDepositBoxData();
        safeDepositBoxAccount.setBoxSize("10");
    }

    @Test(description = "C15040, Create safe box account")
    @Severity(SeverityLevel.CRITICAL)
    public void createSafeBoxAccount() {
        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        ClientsActions.createClient().createClient(client);
        final String clientID = Pages.clientDetailsPage().getClientID();

        logInfo("Step 2: Go to Clients screen and search for client from preconditions");
        Pages.aSideMenuPage().clickClientMenuItem();
        Pages.clientsPage().typeToClientsSearchInputField(clientID);
        Assert.assertTrue(Pages.clientsPage().getAllLookupResults().size() == 1, "There is more than one client found");
        Assert.assertTrue(Pages.clientsPage().isSearchResultsRelative(Pages.clientsPage().getAllLookupResults(), clientID));

        Pages.clientsPage().clickOnSearchButton();

        logInfo("Step 3: Open it on Accounts tab");
        Pages.clientsSearchResultsPage().clickTheExactlyMatchedClientInSearchResults();
        Pages.clientDetailsPage().waitForPageLoaded();
        Pages.clientDetailsPage().clickAccountsTab();

        logInfo("Step 4: Click [Add New] --> select 'Account' in the drop down");
        Pages.clientDetailsPage().clickAddNewButton();
        Pages.clientDetailsPage().clickAccountOption();

        logInfo("Step 5: Select Safe Deposit Box option");
        // Product type field
        Pages.addAccountPage().clickProductTypeSelectorButton();
        Pages.addAccountPage().clickProductTypeOption(safeDepositBoxAccount.getProductType());
        //        AccountActions.createAccount().setProductType(safeDepositBoxAccount);

        logInfo("Step 6: Fill in all the displayed fields with correct values");
        // Box size field
        Pages.addAccountPage().clickBoxSizeSelectorButton();
        Pages.addAccountPage().clickBoxSizeSelectorOption(safeDepositBoxAccount.getBoxSize());
        //        AccountActions.createAccount().setBoxSize(safeDepositBoxAccount);

        // Account number field
        Pages.addAccountPage().setAccountNumberValue(safeDepositBoxAccount.getAccountNumber());

        // Account title field
        Pages.addAccountPage().setAccountTitleValue(safeDepositBoxAccount.getAccountTitle());

        // Bank branch field
        Pages.addAccountPage().clickBankBranchSelectorButton();
        Pages.addAccountPage().clickBankBranchOption(safeDepositBoxAccount.getBankBranch());

        logInfo("Step 7: Click [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 8: Verify values for the fields filled in during account creation");
        Assert.assertEquals(safeDepositBoxAccount.getProductType(), Pages.accountDetailsPage().getProductTypeValue(), "Product type is not relevant");
        Assert.assertEquals(safeDepositBoxAccount.getBoxSize(), Pages.accountDetailsPage().getBoxSizeValue(), "Box size is not relevant");
        Assert.assertEquals(safeDepositBoxAccount.getAccountNumber(), Pages.accountDetailsPage().getAccountNumberValue(), "Account number is not relevant");
        Assert.assertEquals(safeDepositBoxAccount.getAccountTitle(), Pages.accountDetailsPage().getAccountTitleValue(), "Account title is not relevant");
        Assert.assertEquals(safeDepositBoxAccount.getBankBranch(), Pages.accountDetailsPage().getBankBranchValue(), "Bank branch is not relevant");

        logInfo("Step 9: Go to Maintenance tab and verify maintenance history");
        Pages.accountDetailsPage().clickMaintenanceTab();
        Pages.accountDetailsPage().clickViewAllMaintenanceHistoryLink();
        Pages.accountDetailsPage().clickViewMoreButton();

        // TODO: Step 9 requires assertion in account maintenance history
        // Expected result: Account creation info is displayed in maintenance history
        // (separate row for each field , old value == null, new value == filled in with value from account)
    }
}
