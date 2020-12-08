package com.nymbus.frontoffice.backoffice;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.notice.Notice;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Backoffice")
@Feature("Notices")
@Owner("Petro")
public class C22849_DocumentsSearchViewPrintNoticesTest extends BaseTest {

    private Notice randomNoticeData;

    @BeforeMethod
    public void preCondition() {
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        randomNoticeData = WebAdminActions.webAdminUsersActions().getRandomNoticeData();
        WebAdminActions.loginActions().doLogout();
    }

    @Test(description = "C22849, Documents Search: View / print notices")
    @Severity(SeverityLevel.CRITICAL)
    public void documentsSearchViewPrintNotices() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Backoffice > Document Search > Notices tab");
        Pages.aSideMenuPage().clickBackOfficeMenuItem();
        Pages.backOfficePage().clickDocumentSearchSeeMoreLink();
        Pages.documentTransactionsPage().clickNoticesTab();
        Pages.documentTransactionsPage().waitForTabContent();

        logInfo("Step 3: Specify date and branch related to one of the notices from preconditions. Run the search");
        String formattedDate = DateTime.getDateWithFormat(randomNoticeData.getDate(), "yyyy-MM-dd", "MM/dd/yyyy");
        Pages.documentTransactionsPage().setDateOpenedValue(formattedDate);
        Pages.documentTransactionsPage().clickBranchSelectorButton();
        Pages.documentTransactionsPage().clickBranchOption(randomNoticeData.getBankBranch());
        Pages.documentTransactionsPage().clickSearchButton();
        Pages.documentTransactionsPage().waitForSearchResultsWithDate(formattedDate);
        Assert.assertTrue(Pages.documentTransactionsPage().getSearchResultsCount() > 1,
                "There are no results satisfying the 'Date + Bank Branch' query");

        logInfo("Step 4: Specify notice sub-type related to one of the notices from preconditions. Run the search");
        Pages.documentTransactionsPage().clickClearAllButton();
        Pages.documentTransactionsPage().clickAccountTypeSelectorButton();
        Pages.documentTransactionsPage().clickAccountOption(randomNoticeData.getSubType());
        Pages.documentTransactionsPage().clickSearchButton();
        Assert.assertTrue(Pages.documentTransactionsPage().getSearchResultsCount() > 1,
                "There are no results satisfying the 'Sub Type' query");

        logInfo("Step 5: Specify account number for which one of the notices from preconditions was generated. Run the search");
        Pages.documentTransactionsPage().clickClearAllButton();
        Pages.documentTransactionsPage().typeAccountNumber(randomNoticeData.getAccountNumber());
        Pages.documentTransactionsPage().clickSearchButton();
        Assert.assertTrue(Pages.documentTransactionsPage().getSearchResultsCount() > 1,
                "There are no results satisfying the 'Account number' query");

        logInfo("Step 6: Click one of the resulted notices which is not printed yet (check box is enabled). Click [Print] button");
        Pages.documentTransactionsPage().clickClearAllButton();
        Pages.documentTransactionsPage().clickRowWithEnabledCheckbox();
        Pages.documentTransactionsPage().clickPrintButton();
        Actions.mainActions().switchToTab(1);
        Pages.documentTransactionsPage().waitForLoadingSpinnerInvisibility();
        Assert.assertTrue(Pages.documentTransactionsPage().isPdfVisible(), "Final pdf sile is not loaded");
    }
}
