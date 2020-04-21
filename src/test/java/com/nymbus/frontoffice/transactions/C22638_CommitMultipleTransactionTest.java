package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.generation.tansactions.MultipleTransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.CashInMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitCashDepositMultipleTransactionBuilder;
import com.nymbus.newmodels.transaction.MultipleTransaction;
import com.nymbus.newmodels.transaction.Transaction;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22638_CommitMultipleTransactionTest extends BaseTest {
    private MultipleTransaction multipleTransaction;
    private Account chkAccount;
    private Account saveAccount;
    private Account cdAccount;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        // Transaction transaction = new TransactionConstructor(new CashInMiscCreditCHKAccBuilder()).constructTransaction();
        multipleTransaction = new MultipleTransactionConstructor(new MiscDebitCashDepositMultipleTransactionBuilder()).constructTransaction();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // perform transaction
       // Actions.transactionActions().performCashInMiscCreditTransaction(transaction);

    }

    @Test(description = "C22638,  Commit transaction with multiple sources and destinations")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyTransactionWithMultipleSourcesAndDestinations() {
        Actions.transactionActions().performMultipleTransaction(multipleTransaction);
    }
}
