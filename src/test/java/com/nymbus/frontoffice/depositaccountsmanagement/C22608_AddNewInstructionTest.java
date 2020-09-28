package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.accountinstructions.ActivityHoldInstruction;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.accountinstructions.InstructionConstructor;
import com.nymbus.newmodels.generation.accountinstructions.builder.ActivityHoldInstructionBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Clients Management")
@Owner("Petro")
public class C22608_AddNewInstructionTest extends BaseTest {

    private Account chkAccount;
    private ActivityHoldInstruction activityHoldInstruction;

    @BeforeMethod
    public void preCondition() {

        // Set up a client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up account
        chkAccount = new Account().setCHKAccountData();

        // Set up instruction
        InstructionConstructor instructionConstructor = new InstructionConstructor(new ActivityHoldInstructionBuilder());
        activityHoldInstruction = instructionConstructor.constructInstruction(ActivityHoldInstruction.class);

        // Create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22608, Add new instruction")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewInstruction() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for account from the precondition and open it on Notes tab");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);
        Pages.accountNavigationPage().clickInstructionsTab();

        logInfo("Step 3: Click [New Instruction] button");
        logInfo("Step 4: Select any Instruction Type (e.g. Activity Hold) and fill in all fields:\n" +
                "- Notes text field - any alphanumeric value\n" +
                "- Expiration Date > Current date");
        AccountActions.createInstruction().createActivityHoldInstruction(activityHoldInstruction);

        logInfo("Step 5: Refresh the page and pay attention to the colored bar in the header");
        SelenideTools.refresh();
        Pages.accountInstructionsPage().waitForAlertVisible("Account | " + chkAccount.getAccountNumber() +
                " | " + activityHoldInstruction.getNotes());
        Assert.assertTrue(Pages.accountInstructionsPage().isInstructionAlertAppeared("Account | " + chkAccount.getAccountNumber() +
                " | " + activityHoldInstruction.getNotes()), "Note alert not appeared on the page");

        logInfo("Step 6: Go to Account Maintenance-> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 7: Look through the Maintenance History records and check that records about the newly created Instruction are present in the list");
        AccountActions.accountMaintenanceActions().verifyAddedNewInstructionRecords();
    }
}
