package com.nymbus.frontoffice.safedepositboxmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.verifyingmodels.SafeDepositKeyValues;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Frontoffice")
@Feature("Box Accounts Management")
@Owner("Dmytro")
public class C22613_EditSafeDepositBoxAccountTest extends BaseTest {
    private Account safeDepositBoxAccount;
    private String clientID;

    @BeforeMethod
    public void preConditions() {
        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up Safe Deposit Box Account
        safeDepositBoxAccount = new Account().setSafeDepositBoxData();
        safeDepositBoxAccount.setCurrentOfficer(Constants.FIRST_NAME + " " + Constants.LAST_NAME);
        safeDepositBoxAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        safeDepositBoxAccount.setDateOpened(WebAdminActions.loginActions().getSystemDate());

        // Set up CHK account (required to point the 'Corresponding Account')
        Account checkingAccount = new Account().setCHKAccountData();

        // Login to the system and create a client with Safe Deposit Box and CHK accounts
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Get safeDepositKeyValues
        Actions.usersActions().openSafeDepositBoxSizesPage();
        List<SafeDepositKeyValues> safeDepositKeyValues = Actions.usersActions().getSafeDepositBoxValues();
        Assert.assertTrue(safeDepositKeyValues.size() > 0, "Safe deposits values are not set!");

        // Set box size and amount
        AccountActions.verifyingAccountDataActions().setSafeDepositBoxSizeAndRentalAmount(safeDepositBoxAccount, safeDepositKeyValues);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        clientID = Pages.clientDetailsPage().getClientID();

        // Create accounts and logout
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkingAccount);
        safeDepositBoxAccount.setCorrespondingAccount(checkingAccount.getAccountNumber());
        Pages.accountDetailsPage().clickAccountsLink();
        Actions.clientPageActions().closeAllNotifications();
        AccountActions.createAccount().createSafeDepositBoxAccount(safeDepositBoxAccount);
        Actions.loginActions().doLogOut();
    }

    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "C22613, Client Accounts: Edit safe deposit box account")
    public void createSafeBoxAccount() {
        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the client from the preconditions and open his profile on Accounts tab");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientID);
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
        Assert.assertTrue(Pages.editAccountPage().isDateLastPaidDisabledInEditMode(), "'Date Last Paid' field is not disabled");
        Assert.assertTrue(Pages.editAccountPage().isAmountLastPaidDisabledInEditMode(), "'Amount Last Paid' field is not disabled");

        logInfo("Step 5: Select any other value in such drop-down fields:");
        AccountActions.editAccount().selectValuesInDropdownFieldsRequiredForSafeDepositBoxAccountWithJs(safeDepositBoxAccount);

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
        AccountActions.verifyingAccountDataActions().verifyFieldsInEditMode(safeDepositBoxAccount);

        logInfo("Step 10: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 11: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        AccountActions.accountMaintenanceActions().expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("accounttype") >= 1,
                "'accounttype' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Account Title") >= 1,
                "'Account Title' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Account Number") >= 1,
                "'Account Number' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Current Officer") >= 1,
                "'Current Officer' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Bank Branch") >= 1,
                "'Bank Branch' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date Opened") >= 1,
                "'Date Opened' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Box Size") >= 1,
                "'Box Size' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 1") >= 1,
                "'User Defined Field 1' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 2") >= 1,
                "'User Defined Field 2' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 3") >= 1,
                "'User Defined Field 3' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("User Defined Field 4") >= 1,
                "'User Defined Field 4' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Discount Periods") >= 1,
                "'Discount Periods' row count is incorrect!");
        if (safeDepositBoxAccount.getDiscountReason() != null) {
            Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Discount Reason") >= 1,
                    "'Discount Reason' row count is incorrect!");
        }
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Mail Code") >= 1,
                "'Mail Code' row count is incorrect!");
        if (safeDepositBoxAccount.getCorrespondingAccount() != null) {
            Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Corresponding Account") >= 1,
                    "'Corresponding Account' row count is incorrect!");
        }
    }
}