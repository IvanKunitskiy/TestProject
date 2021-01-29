package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.pages.webadmin.WebAdminPages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Date;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Dmytro")
public class C22604_SearchFilterTransactionHistoryItemsToWork extends BaseTest {
    private String number;
    private String effectiveDate;
    private String amount;

    @BeforeMethod
    public void preCondition() {

        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL+
                "RulesUIQuery.ct?waDbName=nymbusdev12DS&dqlQuery=count%3A+10%0D%0Aselect%3A+accountnumber%2C+effectiveentrydate%2C+rejectstatus%2C+amount%2C+transactionheaderid%2C+transactioncode%2C+accounttype%0D%0Afrom%3A+bank.data.transaction.item%0D%0Awhere%3A+%0D%0A-+accountnumber%3A%0D%0A+++not+exists%3A+%0D%0A++++++from%3A+bank.data.sphold%0D%0A++++++where%3A+%0D%0A++++++-+recordcode->code%3A+%5Btpcode.2%2C+tpcode.4%2C+tpcode.6%2C+tpcode.7%5D%0D%0A++++++-+accountid%3A+%28field%29%28%2F%29%2Faccountnumber%0D%0A-+.accounttype->code%3A+%5Btatyp1.1%2C+tatyp1.2%5D%0D%0A-+.accountnumber->dateclosed%3A+%7Bnull%7D%0D%0A-+.accountnumber->currentbalance%3A+%7Bgreater%3A+0%7D%0D%0A-+effectiveentrydate%3A+%7Ble%3A+%28currentday%29%7D%0D%0A-+.rejectstatus->name%3A+Rejected%0D%0A-+amount%3A+%7Bgreater%3A+0%7D%0D%0A-+accountnumber%3A+%7Bnot+null%7D%0D%0A-+.rootid->%5Btransactionitemid%5Dbank.data.transaction.rejectreentry.item%5Breasonrejected%5D->%5Brootid%5Dbank.data.descriptor%5Bname%5D%3A+bad+code%0D%0A-+.transactionheaderid->.transactionsource->name%3A+%5BACH%2C+Bill+pay%5D%0D%0AorderBy%3A+-id%0D%0Aextra%3A+%0D%0A-+%24CurBal%3A+.accountnumber->currentbalance%0D%0Aformats%3A+%0D%0A-+->bank.data.actmst%3A+%24%7Baccountnumber%7D&source=");
        SelenideTools.switchToLastTab();
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        number = WebAdminPages.rulesUIQueryAnalyzerPage().getAccountNumberSecByIndex(1);
        amount = WebAdminPages.rulesUIQueryAnalyzerPage().getAmount(1);
        String balance = WebAdminPages.rulesUIQueryAnalyzerPage().getBalanceByIndex(1);
        effectiveDate = WebAdminPages.rulesUIQueryAnalyzerPage().getEffectiveDate(1);
        Assert.assertTrue(Double.parseDouble(amount)<Double.parseDouble(balance),"Balance is less then amount");
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();
    }

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
        Assert.assertTrue(rejectedTransactionsItems>0,
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
        Actions.backOfficeActions().clickToRejectTransaction(number,effectiveDate);

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
        System.out.println(rejectedTransactionsItems);
        if (rejectedTransactionsItems==1){
            Assert.assertTrue(Pages.accountTransactionPage().isNoResultsVisible(),"Transaction is not visible");
        }else {
            int rejectedTransactionsItems1 = Pages.accountTransactionPage().getRejectedTransactionsItems();
            System.out.println(rejectedTransactionsItems1);
            Assert.assertTrue(rejectedTransactionsItems1 < rejectedTransactionsItems,
                    "Transaction is not visible");
        }
        logInfo("Step 12: Remove the value from the 'Show Transactions from' drop-down search filter and click [Apply Filter] button");
        AccountActions.accountTransactionActions().clearFilterRegion();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithBalanceSymbol();
        Assert.assertEquals(actualTransactionData.getAmount(), Double.parseDouble(amount), "Amount not equals");
        Assert.assertEquals(Date.valueOf(actualTransactionData.getEffectiveDate()).toString(), effectiveDate, "Date not equals");



    }


}
