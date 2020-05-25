package com.nymbus.actions.webadmin;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.pages.webadmin.WebAdminPages;
import org.testng.Assert;

public class LoginActions {

    private  String systemDate;

    private String getDateUrl() {
        return Constants.WEB_ADMIN_URL
                + "RulesUIQuery.ct?"
                + "waDbName=nymbusdev6DS&"
                + "dqlQuery=count%3A+10%0D%0A"
                + "select%3A+date%2C+%28databean%29CODE%0D%0A"
                + "from%3A+bank.data.bcfile%0D%0A"
                + "where%3A+%0D%0A-+%28databean%29CODE%3A+DateFilesUpdatedThrough%0D%0A&source=";
    }

    private String getLogoutUrl() {
        return Constants.WEB_ADMIN_URL + "controller?action=LogoutAction";
    }

    public void doLogin(String userName, String password) {
        WebAdminPages.loginPage().waitForLoginForm();
        WebAdminPages.loginPage().typeUserName(userName);
        WebAdminPages.loginPage().typePassword(password);
        WebAdminPages.loginPage().clickGoButton();
        Assert.assertFalse(WebAdminPages.loginPage().isErrorMessageVisibleOnLoginForm(),
                "Error messages is visible");

        WebAdminPages.navigationPage().waitForPageLoaded();
    }

    public void doLogout() {
        WebAdminPages.navigationPage().clickLogoutMenu();
        WebAdminPages.navigationPage().waitForOptionUlVisibility();
        /*SelenideTools.sleep(2);*/
        WebAdminPages.navigationPage().clickSignOut();
        WebAdminPages.loginPage().waitForLoginForm();
    }

    public void doLogoutProgrammatically() {
        SelenideTools.openUrl(getLogoutUrl());
        WebAdminPages.loginPage().waitForLoginForm();
    }

    private String getSystemDateFromWebAdmin() {
        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL);
        SelenideTools.switchTo().window(1);
        doLogin(Constants.USERNAME, Constants.PASSWORD);
        SelenideTools.openUrl(getDateUrl());
        waitForSearchResults();
        String result = WebAdminPages.rulesUIQueryAnalyzerPage().getDateInSystem();
        doLogoutProgrammatically();
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);

        return result;
    }

    public void openWebAdminPageInNewWindow() {
        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL);
        SelenideTools.switchToLastTab();
    }


    private void waitForSearchResults() {
        WebAdminPages.rulesUIQueryAnalyzerPage().waitForPageLoad();

        WebAdminPages.rulesUIQueryAnalyzerPage().waitForSearchResultTable();
    }

    public String getSystemDate() {
        if (systemDate == null) {
            String systemDateFromWebAdmin = getSystemDateFromWebAdmin();
            systemDate = DateTime.getDateWithFormatPlusDays(systemDateFromWebAdmin, "yyyy-MM-dd", "MM/dd/yyyy", 1);
        }
        return systemDate;
    }
}