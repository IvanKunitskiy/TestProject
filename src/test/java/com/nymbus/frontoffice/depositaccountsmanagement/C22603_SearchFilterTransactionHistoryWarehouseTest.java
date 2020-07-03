package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.pages.webadmin.WebAdminPages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
    }
}