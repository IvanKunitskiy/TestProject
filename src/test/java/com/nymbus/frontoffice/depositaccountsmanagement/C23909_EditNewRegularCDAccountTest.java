package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C23909_EditNewRegularCDAccountTest extends BaseTest {

    private Account cdAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up IRA account
        cdAccount = new Account().setCdAccountData();
        cdAccount.setTermType("3");
        cdAccount.setMaturityDate(DateTime.getDateWithNMonthAdded(cdAccount.getDateOpened(), "MM/dd/yyyy", Integer.parseInt(cdAccount.getTermType())));
        cdAccount.setDateNextInterest(DateTime.getDateWithNMonthAdded(cdAccount.getDateOpened(), "MM/dd/yyyy", 3)); // 3 month added as 'Interest Frequency' is set to 'Quarterly'

        // Login to the system
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set the bank branch of the user to account
        cdAccount.setBankBranch(Actions.usersActions().getBankBranch());
        cdAccount.setProduct(Actions.productsActions().getProduct(Products.CD_PRODUCTS, AccountType.CD, RateType.FIXED));

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CD account and logout
        AccountActions.createAccount().createCDAccount(cdAccount);
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Deposit Accounts Management";

    @TestRailIssue(issueID = 23909, testRunName = TEST_RUN_NAME)
    @Test(description = "C23909, Edit New Regular CD Account")
    @Severity(SeverityLevel.CRITICAL)
    public void editNewRegularCDAccount() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CD account from the precondition and open it in Edit mode");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(cdAccount);
        Pages.accountDetailsPage().clickEditButton();

        logInfo("Step 3: Look at the fields, verify that some of them are grouped in such sections:" +
                "Balance and Interest, Transactions, Overdraft, Misc");
        AccountActions.editAccount().verifyFieldGroupsAreVisible();

        logInfo("Step 4: Look at the fields and verify that such fields are disabled for editing");
        AccountActions.editAccount().verifyRegularCdAccountFieldsAreDisabledForEditing();

        logInfo("Step 5: Select data in such dropdown fields that were not available in Add New mode");
        logInfo("Step 6: Fill in such text fields that were not displayed in Add new mode");
        logInfo("Step 7: Select any other value in such fields");
        logInfo("Step 8: Set switcher Transactional Account = NO");
        AccountActions.editAccount().fillInInputFieldsThatWereNotAvailableDuringCDAccountCreation(cdAccount);

        logInfo("Step 9: Click [-] icon next to any section (e.g. Transactions section) and verify that all fields within this section were hidden");
        Pages.editAccountPage().clickMiscSectionLink();

        logInfo("Step 10: Click [+] icon next to the section from Step9 and verify that all fields within the section are displayed. Fields were NOT cleared out");
        Pages.editAccountPage().clickMiscSectionLink();

        logInfo("Step 11: Submit the account editing by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 9: Pay attention to CD account fields");
        AccountActions.accountDetailsActions().clickMoreButton();
        AccountActions.verifyingAccountDataActions().verifyRegularCdAccountFieldsWithUpdatedDataInEditMode(cdAccount);

        logInfo("Step 10: Click [Edit] button and pay attention to the fields");
        Pages.accountDetailsPage().clickEditButton();
        AccountActions.editAccount().verifyRegularCdAccountFieldsWithUpdatedDataInEditMode(cdAccount);

        logInfo("Step 11: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 12: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        AccountActions.accountMaintenanceActions().verifyRegularCdAccountRecordsAfterEditing(cdAccount);
    }
}
