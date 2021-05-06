package com.nymbus.frontoffice.transactions;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
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
public class C22672_TellerCommitTransactionOnDormantAccountTest extends BaseTest {

    private Transaction transaction;
    private TransactionData transactionData;
    private BalanceDataForCHKAcc actualBalanceDataForCheckingAcc;
    private double transactionAmount;
    private String accountNumberWithDormantStatus;

    @BeforeMethod
    public void preCondition() {

        // Set up transaction
        transaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        transaction.getTransactionDestination().setTransactionCode(TransactionCode.ATM_DEPOSIT_209.getTransCode());

        // Get the dormant account from Web Admin
        accountNumberWithDormantStatus = WebAdminActions.webAdminUsersActions().getAccountWithDormantStatus(2);

        // Transaction misc
        transaction.getTransactionDestination().setAccountNumber(accountNumberWithDormantStatus);
        transactionAmount = transaction.getTransactionDestination().getAmount();

        // Log in and get the current and available balance of retrieved Dormant account
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenClientByName(accountNumberWithDormantStatus);
        actualBalanceDataForCheckingAcc = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        // Set transaction code and logout
        Actions.transactionActions().setTransactionCode(transaction);
        Actions.loginActions().doLogOut();

        double balance = actualBalanceDataForCheckingAcc.getCurrentBalance() + transaction.getTransactionDestination().getAmount();
        transactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "+", balance, transaction.getTransactionDestination().getAmount());
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 22672, testRunName = TEST_RUN_NAME)
    @Test(description = "C22672, Teller: Commit transaction on dormant account")
    @Severity(SeverityLevel.CRITICAL)
    public void tellerCommitTransactionOnDormantAccount() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Selenide.open(Constants.URL);
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller page and log in to the proof date");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Select any source / destination fund types so that at least one is related to regular account\n." +
                "e.g. : - Source: Gl/Debit, - Destination: Deposit");
        logInfo("Step 4: Select dormant account from preconditions in any of the added line items\n" +
                "which are related to regular account (e.g. Deposit) and specify some amount (e.g. $100) for it");
        logInfo("Step 5: Specify fields for opposite line item with correct values:\n" +
                "- search for any GL account\n" +
                "- specify same amount (e.g. $100)\n" +
                "- expand line item and specify Note");
        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);

        logInfo("Step 6: Click [Commit Transaction] button");
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();

        logInfo("Step 7: Specify credentials of the user with supervisor override permissions from preconditions in the popup and submit it");
        Actions.transactionActions().fillingSupervisorModal(userCredentials);
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();

        logInfo("Step 8: Go to dormant account used in transaction and verify its:\n" +
                "- current balance\n" +
                "- available balance\n" +
                "- account status");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(accountNumberWithDormantStatus);
        Assert.assertEquals(AccountActions.retrievingAccountData().getCurrentBalance(),
                actualBalanceDataForCheckingAcc.getCurrentBalance() + transactionAmount, "CHK account current balance is not correct!");
        Assert.assertEquals(AccountActions.retrievingAccountData().getAvailableBalance(),
                actualBalanceDataForCheckingAcc.getAvailableBalance() + transactionAmount, "CHK account available balance is not correct!");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountStatus(), "Active", "Account status is not 'Active'");

        logInfo("Step 9: Open Account on the Transactions History tab and verify the committed transaction");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithBalanceSymbol();
        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");

        logInfo("Step 10: Go to Account Maintenance-> Maintenance History page.\n" +
                " Check that there is record about changing Account status from 'Dormant' to 'Active'");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Account Status") >= 1,
                "'Account Status' row count is incorrect!");
        Assert.assertEquals(Pages.accountMaintenancePage().getRowNewValueByRowName("Account Status", 1),
                "Active","'Account Status' row count is not changed to Active.");
    }
}
