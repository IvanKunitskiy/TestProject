package com.nymbus.frontoffice.backoffice;

import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Backoffice")
@Feature("Account Analysis")
@Owner("Petro")
public class C22861_WeilandRtfChargeTest extends BaseTest {

    private String chkAccountNumber;

    @BeforeMethod
    public void preCondition() {

        SelenideTools.openUrl(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        int accAnalyzeWithRdcCodeAndAmountCount = WebAdminActions.webAdminUsersActions().getAccAnalyzeWithRdcCodeAndAmountCount();
        Assert.assertTrue(accAnalyzeWithRdcCodeAndAmountCount > 0, "There are no records found with RDC charge code");
        WebAdminActions.loginActions().doLogout();

        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up CHK  account
        Account chkAccount = new Account().setCHKAccountData();
        chkAccountNumber = chkAccount.getAccountNumber();

    }

    @Test(description = "C22815, Weiland: RDC charge")
    @Severity(SeverityLevel.CRITICAL)
    public void weilandRdcCharge() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Cashier Defined Transactions page");
        Pages.aSideMenuPage().clickCashierDefinedTransactionsMenuItem();

        logInfo("Step 3: Select CDT Template from the preconditions");

    }

}
