package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.selenide.Selenide;
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
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Petro")
public class C22590_BalanceInquiryOnRegularCDAccountTest extends BaseTest {

    private Client client;
    private Account cdAccount;
    private HoldInstruction instruction;
    private Transaction transaction;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");

        // Set up account
        cdAccount = new Account().setCDAccountData();

        // Set up instruction
        instruction =  new InstructionConstructor(new HoldInstructionBuilder()).constructInstruction(HoldInstruction.class);
        instruction.setAmount(10);
        transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();

        // Create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.createClient().createClient(client);

        // Create CD account
        AccountActions.createAccount().createCDAccount(cdAccount);

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(cdAccount.getAccountNumber());
        transaction.getTransactionDestination().setTransactionCode("311 - New CD");

        // Commit transaction to account
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        // Create instruction
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(cdAccount);
        Pages.accountNavigationPage().clickInstructionsTab();
        AccountActions.createInstruction().createHoldInstruction(instruction);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22590, 'Balance inquiry' on regular CD account")
    @Severity(SeverityLevel.CRITICAL)
    public void viewClientLevelCallStatement() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CD account from the precondition and open it on Details");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(cdAccount);

        logInfo("Step 3: Click [Balance Inquiry] button");
        Pages.accountDetailsPage().clickBalanceInquiry();

        logInfo("Step 4: heck Available Balance and Current Balance values");
        // TODO: parse receipt

        logInfo("Step 5: Click [Close] button");
        Pages.balanceInquiryModalPage().clickCloseButton();
    }
}
