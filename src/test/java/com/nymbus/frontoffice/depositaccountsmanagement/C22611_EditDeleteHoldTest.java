package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.accountinstructions.HoldInstruction;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.accountinstructions.InstructionConstructor;
import com.nymbus.newmodels.generation.accountinstructions.builder.HoldInstructionBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceData;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Petro")
public class C22611_EditDeleteHoldTest extends BaseTest {
    private HoldInstruction instruction;
    private String accountNumber;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client, Account, Transaction and Instruction
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        Account savingsAccount = new Account().setSavingsAccountData();
        Transaction transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        instruction =  new InstructionConstructor(new HoldInstructionBuilder()).constructInstruction(HoldInstruction.class);
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set the product
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createSavingAccountForTransactionPurpose(savingsAccount);
        accountNumber = savingsAccount.getAccountNumber();

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());

        // Create transaction
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction);
        Actions.loginActions().doLogOutProgrammatically();

        // Navigate to instructions tab
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenClientByName(accountNumber);
        AccountActions.editAccount().goToInstructionsTab();
        int instructionsCount = AccountActions.createInstruction().getInstructionCount();

        // Create instruction
        AccountActions.createInstruction().createHoldInstruction(instruction);
        Pages.accountInstructionsPage().waitForCreatedInstruction(instructionsCount + 1);

        // Set data for edited instruction
        instruction.updateInstructionData();
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Deposit Accounts Management";

    @TestRailIssue(issueID = 22611, testRunName = TEST_RUN_NAME)
    @Test(description = "C22611, Client Accounts: Edit / delete hold")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyEditDeleteHoldInstruction() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Search for the account from the precondition and open it on Instructions tab");
        Actions.clientPageActions().searchAndOpenClientByName(accountNumber);
        BalanceData balanceData = AccountActions.retrievingAccountData().getBalanceData();
        AccountActions.editAccount().goToInstructionsTab();

        logInfo("Step 3: Click on hold record and click [Edit] button");
        logInfo("Step 4: Make any changes: \n" +
                "- set another amount \n" +
                "- select another Expiration Date from the calendar \n" +
                " add some char. to Notes field \n" +
                "Click [Save] button and submit the Supervisor Override popup (use current users name and password)");
        AccountActions.createInstruction().editHoldInstruction(instruction);
        balanceData.applyHoldInstruction(instruction.getAmount());

        logInfo("Step 5: Go to Account Details tab and pay attention to the Available Balance");
        AccountActions.editAccount().goToDetailsTab();
        BalanceData actualBalanceData = AccountActions.retrievingAccountData().getBalanceData();
        Assert.assertEquals(actualBalanceData, balanceData, "Balance data doesn't match!");

        logInfo("Step 6: Go back to Instructions tab");
        AccountActions.editAccount().goToInstructionsTab();

        logInfo("Step 7: Click on hold record and click [Delete] button \n" +
                "Submit the Supervisor Override popup (use current users name and password)");
        AccountActions.createInstruction().deleteInstruction(1);
        balanceData.removeHoldInstruction(instruction.getAmount());

        logInfo("Step 8: Go to Account Details tab and pay attention to the Available Balance");
        AccountActions.editAccount().goToDetailsTab();
        actualBalanceData = AccountActions.retrievingAccountData().getBalanceData();
        Assert.assertEquals(actualBalanceData, balanceData, "Balance data doesn't match!");

        logInfo("Step 9: Go to Account Maintenance-> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 10: Look through the Maintenance History records and check that records about the editing Hold and deleting the hold");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("instruction") > 0,
                "Instruction records aren't present in the list!");
    }
}