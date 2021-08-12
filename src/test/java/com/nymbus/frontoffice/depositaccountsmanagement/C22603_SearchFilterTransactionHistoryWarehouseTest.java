package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.pages.webadmin.WebAdminPages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22603_SearchFilterTransactionHistoryWarehouseTest extends BaseTest {
    private String accountNumber;

    @BeforeMethod
    public void setAccountWithWareHouseTransactions() {
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminTransactionActions().goToWarehouseTransactionsUrl();
        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                            "No records with warehouse transaction found!");
        accountNumber = WebAdminPages.rulesUIQueryAnalyzerPage().getAccountNumberWithWarehouseTransaction(1);
        WebAdminActions.loginActions().doLogoutProgrammatically();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();
    }

    private final String TEST_RUN_NAME = "Deposit Accounts Management";

    @TestRailIssue(issueID = 22603, testRunName = TEST_RUN_NAME)
    @Test(description = "C22603, Account Transactions: Search / filter transaction history: Warehouse")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyWarehouseTransactions() {
        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the account from the precondition and open it on Transactions tab");
        Actions.clientPageActions().searchAndOpenClientByName(accountNumber);
        AccountActions.retrievingAccountData().goToTransactionsTab();

        logInfo("Step 3: Look through the list of transactions and make sure that \n" +
                "Warehouse transactions are not displayed in transactions list by default");
        AccountActions.accountTransactionActions().verifyTransactionList(WebAdminActions.loginActions().getSystemDate(), true);

        logInfo("Step 4: Click 'Show Transactions from' drop-down search filter and select Warehouse \n" +
                "Click [Apply Filter] button");
        AccountActions.accountTransactionActions().applyFilterByTransactionFromFiled("Warehouse");
        Assert.assertTrue(AccountActions.accountTransactionActions().getRowCount() > 0,
                        "Warehouse items count is incorrect!");

        logInfo("Step 5: Look through the list of the transactions and make sure that there are no transactions \n" +
                "were Effective Date is less than or equal to Current Date");
        AccountActions.accountTransactionActions().verifyTransactionListWithACH(userCredentials,
                WebAdminActions.loginActions().getSystemDate(), false, accountNumber);

        logInfo("Step 6: Remove the value from the 'Show Transactions from' drop-down search filter and click [Apply Filter] button");
        AccountActions.accountTransactionActions().clearFilterRegion();
        AccountActions.accountTransactionActions().verifyTransactionList(WebAdminActions.loginActions().getSystemDate(), true);
    }
}