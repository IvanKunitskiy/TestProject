package com.nymbus.frontoffice.depositaccountsmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Functions;
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
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

@Epic("Frontoffice")
@Feature("Deposit Accounts Management")
@Owner("Petro")
public class C22580_BalanceInquiryTest extends BaseTest {

    private String savingsAccountNumber = "";

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up account
        Account savingsAccount = new Account().setSavingsAccountData();

        // Set up transaction and instruction
        Transaction transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        HoldInstruction instruction =  new InstructionConstructor(new HoldInstructionBuilder()).constructInstruction(HoldInstruction.class);

        // Log in and create a client
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set the product of the user to account
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createSavingAccountForTransactionPurpose(savingsAccount);
        savingsAccountNumber = savingsAccount.getAccountNumber();

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());

        // Create transaction
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction);
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Navigate to instructions tab
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccountNumber);
        AccountActions.editAccount().goToInstructionsTab();
        int instructionsCount = AccountActions.createInstruction().getInstructionCount();

        // Create instruction
        AccountActions.createInstruction().createHoldInstruction(instruction);
        Pages.accountInstructionsPage().waitForCreatedInstruction(instructionsCount + 1);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22580, Client Accounts: Balance inquiry on Regular Savings account")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyBalanceInquiry() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Search for the Savings account from the precondition and open it on Details tab");
        AccountActions.editAccount().navigateToAccountDetails(savingsAccountNumber, false);
        String accountAvailableBalance = Pages.accountDetailsPage().getAvailableBalance();
        String accountCurrentBalance = Pages.accountDetailsPage().getCurrentBalance();

        logInfo("Step 3: Click 'Balance Inquiry' button");
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