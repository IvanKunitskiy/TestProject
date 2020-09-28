package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.accountinstructions.HoldInstruction;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.accountinstructions.InstructionConstructor;
import com.nymbus.newmodels.generation.accountinstructions.builder.HoldInstructionBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Petro")
public class C22590_BalanceInquiryOnRegularCDAccountTest extends BaseTest {

    private Account cdAccount;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up account
        cdAccount = new Account().setCDAccountData();

        // Set up transaction with account number
        Transaction transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        transaction.getTransactionDestination().setAccountNumber(cdAccount.getAccountNumber());
        transaction.getTransactionDestination().setTransactionCode("311 - New CD");

        // Set up instruction
        HoldInstruction instruction = new InstructionConstructor(new HoldInstructionBuilder()).constructInstruction(HoldInstruction.class);
        instruction.setAmount(10);

        // Create a client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CD account
        AccountActions.createAccount().createCDAccount(cdAccount);

        // Commit transaction to account
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction);
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Navigate to instructions tab
        Actions.clientPageActions().searchAndOpenClientByName(cdAccount.getAccountNumber());
        AccountActions.editAccount().goToInstructionsTab();
        int instructionsCount = AccountActions.createInstruction().getInstructionCount();

        // Create instruction
        AccountActions.createInstruction().createHoldInstruction(instruction);
        Pages.accountInstructionsPage().waitForCreatedInstruction(instructionsCount + 1);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22590, 'Balance inquiry' on regular CD account")
    @Severity(SeverityLevel.CRITICAL)
    public void viewClientLevelCallStatement() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CD account from the precondition and open it on Details");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(cdAccount);
        String accountAvailableBalance = Pages.accountDetailsPage().getAvailableBalanceFromHeaderMenu();
        String accountCurrentBalance = Pages.accountDetailsPage().getCurrentBalanceFromHeaderMenu();

        logInfo("Step 3: Click [Balance Inquiry] button");
        Pages.accountDetailsPage().clickBalanceInquiry();
        Pages.balanceInquiryModalPage().clickCloseModalButton();
        Pages.accountDetailsPage().clickBalanceInquiry();

        logInfo("Step 4: Check Available Balance and Current Balance values");
        File balanceInquiryImageFile = Actions.balanceInquiryActions().saveBalanceInquiryImage();
        Actions.balanceInquiryActions().assertAvailableAndCurrentBalanceValuesFromReceipt(balanceInquiryImageFile, accountAvailableBalance, accountCurrentBalance);

        logInfo("Step 5: Click [Close] button");
        Pages.balanceInquiryModalPage().clickCloseButton();
    }

    @AfterMethod(description = "Delete the downloaded image.")
    public void postCondition() {
        logInfo("Deleting the downloaded image...");
        Functions.cleanDirectory(System.getProperty("user.dir") + "/screenshots/");
    }
}
