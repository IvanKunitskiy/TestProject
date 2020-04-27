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
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitGLCreditTransactionBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.ExtendedBalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22636_MiscDebitGLCreditCHKAccTest extends BaseTest {
    private Transaction miscDebitGLCreditTransaction;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        Account checkAccount = new Account().setCHKAccountData();
        miscDebitGLCreditTransaction = new TransactionConstructor(new MiscDebitGLCreditTransactionBuilder()).constructTransaction();
        Transaction glDebitMiscCreditTransaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);

        // Set up transaction with account number
        glDebitMiscCreditTransaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());
        miscDebitGLCreditTransaction.getTransactionSource().setAccountNumber(checkAccount.getAccountNumber());

        // Perform transaction
        Actions.transactionActions().performGLDebitMiscCreditTransaction(glDebitMiscCreditTransaction);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "22636, Commit transaction Misc Debit -> GL Credit (from CHK Account)")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTransactionMiscDebitGLCreditCHKAcc() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.clientPageActions().searchAndOpenClientByName(miscDebitGLCreditTransaction.getTransactionSource().getAccountNumber());
        ExtendedBalanceDataForCHKAcc balanceData = AccountActions.retrievingAccountData().getExtendedBalanceDataForCHKAcc();

        logInfo("Balance data " + balanceData.toString());

        logInfo("Step 2: Go to Teller screen and log in to proof date");
        logInfo("Step 3: Select the following fund types:\n" +
                         "- Source: Misc Debit \n" +
                         "- Destination: GL Credit");
        logInfo("Step 4: Fill in fields for source line item: \n" +
                "- select regular account from preconditions (e.g. CHK account) \n" +
                "- specify trancode (e.g. 119 - Debit Memo) \n" +
                "- specify some amount which is less than available balance of the selected account (e.g. $90.00)");
        logInfo("Step 5: Fill in fields for destination line item: \n" +
                "- select GL account \n" +
                "- specify the same amount (e.g. $90.00) \n" +
                "- expand line item and specify Note");
        logInfo("Step 6: Click [Commit Transaction] button");
        Actions.transactionActions().performMiscDebitGLCreditTransaction(miscDebitGLCreditTransaction);

        balanceData.reduceAmount(miscDebitGLCreditTransaction.getTransactionSource().getAmount());
        TransactionData transactionData = new TransactionData(miscDebitGLCreditTransaction.getTransactionDate(),
                miscDebitGLCreditTransaction.getTransactionDate(), "-",
                balanceData.getCurrentBalance(),
                miscDebitGLCreditTransaction.getTransactionDestination().getAmount());
        Actions.clientPageActions().searchAndOpenClientByName(miscDebitGLCreditTransaction.getTransactionSource().getAccountNumber());

        ExtendedBalanceDataForCHKAcc actualBalanceDate = AccountActions.retrievingAccountData().getExtendedBalanceDataForCHKAcc();

        logInfo("Step 7: Close Transaction Receipt popup and \n" +
                "Go to the account used in Misc Debit item. Verify such fields: \n" +
                "- current balance \n" +
                "- available balance \n" +
                "- Collected Balance \n" +
                "- Average Balance");
        Assert.assertEquals(actualBalanceDate, balanceData, "Balance data doesn't match!");

        logInfo("Step 8: Open account on Transactions history and verify that transaction is written on transactions history page");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionData();
        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");

        // TODO add verification for webadmin
    }
}