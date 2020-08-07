package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C23911_EditCDIRAAccountTest extends BaseTest {

    private IndividualClient client;
    private Account cdIRAAccount;
    private Account checkingAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up CHK account (required to point the 'Corresponding Account')
        checkingAccount = new Account().setCHKAccountData();

        // Set up CD IRA account
        cdIRAAccount = new Account().setCDIRAAccountData();
        cdIRAAccount.setBankBranch("Inspire - Langhorne"); // Branch of the 'autotest autotest' user
        cdIRAAccount.setMaturityDate(DateTime.getDateWithNMonthAdded(cdIRAAccount.getDateOpened(), "MM/dd/yyyy", Integer.parseInt(cdIRAAccount.getTermType())));
        cdIRAAccount.setDateNextInterest(DateTime.getDateWithNMonthAdded(cdIRAAccount.getDateOpened(), "MM/dd/yyyy", 3)); // 3 month added as 'Interest Frequency' is set to 'Quarterly'

        // Login to the system and create a client
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK and CD IRA accounts and logout
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Pages.accountDetailsPage().clickAccountsLink();
        AccountActions.createAccount().createCDAccount(cdIRAAccount);
        Pages.accountDetailsPage().waitForFullProfileButton();
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C23911, Edit CD IRA Account")
    @Severity(SeverityLevel.CRITICAL)
    public void editCDIRAAccount() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CD account from the precondition and open it in Edit mode");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(cdIRAAccount);

        logInfo("Step 3: Click [Edit] button");
        Pages.accountDetailsPage().clickEditButton();

        logInfo("Step 4: Look at the fields, verify that some of them are grouped in such sections:" +
                "Balance and Interest, Transactions, Overdraft, Misc");
        AccountActions.editAccount().verifyFieldGroupsAreVisible();

        logInfo("Step 5: Look at the fields and verify that such fields are disabled for editing");
        AccountActions.editAccount().verifyCdIraAccountFieldsAreDisabledForEditing();

        logInfo("Step 6: Select data in Federal W/H reason drop-down field");
        logInfo("Step 7: Select any date in such calendar fields:");
        logInfo("Step 8: Fill in such text fields that were not displayed in Add new mode");
        logInfo("Step 9: Select any other value in such fields");
        logInfo("Step 10: Set switcher Transactional Account = YES");
        AccountActions.editAccount().fillInInputFieldsThatWereNotAvailableDuringCDIRAAccountCreation(cdIRAAccount);

        logInfo("Step 11: Click [-] icon next to any section (e.g. Transactions section) and verify that all fields within this section were hidden");
        Pages.editAccountPage().clickMiscSectionLink();

        logInfo("Step 12: Click [+] icon next to the section from Step9 and verify that all fields within the section are displayed. Fields were NOT cleared out");
        Pages.editAccountPage().clickMiscSectionLink();

        logInfo("Step 13: Submit the account editing by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 14: Pay attention to CD IRA account fields");
        AccountActions.accountDetailsActions().clickMoreButton();
        AccountActions.verifyingAccountDataActions().verifyCdIraAccountFieldsWithUpdatedDataInEditMode(cdIRAAccount);

        logInfo("Step 15: Click [Edit] button and pay attention to the fields");
        Pages.accountDetailsPage().clickEditButton();
        AccountActions.editAccount().verifyCdIraAccountFieldsWithUpdatedDataInEditMode(cdIRAAccount);

        logInfo("Step 16: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 17: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        AccountActions.accountMaintenanceActions().verifyCdIraAccountRecordsAfterEditing(cdIRAAccount);
    }
}
