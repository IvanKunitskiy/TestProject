package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.models.client.Client;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitGLCreditTransactionBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22636_MiscDebitGLCreditCHKAccTest extends BaseTest {
    private BalanceDataForCHKAcc balanceData;
    private TransactionData transactionData;
    private Transaction miscDebitGLCreditTransaction;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        Client client = new Client().setDefaultClientData();
        client.setClientStatus("Member");
        client.setClientType("Individual");
        Account checkAccount = new Account().setCHKAccountData();
        miscDebitGLCreditTransaction = new TransactionConstructor(new MiscDebitGLCreditTransactionBuilder()).constructTransaction();
        Transaction glDebitMiscCreditTransaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.createClient().createClient(client);

        // Create account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);

        // Set up transaction with account number
        glDebitMiscCreditTransaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());
        miscDebitGLCreditTransaction.getTransactionSource().setAccountNumber(checkAccount.getAccountNumber());

        // Perform transaction
        Actions.transactionActions().performGLDebitMiscCreditTransaction(glDebitMiscCreditTransaction);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "22636, Commit transaction Misc Debit -> GL Credit (from CHK Acccount)")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTransactionMiscDebitGLCreditCHKAcc() {
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Actions.clientPageActions().searchAndOpenClientByName(miscDebitGLCreditTransaction.getTransactionSource().getAccountNumber());
    }
}
