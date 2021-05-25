package com.nymbus.actions.webadmin;

import com.codeborne.selenide.Selenide;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.UserCredentials;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.PaymentDueData;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.newmodels.transaction.verifyingModels.WebTransactionData;
import com.nymbus.pages.webadmin.WebAdminPages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class WebAdminTransactionActions {
    private String getCurrentEnvironment() {
        return System.getProperty("domain", "dev6");
    }

    private String getForeignATMFeeUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbus"
                + getCurrentEnvironment()
                + "DS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "select%3A+%28databean%29CODE%2C+double%0D%0A"
                + "from%3A+bank.data.bcfile%0D%0A"
                + "where%3A+%0D%0A-+code%3A+ForeignATMFee%0D%0A&source=";
    }

    private String getForeignATMFeeBalanceInquiryUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbus"
                + getCurrentEnvironment()
                + "DS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "select%3A+%28databean%29CODE%2C+double%0D%0A"
                + "from%3A+bank.data.bcfile%0D%0A"
                + "where%3A+%0D%0A-+code%3A+ForeignATMFeeBalanceInquiry%0D%0A&source=";
    }

    private String getTerminalIdUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbus"
                + getCurrentEnvironment()
                + "DS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "select%3A+terminalid%0D%0A"
                + "from%3A+bank.data.datmlc%0D%0A"
                + "orderBy%3A+-id%0D%0A&source=";
    }

    private String getWarehouseTransactionsUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbus"
                + getCurrentEnvironment()
                + "DS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "from%3A+bank.data.transaction.item%0D%0A"
                + "where%3A+%0D%0A-+effectiveentrydate%3A"
                + "+%7Bgreater%3A+%28currentday%29%7D%0D%0A-+rejectstatus%3A+%7Bnull%7D%0D%0A"
                + "-+amount%3A+%7Bgreater%3A+0%7D%0D%0A-+accountnumber%3A+%7Bnot+null%7D%0D%0A"
                + "orderBy%3A+-id%0D%0A%0D%0A"
                + "formats%3A+%0D%0A-+-%3Ebank.data.actmst%3A"
                + "+%24%7Baccountnumber%7D%0D%0A&source=";
    }

    private String getTransactionUrl(String accountNumber) {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev6DS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "from%3A+bank.data.transaction.item%0D%0A"
                + "where%3A+%0D%0A-+.accountnumber->accountnumber%3A+"
                + accountNumber
                + "%0D%0AorderBy%3A+-id%0D%0A&source=";
    }

    private String getTransactionUrl(String accountNumber, String transactionCode) {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev6DS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "from%3A+bank.data.transaction.item%0D%0A"
                + "where%3A+%0D%0A-+.accountnumber->accountnumber%3A+"
                + accountNumber
                + "%0D%0A-+.transactioncode->code%3A+"
                + transactionCode
                + "%0D%0AorderBy%3A+-id%0D%0A&source=";
    }

    private String getGLInterfaceUrl(String transactionNumber) {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev6DS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "from%3A+bank.data.gl.interface%0D%0A"
                + "where%3A+%0D%0A+-+parenttransaction%3A+"
                + transactionNumber
                + "%0D%0A&source=";
    }

    private String getGLInterfaceUrlWithDeletedItems(String transactionNumber) {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev6DS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "from%3A+bank.data.gl.interface%0D%0A"
                + "where%3A+%0D%0A+-+parenttransaction%3A+"
                + transactionNumber
                + "%0D%0AdeletedIncluded%3A+true&source=";
    }

    private String getWaiveATUsageFeeAcronymUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev12DS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "select%3A+%28databean%29CODE%2C+string%0D%0A"
                + "from%3A+bank.data.bcfile%0D%0A"
                + "where%3A+%0D%0A"
                + "-+code%3A+WaiveATUsageFeeAcronym&source=";
    }

    private String getTransactionHeaderUrl(String transactionHeader) {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev12DS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "from%3A+bank.data.transaction.header%0D%0A"
                + "where%3A+%0D%0A-+%28databean%29rootId%3A+"
                + transactionHeader
                + "&source=";
    }

    private String getTransactionHeaderByRootUrl(String rootId) {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=coreDS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "from%3A+bank.data.transaction.header%0D%0A"
                + "where%3A+%0D%0A-+rootid%3A+"
                + rootId  //%0D%0A
                + "&source=";
    }

    public void goToWaiveATUsageFeeAcronymUrl() {
        Selenide.open(getWaiveATUsageFeeAcronymUrl());

        waitForSearchResults();
    }

    public void goToForeignFeeUrl() {
        Selenide.open(getForeignATMFeeUrl());

        waitForSearchResults();
    }

    public void goToForeignATMFeeBalanceInquiryUrl() {
        Selenide.open(getForeignATMFeeBalanceInquiryUrl());

        waitForSearchResults();
    }

    public void goToTerminalIdUrl() {
        Selenide.open(getTerminalIdUrl());

        waitForSearchResults();
    }

    public void goToWarehouseTransactionsUrl() {
        Selenide.open(getWarehouseTransactionsUrl());

        waitForSearchResults();
    }

    public void goToTransactionUrl(String accountNumber) {
        Selenide.open(getTransactionUrl(accountNumber));

        waitForSearchResults();
    }

    public void goToTransactionHeaderUrl(String transactionHeader) {
        Selenide.open(getTransactionHeaderUrl(transactionHeader));

        waitForSearchResults();
    }

    public void goToTransactionHeaderByRootUrl(String root) {
        Selenide.open(getTransactionHeaderByRootUrl(root));

        waitForSearchResults();
    }

    public void goToTransactionUrl(String accountNumber, String transactionCode) {
        Selenide.open(getTransactionUrl(accountNumber, transactionCode));

        waitForSearchResults();
    }

    public void goToGLInterface(String transactionHeader) {
        Selenide.open(getGLInterfaceUrl(transactionHeader));

        waitForSearchResults();
    }

    public void goToGLInterfaceWithDeletedItems(String transactionHeader) {
        Selenide.open(getGLInterfaceUrlWithDeletedItems(transactionHeader));

        waitForSearchResults();
    }

    private void waitForSearchResults() {
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();

        WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();
    }

    public void verifyDeletedRecords() {
        int recordsCount = WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResultInterfaceTable();
        Assert.assertTrue(recordsCount > 0, "Transaction items doesn't find!");
        SoftAssert softAssert = new SoftAssert();
        String empty = "";
        for (int i = 1; i <= recordsCount; i++) {
            softAssert.assertNotEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getDeletedWhenValue(i), empty,
                    "Deleted When field is blank!");
            softAssert.assertNotEquals(WebAdminPages.rulesUIQueryAnalyzerPage().getDeletedBy(i), empty,
                    "Deleted By field is blank!");
        }
        softAssert.assertAll();
    }

    public void setTransactionPostDateAndEffectiveDate(TransactionData transactionData, String accountNumber, String transactionCode) {
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        goToTransactionUrl(accountNumber, transactionCode);
        String effectiveDate = DateTime.getLocalDateWithFormatPlusDays(WebAdminPages.rulesUIQueryAnalyzerPage().getEffectiveDate(1), "yyyy-MM-dd", "MM/dd/yyyy", 0);
        String postingDate = DateTime.getLocalDateWithFormatPlusDays(WebAdminPages.rulesUIQueryAnalyzerPage().getDatePosted(2), "yyyy-MM-dd", "MM/dd/yyyy", 0);
        transactionData.setEffectiveDate(effectiveDate);
        transactionData.setPostingDate(postingDate);
        WebAdminActions.loginActions().doLogoutProgrammatically();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();
    }

    public String getTransactionNumber(UserCredentials userCredentials, Account account) {
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        goToTransactionUrl(account.getAccountNumber());
        String transactionHeader = WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionHeaderIdValue(2);
        goToTransactionHeaderUrl(transactionHeader);
        String transactionNumber = WebAdminPages.rulesUIQueryAnalyzerPage().getNameValue(2);
        WebAdminActions.loginActions().doLogoutProgrammatically();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();

        return transactionNumber;
    }

    public double getInterestEarned(UserCredentials userCredentials, Account account) {
        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL +
                "RulesUIQuery.ct?waDbName=coreDS&dqlQuery=count%3A+10%0D%0Aselect%3A+%28databean%29CREATEDBY%2C+%28databean%29CREATEDWHEN%2C+accountid%2C+interestearned%0D%0Afrom%3A+bank.data.actloan%0D%0Awhere%3A+%0D%0A-+.accountid->accountnumber%3A+" +
                account.getAccountNumber() + "%0D%0AdeletedIncluded%3A+true&source=");
        SelenideTools.switchToLastTab();
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        String transactionNumber = WebAdminPages.rulesUIQueryAnalyzerPage().getInterestEarnedValueByIndex(1);
        WebAdminActions.loginActions().doLogoutProgrammatically();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();

        return Double.parseDouble(transactionNumber);
    }

    public PaymentDueData checkPaymentDue(UserCredentials userCredentials, Account account) {
        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL +
                "RulesUIQuery.ct?waDbName=coreDS&dqlQuery=count%3A+10%0D%0A%23select%3A+accountid%2C+duedate%2C+principal%2C+interest%2C+escrow%2C+amount%2C+dateassessed%2C+paymentduetype%2C+paymentduestatus%0D%0Afrom%3A+bank.data.paymentdue%0D%0Awhere%3A+%0D%0A-+.accountid->accountnumber%3A+" +
                account.getAccountNumber() + "%0D%0AorderBy%3A+-id%0D%0AdeletedIncluded%3A+true&source=");
        SelenideTools.switchToLastTab();
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        PaymentDueData paymentDueData = new PaymentDueData();
        paymentDueData.setAccountId(Integer.parseInt(WebAdminPages.rulesUIQueryAnalyzerPage().getAccountIdByIndex(1)));

        paymentDueData.setDueDate(DateTime.getDateWithFormat(WebAdminPages.rulesUIQueryAnalyzerPage().getDueDateByIndex(1),
                "yyyy-mm-dd", "mm/dd/yyyy"));
        paymentDueData.setPrincipal(Double.parseDouble(WebAdminPages.rulesUIQueryAnalyzerPage().getPrincipalValue(2)));
        paymentDueData.setInterest(String.format("%.2f", Double.parseDouble(WebAdminPages.rulesUIQueryAnalyzerPage().getInterest(2))));
        paymentDueData.setEscrow(Double.parseDouble(WebAdminPages.rulesUIQueryAnalyzerPage().getEscrowByIndex(1)));
        paymentDueData.setAmount(Double.parseDouble(WebAdminPages.rulesUIQueryAnalyzerPage().getAmount(2)));
        paymentDueData.setDateAssessed(DateTime.getDateWithFormat(WebAdminPages.rulesUIQueryAnalyzerPage().getDateAssessedByIndex(1),
                "yyyy-mm-dd", "mm/dd/yyyy"));
        paymentDueData.setPaymentDueType(WebAdminPages.rulesUIQueryAnalyzerPage().getPaymentDueTypeByIndex(1));
        paymentDueData.setPaymentDueStatus(WebAdminPages.rulesUIQueryAnalyzerPage().getPaymentDueStatusByIndex(1));
        WebAdminActions.loginActions().doLogoutProgrammatically();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();
        return paymentDueData;
    }

    public PaymentDueData checkPaymentDuePrinAndInt(UserCredentials userCredentials, Account account) {
        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL +
                "RulesUIQuery.ct?waDbName=coreDS&dqlQuery=count%3A+10%0D%0A%23select%3A+accountid%2C+duedate%2C+principal%2C+interest%2C+escrow%2C+amount%2C+dateassessed%2C+paymentduetype%2C+paymentduestatus%0D%0Afrom%3A+bank.data.paymentdue%0D%0Awhere%3A+%0D%0A-+.accountid->accountnumber%3A+" +
                account.getAccountNumber() + "%0D%0AorderBy%3A+-id%0D%0AdeletedIncluded%3A+true&source=");
        SelenideTools.switchToLastTab();
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        PaymentDueData paymentDueData = new PaymentDueData();
        paymentDueData.setAccountId(Integer.parseInt(WebAdminPages.rulesUIQueryAnalyzerPage().getAccountIdByIndex(1)));

        paymentDueData.setDueDate(DateTime.getDateWithFormat(WebAdminPages.rulesUIQueryAnalyzerPage().getDueDateByIndex(1),
                "yyyy-mm-dd", "mm/dd/yyyy"));
        paymentDueData.setPrincipal(0.00);
        paymentDueData.setInterest("0.00");
        paymentDueData.setEscrow(Double.parseDouble(WebAdminPages.rulesUIQueryAnalyzerPage().getEscrowByIndex(1)));
        paymentDueData.setAmount(Double.parseDouble(WebAdminPages.rulesUIQueryAnalyzerPage().getAmount(2)));
        paymentDueData.setDateAssessed(DateTime.getDateWithFormat(WebAdminPages.rulesUIQueryAnalyzerPage().getDateAssessedByIndex(1),
                "yyyy-mm-dd", "mm/dd/yyyy"));
        paymentDueData.setPaymentDueType(WebAdminPages.rulesUIQueryAnalyzerPage().getPaymentDueTypeByIndex(1));
        paymentDueData.setPaymentDueStatus(WebAdminPages.rulesUIQueryAnalyzerPage().getPaymentDueStatusByIndex(1));
        WebAdminActions.loginActions().doLogoutProgrammatically();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();
        return paymentDueData;
    }

    public void checkErrorPrincipalNextPaymentDate(UserCredentials userCredentials, Account account) {
        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL);
        SelenideTools.switchToLastTab();
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        String principalNextDate = WebAdminActions.webAdminUsersActions().getPrincipalNextPaymentDate(account.getAccountNumber());
        principalNextDate = DateTime.getDateWithFormat(principalNextDate, "yyyy-MM-dd", "MM/dd/yyyy");
        String dateExpected = DateTime.getDatePlusMonth(account.getDateOpened(),2);
        TestRailAssert.assertTrue(principalNextDate.equals(dateExpected),
                new CustomStepResult("'principalnextpaymentdate' is not valid","'principalnextpaymentdate' is valid" ));
    }

    public WebTransactionData getTransactionInfo(UserCredentials userCredentials) {
        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL +
                "RulesUIQuery.ct?waDbName=coreDS&dqlQuery=count%3A+100%0D%0Aselect%3A+accountnumber%2C+effectiveentrydate%2C+rejectstatus%2C+amount%2C+transactionheaderid%2C+transactioncode%2C+accounttype%0D%0Afrom%3A+bank.data.transaction.item%0D%0Awhere%3A+%0D%0A-+accountnumber%3A%0D%0A+++not+exists%3A+%0D%0A++++++from%3A+bank.data.sphold%0D%0A++++++where%3A+%0D%0A++++++-+.recordcode->code%3A+%5Btpcode.2%2C+tpcode.4%2C+tpcode.6%2C+tpcode.7%5D%0D%0A++++++-+accountid%3A+%28field%29%28%2F%29%2Faccountnumber%0D%0A-+.accounttype->code%3A+%5Btatyp1.1%2C+tatyp1.2%5D%0D%0A-+.accountnumber->dateclosed%3A+%7Bnull%7D%0D%0A-+.accountnumber->currentbalance%3A+%7Bgreater%3A+0%7D%0D%0A-+effectiveentrydate%3A+%7Ble%3A+%28currentday%29%7D%0D%0A-+.rejectstatus->name%3A+Rejected%0D%0A-+amount%3A+%7Bgreater%3A+0%7D%0D%0A-+accountnumber%3A+%7Bnot+null%7D%0D%0A-+.rootid->%5Btransactionitemid%5Dbank.data.transaction.rejectreentry.item%5Breasonrejected%5D->%5Brootid%5Dbank.data.descriptor%5Bname%5D%3A+bad+code%0D%0A-+.transactionheaderid->.transactionsource->name%3A+%5BACH%2C+Bill+pay%5D%0D%0A%0D%0AorderBy%3A+-effectiveentrydate%0D%0Aextra%3A+%0D%0A-+%24CurBal%3A+.accountnumber->currentbalance%0D%0A-+%24DateOpened%3A+.accountnumber->dateopened%0D%0Aformats%3A+%0D%0A-+->bank.data.actmst%3A+%24%7Baccountnumber%7D&source=");
        SelenideTools.switchToLastTab();
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        int index = 2;

        WebTransactionData webTransactionData = new WebTransactionData();
        webTransactionData.setNumber(WebAdminPages.rulesUIQueryAnalyzerPage().getAccountNumberSecByIndex(index));
        webTransactionData.setAmount(Double.parseDouble(WebAdminPages.rulesUIQueryAnalyzerPage().getAmount(index)));
        String balance = WebAdminPages.rulesUIQueryAnalyzerPage().getBalanceByIndex(index);
        webTransactionData.setEffectiveDate(WebAdminPages.rulesUIQueryAnalyzerPage().getEffectiveDate(index));
        String dateOpened = WebAdminPages.rulesUIQueryAnalyzerPage().getDateOpened(index);
        while (webTransactionData.getAmount() > Double.parseDouble(balance) ||
                DateTime.isDateAfter(dateOpened, webTransactionData.getEffectiveDate(), "yyyy-MM-dd")) {
            index++;
            webTransactionData.setNumber(WebAdminPages.rulesUIQueryAnalyzerPage().getAccountNumberSecByIndex(index));
            webTransactionData.setAmount(Double.parseDouble(WebAdminPages.rulesUIQueryAnalyzerPage().getAmount(index)));
            balance = WebAdminPages.rulesUIQueryAnalyzerPage().getBalanceByIndex(index);
            webTransactionData.setEffectiveDate(WebAdminPages.rulesUIQueryAnalyzerPage().getEffectiveDate(index));
            dateOpened = WebAdminPages.rulesUIQueryAnalyzerPage().getDateOpened(index);
        }
        Assert.assertTrue(webTransactionData.getAmount() < Double.parseDouble(balance), "Balance is less then amount");
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();
        return webTransactionData;
    }

    public String getTransactionStatusFromHeader(UserCredentials userCredentials, String clientRootId){
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        WebAdminActions.webAdminTransactionActions().goToTransactionHeaderByRootUrl(clientRootId);
        return WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionStatus(2);
    }

    public String getTransactionStatusFromHeader(UserCredentials userCredentials, Account account) {
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        goToTransactionUrl(account.getAccountNumber());
        String transactionHeader = WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionHeaderIdValue(2);
        goToTransactionHeaderUrl(transactionHeader);
        String transactionStatus = WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionStatus(2);
        WebAdminActions.loginActions().doLogoutProgrammatically();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();

        return transactionStatus;
    }
}