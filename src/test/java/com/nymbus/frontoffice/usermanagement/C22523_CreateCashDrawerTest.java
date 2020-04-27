package com.nymbus.frontoffice.usermanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.data.entity.CashDrawer;
import com.nymbus.data.entity.User;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("User Management")
@Owner("Petro")
public class C22523_CreateCashDrawerTest extends BaseTest {

    private User user;
    private CashDrawer cashDrawer;

    @BeforeMethod
    public void prepareUserData() {
        user = new User().setDefaultUserData();
        cashDrawer = new CashDrawer().setDefaultTellerValues();
    }

    @Test(description = "C22523, Create Cash Drawer from Cash Drawers dashboard + assign to existing user")
    public void verifyCashDrawerCreationAssignFromCashDrawerPage() {

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        prepareUser();

        cashDrawer.setDefaultUser(user.getFirstName() + " " + user.getLastName());
        cashDrawer.setBranch(user.getBranch());
        cashDrawer.setLocation(user.getLocation());
        Actions.cashDrawerAction().createCashDrawer(cashDrawer);
        SettingsPage.viewCashDrawerPage().waitViewCashDrawerDataVisible();
        Assert.assertEquals(Actions.cashDrawerAction().getCashDrawerFromCashDrawerViewPage(), cashDrawer,
                "Cash Drawer's data isn't the same");

        Actions.cashDrawerAction().searchCashDrawerOnCashDrawerSearchPage(cashDrawer);

        Assert.assertEquals(Actions.cashDrawerAction().getCashDrawerFromCashDrawerViewPage(), cashDrawer,
                "Cash Drawer's data isn't the same");

        Actions.usersActions().searUserOnCustomerSearchPage(user);

        Assert.assertEquals(SettingsPage.viewUserPage().getCashDrawerValue(),
                cashDrawer.getName() + " (Default User: " + user.getFirstName() + " " + user.getLastName() + ")",
                "Cash Drawer value is not correct");
        user.setTeller(true);
        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());

        Pages.aSideMenuPage().clickTellerMenuItem();
        Assert.assertTrue(Pages.aSideMenuPage().isTellerPageOpened(),
                "Teller page is not opened");

        Pages.tellerPage().waitModalWindow();

        Assert.assertEquals(Pages.tellerPage().getCashDrawerName(), cashDrawer.getName(),
                "Cash Drawer name isn't correct");
    }

    private void prepareUser() {
        Actions.usersActions().createUser(user);
        SettingsPage.viewUserPage().waitViewUserDataVisible();

        Assert.assertEquals(Actions.usersActions().getUserFromUserViewPage(), user,
                "User's data isn't the same");
        Actions.loginActions().doLogOut();

        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        WebAdminActions.webAdminUsersActions().setUserPassword(user);

        Selenide.open(Constants.URL);

        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());

        Pages.navigationPage().waitForUserMenuVisible();
        Pages.navigationPage().clickAccountButton();

        Assert.assertTrue(Pages.navigationPage().getUserFullName().contains(user.getFirstName()),
                "User's first name is not visible. Found: " + Pages.navigationPage().getUserFullName());

        Assert.assertTrue(Pages.navigationPage().getUserFullName().contains(user.getLastName()),
                "User's last name is not visible. Found: " + Pages.navigationPage().getUserFullName());
    }
}