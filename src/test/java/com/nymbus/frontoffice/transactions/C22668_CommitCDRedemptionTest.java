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
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCDAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitGLCreditTransactionBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22668_CommitCDRedemptionTest extends BaseTest {
    private Transaction withdrawTransaction;
    private TransactionData transactionData;
    private double accruedInterest;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        Account cdAccount = new Account().setCDAccountData();
        cdAccount.setDateOpened(DateTime.getDateMinusDays(WebAdminActions.loginActions().getSystemDate(), 10));

        TransactionConstructor constructor = new TransactionConstructor(new GLDebitMiscCreditCDAccBuilder());

        // Set up transactions
        Transaction transaction = constructor.constructTransaction();
        transaction.setTransactionDate(cdAccount.getDateOpened());

        // Set up withdraw transactions
        constructor.setBuilder(new MiscDebitGLCreditTransactionBuilder());
        withdrawTransaction = constructor.constructTransaction();
        withdrawTransaction.getTransactionSource().setTransactionCode(TransactionCode.CD_REDEMPTION.getTransCode());
        withdrawTransaction.getTransactionSource().setAccountNumber(cdAccount.getAccountNumber());

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCDAccount(cdAccount);

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(cdAccount.getAccountNumber());

        // perform transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);
        Pages.tellerPage().setEffectiveDate(cdAccount.getDateOpened());
        Actions.transactionActions().clickCommitButton();
        Assert.assertFalse(Pages.tellerPage().isNotificationsPresent(), "Error message is visible!");
        Pages.tellerPage().closeModal();

        // Set transaction with amount value
        Actions.clientPageActions().searchAndOpenClientByName(cdAccount.getAccountNumber());
        accruedInterest = AccountActions.retrievingAccountData().getAccruedInterest();
        double transactionAmount = AccountActions.retrievingAccountData().getCurrentBalanceWithAccruedInterest();
        withdrawTransaction.getTransactionSource().setAmount(transactionAmount);
        withdrawTransaction.getTransactionDestination().setAmount(transactionAmount);
        transactionData = new TransactionData(withdrawTransaction.getTransactionDate(), withdrawTransaction.getTransactionDate(), "-", 0.00,
                transactionAmount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "22668, Commit CD redemption (balance + IENP)")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyCDRedemptionTransaction() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Go to Teller page and log in to the proof date");
        Actions.transactionActions().goToTellerPage();
        transactionData.setPostingDate(Pages.tellerModalPage().getProofDateValue());
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Select the following fund types: \n" +
                "- Source: Misc Debit \n" +
                "- Destination: any (e.g. GL credit)");
        logInfo("Step 4: Specify misc debit line item with the following values: \n" +
                "- CHK account from preconditions \n" +
                "- 227 Withdraw&Close trancode \n" +
                "and verify the Available Balance value in Account Details section");
        logInfo("Step 5: Specify Amount == account's current balance + IENP (e.g. $105)");
        int currentIndex = 0;
        Actions.transactionActions().setMiscDebitSourceForWithDraw(withdrawTransaction.getTransactionSource(), currentIndex);
        double availableBalance = Actions.transactionActions().getAvailableBalance();
        Assert.assertEquals(availableBalance, withdrawTransaction.getTransactionSource().getAmount(),
                "Available balance is incorrect!");

        logInfo("Step 6: Specify details for selected destination line item correctly (Amount should be the same as it was set in Debit Item)");
        Actions.transactionActions().setGLCreditDestination(withdrawTransaction.getTransactionDestination(), currentIndex);
        transactionData.setEffectiveDate(Pages.tellerPage().getEffectiveDate());

        logInfo("Step 7: Click [Commit Transaction] button");
        Actions.transactionActions().clickCommitButton();
        Assert.assertFalse(Pages.tellerPage().isNotificationsPresent(), "Error message is visible!");
        Pages.tellerPage().closeModal();

        logInfo("Step 8: Go to account used in source item and verify its: \n" +
                "- current balance \n" +
                "- available balance \n" +
                "- accrued interest \n" +
                "- account status \n" +
                "- account date closed");
        Actions.clientPageActions().searchAndOpenClientByName(withdrawTransaction.getTransactionSource().getAccountNumber());
        ClosedAccountData expectedClosedAccountData = new ClosedAccountData();
        ClosedAccountData actualClosedAccountData = AccountActions.retrievingAccountData().getClosedAccountDataForCDAccount();
        Assert.assertEquals(actualClosedAccountData, expectedClosedAccountData, "Closed account data is incorrect!");

        logInfo("Step 9: Open Account on Transactions tab and check transaction details");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        Assert.assertEquals(Pages.accountTransactionPage().getTransactionCodeByIndex(2), TransactionCode.INT_CD_REDEMPTION.getTransCode(),
                "Transaction 207 - Int Deposit is not displayed!");
        Assert.assertEquals(Pages.accountTransactionPage().getAmountSymbol(2), "+",
                "Transaction 207 - Int Deposit amount symbol is incorrect!");
        Assert.assertEquals(AccountActions.retrievingAccountData().getAmountValue(2), accruedInterest,
                "Transaction 207 - Int Deposit amount value is incorrect!");
        Assert.assertEquals(AccountActions.retrievingAccountData().getBalanceValue(2),
                AccountActions.retrievingAccountData().getAmountValue(1),
                "Transaction '207 - Int Deposit' balance and transaction '227 - Withdraw&Close' amount are not equal!");
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();
        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");
    }
}