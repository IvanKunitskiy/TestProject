package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.accountinstructions.DebitHoldInstruction;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.accountinstructions.InstructionConstructor;
import com.nymbus.newmodels.generation.accountinstructions.builder.DebitHoldInstructionBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitGLCreditTransactionBuilder;
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
@Owner("Petro")
public class C22679_CommitTransactionWithDebitHoldTest extends BaseTest {

    private Account checkingAccount;
    private Transaction withdrawalTransaction;
    private BalanceDataForCHKAcc chkAccBalanceData;
    private TransactionData transactionData;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up CHK account (required as Corresponding Account)
        checkingAccount = new Account().setCHKAccountData();

        // Set up transaction for increasing the CHK account balance
        Transaction transaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        double transactionAmount = transaction.getTransactionSource().getAmount();

        // Set up withdrawal transaction
        withdrawalTransaction = new TransactionConstructor(new MiscDebitGLCreditTransactionBuilder()).constructTransaction();
        withdrawalTransaction.getTransactionSource().setAccountNumber(checkingAccount.getAccountNumber());
        double withdrawalTransactionAmount = 80.00;
        withdrawalTransaction.getTransactionSource().setAmount(withdrawalTransactionAmount);
        withdrawalTransaction.getTransactionDestination().setAmount(withdrawalTransactionAmount);

        // Set up instruction
        InstructionConstructor instructionConstructor = new InstructionConstructor(new DebitHoldInstructionBuilder());
        DebitHoldInstruction debitHoldInstruction = instructionConstructor.constructInstruction(DebitHoldInstruction.class);

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set the missing CHK account data
        checkingAccount.setBankBranch(Actions.usersActions().getBankBranch());
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create CHK account
        AccountActions.createAccount().createCHKAccount(checkingAccount);

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(checkingAccount.getAccountNumber());

        // Perform transaction to assign the money to CHK account
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction);
        Actions.loginActions().doLogOutProgrammatically();

        // Create instruction and logout
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(checkingAccount);
        Pages.accountNavigationPage().clickInstructionsTab();
        int instructionsCount = AccountActions.createInstruction().getInstructionCount();
        AccountActions.createInstruction().createDebitHoldInstruction(debitHoldInstruction);
        Pages.accountInstructionsPage().waitForCreatedInstruction(instructionsCount + 1);

        // Retrieve the current and available balance from account after transaction is performed
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(checkingAccount);
        chkAccBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Actions.loginActions().doLogOut();

        // Set transaction data
        transactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-",
                transactionAmount - withdrawalTransactionAmount,
                withdrawalTransaction.getTransactionDestination().getAmount());
    }

    @Test(description = "C22679, Commit transaction with debit hold")
    @Severity(SeverityLevel.CRITICAL)
    public void commitTransactionWithDebitHold() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Teller screen and log in to proof date");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Select any source related to regular account (e.g. Misc Debit);\n" +
                "select any opposite line item (e.g. GL/Credit)");
        logInfo("Step 4: Select account from preconditions in source line item and set any amount which is less than account's available balance,"+
                "select 116- Withdrawal/ 216-Withdrawal trancode");
        logInfo("Step 5: Specify fields for opposite line item with correct values " +
                "(any GL account#, any notes, amount - same as for the Misc Debit item)");
        Actions.transactionActions().createMiscDebitGLCreditTransaction(withdrawalTransaction);

        logInfo("Step 6: Click [Commit Transaction] button");
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 7: Specify credentials of the user from preconditions in the popup and submit it");
        Actions.transactionActions().fillingSupervisorModal(userCredentials);
        chkAccBalanceData.reduceAvailableBalance(withdrawalTransaction.getTransactionDestination().getAmount());
        chkAccBalanceData.reduceCurrentBalance(withdrawalTransaction.getTransactionDestination().getAmount());
        Actions.loginActions().doLogOutProgrammatically();

        logInfo("Step 8: Go to account with debit hold instruction used in transaction and verify its:\n" +
                "- current balance\n" +
                "- available balance");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(checkingAccount);
        Assert.assertEquals(AccountActions.retrievingAccountData().getCurrentBalance(),
                chkAccBalanceData.getCurrentBalance(), "CHK account current balance is not correct!");
        Assert.assertEquals(AccountActions.retrievingAccountData().getAvailableBalance(),
                chkAccBalanceData.getAvailableBalance(), "CHK account available balance is not correct!");

        logInfo("Step 9: Open account on the Transactions tab and verify the committed transaction");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset, 1);
        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");
    }
}
