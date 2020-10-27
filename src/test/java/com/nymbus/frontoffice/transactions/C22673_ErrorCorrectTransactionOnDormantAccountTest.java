package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
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
public class C22673_ErrorCorrectTransactionOnDormantAccountTest extends BaseTest {

    private String accountNumberWithDormantStatus;
    private Transaction transaction;
    private BalanceDataForCHKAcc accountBalanceData;
    private TransactionData transactionData;

    @BeforeMethod
    public void preCondition() {

        // Set up transaction
        transaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        transaction.getTransactionDestination().setTransactionCode(TransactionCode.ATM_DEPOSIT_209.getTransCode());

        // Get the dormant account from Web Admin
        accountNumberWithDormantStatus = WebAdminActions.webAdminUsersActions().getAccountWithDormantStatus(1);

        // Transaction misc
        transaction.getTransactionDestination().setAccountNumber(accountNumberWithDormantStatus);

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Get the current and available balance of retrieved Dormant account
        Actions.clientPageActions().searchAndOpenClientByName(accountNumberWithDormantStatus);
        accountBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        // Set transaction code and logout
        Actions.transactionActions().setTransactionCode(transaction);

        // Perform transaction using the captured dormant account -> Log out
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();
        Actions.transactionActions().fillingSupervisorModal(userCredentials);
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOut();

        // Set transaction data
        transactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                DateTime.getLocalDateOfPattern("MM/dd/yyyy"), "-",
                accountBalanceData.getCurrentBalance(),
                transaction.getTransactionDestination().getAmount());
    }

    @Test(description = "C22673, Error correct transaction on dormant account")
    @Severity(SeverityLevel.CRITICAL)
    public void errorCorrectTransactionOnDormantAccount() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
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
        Assert.assertEquals(Actions.journalActions().getTransactionState(), "Void",
                "Transaction state hasn't changed");

        logInfo("Step 5: Go to dormant account used in transaction and verify its:\n" +
                "- current balance\n" +
                "- available balance\n" +
                "- account status");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(accountNumberWithDormantStatus);
        Assert.assertEquals(AccountActions.retrievingAccountData().getCurrentBalance(),
                accountBalanceData.getCurrentBalance(), "CHK account current balance is not correct!");
        Assert.assertEquals(AccountActions.retrievingAccountData().getAvailableBalance(),
                accountBalanceData.getAvailableBalance(), "CHK account available balance is not correct!");
        Assert.assertEquals(Pages.accountDetailsPage().getAccountStatus(), "Dormant",
                "Account status is not 'Dormant'");

        logInfo("Step 6: Open Account on the Transactions History tab");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset, 1);
        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");

        logInfo("Step 7: Go to Account Maintenance-> Maintenance History page.\n" +
                " Check that there is record about changing Account status from 'Active' to 'Dormant'");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Account Status") >= 2,
                "'Account Status' row count is incorrect!");
        Assert.assertEquals(Pages.accountMaintenancePage().getRowNewValueByRowName("Account Status", 1),
                "Dormant","'Account Status' row count is not changed to Dormant.");
    }
}
