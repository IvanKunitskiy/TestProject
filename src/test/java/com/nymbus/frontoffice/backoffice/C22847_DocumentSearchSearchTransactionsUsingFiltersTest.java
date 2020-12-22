package com.nymbus.frontoffice.backoffice;

import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.transaction.verifyingModels.WebAdminTransactionFromQuery;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Backoffice")
@Feature("Notices")
@Owner("Petro")
public class C22847_DocumentSearchSearchTransactionsUsingFiltersTest extends BaseTest {

    private WebAdminTransactionFromQuery T1;
    private WebAdminTransactionFromQuery T2;
    private WebAdminTransactionFromQuery T3;

    @BeforeMethod
    public void preCondition() {
        SelenideTools.openUrl(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        T1 = WebAdminActions.webAdminUsersActions().getCheckTransaction();
        T2 = WebAdminActions.webAdminUsersActions().getAtmTransaction();
        T3 = WebAdminActions.webAdminUsersActions().getTransactionCommittedOnCurrentDate();

        WebAdminActions.loginActions().doLogout();
    }

    @Test(description="C22847, Document Search: Search transactions using filters")
    @Severity(SeverityLevel.CRITICAL)
    public void documentSearchSearchTransactionsUsingFilters() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        SelenideTools.openUrl(Constants.URL);
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Backoffice > Document Search > Transactions tab");
        Pages.aSideMenuPage().clickBackOfficeMenuItem();
        Pages.backOfficePage().clickDocumentSearchSeeMoreLink();
        Pages.documentSearchTransactionsPage().waitForTabContent();

        logInfo("Step 3: Specify date == date of T1 from preconditions (e.g. 12/12/2020),\n" +
                "check number XXXX in filters section; Run the search");
        Pages.documentSearchTransactionsPage().setDate(T1.getEffectiveEntryDate());
        Pages.documentSearchTransactionsPage().setCheckNumber(T1.getCheckNumber());
        Pages.documentSearchTransactionsPage().clickSearchButton();
        Pages.documentSearchTransactionsPage().waitForSearchButtonClickable();
        Assert.assertTrue(Pages.documentSearchTransactionsPage().getTransactionsCount() >= 1,
                "There are no transactions found");

        logInfo("Step 4: Click the found transaction");
        Pages.documentSearchTransactionsPage().clickLineByIndex(1);
        Pages.documentSearchTransactionsPage().waitForCheckVisibility();
        Assert.assertTrue(Pages.documentSearchTransactionsPage().isCheckVisible(), "Check image is not visible");

        logInfo("Step 5: Specify amount == amount of T2 from preconditions (e.g. $200), \n" +
                "account number related to T2 and branch2 in filters section; Run the search");
        Pages.documentSearchTransactionsPage().clickClearAllButton();
        Pages.documentSearchTransactionsPage().setAmount(String.valueOf(T2.getAmount()));
        Pages.documentSearchTransactionsPage().setAccount(T2.getAccountNumber());
        Pages.documentSearchTransactionsPage().setDate(T2.getEffectiveEntryDate());
        Pages.documentSearchTransactionsPage().clickSearchButton();
        Pages.documentSearchTransactionsPage().waitForSearchButtonClickable();
        Assert.assertTrue(Pages.documentSearchTransactionsPage().getTransactionsCount() >= 1,
                "There are no transactions found");

        logInfo("Step 6: Specify current date in filters section; Run the search");
        Pages.documentSearchTransactionsPage().clickClearAllButton();
        Pages.documentSearchTransactionsPage().setDate(T3.getEffectiveEntryDate());
        Pages.documentSearchTransactionsPage().clickSearchButton();
        Pages.documentSearchTransactionsPage().waitForSearchButtonClickable();
        Assert.assertTrue(Pages.documentSearchTransactionsPage().getTransactionsCount() >= 1,
                "There are no transactions found");
    }
}
