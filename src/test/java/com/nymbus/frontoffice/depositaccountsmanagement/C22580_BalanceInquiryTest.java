package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.accountinstructions.HoldInstruction;
import com.nymbus.newmodels.generation.accountinstructions.InstructionConstructor;
import com.nymbus.newmodels.generation.accountinstructions.builder.HoldInstructionBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22580_BalanceInquiryTest extends BaseTest {
    private String savingsAccountNumber = "";

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client, Account, Transaction and Instruction
        Client client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        Account savingsAccount = new Account().setSavingsAccountData();
        Transaction transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        HoldInstruction instruction =  new InstructionConstructor(new HoldInstructionBuilder()).constructInstruction(HoldInstruction.class);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.createClient().createClient(client);

        // Create account
        AccountActions.createAccount().createSavingsAccount(savingsAccount);
        savingsAccountNumber = savingsAccount.getAccountNumber();

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());

        // Create transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        // Create instruction
        AccountActions.editAccount().navigateToAccountDetails(savingsAccount.getAccountNumber(), false);
        AccountActions.editAccount().goToInstructionsTab();
        AccountActions.createInstruction().createHoldInstruction(instruction);
    }

    @Test(description = "C22580, Client Accounts: Balance inquiry on Regular Savings account")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyBalanceInquiry() {

        logInfo("Step 2: Search for the Savings account from the precondition and open it on Details tab");
        AccountActions.editAccount().navigateToAccountDetails(savingsAccountNumber, false);

        logInfo("Step 3: Click 'Balance Inquiry' button");
        Pages.accountDetailsPage().clickBalanceInquiry();

        Pages.accountDetailsModalPage().waitForLoadSpinnerInvisibility();

        // TODO add image recognizer
    }
}
