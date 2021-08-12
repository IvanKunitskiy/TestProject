package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.newmodels.transaction.verifyingModels.WebTransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22604_SearchFilterTransactionHistoryItemsToWork extends BaseTest {
    private String number;
    private String effectiveDate;
    private String amount;

    @BeforeMethod
    public void preCondition() {
        WebTransactionData transactionInfo = WebAdminActions.webAdminTransactionActions().getTransactionInfo(userCredentials);
        number = transactionInfo.getNumber();
        amount = transactionInfo.getAmount() + "";
        effectiveDate = transactionInfo.getEffectiveDate();
    }

    private final String TEST_RUN_NAME = "Deposit Accounts Management";

    @TestRailIssue(issueID = 22604, testRunName = TEST_RUN_NAME)
    @Test(description = "C22604, Search / filter transaction history: Items to Work")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyWarehouseTransactions() {
        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the account from the precondition and open it on Transactions tab");
        Actions.clientPageActions().searchAndOpenClientByName(number);
        AccountActions.retrievingAccountData().goToTransactionsTab();

        logInfo("Step 3: Look through the list of transactions and make sure that rejected transactions are not " +
                "displayed in transactions list by default");


        logInfo("Step 4: Click 'Show Transactions from' drop-down search filter and select 'Items to Work'\n" +
                "Click [Apply Filter] button");
        AccountActions.accountTransactionActions().applyFilterByTransactionFromFiled("Items to Work");
        int rejectedTransactionsItems = Pages.accountTransactionPage().getRejectedTransactionsItems();
        Assert.assertTrue(rejectedTransactionsItems > 0,
                "Transaction is not visible");

        logInfo("Step 5: Remove the value from the 'Show Transactions from' drop-down search filter " +
                "and click [Apply Filter] button");
        AccountActions.accountTransactionActions().clearFilterRegion();

        logInfo("Step 6: Go to Backoffice-> Items to Work page");
        Pages.aSideMenuPage().clickBackOfficeMenuItem();
        Pages.backOfficePage().clickItemToWork();

        logInfo("Step 7: Search for the rejected transaction from the precondition by its status ('Rejected'), " +
                "Proof Date, Reason Rejected ('Bad Code')\n" +
                "open it on Details");
        Actions.backOfficeActions().clickToRejectTransaction(number, effectiveDate);

        logInfo("Step 8: Click [Edit]button and select valid trancode for account (106 trancode for " +
                "CHK account) and click [Save Changes] button, Confirm Create Swap popup");
        Actions.backOfficeActions().changeTransactionCode();

        logInfo("Step 9: Open account for which Transaction was paid on Items to Work");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenClientByName(number);
        AccountActions.retrievingAccountData().goToTransactionsTab();

        logInfo("Step 10: Click 'Show Transactions from' drop-down search filter and select 'Items to Work'\n" +
                "Click [Apply Filter] button");
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        AccountActions.accountTransactionActions().applyFilterByTransactionFromFiled("Items to Work");

        logInfo("Step 11: \t\n" +
                "Look through the list of the rejected transaction and make sure that transaction that was paid on " +
                "Items to work is not present in the list of rejected transactions");
        Actions.clientPageActions().checkRejectedItems(rejectedTransactionsItems);

        logInfo("Step 12: Remove the value from the 'Show Transactions from' drop-down search filter and click [Apply Filter] button");
        AccountActions.accountTransactionActions().clearFilterRegion();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithBalanceSymbol();
        Assert.assertEquals(actualTransactionData.getAmount(), Double.parseDouble(amount), "Amount not equals");
        String replace = actualTransactionData.getEffectiveDate().replace('/', '-');
        replace = replace.substring(6) + "-" + replace.substring(0,5);
        Assert.assertEquals(replace, effectiveDate, "Date not equals");
    }


}
