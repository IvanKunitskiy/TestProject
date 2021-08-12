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
public class C22523_CreateCashDrawerTest extends BaseTest {

    private User user;
    private CashDrawer cashDrawer;

    @BeforeMethod
    public void prepareUserData() {
        user = new User().setDefaultUserData();
        cashDrawer = new CashDrawer().setDefaultTellerValues();
    }

    private final String TEST_RUN_NAME = "User Management";

    @TestRailIssue(issueID = 22523, testRunName = TEST_RUN_NAME)
    @Test(description = "C22523, Create Cash Drawer from Cash Drawers dashboard + assign to existing user")
    public void verifyCashDrawerCreationAssignFromCashDrawerPage() {
        logInfo("Step 1: Log in to the system as the User1 from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        prepareUser();

        logInfo("Step 2: Go to Settings and click [Add New] button in 'Cash Drawer' widget");
        logInfo("Step 3: Fill in all fields for the new Cash Drawer:\n" +
                "- Name - any\n" +
                "- Cash Drawer type - Teller\n" +
                "- Default User - User2 from the preconditions\n" +
                "- Branch - the same branch as it is set for User2 from the preconditions\n" +
                "- Location - the same location as it is set for User2 from the preconditions\n" +
                "- GL Account Number - any\n" +
                "click [Save Changes] button");
        cashDrawer.setDefaultUser(user.getFirstName() + " " + user.getLastName());
        cashDrawer.setBranch(user.getBranch());
        cashDrawer.setLocation(user.getLocation());
        Actions.cashDrawerAction().createCashDrawer(cashDrawer);
        SettingsPage.viewCashDrawerPage().waitViewCashDrawerDataVisible();
        Assert.assertEquals(Actions.cashDrawerAction().getCashDrawerFromCashDrawerViewPage(), cashDrawer,
                "Cash Drawer's data isn't the same");

        logInfo("Step 4: Go to the Cash Drawers page and search for the newly created Cash Drawer");
        Actions.cashDrawerAction().searchCashDrawerOnCashDrawerSearchPage(cashDrawer);

        Assert.assertEquals(Actions.cashDrawerAction().getCashDrawerFromCashDrawerViewPage(), cashDrawer,
                "Cash Drawer's data isn't the same");

        logInfo("Step 5: Go back to Settings and search for the User2 that was assigned to the Cash Drawer in 'Users' widget, open User's profile");
        Actions.usersActions().searchUser(user);
        SettingsPage.usersSearchPage().clickCellByUserData(user.getEmail());

        logInfo("Step 6: Pay attention to the Cash Drawer value");
        SettingsPage.viewUserPage().waitForCashDrawerFieldValue();
        Assert.assertEquals(SettingsPage.viewUserPage().getCashDrawerValue(),
                cashDrawer.getName() + " (Default User: " + user.getFirstName() + " " + user.getLastName() + ")",
                "Cash Drawer value is not correct");
        user.setTeller(true);

        logInfo("Step 7: Perform log out from the users sub-menu in the header at the top-right corner of the screen");
        Actions.loginActions().doLogOut();

        logInfo("Step 8: Log in to the system as the User2 that was assigned to the Cash Drawer");
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());

        logInfo("Step 9: Go to the Teller page and pay attention to the Proof Date Login popup");
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