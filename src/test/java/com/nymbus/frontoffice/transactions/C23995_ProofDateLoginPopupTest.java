package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.data.entity.verifyingmodels.TellerSessionVerifyingModel;
import com.nymbus.newmodels.settings.UserSettings;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C23995_ProofDateLoginPopupTest extends BaseTest {

    @BeforeMethod
    public void verifyWebAdminTellerSession() {
        TellerSessionVerifyingModel verifyingModel = WebAdminActions.webAdminUsersActions().getTellerSessionDate(Constants.USERNAME);
        Assert.assertEquals(verifyingModel.getCFMIntegrationEnabled(), "CFMIntegrationEnabled",
                        "Bank control file setting CFMIntegrationEnabled name doesn't match!");
        Assert.assertEquals(verifyingModel.getCFMIntegrationEnabledSettingValue(), 0,
                "Bank control file setting CFMIntegrationEnabled value doesn't match!");
        Assert.assertFalse(verifyingModel.isUserSessionExist(), "Teller session is active for current user!");
    }

    @Test(description = "C23995, Teller: Proof Date Login popup")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyProofDateLoginPopup() {
        logInfo("Step 1: Log in to the system as the User from the precondition");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
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
    }
}