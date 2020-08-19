package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.verifyingmodels.ClosedAccountData;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitGLCreditTransactionBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22665_PerformECForWithdrawAndCloseSavingsAccTest extends BaseTest {
    private double accruedInterest;
    private double balanceBeforeClose;
    private Transaction withdrawTransaction;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        Account savingsAcc = new Account().setSavingsAccountData();
        savingsAcc.setDateOpened(DateTime.getDateMinusDays(WebAdminActions.loginActions().getSystemDate(), 10));

        TransactionConstructor constructor = new TransactionConstructor(new GLDebitMiscCreditBuilder());

        // Set up transactions
        Transaction transaction = constructor.constructTransaction();
        transaction.setTransactionDate(savingsAcc.getDateOpened());

        // Set up withdraw transactions
        constructor.setBuilder(new MiscDebitGLCreditTransactionBuilder());
        withdrawTransaction = constructor.constructTransaction();
        withdrawTransaction.getTransactionSource().setTransactionCode(TransactionCode.WITHDRAW_AND_CLOSE_SAVINGS.getTransCode());
        withdrawTransaction.getTransactionSource().setAccountNumber(savingsAcc.getAccountNumber());

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createSavingsAccount(savingsAcc);

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(savingsAcc.getAccountNumber());

        // perform transaction
        performGLDebitMiscCreditTransaction(transaction, savingsAcc.getDateOpened());
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Go to account details
        Actions.clientPageActions().searchAndOpenClientByName(savingsAcc.getAccountNumber());

        // Get data for withdraw transaction
        accruedInterest = AccountActions.retrievingAccountData().getAccruedInterest();
        balanceBeforeClose =  AccountActions.retrievingAccountData().getCurrentBalance();
        withdrawTransaction.getTransactionSource().setAmount(accruedInterest + balanceBeforeClose);
        withdrawTransaction.getTransactionDestination().setAmount(accruedInterest + balanceBeforeClose);

        // Re-login in system for reset teller session
        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        //  perform withdraw transaction
        performWithdrawAndCloseTransaction(withdrawTransaction);

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
    }

    @Test(description = "22665, Error correct withdraw&close CHK (balance + IENP)")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyECForWithdrawAndCloseTransaction() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Journal page and log in to the proof date");
        Actions.transactionActions().loginTeller();
        Actions.journalActions().goToJournalPage();

        logInfo("Step 3: Search for 127 - withdraw&close transaction from preconditions and open it on details");
        Actions.journalActions().applyFilterByAccountNumber(withdrawTransaction.getTransactionSource().getAccountNumber());
        Actions.journalActions().clickLastTransaction();

        logInfo("Step 4: Click [Error Correct] button");
        Pages.journalDetailsPage().clickErrorCorrectButton();
        Pages.journalDetailsPage().waitForErrorCorrectButtonInvisibility();
        Assert.assertEquals(Actions.journalActions().getTransactionState(), "Void",
                "Transaction state doesn't changed");

        logInfo("Step 5: Go to account used in source item and verify its: \n" +
                "- current balance \n" +
                "- available balance \n" +
                "- accrued interest \n" +
                "- account status \n" +
                "- account date closed");
        Actions.clientPageActions().searchAndOpenClientByName(withdrawTransaction.getTransactionSource().getAccountNumber());
        ClosedAccountData expectedClosedAccountData = getClosedAccountData(balanceBeforeClose, accruedInterest);
        ClosedAccountData actualClosedAccountData = AccountActions.retrievingAccountData().getClosedAccountData();
        Assert.assertEquals(actualClosedAccountData, expectedClosedAccountData, "Closed account data is incorrect!");

        logInfo("Step 9: Open Account on Transactions tab and check transaction details");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        verifyIntDepositTransactionData(accruedInterest);
        verifyWithDrawTransactionData(accruedInterest, balanceBeforeClose);
    }
    private void performGLDebitMiscCreditTransaction(Transaction transaction, String date) {
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);
        Pages.tellerPage().setEffectiveDate(date);
        Actions.transactionActions().clickCommitButton();
        Assert.assertFalse(Pages.tellerPage().isNotificationsPresent(), "Error message is visible!");
        Pages.tellerPage().closeModal();
    }

    private void performWithdrawAndCloseTransaction(Transaction transaction) {
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createWithdrawMiscDebitGLCreditTransaction(transaction);
        Actions.transactionActions().clickCommitButton();
        Assert.assertFalse(Pages.tellerPage().isNotificationsPresent(), "Error message is visible!");
        Pages.tellerPage().closeModal();
    }

    private ClosedAccountData getClosedAccountData(double balance, double accruedInterest) {
        ClosedAccountData expectedClosedAccountData = new ClosedAccountData();
        expectedClosedAccountData.setDateClosed("");
        expectedClosedAccountData.setAccountStatus("Active");
        expectedClosedAccountData.setCurrentBalance(balance);
        expectedClosedAccountData.setAvailableBalance(balance);
        expectedClosedAccountData.setAccruedInterest(accruedInterest);

        return expectedClosedAccountData;
    }

    private void verifyIntDepositTransactionData(double accruedInterest) {
        Assert.assertEquals(Pages.accountTransactionPage().getTransactionCodeByIndex(2), TransactionCode.INT_DEPOSIT_SAVINGS.getTransCode(),
                "Transaction 207 - Int Deposit is not displayed!");
        Assert.assertEquals(Pages.accountTransactionPage().getAmountSymbol(2), "-",
                "Transaction 207 - Int Deposit amount symbol is incorrect!");
        Assert.assertEquals(AccountActions.retrievingAccountData().getAmountValue(2), accruedInterest,
                "Transaction 207 - Int Deposit amount value is incorrect!");
    }

    private void verifyWithDrawTransactionData(double accruedInterest, double balanceBeforeClose) {
        Assert.assertEquals(Pages.accountTransactionPage().getTransactionCodeByIndex(1), TransactionCode.WITHDRAW_AND_CLOSE_SAVINGS.getTransCode(),
                "Transaction 227 - Withdraw&Close is not displayed!");
        Assert.assertEquals(Pages.accountTransactionPage().getAmountSymbol(1), "+",
                "Transaction 227 - Withdraw&Close amount symbol is incorrect!");
        double expectedAmount = accruedInterest + balanceBeforeClose;
        Assert.assertEquals(AccountActions.retrievingAccountData().getAmountValue(1), expectedAmount,
                "Transaction 227 - Withdraw&Close amount value is incorrect!");
        Assert.assertEquals(AccountActions.retrievingAccountData().getBalanceValue(1), balanceBeforeClose,
                "Transaction 227 - Withdraw&Close balance value is incorrect!");
    }
}