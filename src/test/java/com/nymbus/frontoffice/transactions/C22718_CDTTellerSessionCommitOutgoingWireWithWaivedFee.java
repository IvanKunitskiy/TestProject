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
import com.nymbus.newmodels.cashier.CashierDefinedTransactions;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.generation.transactions.builder.WithdrawalGLDebitCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C22718_CDTTellerSessionCommitOutgoingWireWithWaivedFee extends BaseTest {
    private Transaction transaction;
    private Transaction savingsTransaction;
    private BalanceDataForCHKAcc expectedSavingsBalanceData;
    private TransactionData savingsAccTransactionData;
    private Account savingsAccount;
    private double transactionAmount = 200.00;
    private double savingsTransactionAmount = 200.00;
    private double returnTransactionAmount = 100.00;
    private double fee = 30.00;


    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Accounts
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        savingsAccount = new Account().setSavingsAccountData();
        Transaction depositSavingsTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        transaction = new TransactionConstructor(new WithdrawalGLDebitCHKAccBuilder()).constructTransaction();
        savingsTransaction = new TransactionConstructor(new WithdrawalGLDebitCHKAccBuilder()).constructTransaction();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createSavingsAccount(savingsAccount);

        // Set up transactions with account number
        transaction.getTransactionSource().setAmount(returnTransactionAmount);
        transaction.getTransactionDestination().setAmount(returnTransactionAmount);
        transaction.getTransactionSource().setAccountNumber(savingsAccount.getAccountNumber());
        depositSavingsTransaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());
        depositSavingsTransaction.getTransactionDestination().setTransactionCode("209 - Deposit");
        depositSavingsTransaction.getTransactionDestination().setAmount(savingsTransactionAmount);
        depositSavingsTransaction.getTransactionSource().setAmount(savingsTransactionAmount);

        // Perform deposit transactions
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositSavingsTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set transaction with amount value
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccount.getAccountNumber());
        expectedSavingsBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        savingsAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedSavingsBalanceData.getCurrentBalance(), fee);

        //Check CDT template
        boolean templateNotExists = Actions.cashierDefinedActions().checkCDTTemplateIsExist(CashierDefinedTransactions.OUTGOING_WIRE_FROM_SAVINGS);
        if (templateNotExists){
            boolean isCreated = Actions.cashierDefinedActions().createOutgoingWireFromSavings();
            Assert.assertTrue(isCreated, "CDT template not created");
        }
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 22718, testRunName = TEST_RUN_NAME)
    @Test(description = "C22718, CDT+Teller Session - Commit outgoing wire with waived fee")
    @Severity(SeverityLevel.CRITICAL)
    public void printTellerReceiptWithoutBalance() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Cashier Defined Transactions screen and log in to proof date");
        Actions.transactionActions().loginTeller();
        Pages.aSideMenuPage().waitForASideMenu();
        Pages.aSideMenuPage().clickCashierDefinedTransactionsMenuItem();

        logInfo("Step 3: Search for template from preconditions and select it");
        logInfo("Step 4: Click on [Waive Fee] toggle button");
        logInfo("Step 5: Specify account from precondition in destination line account number field;\n" +
                "Set transaction amount > fee amount");
        Actions.cashierDefinedActions().createOutgoingTransaction(CashierDefinedTransactions.OUTGOING_WIRE_FROM_SAVINGS,
                transaction, true);
        expectedSavingsBalanceData.reduceAmount(transaction.getTransactionDestination().getAmount());

        logInfo("Step 6: Click [Commit Transaction] button");
        Actions.transactionActions().clickCommitButton();
        AccountActions.callStatement().verifyTransactionFields(CashierDefinedTransactions.OUTGOING_WIRE_FROM_SAVINGS);

        logInfo("Step 7: Go to account used in CREDIT item and verify its:\n" +
                "- current balance\n" +
                "- available balance");
        Actions.transactionActions().goToTellerPage();
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccount.getAccountNumber());
        BalanceDataForCHKAcc actualSavBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        Assert.assertEquals(actualSavBalanceData.getCurrentBalance(), expectedSavingsBalanceData.getCurrentBalance(),
                "Current balance doesn't match!");
        Assert.assertEquals(actualSavBalanceData.getAvailableBalance(), expectedSavingsBalanceData.getAvailableBalance(),
                "Available balance doesn't match!");

        logInfo("Step 8: Open account on the Transactions tab and verify the committed transaction");
        Pages.accountDetailsPage().clickTransactionsTab();
        savingsAccTransactionData.setBalance(expectedSavingsBalanceData.getCurrentBalance());
        savingsAccTransactionData.setAmount(expectedSavingsBalanceData.getCurrentBalance());
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualSavTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithBalanceSymbol();
        Assert.assertEquals(actualSavTransactionData, savingsAccTransactionData, "Transaction data doesn't match!");

    }

}
