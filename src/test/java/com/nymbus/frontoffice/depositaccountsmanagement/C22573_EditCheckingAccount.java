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
public class C22573_EditCheckingAccount extends BaseTest {

    Client client;
    Account checkingAccount;

    @BeforeMethod
    public void preCondition() {
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        checkingAccount = new Account().setCHKAccountData();
    }

    @Test(description = "C22573, Edit checking account")
    @Severity(SeverityLevel.CRITICAL)
    public void editCheckingAccount() {

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

        LOG.info("Step 3: Click [Edit] button");
        Pages.accountDetailsPage().clickEditButton();

        LOG.info("Step 4: Look at the fields and verify that such fields are disabled for editing");
        Assert.assertTrue(Pages.accountDetailsPage().isProductTypeFieldDisabledInEditMode(), "'Product Type' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isProductFieldDisabledInEditMode(), "'Product' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isAccountNumberFieldDisabledInEditMode(), "'Account Number' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isAccountTypeFieldDisabledInEditMode(), "'Account Type' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isOriginatingOfficerFieldDisabledInEditMode(), "'Originating Officer' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isAccountStatusFieldDisabledInEditMode(), "'Account Status' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isDateOpenedFieldDisabledInEditMode(), "'Date Opened' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isDateClosedFieldDisabledInEditMode(), "'Date Closed field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isAnnualPercentageYieldFieldDisabledInEditMode(), "'Annual Percentage Yield' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isDaysOverdraftFieldDisabledInEditMode(), "'Times Overdrawn-6 Months' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isDaysOverdraftAboveLimitFieldDisabledInEditMode(), "'Times $5000 Overdrawn-6 Months' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isLastDebitAmountFieldDisabledInEditMode(), "'Last Debit Amount' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isAutomaticOverdraftLimitFieldDisabledInEditMode(), "'Automatic Overdraft Limit Field' field is not disabled");
        Assert.assertTrue(Pages.accountDetailsPage().isTotalEarningsFieldDisabledInEditMode(), "'Total Earnings' field is not disabled");

        LOG.info("Step 5: Select data in such dropdown fields that were not available in Add New mode");
        AccountActions.editAccount().setFederalWHReason(checkingAccount);
        AccountActions.editAccount().setReasonATMChargeWaived(checkingAccount);
//        - OD protection acct #
//        - Reason auto NSF chg waived
//        - Reason Debit Card Charge Waived
//        - Automatic Overdraft Status = Active
//        - Reason auto OD chg waived
//        - When surcharges refunded


    }

}
