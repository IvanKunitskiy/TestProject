package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.accountinstructions.HoldInstruction;
import com.nymbus.newmodels.accountinstructions.enums.InstructionType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.accountinstructions.InstructionConstructor;
import com.nymbus.newmodels.generation.accountinstructions.builder.HoldInstructionBuilder;
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
public class C22609_EditDeleteSpecialInstructionTest extends BaseTest {

    private IndividualClient client;
    private Account chkAccount;
    private HoldInstruction holdInstruction;

    @BeforeMethod
    public void preCondition() {

        // Set up a client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up account
        chkAccount = new Account().setCHKAccountData();

        // Set up instruction
        InstructionConstructor instructionConstructor = new InstructionConstructor(new HoldInstructionBuilder());
        holdInstruction = instructionConstructor.constructInstruction(HoldInstruction.class);
        holdInstruction.setInstructionType(InstructionType.HOLD);

        // Create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Pages.accountNavigationPage().clickInstructionsTab();

        // Create instruction and logout
        AccountActions.createInstruction().createHoldInstruction(holdInstruction);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22609, Edit / Delete Special Instruction")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewInstruction() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for account from the precondition and open it on Instructions tab");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);
        Pages.accountNavigationPage().clickInstructionsTab();

        logInfo("Step 3: Select any Instruction and click [Edit] button");
        Pages.accountInstructionsPage().clickInstructionInListByIndex(1);
        Pages.accountInstructionsPage().clickEditButton();

        logInfo("Step 4: Make any changes: e.g.\n" +
                "- add any alphanumeric char. to the Notes text field\n" +
                "- Select another Expiration Date from the calendar and click [Save] button");
        AccountActions.editInstructionActions().addTextToNotesField();
        AccountActions.editInstructionActions().changeExpirationDate();
        Pages.accountInstructionsPage().clickSaveButton();

        logInfo("Step 5: Refresh the page and pay attention to the colored bar in the header");
        SelenideTools.refresh();
        Assert.assertTrue(Pages.accountInstructionsPage().isInstructionAlertAppeared("Account | " + chkAccount.getAccountNumber() +
                " | " + holdInstruction.getNotes()), "Note alert not appeared on the page");

        logInfo("Step 6: Click on the Instruction and click [Delete] button");
        Pages.accountInstructionsPage().clickDeleteButton();
        // TODO: Procedd test implementation
    }
}
