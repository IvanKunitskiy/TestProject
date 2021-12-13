package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Generator;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.UserCredentials;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C19469_CDT2TBetweenCashDrawersCashOut extends BaseTest {
    private String cashRecycler;
    private UserCredentials secondUserCredentials;
    private String amount = "100";
    private String cashOut;
    private String hundredsAmount;
    private String cashIn;
    private String hundredsAmountForSec;

    @BeforeMethod
    public void prepareTransactionData() {
        secondUserCredentials = Constants.USERS.pop();
        //Check CFMIntegrationEnabled
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        if (WebAdminActions.webAdminUsersActions().isCFMIntegrationEnabled()) {
            throw new SkipException("CFMIntegrationEnabled = 1");
        }
        cashRecycler = WebAdminActions.webAdminUsersActions().getCashRecyclerNameByRawIndex(1);
        WebAdminActions.loginActions().doLogout();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();

        //Get drawer balances
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        Actions.transactionActions().doLoginTeller();
        cashOut = Pages.cashDrawerBalancePage().getCashOut();
        hundredsAmount = Pages.cashDrawerBalancePage().getHundredsAmount();
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(secondUserCredentials.getUserName(), secondUserCredentials.getPassword());
        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        Actions.transactionActions().doLoginTeller();
        cashIn = Pages.cashDrawerBalancePage().getCashIn();
        hundredsAmountForSec = Pages.cashDrawerBalancePage().getHundredsAmount();
        Actions.loginActions().doLogOutProgrammatically();
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 19468, testRunName = TEST_RUN_NAME)
    @Test(description = "C19468, End batch - Cash Drawer is Unbalanced")
    @Severity(SeverityLevel.CRITICAL)
    public void cDTTellerSessionCommitOfficialCheckFromCashWithFee() throws AWTException {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: [Workstration1, Teller1]:\n" +
                "Go to Teller to Teller Transfer screen and log in to proof date");
        Pages.aSideMenuPage().clickTellerToTellerMenuItem();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: [Workstration1, Teller1]:\n" +
                "Click [Add New Transfer] button");
        Pages.tellerToTellerPage().clickAddNewTransfer();

        logInfo("Step 4: [Workstration1, Teller1]:\n" +
                "Fill in fields for the transaction:\n" +
                "- Select Action for the creator (e.g. Transfer Cash Out teller)\n" +
                "- Select Cashdrawer of the Teller2 from precondition#2 in \"Approver\" drop-down\n" +
                "- Specify Notes field with any data\n" +
                "Specify denominations with any amount >>>Make sure that Cash Drawer's1 Balance is enough to cover " +
                "transaction amount in the selected denominations");
        Pages.tellerToTellerPage().clickAction("Out");
        Pages.tellerToTellerPage().clickApprover(secondUserCredentials.getUserName());
        String notes = Generator.genString(20);
        Pages.tellerToTellerPage().typeNotes(notes);
        Pages.tellerToTellerPage().typeHundredsAmountValue(amount);

        logInfo("Step 5: [Workstration1, Teller1]: Click [Commit] button");
        Pages.tellerToTellerPage().clickCommitTransfer();
        TestRailAssert.assertEquals(Pages.tellerToTellerPage().getStatusTransfer(), "Teller-to-Teller Pending",
                new CustomStepResult("Status is correct", "Status is not correct"));
        String numberTransfer = Pages.tellerToTellerPage().getNumberTransfer();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);

        logInfo("Step 6: [Workstration1, Teller1]:\n" +
                "Go to Cash Drawer screen and verify fields for the Creator:\n" +
                "- Cash out total on the left part of the screen (if Teller1 was selected as cash out)\n" +
                "- Denominations used in the committed transaction");
        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        TestRailAssert.assertEquals(Pages.cashDrawerBalancePage().getCashOut(),
                 Double.parseDouble(cashOut) + Double.parseDouble(amount) + "0",
                new CustomStepResult("Cash out is correct", "Cash out isn't correct"));
        TestRailAssert.assertEquals(Pages.cashDrawerBalancePage().getHundredsAmount(),
                Double.parseDouble(hundredsAmount) - Double.parseDouble(amount) + "0",
                new CustomStepResult("Hundreds is correct","Hundreds isn't correct"));

        logInfo("Step 7: Log in to the system as the Teller2 from precondition#2 on Workstation2");
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(secondUserCredentials.getUserName(), secondUserCredentials.getPassword());

        logInfo("Step 8: [Workstration2, Teller2]:\n" +
                "Go to Cash Drawer screen and log in to proof date\n" +
                "verify fields for Approver:\n" +
                "- Cash in total on the left part of the screen (if Teller2 was selected as cash in)\n" +
                "- Denominations used in the committed transaction");
        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        Actions.transactionActions().doLoginTeller();
        TestRailAssert.assertEquals(Pages.cashDrawerBalancePage().getCashIn(),
                cashIn,
                new CustomStepResult("Cash in is correct", "Cash in isn't correct"));
        TestRailAssert.assertEquals(Pages.cashDrawerBalancePage().getHundredsAmount(),
                hundredsAmountForSec,
                new CustomStepResult("Hundreds is correct","Hundreds isn't correct"));

        logInfo("Step 9: [Workstration2, Teller2]:" +
                "Go to Teller to Teller Transfer screen");
        Pages.aSideMenuPage().clickTellerToTellerMenuItem();
        TestRailAssert.assertEquals(Pages.tellerToTellerPage().getStatusTransfer(), "Teller-to-Teller Pending",
                new CustomStepResult("Status is correct", "Status is not correct"));

        logInfo("Step 10: [Workstration2, Teller2]:\n" +
                "Click [Approve] button");
        Pages.tellerToTellerPage().clickApproveButton();

        logInfo("Step 11: [Workstration2, Teller2]:\n" +
                "Go to Cash Drawer screen and verify fields for Approver:\n" +
                "- Cash in total on the left part of the screen (if Teller2 was selected as cash in)\n" +
                "- Denominations used in the committed transaction");
        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        TestRailAssert.assertEquals(Pages.cashDrawerBalancePage().getCashIn(),
                Double.parseDouble(cashIn) + Double.parseDouble(amount) + "0",
                new CustomStepResult("Cash in is correct", "Cash in isn't correct"));
        TestRailAssert.assertEquals(Pages.cashDrawerBalancePage().getHundredsAmount(),
                Double.parseDouble(hundredsAmountForSec) + Double.parseDouble(amount) + "0",
                new CustomStepResult("Hundreds is correct","Hundreds isn't correct"));

        logInfo("Step 12: Go back to Workstration1 with Teller1 user logged in;\n" +
                "Go to Cash Drawer screen and verify fields for the Creator:\n" +
                "- Cash out total on the left part of the screen (if Teller1 was selected as cash out)\n" +
                "- Denominations used in the committed transaction");
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        Actions.transactionActions().doLoginTeller();
        TestRailAssert.assertEquals(Pages.cashDrawerBalancePage().getCashOut(),
                Double.parseDouble(cashOut) + Double.parseDouble(amount) + "0",
                new CustomStepResult("Cash out is correct", "Cash out isn't correct"));
        TestRailAssert.assertEquals(Pages.cashDrawerBalancePage().getHundredsAmount(),
                Double.parseDouble(hundredsAmount) - Double.parseDouble(amount) + "0",
                new CustomStepResult("Hundreds is correct","Hundreds isn't correct"));

        logInfo("Step 13: [Workstration1, Teller1 ]:\n" +
                "Go to Teller to Teller Transfer screen and search for an approved transaction");
        Pages.aSideMenuPage().clickTellerToTellerMenuItem();
        boolean b = Pages.tellerToTellerPage().noTransactionsByNumber(numberTransfer);
        TestRailAssert.assertTrue(b, new CustomStepResult("Transactions is not visible","Transactions is visible"));

    }

}
