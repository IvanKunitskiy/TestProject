package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22587_AddNewRegularCDAccountTest extends BaseTest {

    private IndividualClient client;
    private Account cdAccount;

    @BeforeMethod
    public void preConditions() {
        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up IRA account
        cdAccount = new Account().setCDAccountData();
        cdAccount.setTermType("3");
        cdAccount.setMaturityDate(DateTime.getDateWithNMonthAdded(cdAccount.getDateOpened(), "MM/dd/yyyy", Integer.parseInt(cdAccount.getTermType())));
        cdAccount.setDateNextInterest(DateTime.getDateWithNMonthAdded(cdAccount.getDateOpened(), "MM/dd/yyyy", 3)); // 3 month added as 'Interest Frequency' is set to 'Quarterly'

        // Set up CHK account (required to point the 'Corresponding Account')
        Account checkingAccount = new Account().setCHKAccountData();

        // Login to the system
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set the bank branch of the user to account
        cdAccount.setBankBranch(Actions.usersActions().getBankBranch());

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create account and logout
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22587, Add New Regular CD Account")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewRegularCDAccountTest() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the client from the preconditions and open his profile on Accounts tab");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        Pages.clientDetailsPage().clickAccountsTab();

        logInfo("Step 3: Click 'Add New' drop down and select 'Account'");
        Pages.clientDetailsPage().clickAddNewButton();
        AccountActions.createAccount().setAddNewOption(cdAccount);

        logInfo("Step 4: Select 'Product Type' = 'CD Account'");
        AccountActions.createAccount().setProductType(cdAccount);

        logInfo("Step 5: Select any product not IRA (e.g. 3 month Regular Certificate)");
        AccountActions.createAccount().setProduct(cdAccount);

        logInfo("Step 6: Look through the fields. Check that fields are prefilled by default");
        AccountActions.createAccount().verifyRegularCdAccountPrefilledFields(cdAccount, client);

        logInfo("Step 7: Select values in such drop-down fields:");
        logInfo("Step 8: Fill in such text fields with valid data (except Account Number field):");
        logInfo("Step 9: Set 'Transactional Account' switcher to YES):");
        logInfo("Step 10: Select Date Opened as any date < Current Date");
        cdAccount.setApplyInterestTo("CHK Acct");
        AccountActions.createAccount().selectValuesInFieldsRequiredForCDAccount(cdAccount);

        logInfo("Step 11: Submit the account creation by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 12: Check the fields that were filled in during account creation");
        AccountActions.accountDetailsActions().clickMoreButton();
        AccountActions.accountDetailsActions().verifyCDAccountRecords(cdAccount);

        logInfo("Step 13: Check the Maturity Date field value (verify that it's calculated based on Date Opened + Term of Account)");
        Assert.assertEquals(Pages.accountDetailsPage().getMaturityDate(), cdAccount.getMaturityDate(), "'Maturity Date' value does not match");

        logInfo("Step 14: Check the Date next interest field value (verify that it's calculated based on Date Opened + Interest Frequency)");
        Assert.assertEquals(Pages.accountDetailsPage().getDateNextInterest(), cdAccount.getDateNextInterest(), "'Maturity Date' value does not match");

        logInfo("Step 15: Click [Edit] button and pay attention to the fields that were filled in during account creation");
        Pages.accountDetailsPage().clickEditButton();
        AccountActions.editAccount().verifyCdAccountFieldsAfterCreationInEditMode(cdAccount);

        logInfo("Step 16: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 17: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        AccountActions.accountMaintenanceActions().verifyRegularCdAccountRecords(cdAccount);
    }
}
