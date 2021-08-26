package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import com.nymbus.util.Random;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.DecimalFormat;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C25454_ManuallyChangeInterestRateOnConvertedLoanAccountTest extends BaseTest {

    private String activeLoanAccountId;

    @BeforeMethod
    public void preCondition() {
        SelenideTools.openUrl(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        activeLoanAccountId = WebAdminActions.webAdminUsersActions().getActiveConvertedLoanAccountFundedInPast();
        WebAdminActions.loginActions().doLogout();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25454, testRunName = TEST_RUN_NAME)
    @Test(description = "C25454, Interest Rate Change: Manually change interest rate on converted loan account")
    @Severity(SeverityLevel.CRITICAL)
    public void interestRateChangeManuallyChangeInterestRateOnConvertedLoanAccount() {

        logInfo("Step 1: Log in to SmartCore");
        SelenideTools.openUrl(Constants.URL);
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open loan account from preconditions -> Maintenance -> Tools -> Interest Rate Change");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(activeLoanAccountId);

        int yearBase = Integer.parseInt(Pages.accountDetailsPage().getDaysBaseYearBase().split("/")[1].substring(0, 3));
        double currentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance());
        String accountNumber = Pages.accountDetailsPage().getAccountNumberValue();

        Pages.accountDetailsPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickChooseToolSelectorButton();
        Pages.accountMaintenancePage().clickChooseToolOption("Interest Rate Change");
        Pages.accountMaintenancePage().clickToolsLaunchButton();
        String currentEffectiveRate = Pages.interestRateChangeModalPage().getCurrentEffectiveRate();
        Pages.interestRateChangeModalPage().clickAddBackDatedRateChangeButton();

        logInfo("Step 3: Specify the following parameters:\n" +
                "- NEW Current Effective Rate != old Current Effective Rate\n" +
                "- Begin Earn Date = date in the past\n" +
                "- Accrue Thru Date = yesterday (by default value is set to Current date - 1 day)");
        int beginEarnDateDays = 4;
        double oldCurrentEffectiveRate = Double.parseDouble(currentEffectiveRate);
        double newCurrentEffectiveRate = oldCurrentEffectiveRate + Random.genInt(1, 10);

        Pages.interestRateChangeModalPage().setNewCurrentEffectiveRateValue(String.valueOf(newCurrentEffectiveRate));
        Pages.interestRateChangeModalPage().setBeginEarnDate(DateTime.getDateMinusDays(DateTime.getLocalDateWithPattern("MM/dd/yyyy"), beginEarnDateDays));

        logInfo("Step 4: Click on the 'Commit Transaction' button");
        Pages.interestRateChangeModalPage().clickCommitTransactionButton();

        logInfo("Step 5: Specify supervisor login name and password and click 'Enter'");
        Pages.supervisorModalPage().inputLogin(userCredentials.getUserName());
        Pages.supervisorModalPage().inputPassword(userCredentials.getPassword());
        Pages.supervisorModalPage().clickEnter();

        double adjustmentAmount = (currentBalance * ((newCurrentEffectiveRate - oldCurrentEffectiveRate) / 100) / yearBase) * beginEarnDateDays;
        adjustmentAmount = roundAmountToTwoDecimals(adjustmentAmount);
        double interestEarned = getInterestEarned(accountNumber);
        String alertMessageModalText = Pages.alertMessageModalPage().getAlertMessageModalText();

        Assert.assertTrue(alertMessageModalText.contains(String.valueOf(adjustmentAmount)), "'Adjustment Amount' is calculated incorrect");
        Assert.assertTrue(alertMessageModalText.contains(String.format("%.2f", interestEarned)), "'Interest Earned' is calculated incorrect");
        Pages.alertMessageModalPage().clickOkButton();

        logInfo("Step 7: Go to 'Transactions' tab and pay attention at the generated transaction");
        Pages.interestRateChangeModalPage().clickCloseButton();
        Pages.loginPage().waitForLoginForm();
        Pages.loginPage().typeUserName(userCredentials.getUserName());
        Pages.loginPage().typePassword(userCredentials.getPassword());
        Pages.loginPage().clickEnterButton();
        Pages.accountDetailsPage().clickTransactionsTab();
        String transactionCode = Pages.accountTransactionPage().getTransactionCodeByIndex(1);
        checkTransactionCode(transactionCode, newCurrentEffectiveRate, oldCurrentEffectiveRate);

        logInfo("Step 8: Go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Current effective rate") >= 1,
                "'Current effective rate' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest earned") >= 1,
                "'Interest earned' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date this rate change") >= 1,
                "'Date this rate change' row count is incorrect!");
    }

    private void checkTransactionCode(String transactionCode, double newCurrentEffectiveRate, double oldCurrentEffectiveRate) {
        if (newCurrentEffectiveRate > oldCurrentEffectiveRate) {
            Assert.assertEquals(TransactionCode.ADD_TO_IENC_409.getTransCode(), transactionCode,
                    "Transaction code is not equal to '408 - Take From IENC'");
        } else {
            Assert.assertEquals(TransactionCode.TAKE_FROM_IENC_408.getTransCode(), transactionCode,
                    "Transaction code is not equal to '409 - Add To IENC'");
        }
    }

    private double roundAmountToTwoDecimals(double amount) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(amount));
    }

    private double getInterestEarned(String accountNumber) {
        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL);
        SelenideTools.switchTo().window(1);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        double interestEarned = Double.parseDouble(WebAdminActions.webAdminUsersActions().getInterestEarned(accountNumber));

        WebAdminActions.loginActions().doLogout();
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);

        return interestEarned;
    }
}
