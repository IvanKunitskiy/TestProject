package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCDAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCDAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.nymbus.core.utils.Functions.getCalculatedInterestAmount;

public class C23915_GLDebitMiscCreditCDAccTest extends BaseTest {
    private Transaction transaction;
    private BalanceDataForCDAcc balanceData;
    private TransactionData transactionData;
    private Account cdAccountData;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        Client client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        cdAccountData = new Account().setCDAccountData();
        transaction = new TransactionConstructor(new GLDebitMiscCreditCDAccBuilder()).constructTransaction();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.createClient().createClient(client);

        // Create account
        AccountActions.createAccount().createCDAccountForTransactionPurpose(cdAccountData);

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(cdAccountData.getAccountNumber());

        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);
        balanceData = AccountActions.retrievingAccountData().getBalanceDataForCDAcc();

        transactionData = new TransactionData(transaction.getTransactionDate(), transaction.getTransactionDate(), "+", balanceData.getCurrentBalance(),
                transaction.getTransactionDestination().getAmount());
    }

    @Test(description = "23915, Commit transaction GL Debit -> Misc Credit(on CD Account)")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTransactionGLDebitMiscCredit() {
        Actions.transactionActions().goToTellerPage();

        Actions.transactionActions().doLoginTeller();

        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);

        Actions.transactionActions().clickCommitButton();

        Assert.assertEquals(Pages.tellerPage().getModalText(),
                "Transaction was successfully completed.",
                "Transaction doesn't commit");

        Pages.tellerPage().closeModal();

        balanceData.addAmount(transaction.getTransactionDestination().getAmount());

        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);

        BalanceDataForCDAcc actualBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCDAcc();

        Assert.assertEquals(actualBalanceData, balanceData, "Balance data doesn't match!");

        String expectedPaymentAmount = getCalculatedInterestAmount(balanceData.getCurrentBalance(),
                                                                    Double.parseDouble(cdAccountData.getInterestRate()),
                                                                    AccountActions.retrievingAccountData().getDateLastInterestPaid(),
                                                                    Pages.accountDetailsPage().getDateNextInterest());

        String actualPaymentAmount = Pages.accountDetailsPage().getNextInterestPaymentAmount();

        Assert.assertEquals(actualPaymentAmount, expectedPaymentAmount, "Payment amount doesn't match!");

        transactionData.setBalance(balanceData.getCurrentBalance());

        AccountActions.retrievingAccountData().goToTransactionsTab();

        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();

        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");

        // TODO add verification for webadmin
    }
}
