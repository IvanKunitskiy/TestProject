package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.cashier.CashierDefinedTransactions;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C22729_CDTWithoutTellerSessionCommitOfficialCheckFromCashInvalid extends BaseTest {

    @Test(description = "C22729, CDT without Teller Session - Commit official check from cash (invalid)")
    @Severity(SeverityLevel.CRITICAL)
    public void printTellerReceiptWithoutBalance() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.NOT_TELLER_USERNAME, Constants.NOT_TELLER_PASSWORD);

        logInfo("Step 2: Go to Cashier Defined Transactions page");
        Pages.aSideMenuPage().waitForASideMenu();
        Pages.aSideMenuPage().clickCashierDefinedTransactionsMenuItem();

        logInfo("Step 3: Search for template from preconditions and select it");
        Actions.cashierDefinedActions().setTellerOperation(CashierDefinedTransactions.OFFICIAL_CHECK_WITH_CASH.getOperation());
        Assert.assertTrue(Pages.confirmModalPage().checkAllertMessage(),"Alert message is not visible");

        logInfo("Step 4: Press [OK] button");
        Pages.confirmModalPage().clickOk();
        Assert.assertTrue(Pages.cashierPage().checkSourceAccountNumberDisabled(1),"Account number input is not disabled");
    }


}
