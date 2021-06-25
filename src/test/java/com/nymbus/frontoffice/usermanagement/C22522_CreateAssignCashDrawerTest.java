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
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
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

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Actions.usersActions().createUser(user);

        SettingsPage.viewUserPage().waitViewUserDataVisible();

        Assert.assertEquals(Actions.usersActions().getUserFromUserViewPage(), user,
                "User's data isn't the same");

        Actions.loginActions().doLogOut();

        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        WebAdminActions.webAdminUsersActions().setUserPassword(user);
    }

    private final String TEST_RUN_NAME = "User Management";

    @TestRailIssue(issueID = 22522, testRunName = TEST_RUN_NAME)
    @Test(description = "C22522, Create & assign Cash Drawer from User profile page")
    public void verifyCashDrawerCreationAssignFromUserPage() {

        logInfo("Step 1: Log in to the system as the User from the precondition");
        Selenide.open(Constants.URL);

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Settings, search for the User from the precondition2 and open his profile in Edit mode");
        logInfo("Step 3: Switch the 'Teller' switcher to YES");
        logInfo("Step 4: Click [Add New] button next to the 'Cash Drawer' field");
        logInfo("Step 5: Fill in Drawer Name with any value, select any Cash Drawer type (e.g. Teller), search for any GL account and click [Add] button");
        logInfo("Step 6: Click [Save Changes] button");
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

        logInfo("Step 7: Go back to the Settings page and search for the newly created Cash Drawer in 'Cash Drawer' widget");
        Actions.cashDrawerAction().searchCashDrawerOnCashDrawerSearchPage(user.getCashDrawer());

        Assert.assertEquals(Actions.cashDrawerAction().getCashDrawerFromCashDrawerViewPage(), user.getCashDrawer(),
                "Cash Drawer's data isn't the same");

        logInfo("Step 8: Perform log out from the users sub-menu in the header at the top-right corner of the screen");
        Actions.loginActions().doLogOut();

        logInfo("Step 9: Log in to the system as the User from the Step2");
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());

        logInfo("Step 10: Go to the Teller page and pay attention to the Proof Date Login popup");
        Pages.aSideMenuPage().clickTellerMenuItem();
        Assert.assertTrue(Pages.aSideMenuPage().isTellerPageOpened(),
                "Teller page is not opened");

        Pages.tellerPage().waitModalWindow();

        Assert.assertEquals(Pages.tellerPage().getCashDrawerName(), cashDrawer.getName(),
                "Cash Drawer name isn't correct");

    }
}