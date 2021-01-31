package com.nymbus.frontoffice.transactions;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.data.entity.CashDrawer;
import com.nymbus.data.entity.User;
import com.nymbus.data.entity.verifyingmodels.TellerSessionVerifyingModel;
import com.nymbus.newmodels.settings.UserSettings;
import com.nymbus.pages.Pages;
import com.nymbus.pages.webadmin.WebAdminPages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C23995_ProofDateLoginPopupTest extends BaseTest {
    private User user;

    @BeforeMethod
    public void prepareData() {
        // Set up user and cashDrawer
        user = new User().setDefaultUserData();
        CashDrawer cashDrawer = new CashDrawer().setDefaultTellerValues();

        // Create user
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.usersActions().createUser(user);
        Actions.loginActions().doLogOut();

        // Set password for new user on webAdmin page
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        WebAdminActions.webAdminUsersActions().setUserPassword(user);

        // Set additional data for cash drawer
        user.setTeller(true);
        cashDrawer.setDefaultUser(user.getFirstName() + " " + user.getLastName());
        cashDrawer.setBranch(user.getBranch());
        cashDrawer.setLocation(user.getLocation());
        user.setCashDrawer(cashDrawer);

        // Get teller session data for new user
        TellerSessionVerifyingModel verifyingModel = WebAdminActions.webAdminUsersActions().getTellerSessionDate(user.getLoginID());
        WebAdminActions.loginActions().doLogoutProgrammatically();

        // Login, create cash drawer and logout
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());
        Actions.cashDrawerAction().createCashDrawer(cashDrawer);
        Actions.loginActions().doLogOut();

        // Verifying preconditions (1 is expected value for CFMIntegrationEnabled)
        Assert.assertEquals(verifyingModel.getCFMIntegrationEnabled(), "CFMIntegrationEnabled",
                        "Bank control file setting CFMIntegrationEnabled name doesn't match!");
        Assert.assertFalse(verifyingModel.isUserSessionExist(), "Teller session is active for current user!");
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 23995, testRunName = TEST_RUN_NAME)
    @Test(description = "C23995, Teller: Proof Date Login popup")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyProofDateLoginPopup() {
        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());
        UserSettings userSettings = Actions.usersActions().getUserSettings();

        logInfo("Step 2: Go to Teller page");
        Actions.transactionActions().goToTellerPage();
        Assert.assertTrue(Pages.tellerModalPage().isModalWindowVisible(), "Proof date login popup isn't opened automatically!");

        logInfo("Step 3: Look at the displayed proof date login popup");
        Assert.assertTrue(Pages.tellerModalPage().isBlankTellerFieldVisible(), "Teller field isn't blank!");
        Assert.assertEquals(Pages.tellerModalPage().getLocation(), userSettings.getLocation(), "Location field is incorrect!");
        Assert.assertEquals(Pages.tellerModalPage().getCashDrawerName(), userSettings.getCashDrawer(), "CashDrawer field is incorrect!");
        Assert.assertEquals(Pages.tellerModalPage().getProofDateValue(), WebAdminActions.loginActions().getSystemDate(), "System date is incorrect!");

        logInfo("Step 4: Сlick [Enter] button");
        Actions.transactionActions().doLoginTeller();
        Assert.assertFalse(Pages.tellerModalPage().isModalWindowVisible(), "Proof date login popup is visible");
        Assert.assertEquals(Pages.tellerPage().getCashDrawerNameInFooter(), userSettings.getCashDrawer(), "Cash drawer name is incorrect!");

        // Navigate to bank.session.teller.parameters
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminUsersActions().goToTellerSessionUrl(user.getLoginID());

        Assert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().isSearchResultTableExist(),
                "Teller session is not active for " + user.getLoginID() + " user!");
    }
}