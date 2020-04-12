package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.accountinstructions.HoldInstruction;
import com.nymbus.newmodels.accountinstructions.verifyingModels.InstructionBalanceData;
import com.nymbus.newmodels.generation.accountinstructions.InstructionConstructor;
import com.nymbus.newmodels.generation.accountinstructions.builder.HoldInstructionBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22610_AddHoldToAccountTest extends BaseTest {
    private HoldInstruction instruction;
    private Account savingsAccount;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client, Account, Transaction and Instruction
        Client client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        savingsAccount = new Account().setSavingsAccountData();
        Transaction transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        instruction =  new InstructionConstructor(new HoldInstructionBuilder()).constructInstruction(HoldInstruction.class);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.createClient().createClient(client);

        // Create account
        AccountActions.createAccount().createSavingsAccount(savingsAccount);

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());

        // Create transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
    }

    @Test(description = "C22610, Client Accounts: Add hold to account (review available balance)")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyHoldInstruction() {

        logInfo("Step 2: Search for the account from the precondition and open it on Instructions tab");
        AccountActions.editAccount().navigateToAccountDetails(savingsAccount.getAccountNumber(), false);

        InstructionBalanceData instructionBalanceData = AccountActions.retrievingAccountData().getInstructionBalanceData();

        AccountActions.editAccount().goToInstructionsTab();

        int instructionsCount = Pages.accountInstructionsPage().isInstructionsListVisible() ?
                Pages.accountInstructionsPage().getCreatedInstructionsCount() : 0;

        logInfo("Step 3: Click 'New Instruction' button");
        logInfo("Step 4: Create a hold on account");
        AccountActions.createInstruction().createHoldInstruction(instruction);

        Pages.accountInstructionsPage().waitForCreatedInstruction(instructionsCount + 1);

        Assert.assertEquals(Pages.accountInstructionsPage().getCreatedInstructionType(1), instruction.getType(),
                            "Instruction type doesn't match!");

        Assert.assertEquals(Pages.accountInstructionsPage().getCreatedInstructionExpirationDate(1), instruction.getExpirationDate(),
                            "Instruction expiration date doesn't match!");

        instructionBalanceData.reduceAmount(instruction.getAmount());

        logInfo("Step 5: Go to Account Details tab and pay attention to the Current and Available Balances");
        AccountActions.editAccount().goToDetailsTab();

        InstructionBalanceData actualBalanceData = AccountActions.retrievingAccountData().getInstructionBalanceData();

        Assert.assertEquals(actualBalanceData, instructionBalanceData, "Balance data doesn't match!");

        logInfo("Step 6: Go to Account Maintenance-> Maintenance History page");
        AccountActions.editAccount().goToMaintenanceHistory();

        logInfo("Step 7: Look through the Maintenance History records and check that records about the newly created Hold are present in the list");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("instruction") > 0,
                        "Instruction records aren't present in the list!");
    }
}