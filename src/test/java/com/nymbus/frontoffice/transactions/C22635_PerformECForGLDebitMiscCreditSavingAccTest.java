package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceData;
import com.nymbus.newmodels.transaction.verifyingModels.DatesData;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22635_PerformECForGLDebitMiscCreditSavingAccTest extends BaseTest {
    private Transaction transaction;
    private BalanceData balanceData;
    private TransactionData transactionData;
    private DatesData datesData;


    @BeforeMethod
    public void prepareTransactionData() {
        transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        transaction.getTransactionDestination().setAccountNumber("12400590175");
        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);
        datesData = AccountActions.retrievingAccountData().getDatesData();

        // perform transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        // Get balance info after transaction
        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);
        balanceData = AccountActions.retrievingAccountData().getBalanceData();
        transactionData = new TransactionData(transaction.getTransactionDate(),
                transaction.getTransactionDate(),
                "-",
                balanceData.getCurrentBalance(),
                transaction.getTransactionDestination().getAmount());
    }

    @Test(description = "C22635, Perform EC for GL Debit -> Misc Credit transaction (Savings Account)")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyErrorCorrectForGLDebitMiscCreditTransactionOnSavingAcc() {
        Actions.journalActions().goToJournalPage();

        Actions.journalActions().clickLastTransaction();

        Pages.journalDetailsPage().clickErrorCorrectButton();

        Pages.journalDetailsPage().waitForErrorCorrectButtonInvisibility();

        Assert.assertEquals(Actions.journalActions().getTransactionState(), "Void",
                   "Transaction state doesn't changed");

        balanceData.subtractAmount(transaction.getTransactionDestination().getAmount());

        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);

        BalanceData actualBalanceData = AccountActions.retrievingAccountData().getBalanceData();

        DatesData actualDatesData = AccountActions.retrievingAccountData().getDatesData();

        Assert.assertEquals(actualBalanceData, balanceData, "Balance data doesn't match!");

        Assert.assertEquals(actualDatesData.getLastDepositDate(), datesData.getLastDepositDate(),
                    "Last Deposit Date doesn't match!");

        Assert.assertEquals(actualDatesData.getLastActivityDate(), datesData.getLastActivityDate(),
                "Last Activity Date doesn't match!");

        transactionData.setBalance(balanceData.getCurrentBalance());

        AccountActions.retrievingAccountData().goToTransactionsTab();

        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();

        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");
    }
}
