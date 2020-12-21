package com.nymbus.actions.webadmin;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Generator;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.data.entity.User;
import com.nymbus.data.entity.verifyingmodels.TellerSessionVerifyingModel;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.basicinformation.type.ClientType;
import com.nymbus.newmodels.client.verifyingmodels.FirstNameAndLastNameModel;
import com.nymbus.newmodels.notice.Notice;
import com.nymbus.newmodels.transaction.nontellertransactions.JSONData;
import com.nymbus.newmodels.transaction.verifyingModels.WebAdminTransactionFromQuery;
import com.nymbus.pages.webadmin.WebAdminPages;
import org.testng.Assert;

import java.util.Map;
import java.util.Random;

public class WebAdminUsersActions {

    private String getTellerSessionUrl(String userId) {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev6DS&"
                + "dqlQuery=count%3A+20%0D%0A"
                + "from%3A+bank.session.teller.parameters%0D%0A"
                + "where%3A+%0D%0A-+.userid->Code%3A+"
                +  userId
                + "+%23user%27s+login%0D%0AorderBy%3A+-id&source=";
    }

    private String getSecurityOfacEntryUrl(String individualType) {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev6DS&"
                + "dqlQuery=count%3A+100%0D%0A"
                + "from%3A+security.ofac.entry%0D%0A"
                + "where%3A+%0D%0A"
                + "-+.sdntype->name%3A+"
                +  individualType
                + "&source=";
    }

    private String getBankControlFileUrl() {
        return Constants.WEB_ADMIN_URL
                + "rulesui2.sbs.bcfile.ct?"
                + "formName=bank.data.bcfile&filter_(databean)code=CFMIntegrationEnable";
    }

    private String getAccountsWithYtdInterestPaidNotNullUrl() {
        return Constants.WEB_ADMIN_URL
            + "RulesUIQuery.ct?"
            + "waDbName=nymbusdev6DS&"
            + "dqlQuery=count%3A+10%0D%0Aselect%3A+accountid%2C+interestpaidytd%0D%0A"
            + "from%3A+bank.data.actchecking%0D%0A"
            + "where%3A+%0D%0A"
            + "-+interestpaidytd%3A+%7Bgreater%3A+0%7D%0D%0A"
            + "-+.accountid-%3Edateclosed%3A+%7Bnull%7D%0D%0A"
            + "formats%3A+%0D%0A"
            + "-+-%3Ebank.data.actmst%3A+%24%7Baccountnumber%7D%0D%0A"
            + "orderBy%3A+-id&source=";
    }

    private String getAccountsWithInterestPaidLastYearNotNullUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev6DS&"
                + "dqlQuery=count%3A+10%0D%0Aselect%3A+accountid%2C+interestpaidlastyear%0D%0A"
                + "from%3A+bank.data.actchecking%0D%0A"
                + "where%3A+%0D%0A"
                + "-+interestpaidlastyear%3A+%7Bgreater%3A+0%7D%0D%0A"
                + "-+.accountid-%3Edateclosed%3A+%7Bnull%7D%0D%0Aformats%3A+%0D%0A"
                + "-+-%3Ebank.data.actmst%3A+%24%7Baccountnumber%7D%0D%0A"
                + "orderBy%3A+-id&source=";
    }

    private String getAccountsWithYtdTaxesWithheldNotNullUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev6DS&"
                + "dqlQuery=count%3A+10%0D%0Aselect%3A+accountid%2C+taxeswithheldytd%0D%0A"
                + "from%3A+bank.data.actchecking%0D%0A"
                + "where%3A+%0D%0A"
                + "-+taxeswithheldytd%3A+%7Bgreater%3A+0%7D%0D%0A"
                + "-+.accountid-%3Edateclosed%3A+%7Bnull%7D%0D%0Aformats%3A+%0D%0A"
                + "-+-%3Ebank.data.actmst%3A+%24%7Baccountnumber%7D%0D%0A"
                + "orderBy%3A+-id&source=";
    }

    private String getAccountsWithOverdraftChargedOffMoreThanZeroUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev6DS&"
                + "dqlQuery=count%3A+10%0D%0Aselect%3A+accountid%2C+overdraftchargedoff%0D%0A"
                + "from%3A+bank.data.actchecking%0D%0A"
                + "where%3A+%0D%0A"
                + "-+overdraftchargedoff%3A+%7Bgreater%3A+0%7D%0D%0A"
                + "-+.accountid-%3Edateclosed%3A+%7Bnull%7D%0D%0Aformats%3A+%0D%0A"
                + "-+-%3Ebank.data.actmst%3A+%24%7Baccountnumber%7D%0D%0A"
                + "orderBy%3A+-id&source=";
    }

    private String getAccountsWithDormantStatusUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev6DS&"
                + "dqlQuery=count%3A+10%0D%0Aselect%3A+accountnumber%2C+accountstatus%2C+currentbalance%0D%0A"
                + "from%3A+bank.data.actmst%0D%0A"
                + "where%3A+%0D%0A"
                + "-+accountstatus%3A+D%0D%0A"
                + "-+.accounttype-%3Ecode%3A+%5Btatyp1.1%2C+tatyp1.2%5D%0D%0A%0D%0"
                + "AorderBy%3A+-id&source=";
    }

    private String getPrintBalanceOnReceiptUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev6DS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "from%3A+bank.data.bcfile%0D%0A"
                + "where%3A+%0D%0A"
                + "-+code%3A+PrintBalanceOnReceipt%0D%0A&source=";
    }

    private String getNoticesWithDifferentTypesUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev12DS&"
                + "dqlQuery=count%3A+200%0D%0A"
                + "select%3A+accountid%2C+accounttype%2C+bcdate%2C+templateid%0D%0A"
                + "from%3A+bank.data.cifext%0D%0A"
                + "where%3A+%0D%0A-+accountid%3A+%7Bnot+null%7D%0D%0AorderBy%3A+-id%0D%0A%0D%0A"
                + "formats%3A+%0D%0A-+-%3Ebank.data.notice%3A+%24%7Bdescription%7D%0D%0A-+-%3Ebank.data.actmst%3A+%24%7Baccountnumber%7D%0D%0A"
                + "extra%3A+%0D%0A-+%24bankbranch%3A+.accountid-%3Ebankbranch%0D%0A"
                + "orderBy%3A+-id&source=";
    }

    private String getInterestChecksUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev12DS&"
                + "dqlQuery=count%3A+200%0D%0A"
                + "select%3A+accountid%2C+accounttype%2C+bcdate%2C+templateid%0D%0A"
                + "from%3A+bank.data.cifext%0D%0A"
                + "where%3A+%0D%0A-+.templateid-%3Ecode%3A+form35%0D%0A%0D%0A"
                + "-+accountid%3A+%7Bnot+null%7D%0D%0A"
                + "orderBy%3A+-id%0D%0A%0D%0A"
                + "formats%3A+%0D%0A"
                + "-+-%3Ebank.data.notice%3A+%24%7Bdescription%7D%0D%0A"
                + "-+-%3Ebank.data.actmst%3A+%24%7Baccountnumber%7D%0D%0A"
                + "extra%3A+%0D%0A-+%24bankbranch%3A+.accountid-%3Ebankbranch%0D%0A"
                + "orderBy%3A+-id&source=";
    }

    private String getOfficialCheckControlNumberUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev12DS&"
                + "dqlQuery=count%3A+100%0D%0A"
                + "select%3A+checktype%2C+checkingaccountnumber%0D%0A"
                + "from%3A+bank.data.officialcheck.control%0D%0A"
                + "where%3A+%0D%0A-+.checktype-%3Ename%3A+Interest+Check%0D%0A"
                + "formats%3A+%0D%0A-+-%3Ebank.data.actmst%3A+%24%7Baccountnumber%7D%0D%0A"
                + "orderBy%3A+-id&source=";
    }

    public String getOfficialCheckControlNumber() {
        return getOfficialCheckControlNumberFromQueryByUrl(getOfficialCheckControlNumberUrl());
    }

    public String getAccountNumberWithInterestCheck(int index) {
        return getAccountWithCheckByIndexFromQueryByUrl(getInterestChecksUrl(), 1);
    }

    private String getAccAnalyzeWithRdcCodeAndAmountUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev12DS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "from%3A+bank.data.accode%0D%0A"
                + "where%3A+%0D%0A"
                + "-+chargecode%3A+RDC%0D%0A&source=";
    }

    private String getRemoteDepositReturnEFTDescriptionUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev12DS&"
                + "dqlQuery=count%3A+30%0D%0A"
                + "from%3A+bank.data.bcfile%0D%0A"
                + "where%3A+%0D%0A-+code%3A+RemoteDepositReturnEFTDescription%0D%0A&source=";
    }

    private String getCdtTemplateWithMiscDebitCommittedFromChkOnGlAccountUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev12DS&"
                + "dqlQuery=count%3A+100%0D%0A"
                + "from%3A+bank.data.cdtfrm%0D%0A"
                + "where%3A+%0D%0A"
                + "-+.debitaccounttype-%3Ename%3A+CHK+Account%0D%0A"
                + "-+.creditaccounttype-%3Ename%3A+g%2Fl+tickets%0D%0A"
                + "-+.debittrancode-%3Ename%3A+Debit+Memo%0D%0A"
                + "-+operationcode%3A+%7Bnull%7D%0D%0A"
                + "-+.credittrancode-%3Ename%3A+G%2FL+Credit%0D%0A"
                + "-+creditdescription%3A+%7Bnull%7D%0D%0A"
                + "-+debitdescription%3A+%7Bnull%7D&source=";
    }

    private String getCheckTransactionUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=coreDS&dql"
                + "Query=count%3A+10%0D%0A"
                + "select%3A+effectiveentrydate%2C+accountnumber%2C+amount%2C+itemtype%2C+uniqueeftdescription%2C+checknumber%0D%0A"
                + "from%3A+bank.data.transaction.item%0D%0A"
                + "where%3A+%0D%0A-+.transactioncode-%3Ecode%3A+128%0D%0A-+rejectstatus%3A+%7Bnull%7D%0D%0A"
                + "extra%3A+%0D%0A-+%24bankbranch%3A+.accountnumber-%3Ebankbranch%0D%0A"
                + "formats%3A+%0D%0A-+-%3Ebank.data.actmst%3A+%24%7Baccountnumber%7D&source=";
    }

    private String getAtmTransactionUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=coreDS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "select%3A+effectiveentrydate%2C+accountnumber%2C+amount%2C+itemtype%2C+uniqueeftdescription%2C+transactionheaderid%2C+transactioncode%0D%0A"
                + "from%3A+bank.data.transaction.item%0D%0A"
                + "where%3A+%0D%0A"
                + "-+.transactioncode-%3Ename%3A+ATM+Deposit%0D%0A"
                + "-+.transactionheaderid-%3E.transactionsource-%3Ename%3A+ATM%0D%0A"
                + "-+rejectstatus%3A+%7Bnull%7D%0D%0Aextra%3A+%0D%0A"
                + "-+%24bankbranch%3A+.accountnumber"
                + "-%3Ebankbranch%0D%0A"
                + "formats%3A+%0D%0A-+-%3"
                + "Ebank.data.actmst%3A+%24%7Baccountnumber%7D&source=";
    }

    private String getTransactionsCommittedOnCurrentDateUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=coreDS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "select%3A+effectiveentrydate%2C+accountnumber%2C+amount%2C+itemtype%2C+uniqueeftdescription%2C+transactionheaderid%2C+transactioncode%0D%0A"
                + "from%3A+bank.data.transaction.item%0D%0A"
                + "where%3A+%0D%0A-+effectiveentrydate%3A+%28currentday%29%0D%0A-+rejectstatus%3A+%7Bnull%7D%0D%0A"
                + "extra%3A+%0D%0A-+%24bankbranch%3A+.accountnumber-%3Ebankbranch%0D%0A"
                + "formats%3A+%0D%0A-+-%3Ebank.data.actmst%3A+%24%7Baccountnumber%7D&source=";
    }

    public boolean isCdtTemplateCommittedFromChkOnGlAccountCreated(String templateName) {
        return isCdtTemplateCommittedFromChkOnGlAccountCreatedFromQueryByUrl(
                getCdtTemplateWithMiscDebitCommittedFromChkOnGlAccountUrl(), templateName);
    }

    public String getRemoteDepositReturnEFTDescription() {
        return getRemoteDepositReturnEFTDescriptionFromQueryByUrl(getRemoteDepositReturnEFTDescriptionUrl());
    }

    public int getAccAnalyzeWithRdcCodeAndAmountCount() {
        return getAccAnalyzeWithRdcCodeCountFromQueryByUrl(getAccAnalyzeWithRdcCodeAndAmountUrl());
    }

    public String getAccountWithDormantStatus(int index) {
        return getDormantAccountByIndexFromQueryByUrl(getAccountsWithDormantStatusUrl(), index);
    }

    public String getAccountsWithOverdraftChargedOffMoreThanZeroNull() {
        return getAccountFromQueryByUrl(getAccountsWithOverdraftChargedOffMoreThanZeroUrl());
    }

    public String getAccountWithYtdTaxesWithheldNotNull() {
        return getAccountFromQueryByUrl(getAccountsWithYtdTaxesWithheldNotNullUrl());
    }

    public String getAccountWithInterestPaidLastYearNotNull() {
        return getAccountFromQueryByUrl(getAccountsWithInterestPaidLastYearNotNullUrl());
    }

    public String getAccountWithYtdInterestPaidNotNull() {
        return getAccountFromQueryByUrl(getAccountsWithYtdInterestPaidNotNullUrl());
    }

    public int getPrintBalanceOnReceiptValue() {
        return getPrintBalanceOnReceiptValueFromQueryByUrl(getPrintBalanceOnReceiptUrl());
    }

    public WebAdminTransactionFromQuery getCheckTransaction() {
        return getCheckTransactionFromQueryByUrl(getCheckTransactionUrl());
    }

    public WebAdminTransactionFromQuery getAtmTransaction() {
        return getAtmTransactionFromQueryByUrl(getAtmTransactionUrl());
    }

    public WebAdminTransactionFromQuery getTransactionCommittedOnCurrentDate() {
        return getTransactionCommittedOnCurrentDateQueryByUrl(getTransactionsCommittedOnCurrentDateUrl());
    }

    private int getPrintBalanceOnReceiptValueFromQueryByUrl(String url) {
        SelenideTools.openUrlInNewWindow(url);

        SelenideTools.switchTo().window(1);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();

        WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();
        String printBalanceOnReceiptCode = WebAdminPages.rulesUIQueryAnalyzerPage().getPrintBalanceOnReceiptByIndex(1);

        WebAdminActions.loginActions().doLogoutProgrammatically();
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);

        return Integer.parseInt(printBalanceOnReceiptCode);
    }

    private String getAccountFromQueryByUrl(String url) {
        SelenideTools.openUrlInNewWindow(url);

        SelenideTools.switchTo().window(1);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();

        int amountOfRecordsFound = WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfFoundRecords();
        String accountNumber = "";

        if (amountOfRecordsFound > 0) {
            WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();
            int index = (new Random().nextInt(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult())) + 1;
            accountNumber = WebAdminPages.rulesUIQueryAnalyzerPage().getAccountNumberByIndex(index);
        }

        WebAdminActions.loginActions().doLogoutProgrammatically();
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);

        return accountNumber;
    }


    private String getDormantAccountByIndexFromQueryByUrl(String url, int index) {
        SelenideTools.openUrlInNewWindow(url);

        SelenideTools.switchTo().window(1);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();

        int amountOfRecordsFound = WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfFoundRecords();
        String accountNumber = "";

        if (amountOfRecordsFound > 0) {
            WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();
            accountNumber = WebAdminPages.rulesUIQueryAnalyzerPage().getDormantAccountNumberByIndex(index);
        }

        WebAdminActions.loginActions().doLogoutProgrammatically();
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);

        return accountNumber;
    }

    public FirstNameAndLastNameModel getExistingIndividualClient() {
        FirstNameAndLastNameModel existingUser = new FirstNameAndLastNameModel();
        SelenideTools.openUrl(getSecurityOfacEntryUrl(ClientType.INDIVIDUAL.getClientType()));

        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();

        int index = (new Random().nextInt(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult())) + 1;
        existingUser.setFirstName(WebAdminPages.rulesUIQueryAnalyzerPage().getFirstNameByIndex(index));
        existingUser.setLastName(WebAdminPages.rulesUIQueryAnalyzerPage().getLastNameByIndex(index));

        return existingUser;
    }

    public TellerSessionVerifyingModel getTellerSessionDate(String userId) {
        TellerSessionVerifyingModel verifyingModel = new TellerSessionVerifyingModel();

        SelenideTools.openUrl(getBankControlFileUrl());
        verifyingModel.setCFMIntegrationEnabled(WebAdminPages.rulesUIQueryAnalyzerPage().getBankControlFileNameFieldValue());
        int bcfValue = Integer.parseInt(WebAdminPages.rulesUIQueryAnalyzerPage().getBankControlFileValue());
        verifyingModel.setCFMIntegrationEnabledSettingValue(bcfValue);

        SelenideTools.openUrl(getTellerSessionUrl(userId));
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();
        verifyingModel.setUserSessionExist(WebAdminPages.rulesUIQueryAnalyzerPage().isSearchResultTableExist());

        return verifyingModel;
    }

    public void goToTellerSessionUrl(String userId) {
        SelenideTools.openUrl(getTellerSessionUrl(userId));
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();
    }

    public Notice getRandomNoticeData() {
        Notice notice = new Notice();

        SelenideTools.openUrl(getNoticesWithDifferentTypesUrl());
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();

        int numberOfSearchResult = WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult();
        int showCount = 200;
        int bound = Math.min(numberOfSearchResult, showCount);
        int bankBranchAndDateRandomIndex = getRandomIndex(bound);

        notice.setBankBranch(WebAdminPages.rulesUIQueryAnalyzerPage().getNoticeBankBranchValue(bankBranchAndDateRandomIndex));
        notice.setDate(WebAdminPages.rulesUIQueryAnalyzerPage().getNoticeDateValue(bankBranchAndDateRandomIndex));
        notice.setAccountNumber(WebAdminPages.rulesUIQueryAnalyzerPage().getNoticeAccountIdValue(bound));
        notice.setSubType(WebAdminPages.rulesUIQueryAnalyzerPage().getNoticeAccountTypeValue(bound));

        return notice;
    }

    public String getAccountWithCheckByIndexFromQueryByUrl(String url, int index) {

        SelenideTools.openUrl(url);
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();

        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "There are no search results found on query");

        return WebAdminPages.rulesUIQueryAnalyzerPage().getAccountNumberWithCheckValueByIndex(index);
    }

    private String getOfficialCheckControlNumberFromQueryByUrl(String url) {

        SelenideTools.openUrl(url);
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();

        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "There are no search results found on query");

        return WebAdminPages.rulesUIQueryAnalyzerPage().getOfficialCheckControlNumber();
    }

    private String getRemoteDepositReturnEFTDescriptionFromQueryByUrl(String url) {

        SelenideTools.openUrl(url);
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();

        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "There are no search results found on query");

        return WebAdminPages.rulesUIQueryAnalyzerPage().getRemoteDepositReturnEFTDescription();
    }

    private boolean isCdtTemplateCommittedFromChkOnGlAccountCreatedFromQueryByUrl(String url, String templateName) {

        SelenideTools.openUrl(url);
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();

        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "There are no search results found on query");

        return WebAdminPages.rulesUIQueryAnalyzerPage().isCdtTemplateCommittedFromChkOnGlAccountCreated(templateName);
    }

    public int getAccAnalyzeWithRdcCodeCountFromQueryByUrl(String url) {
        SelenideTools.openUrl(url);
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();

        return WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult();
    }

    public WebAdminTransactionFromQuery getCheckTransactionFromQueryByUrl(String url) {
        WebAdminTransactionFromQuery transaction = new WebAdminTransactionFromQuery();
        int tmpIndex = 1;

        SelenideTools.openUrl(url);
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();

        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "There are no 'teller 128-check -> deposit' transactions");

        transaction.setBankBranch(WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionBankBranchValueByIndex(tmpIndex));
        transaction.setAccountNumber(WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionAccountNumberByIndex(tmpIndex));
        String trAmount = WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionAmountValueByIndex(tmpIndex);
        System.out.println("-----------> " + trAmount);
        transaction.setAmount(Double.parseDouble(trAmount));
        String date = WebAdminPages.rulesUIQueryAnalyzerPage().getEffectiveDate(tmpIndex);
        transaction.setEffectiveEntryDate(DateTime.getDateWithFormat(date, "yyyy-MM-dd", "MM/dd/yyyy"));
        transaction.setCheckNumber(WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionCheckNumberValueByIndex(tmpIndex));
        transaction.setUniqueEftDescription(WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionDescriptionValueByIndex(tmpIndex));

        return transaction;
    }

    public WebAdminTransactionFromQuery getAtmTransactionFromQueryByUrl(String url) {
        WebAdminTransactionFromQuery transaction = new WebAdminTransactionFromQuery();
        int tmpIndex = 1;

        SelenideTools.openUrl(url);
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();

        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "There are no 'atm deposit' transactions");

        transaction.setBankBranch(WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionBankBranchValueByIndex(tmpIndex));
        transaction.setAccountNumber(WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionAccountNumberByIndex(tmpIndex));
        String trAmount = WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionAmountValueByIndex(tmpIndex);
        System.out.println("-----------> " + trAmount);
        transaction.setAmount(Double.parseDouble(trAmount));
        String date = WebAdminPages.rulesUIQueryAnalyzerPage().getEffectiveDate(tmpIndex);
        transaction.setEffectiveEntryDate(DateTime.getDateWithFormat(date, "yyyy-MM-dd", "MM-dd-yyyy"));
        transaction.setTransactionCode(WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionCodeValueByIndex(tmpIndex));
        transaction.setTransactionHeaderId(WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionHeaderIdValue(tmpIndex));
        transaction.setUniqueEftDescription(WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionDescriptionValueByIndex(tmpIndex));

        return transaction;
    }

    public WebAdminTransactionFromQuery getTransactionCommittedOnCurrentDateQueryByUrl(String url) {
        WebAdminTransactionFromQuery transaction = new WebAdminTransactionFromQuery();
        int tmpIndex = 1;

        SelenideTools.openUrl(url);
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();

        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult() > 0,
                "There are no 'atm deposit' transactions");

        transaction.setBankBranch(WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionBankBranchValueByIndex(tmpIndex));
        transaction.setAccountNumber(WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionAccountNumberByIndex(tmpIndex));
        String trAmount = WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionAmountValueByIndex(tmpIndex);
        transaction.setAmount(Double.parseDouble(trAmount));
        String date = WebAdminPages.rulesUIQueryAnalyzerPage().getEffectiveDate(tmpIndex);
        transaction.setEffectiveEntryDate(DateTime.getDateWithFormat(date, "yyyy-MM-dd", "MM-dd-yyyy"));
        transaction.setItemType(WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionItemTypeValueByIndex(tmpIndex));
        transaction.setTransactionCode(WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionCodeValueByIndex(tmpIndex));
        transaction.setTransactionHeaderId(WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionHeaderIdValue(tmpIndex));
        transaction.setUniqueEftDescription(WebAdminPages.rulesUIQueryAnalyzerPage().getTransactionDescriptionValueByIndex(tmpIndex));

        return transaction;
    }

    private int getRandomIndex(int bound) {
        return new Random().nextInt(bound) + 1;
    }

    public void setUserPassword(User user) {
        WebAdminPages.navigationPage().waitForPageLoaded();
        WebAdminPages.navigationPage().clickUsersAndSecurityItem();
        WebAdminPages.navigationPage().clickUsersItem();
        WebAdminPages.usersPage().waitUsersRegion();
        WebAdminPages.usersPage().setValueToUserField(user.getLoginID());
        WebAdminPages.usersPage().clickSearchButton();
        WebAdminPages.usersPage().waitForUserListRegion();
        user.setPassword(Generator.genString(6));
        WebAdminPages.usersPage().setNewPassword(user.getPassword());
        WebAdminPages.usersPage().setConfirmPassword(user.getPassword());
        WebAdminPages.usersPage().clickSaveButton();
    }

    public void setDormantAccount(String rootID, Account account) {
        WebAdminPages.navigationPage().waitForPageLoaded();
        WebAdminPages.navigationPage().clickAccountsTransactionsItem();
        WebAdminPages.navigationPage().clickAccountsItem();
        WebAdminPages.accountsPage().setValueToRootIDField(rootID);
        WebAdminPages.accountsPage().waitForRootIDLink(account.getAccountNumber());
        WebAdminPages.accountsPage().clickRootIDLink(account.getAccountNumber());
        WebAdminPages.accountsPage().setAccountStatus("D");
        WebAdminPages.accountsPage().clickSaveChangesButton();
    }

    public void goToDRLCaches() {
        WebAdminPages.navigationPage().waitForPageLoaded();
        WebAdminPages.navigationPage().clickAssetsItem();
        WebAdminPages.navigationPage().clickDRLCachesItem();
        WebAdminPages.drlCachesPage().waitForPageLoaded();
    }


    public void goToCashValues(int modalWindow, String clientRootId) {
        WebAdminPages.drlCachesPage().typeToSearchInput(modalWindow, clientRootId);
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        WebAdminPages.drlCachesPage().clickValueItemByModalAndClientRootId(modalWindow, clientRootId);
    }

    public void setFieldsMapWithValues(int modal, Map<String, String> fieldsMap) {
        String data = WebAdminPages.drlCachesPage().getCashValuesData(modal);
        JSONData.setFieldsMap(data, fieldsMap);
    }
}