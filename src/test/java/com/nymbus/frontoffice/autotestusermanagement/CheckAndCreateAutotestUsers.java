package com.nymbus.frontoffice.autotestusermanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.data.entity.CashDrawer;
import com.nymbus.data.entity.User;
import com.nymbus.newmodels.UserCredentials;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Autotest User Management")
@Owner("Dmytro")
public class CheckAndCreateAutotestUsers extends BaseTest {
    private User user = new User();

    @BeforeMethod
    public void prepareData() {
        user = user.setDefaultUserData();
    }

    private final String TEST_RUN_NAME = "Autotest User Management";

    @TestRailIssue(issueID = 1, testRunName = TEST_RUN_NAME)
    @Test(description = "C1, Create User + Check Users")
    public void checkAndCreateUsers() {

        logInfo("Step 1: Search user");
        for (UserCredentials userCredentials : Constants.USERS) {
            Pages.loginPage().waitForLoginForm();
            Pages.loginPage().typeUserName(userCredentials.getUserName());
            Pages.loginPage().typePassword(userCredentials.getPassword());
            Pages.loginPage().clickEnterButton();
            SelenideTools.sleep(1);
            if (Pages.loginPage().isErrorsVisibleOnLoginForm()) {
                Pages.loginPage().clickEnterButton();
                String errorMessage = Pages.loginPage().getErrorMessage();
                if (errorMessage.equals("The user account has been disabled. Please contact your System Administrator to restore access.")) {
                    Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
                    user.setLoginID(userCredentials.getUserName());
                    user.setEmail(userCredentials.getUserName() + "@nymbus.com");
                    Actions.usersActions().searchUser(user);

                    SettingsPage.usersSearchPage().clickCellByUserData(user.getEmail());

                    user.setIsLoginDisabledFlag(false);

                    SettingsPage.viewUserPage().waitViewUserDataVisible();
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SettingsPage.viewUserPage().clickEditButton();

                    if (SettingsPage.addingUsersPage().isLoginDisabledOptionActivated()) {
                        SettingsPage.addingUsersPage().clickLoginDisabledToggle();
                    }

                    SettingsPage.addingUsersPage().clickSaveChangesButton();
                    Actions.loginActions().doLogOut();
                    Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());
                } else if (errorMessage.equals("Wrong User or Password")) {
                    SelenideTools.sleep(10);
                    Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
                    user = user.setAutotestUserData(userCredentials);
                    CashDrawer cashDrawer = new CashDrawer().setDefaultTellerValues();
                    cashDrawer.setDefaultUser(user.getFirstName() + " " + user.getLastName());
                    cashDrawer.setBranch(user.getBranch());
                    cashDrawer.setLocation(user.getLocation());
                    cashDrawer.setGlAccountNumber("0-0-Dummy");
                    user.setCashDrawer(cashDrawer);
                    Actions.usersActions().createUser(user);
                    Actions.loginActions().doLogOut();
                    Selenide.open(Constants.WEB_ADMIN_URL);
                    WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
                    WebAdminActions.webAdminUsersActions().setUserPasswordWithoutGen(user);
                    WebAdminActions.loginActions().doLogoutProgrammatically();
                    Selenide.open(Constants.URL);
                    Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());
                }
            }
            Pages.aSideMenuPage().clickCashDrawerMenuItem();
            Actions.transactionActions().doLoginTeller();
            double countedCashValue = Actions.cashDrawerAction().getCountedCashValueWithoutFormat();
            if (countedCashValue == 0.00) {
                Pages.cashDrawerBalancePage().clickEnterAmounts();
                Pages.cashDrawerBalancePage().setHundredsAmount(1000000.00);
                Pages.cashDrawerBalancePage().setFiftiesAmount(1000000.00);
                Pages.cashDrawerBalancePage().clickSave();
                SelenideTools.sleep(2);
            }
            Actions.loginActions().doLogOut();
        }
    }
}
