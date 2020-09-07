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
public class C22522_CreateAssignCashDrawerTest extends BaseTest {

    private User user;
    private CashDrawer cashDrawer;

    @BeforeMethod
    public void prepareUserData() {
        user = new User().setDefaultUserData();
        cashDrawer = new CashDrawer().setDefaultTellerValues();
    }

    @Test(description = "C22522, Create & assign Cash Drawer from User profile page")
    public void verifyCashDrawerCreationAssignFromUserPage() {

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Actions.usersActions().createUser(user);

        SettingsPage.viewUserPage().waitViewUserDataVisible();

        Assert.assertEquals(Actions.usersActions().getUserFromUserViewPage(), user,
                "User's data isn't the same");

        Actions.loginActions().doLogOut();

        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        WebAdminActions.webAdminUsersActions().setUserPassword(user);

        Selenide.open(Constants.URL);

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Actions.usersActions().searUserOnCustomerSearchPage(user);
        cashDrawer.setDefaultUser(user.getFirstName() + " " + user.getLastName());
        cashDrawer.setBranch(user.getBranch());
        cashDrawer.setLocation(user.getLocation());
        user.setTeller(true);
        user.setCashDrawer(cashDrawer);

        Actions.usersActions().editUser(user);

        Assert.assertEquals(Actions.usersActions().getUserFromUserViewPage(), user,
                "User's data isn't the same");

        SettingsPage.viewUserPage().waitForCashDrawerFieldValue();
        Assert.assertEquals(SettingsPage.viewUserPage().getCashDrawerValue(),
                user.getCashDrawer().getName() + " (Default User: " + user.getFirstName() + " " + user.getLastName() + ")",
                "Cash Drawer data isn't the same");

        Actions.cashDrawerAction().searchCashDrawerOnCashDrawerSearchPage(user.getCashDrawer());

        Assert.assertEquals(Actions.cashDrawerAction().getCashDrawerFromCashDrawerViewPage(), user.getCashDrawer(),
                "Cash Drawer's data isn't the same");

        Actions.loginActions().doLogOut();

        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());

        Pages.aSideMenuPage().clickTellerMenuItem();
        Assert.assertTrue(Pages.aSideMenuPage().isTellerPageOpened(),
                "Teller page is not opened");

        Pages.tellerPage().waitModalWindow();

        Assert.assertEquals(Pages.tellerPage().getCashDrawerName(), cashDrawer.getName(),
                "Cash Drawer name isn't correct");

    }
}