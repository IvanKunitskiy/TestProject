package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.UserCredentials;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.generation.transactions.builder.WithdrawalGLCreditCHKAccBuilder;
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
@Owner("Petro")
public class C22651_JournalPerformEcForNsfTransactionTest extends BaseTest {

    private Transaction transaction;
    private BalanceDataForCHKAcc chkAccBalanceData;
    private Account checkingAccount;
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
        transaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();

        // Set up withdrawal transaction
        Transaction withdrawalTransaction = new TransactionConstructor(new WithdrawalGLCreditCHKAccBuilder()).constructTransaction();
        withdrawalTransaction.getTransactionSource().setAccountNumber(checkingAccount.getAccountNumber());

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

        // Retrieve the current and available balance from account after transaction is performed
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(checkingAccount);
        chkAccBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        // Perform NSF transaction with supervisor override
        performWithdrawalGlCreditTransaction(withdrawalTransaction, userCredentials);
        Actions.loginActions().doLogOutProgrammatically();

        // Set transaction data
        transactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "+",
                transaction.getTransactionDestination().getAmount(),
                withdrawalTransaction.getTransactionDestination().getAmount());
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 22651, testRunName = TEST_RUN_NAME)
    @Test(description = "C22651, Journal: Perform EC for NSF transaction")
    @Severity(SeverityLevel.CRITICAL)
    public void journalPerformEcForNsfTransaction() {

        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Journal page and log in to the proof date");
        Actions.transactionActions().loginTeller();
        Actions.journalActions().goToJournalPage();

        logInfo("Step 3: Search for the transaction from preconditions and open its details");
        Actions.journalActions().applyFilterByAccountNumber(transaction.getTransactionDestination().getAccountNumber());
        Actions.journalActions().clickLastTransaction();

        logInfo("Step 4: Click [Error Correct] button");
        Pages.journalDetailsPage().clickErrorCorrectButton();
        Pages.journalDetailsPage().waitForErrorCorrectButtonInvisibility();
        Pages.journalDetailsPage().waitForLoadingSpinnerInvisibility();
        Assert.assertEquals(Actions.journalActions().getTransactionState(), "Void",
                "Transaction state hasn't changed");

        logInfo("Step 5: Go to account used in transaction from preconditions and verify its:\n" +
                "- current balance\n" +
                "- available balance");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(checkingAccount);
        Assert.assertEquals(AccountActions.retrievingAccountData().getCurrentBalance(),
                chkAccBalanceData.getCurrentBalance(), "CHK account current balance is not correct!");
        Assert.assertEquals(AccountActions.retrievingAccountData().getAvailableBalance(),
                chkAccBalanceData.getAvailableBalance(), "CHK account available balance is not correct!");

        logInfo("Step 6: Open account on the Transactions tab and verify the Error corrected transaction");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset, 1);
        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");
    }

    public void performWithdrawalGlCreditTransaction(Transaction transaction, UserCredentials userCredentials) {
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createWithdrawalGlCreditTransaction(transaction);
        Actions.transactionActions().clickCommitButton();
        Actions.transactionActions().fillingSupervisorModal(userCredentials);
        Pages.tellerPage().closeModal();
    }
}
