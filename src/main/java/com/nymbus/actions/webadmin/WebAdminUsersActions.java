package com.nymbus.actions.webadmin;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Generator;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.data.entity.User;
import com.nymbus.data.entity.verifyingmodels.TellerSessionVerifyingModel;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.basicinformation.type.ClientType;
import com.nymbus.newmodels.client.verifyingmodels.FirstNameAndLastNameModel;
import com.nymbus.newmodels.notice.Notice;
import com.nymbus.newmodels.transaction.nontellertransactions.JSONData;
import com.nymbus.pages.webadmin.WebAdminPages;

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

    private String getNoticesWithDifferentTypes() {
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

    private String getAccAnalyzeWithRdcCodeAndAmountUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev12DS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "from%3A+bank.data.accode%0D%0A"
                + "where%3A+%0D%0A"
                + "-+chargecode%3A+RDC%0D%0A&source=";
    }

    public int getAccAnalyzeWithRdcCodeAndAmountCount() {
        return getAccAnalyzeWithRdcCodeCount(getAccAnalyzeWithRdcCodeAndAmountUrl());
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

        SelenideTools.openUrl(getNoticesWithDifferentTypes());
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


    public int getAccAnalyzeWithRdcCodeCount(String url) {
        SelenideTools.openUrl(url);
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();

        return WebAdminPages.rulesUIQueryAnalyzerPage().getNumberOfSearchResult();
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