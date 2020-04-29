package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
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
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        cdAccountData = new Account().setCDAccountData();
        transaction = new TransactionConstructor(new GLDebitMiscCreditCDAccBuilder()).constructTransaction();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

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
        logInfo("Step 2: Go to Teller page and log in to the proof date");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Select the following fund types: Source: GL Debit, Destination: Misc Credit");
        logInfo("Step 4: Fill in fields for source line item: select GL account, specify some amount, expand line item and specify Note");
        logInfo("Step 5: Fill in fields for destination line item: select regular account from preconditions, specify trancode, specify the same amount");
        Actions.transactionActions().createGlDebitMiscCreditTransaction(transaction);

        logInfo("Step 6: Do not change the Effective Date and click [Commit Transaction] button");
        Actions.transactionActions().clickCommitButton();
        Assert.assertEquals(Pages.tellerPage().getModalText(),
                "Transaction was successfully completed.",
                "Transaction doesn't commit");
        Pages.tellerPage().closeModal();
        balanceData.addAmount(transaction.getTransactionDestination().getAmount());

        logInfo("Step 7: Close Transaction Receipt popup and" +
                "Go to the account used in Misc Credit item. Verify such fields: current balance, Original Balance, Total Contributions for Life of Account");
        AccountActions.editAccount().navigateToAccountDetails(transaction.getTransactionDestination().getAccountNumber(), false);
        BalanceDataForCDAcc actualBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCDAcc();
        Assert.assertEquals(actualBalanceData, balanceData, "Balance data doesn't match!");

        logInfo("Step 8: Verify Next Interest Payment Amount field value");
        String expectedPaymentAmount = getCalculatedInterestAmount(balanceData.getCurrentBalance(),
                                                                    Double.parseDouble(cdAccountData.getInterestRate()),
                                                                    AccountActions.retrievingAccountData().getDateLastInterestPaid(),
                                                                    Pages.accountDetailsPage().getDateNextInterest());
        String actualPaymentAmount = Pages.accountDetailsPage().getNextInterestPaymentAmount();
        Assert.assertEquals(actualPaymentAmount, expectedPaymentAmount, "Payment amount doesn't match!");

        logInfo("Step 9: Open account on Transactions tab and verify that transaction is written on transactions history page");
        transactionData.setBalance(balanceData.getCurrentBalance());
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();
        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");

        // TODO add verification for webadmin
    }
}
