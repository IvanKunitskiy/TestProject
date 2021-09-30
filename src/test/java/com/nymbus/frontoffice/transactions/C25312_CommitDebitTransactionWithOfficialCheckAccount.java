package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.generation.transactions.builder.MiscDebitGLCreditTransactionBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C25312_CommitDebitTransactionWithOfficialCheckAccount extends BaseTest {
    private double transactionAmount = 100.00;
    private Transaction miscDebitGLCreditTransaction;
    private String chkAccountNumber;
    private BalanceDataForCHKAcc balance;
    private TransactionData transactionData;


    @BeforeMethod
    public void preCondition() {
        //Check
        if (WebAdminActions.webAdminUsersActions().getUseGLAccountNumberForOfficialChecks(userCredentials.getUserName(), userCredentials.getPassword())) {
            throw new SkipException("UseGLAccountNumberForOfficialChecks != 0");
        }

        //Get CHK account number
        chkAccountNumber = WebAdminActions.webAdminUsersActions().getInternalCheckingAccountNumber(userCredentials.getUserName(), userCredentials.getPassword());

        //Get balance
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenClientByName(chkAccountNumber);
        balance = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        //Check balance
        if (balance.getCurrentBalance() < transactionAmount) {
            Transaction depositSavingsTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
            //Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
            depositSavingsTransaction.getTransactionDestination().setAccountNumber(chkAccountNumber);
            Actions.transactionActions().goToTellerPage();
            Actions.transactionActions().doLoginTeller();
            Actions.transactionActions().createTransaction(depositSavingsTransaction);
            Actions.transactionActions().clickCommitButton();
            Pages.tellerPage().closeModal();
        }
        Actions.loginActions().doLogOutProgrammatically();

        //Set up transaction
        miscDebitGLCreditTransaction = new TransactionConstructor(new MiscDebitGLCreditTransactionBuilder()).constructTransaction();
        miscDebitGLCreditTransaction.getTransactionSource().setAccountNumber(chkAccountNumber);
        miscDebitGLCreditTransaction.getTransactionSource().setTransactionCode(TransactionCode.ATM_DEBIT_MEMO_119.getTransCode());

        //Set up Transaction Data
        transactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", balance.getCurrentBalance(), transactionAmount);

    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 25312, testRunName = TEST_RUN_NAME)
    @Test(description = "C25312, Commit debit transaction with official check account")
    @Severity(SeverityLevel.CRITICAL)
    public void commitDebitTransactionWithOfficialCheckAccount() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller screen and log in to proof date");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Select such line items:\n" +
                "Source: Misc Debit\n" +
                "Destination: GL/Credit");
        logInfo("Step 4: In the Source line item:\n" +
                "Select account from preconditions;\n" +
                "Set 119 - Debit Memo trancode;\n" +
                "Set any amount which is less than Account's available balance");
        logInfo("Step 5: Specify fields for opposite line item with correct values " +
                "(any GL account#, any notes, amount - same as for the Misc Debit item)");
        Actions.transactionActions().createMiscDebitGLCreditTransaction(miscDebitGLCreditTransaction);

        logInfo("Step 6: Commit transaction");
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 7: Specify credentials of the user from preconditions in the popup and submit it");
        Actions.transactionActions().fillingSupervisorModal(userCredentials);
        Pages.tellerPage().closeModal();
        balance.setCurrentBalance(balance.getCurrentBalance() - 100);
        balance.setAvailableBalance(balance.getAvailableBalance() - 100);

        logInfo("Step 8: Go to account used in source item and verify its:" +
                "- current balance" +
                "- available balance");
        Actions.clientPageActions().searchAndOpenClientByName(chkAccountNumber);
        BalanceDataForCHKAcc actualBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        Assert.assertEquals(actualBalanceData.getCurrentBalance(), balance.getCurrentBalance(),
                "Current balance doesn't match!");
        Assert.assertEquals(actualBalanceData.getAvailableBalance(), balance.getAvailableBalance(),
                "Available balance doesn't match!");

        logInfo("Step 9: Open account on the Transactions tab and verify the committed transaction and Balance");
        Pages.accountDetailsPage().clickTransactionsTab();
        transactionData.setBalance(balance.getCurrentBalance());
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithBalanceSymbol();
        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");


    }

}
