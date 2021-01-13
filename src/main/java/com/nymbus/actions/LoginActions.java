package com.nymbus.actions;

import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.pages.Pages;
import org.testng.Assert;

public class LoginActions {

    public void doLogin(String userName, String password) {
        Pages.loginPage().waitForLoginForm();
        Pages.loginPage().typeUserName(userName);
        Pages.loginPage().typePassword(password);
        Pages.loginPage().clickEnterButton();
        Assert.assertFalse(Pages.loginPage().isErrorsVisibleOnLoginForm(),
                "Error messages is visible");
        Pages.loginPage().waitForLoginFormNotVisible();
        Pages.clientsSearchPage().waitForAddNewClientButton();
        Pages.aSideMenuPage().waitForASideMenu();
        Assert.assertTrue(Pages.aSideMenuPage().isClientPageOpened(),
                "Client page is not opened");
    }

    public void doLogOut() {
        Pages.navigationPage().waitForUserMenuVisible();
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        if (!Pages.navigationPage().isSingOutButtonVisible())
            Pages.navigationPage().clickAccountButton();
        Pages.navigationPage().clickSignOut();
        Pages.loginPage().waitForLoginForm();
    }

    public void doLogOutWithSubmit() {
        Pages.navigationPage().waitForUserMenuVisible();
        if (!Pages.navigationPage().isSingOutButtonVisible())
            Pages.navigationPage().clickAccountButton();
        Pages.navigationPage().clickSignOut();
        Actions.transactionActions().confirmTransaction();
        Pages.loginPage().waitForLoginForm();
    }

    public void doLogOutProgrammatically() {
        doLogOut();
        SelenideTools.clearCookies();
        SelenideTools.refresh();
        SelenideTools.openUrlInNewWindow(Constants.URL);
        SelenideTools.switchToLastTab();
        SelenideTools.closeAllTabsExceptCurrent();
        Pages.loginPage().waitForLoginForm();
    }
}
