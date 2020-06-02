package com.nymbus.frontoffice.transactions;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.data.entity.User;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C26519_ProofDateLoginPopupNoCdButOmTest extends BaseTest {
    private User user;

    @BeforeMethod
    public void prepareData() {
        // Set up user and cashDrawer
        user = new User().setDefaultUserData();

        // Create user
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.usersActions().createUser(user);
        Actions.loginActions().doLogOut();

        // Set password for new user on webAdmin page
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminUsersActions().setUserPassword(user);
        WebAdminActions.loginActions().doLogoutProgrammatically();
        Selenide.open(Constants.URL);
    }

    @Test(description = "C26519, Proof Date Login popup (no CD but OM)")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyProofDateLoginPopup() {
        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(user.getLoginID(), user.getPassword());

        logInfo("Step 2: Go to Teller page");
        Actions.transactionActions().goToTellerPage();
        Assert.assertTrue(Pages.aSideMenuPage().isTellerPageOpened(), "Teller page is not opened!");
        Assert.assertFalse(Pages.tellerModalPage().isModalWindowVisible(), "Proof date login popup is visible!");
    }
}