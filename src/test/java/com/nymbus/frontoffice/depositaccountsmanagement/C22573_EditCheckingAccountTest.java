package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
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
public class C22573_EditCheckingAccountTest extends BaseTest {

    private Account checkingAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up account
        checkingAccount = new Account().setCHKAccountData();

        // Login to the system and create a client with checking account
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set the product
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22573, Edit checking account")
    @Severity(SeverityLevel.CRITICAL)
    public void editCheckingAccount() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CHK account from the precondition and open it on Details");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(checkingAccount);

        logInfo("Step 3: Click [Edit] button");
        Pages.accountDetailsPage().clickEditButton();

        logInfo("Step 4: Look at the fields, verify that some of them are grouped in such sections:" +
                "Balance and Interest, Transactions, Overdraft, Misc");
        AccountActions.editAccount().verifyFieldGroupsAreVisible();

        logInfo("Step 5: Look at the fields and verify that such fields are disabled for editing");
        AccountActions.editAccount().verifyChkAccountFieldsAreDisabledForEditing();

        logInfo("Step 6: Select data in such dropdown fields that were not available in Add New mode");
        logInfo("Step 7: Fill in such text fields that were not displayed in Add new mode");
        logInfo("Step 8: Select any other value in such fields");
        logInfo("Step 9: Set Apply Seasonal Address switcher to NO");
        AccountActions.editAccount().selectValuesInFieldsThatWereNotAvailableDuringCheckingAccountCreation(checkingAccount);

        logInfo("Step 10: Click [-] icon next to any section (e.g. Transactions section) and verify that all fields within this section were hidden");
        Pages.editAccountPage().clickMiscSectionLink();

        logInfo("Step 11: Click [+] icon next to the section from Step9 and verify that all fields within the section are displayed. Fields were NOT cleared out");
        Pages.editAccountPage().clickMiscSectionLink();

        logInfo("Step 12: Submit the account editing by clicking [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 13: Pay attention to CHK account fields");
        AccountActions.accountDetailsActions().clickMoreButton();
        AccountActions.verifyingAccountDataActions().verifyChkAccountFieldsWithUpdatedDataInViewMode(checkingAccount);

        logInfo("Step 14: Click [Edit] button and pay attention to the fields");
        Pages.accountDetailsPage().clickEditButton();
        AccountActions.editAccount().verifyChkAccountFieldsWithUpdatedDataInEditMode(checkingAccount);

        logInfo("Step 15: Do not make any changes and go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 16: Look through the records on Maintenance History page and check that all fields that were filled in during account creation are reported in account Maintenance History");
        AccountActions.accountMaintenanceActions().verifyChkAccountRecordsAfterEditing(checkingAccount);
    }
}