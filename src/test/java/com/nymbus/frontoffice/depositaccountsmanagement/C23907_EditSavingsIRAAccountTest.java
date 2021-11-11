package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C23907_EditSavingsIRAAccountTest extends BaseTest {

    private Account savingsIRAAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up IRA account
        savingsIRAAccount = new Account().setSavingsIraAccountData();

        // Login to the system
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set the bank branch of the user to account
        savingsIRAAccount.setBankBranch(Actions.usersActions().getBankBranch());
        savingsIRAAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.IRA, RateType.TIER));
        savingsIRAAccount.setApplyInterestTo("Remain in Account");
        savingsIRAAccount.setBirthDate(DateTime.getYesterdayDate("MM/dd/yyyy"));
        savingsIRAAccount.setDateOfFirstDeposit(DateTime.getLocalDate());
        savingsIRAAccount.setDateDeceased(DateTime.getLocalDate());

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create IRA account and logout
        AccountActions.createAccount().createIRAAccount(savingsIRAAccount);
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Deposit Accounts Management";

    @TestRailIssue(issueID = 23907, testRunName = TEST_RUN_NAME)
    @Test(description = "C23907, Edit savings IRA account")
    @Severity(SeverityLevel.CRITICAL)
    public void editSavingsIRAAccount() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the Savings IRA account from the precondition and open it on Details");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(savingsIRAAccount);

        logInfo("Step 3: Click [Edit] button");
        Pages.accountDetailsPage().clickEditButton();

        logInfo("Step 4: Look at the fields, verify that some of them are grouped in such sections:" +
                "Balance and Interest, Transactions, Overdraft, Misc");
        AccountActions.editAccount().verifyFieldGroupsAreVisible();

        logInfo("Step 5: Look at the fields and verify that such fields are disabled for editing");
        AccountActions.editAccount().verifySavingsIraAccountFieldsAreDisabledForEditing();

        logInfo("Step 6: Look at the field 'Account Title' and verify that such field is not a required field");
        TestRailAssert.assertFalse(Pages.addAccountPage().isAccountTitleFieldRequired(),
                new CustomStepResult("'Account Title' is required", "'Account Title' is not required"));

        logInfo("Step 7: Select data in such dropdown fields that were not available in Add New mode");
        logInfo("Step 8: Look at the field 'Account Type' and verify that such field is not disabled for editing");
        logInfo("Step 9: Click on the 'Account Type' drop-down and verify the account types that are present in the list");
        logInfo("Step 10: Change account type to some value from the drop-down list (e.g. Officer)");
        logInfo("Step 11: Select any date in such calendar fields:\n" +
                "- Date Of First Deposit - any\n" +
                "- Birth Date - any < Current Date\n" +
                "- Date Deceased - any date > Birth Date, Less or equal to Current Date");
        logInfo("Step 12: Fill in such text fields that were not displayed in Add new mode");
        logInfo("Step 13: Select any other value in such fields");
        logInfo("Step 14: Set such switchers");
        AccountActions.editAccount().selectValuesInFieldsThatWereNotAvailableDuringSavingsIraAccountCreation(savingsIRAAccount);

        logInfo("Step 15: Click [-] icon next to any section (e.g. Transactions section) and verify that all fields within this section were hidden");
        Pages.editAccountPage().clickTransactionsSectionLink();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        TestRailAssert.assertFalse(Pages.editAccountPage().isTransactionsGroupVisible(), new CustomStepResult("'Transaction' section is visible", "'Transaction' section is collapsed"));

        logInfo("Step 16: Click [+] icon next to the section from Step9 and verify that all fields within the section are displayed. Fields were NOT cleared out");
        Pages.editAccountPage().clickTransactionsSectionLink();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        TestRailAssert.assertTrue(Pages.editAccountPage().isTransactionsGroupVisible(), new CustomStepResult("'Transaction' section is collapsed", "'Transaction' section is visible"));

        logInfo("Step 17: Submit the account editing by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 18: Pay attention to Savings IRA account fields");
        AccountActions.accountDetailsActions().clickMoreButton();
        AccountActions.verifyingAccountDataActions().verifySavingsIraAccountFieldsWithUpdatedDataInEditMode(savingsIRAAccount);

        logInfo("Step 19: Click [Edit] button and pay attention to the fields");
        Pages.accountDetailsPage().clickEditButton();
        AccountActions.editAccount().verifySavingsIraAccountFieldsWithUpdatedDataInEditMode(savingsIRAAccount);

        logInfo("Step 20: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 21: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        AccountActions.accountMaintenanceActions().verifySavingsIraAccountRecordsAfterEditing(savingsIRAAccount);
    }
}
