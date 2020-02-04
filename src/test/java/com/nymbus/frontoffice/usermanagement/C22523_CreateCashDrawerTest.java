package com.nymbus.frontoffice.usermanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.base.BaseTest;
import com.nymbus.models.CashDrawer;
import com.nymbus.models.User;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import com.nymbus.util.Constants;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("User Management")
public class C22523_CreateCashDrawerTest extends BaseTest {

    private User user;
    private CashDrawer cashDrawer;

    @BeforeMethod
    public void prepareUserData(){
        user = new User().setDefaultUserData();
        cashDrawer = new CashDrawer().setDefaultTellerValues();
    }

    @Test(description = "C22523, Create Cash Drawer from Cash Drawers dashboard + assign to existing user")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyUserCreation() {
        navigateToUrl(Constants.URL);

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        prepareUser();

        cashDrawer.setDefaultUser(user.getFirstName() + " " + user.getLastName());
        cashDrawer.setBranch(user.getBranch());
        cashDrawer.setLocation(user.getLocation());
        Actions.cashDrawerAction().createCashDrawer(cashDrawer);
        SettingsPage.viewCashDrawerPage().waitViewCashDrawerDataVisible();
        Assert.assertEquals(Actions.cashDrawerAction().getCashDrawerFromCashDrawerViewPage(), cashDrawer,
                "Cash Drawer's data isn't the same");

        Actions.cashDrawerAction().searCashDrawerOnCashDrawerSearchPage(cashDrawer);

        Assert.assertEquals(Actions.cashDrawerAction().getCashDrawerFromCashDrawerViewPage(), cashDrawer,
                "Cash Drawer's data isn't the same");

        Actions.usersActions().searUserOnCustomerSearchPage(user);

        Assert.assertEquals(SettingsPage.viewUserPage().getCashDrawerValue(),
                cashDrawer.getName() + " (Default User: " + user.getFirstName() + " " + user.getLastName() + ")",
                "Cash Drawer value is not correct");
        user.setTeller(true);
        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());

        Pages.aSideMenuPage().clickTellerToTellerMenuItem();
        Assert.assertTrue(Pages.aSideMenuPage().isTellerToTellerPageOpened(),
                "Teller To Teller Transfer page is not opened");

        Pages.tellerToTellerPage().waitModalWindow();

        Assert.assertEquals(Pages.tellerToTellerPage().getCashDrawerName(), cashDrawer.getName(),
                "Cash Drawer name isn't correct");
    }

    private void prepareUser(){
        Actions.usersActions().createUser(user);
        SettingsPage.viewUserPage().waitViewUserDataVisible();

        Assert.assertEquals(Actions.usersActions().getUserFromUserViewPage(), user,
                "User's data isn't the same");
        Actions.loginActions().doLogOut();

        navigateToUrl(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        WebAdminActions.webAdminUsersActions().setUserPassword(user);

        navigateToUrl(Constants.URL);

        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());

        Pages.navigationPage().waitForUserMenuVisible();
        Pages.navigationPage().clickAccountButton();

        Assert.assertTrue(Pages.navigationPage().getUserFullName().contains(user.getFirstName()),
                "User's first name is not visible. Found: " + Pages.navigationPage().getUserFullName());

        Assert.assertTrue(Pages.navigationPage().getUserFullName().contains(user.getLastName()),
                "User's last name is not visible. Found: " + Pages.navigationPage().getUserFullName());
    }
}
