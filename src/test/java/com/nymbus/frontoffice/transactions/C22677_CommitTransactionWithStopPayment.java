package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.accountinstructions.StopPaymentInstruction;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.accountinstructions.InstructionConstructor;
import com.nymbus.newmodels.generation.accountinstructions.builder.StopPaymentInstructionBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.CheckGLCreditCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C22677_CommitTransactionWithStopPayment extends BaseTest {
    private Transaction transaction;
    private BalanceDataForCHKAcc expectedBalanceData;
    private TransactionData chkAccTransactionData;
    private Account checkAccount;
    private double transactionAmount = 150.00;
    private IndividualClient client;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();
        checkAccount = new Account().setCHKAccountData();
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        transaction = new TransactionConstructor(new CheckGLCreditCHKAccBuilder()).constructTransaction();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        checkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);
        transaction.getTransactionSource().setAccountNumber(checkAccount.getAccountNumber());

        // Set up instruction
        StopPaymentInstruction instruction = new InstructionConstructor(new StopPaymentInstructionBuilder())
                .constructInstruction(StopPaymentInstruction.class);

        // Create instruction and logout
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(checkAccount);
        Pages.accountNavigationPage().clickInstructionsTab();

        int instructionsCount = AccountActions.createInstruction().getInstructionCount();
        AccountActions.createInstruction().createStopPaymentInstruction(instruction);
        Pages.accountInstructionsPage().waitForCreatedInstruction(instructionsCount + 1);
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set transaction with amount value
        Actions.clientPageActions().searchAndOpenClientByName(checkAccount.getAccountNumber());
        expectedBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        chkAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedBalanceData.getCurrentBalance(), transactionAmount);
        Actions.loginActions().doLogOut();
    }


    @Test(description = "C22677, Commit transaction with stop payment")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyNSFTransaction() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller page and log in to the proof date");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Select Source - Check\n" +
                "Select Destination - GL/Credit");
        logInfo("Step 4: Select account from preconditions in the Source and specify its details which are " +
                "covered by stop payment instruction (e.g. check number == #5555, amount == $101.00) --- Amount should " +
                "be < Account's Available Balance");
        int currentIndex = 0;
        Actions.transactionActions().setCheckSource(transaction.getTransactionSource(), currentIndex);

        logInfo("Step 5: Specify fields for opposite line item with correct values (any GL account#, any notes)");
        Actions.transactionActions().setGLCreditDestination(transaction.getTransactionDestination(), currentIndex);

        logInfo("Step 6: Click [Commit Transaction] button");
        Actions.transactionActions().clickCommitButton();
        expectedBalanceData.reduceAmount(transaction.getTransactionSource().getAmount());
        Actions.transactionActions().verifyPreSelectedClientFields(client);
        Pages.verifyConductorModalPage().clickVerifyButton();

        logInfo("Step 7: Specify credentials of the user with supervisor override permissions  \n" +
                "(User with all ACLs from the preconditions) and submit the form");
        Actions.transactionActions().fillingSupervisorModal(userCredentials);
        Assert.assertEquals(Pages.tellerPage().getModalText(),
                "Transaction was successfully completed.",
                "Transaction doesn't commit");
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 8: Go to account used in source item and verify its:" +
                "- current balance" +
                "- available balance");
        Actions.clientPageActions().searchAndOpenClientByName(checkAccount.getAccountNumber());
        BalanceDataForCHKAcc actualBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        Assert.assertEquals(actualBalanceData.getCurrentBalance(), expectedBalanceData.getCurrentBalance(),
                "Current balance doesn't match!");
        Assert.assertEquals(actualBalanceData.getAvailableBalance(), expectedBalanceData.getAvailableBalance(),
                "Available balance doesn't match!");

        logInfo("Step 9: Open account on the Transactions tab and verify the committed transaction and Balance");
        Pages.accountDetailsPage().clickTransactionsTab();
        chkAccTransactionData.setBalance(expectedBalanceData.getCurrentBalance());
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithBalanceSymbol();
        Assert.assertEquals(actualTransactionData, chkAccTransactionData, "Transaction data doesn't match!");

    }

}
