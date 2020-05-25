package com.nymbus.actions.webadmin;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Generator;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.data.entity.User;
import com.nymbus.data.entity.verifyingmodels.TellerSessionVerifyingModel;
import com.nymbus.newmodels.account.Account;
import com.nymbus.pages.webadmin.WebAdminPages;

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

    private String getBankControlFileUrl() {
        return Constants.WEB_ADMIN_URL
                + "rulesui2.sbs.bcfile.ct?"
                + "formName=bank.data.bcfile&filter_(databean)code=CFMIntegrationEnable";
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
}
