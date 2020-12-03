package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.WithdrawalGLDebitCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C22675_CommitNSFTransactionOnAccountWithAutomaticOverdraft extends BaseTest {
    private Transaction transaction;
    private BalanceDataForCHKAcc expectedBalanceData;
    private TransactionData chkAccTransactionData;
    private Account checkAccount;
    private double transactionAmount = 150.00;
    private String overdraftLimit;
    private double overdraftLimitSum;


    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        checkAccount = new Account().setCHKAccountData();
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        transaction = new TransactionConstructor(new WithdrawalGLDebitCHKAccBuilder()).constructTransaction();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        checkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        overdraftLimit = "$ 500.00";

        // Create account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);
        AccountActions.editAccount().editOverdraftStatusAndLimit(overdraftLimit);

        // Set up transaction with account number
        depositTransaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());
        transaction.getTransactionSource().setAccountNumber(checkAccount.getAccountNumber());
        transaction.getTransactionSource().setAmount(transactionAmount);
        transaction.getTransactionDestination().setAmount(transactionAmount);
        overdraftLimitSum = Double.parseDouble(overdraftLimit.substring(overdraftLimit.indexOf("$") + 1)) +
                depositTransaction.getTransactionSource().getAmount();

        // Perform deposit transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set transaction with amount value
        Actions.clientPageActions().searchAndOpenClientByName(checkAccount.getAccountNumber());
        expectedBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        chkAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedBalanceData.getCurrentBalance(), transactionAmount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22675, Commit NSF transaction on account with automatic overdraft")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyNSFTransaction() {
        logInfo("Step 1: Log in to the system as the user from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller page and log in to the proof date");
        Actions.transactionActions().goToTellerPage();
        chkAccTransactionData.setPostingDate(Pages.tellerModalPage().getProofDateValue());
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Select the folowing fund types: \n" +
                "- Source: Withdrawal \n" +
                "- Destination: any (e.g. Gl/Credit)");
        logInfo("Step 4: Select account from preconditions in source item and specify amount > available " +
                "balance BUT less than sum of available balance + Automatic Overdraft Limit (e.g. $250)");
        int currentIndex = 0;
        Actions.transactionActions().setWithDrawalSource(transaction.getTransactionSource(), currentIndex);

        logInfo("Step 5: Specify the same amount for destination item, set valid GL account# and fill in Notes field with any data");
        Actions.transactionActions().setGLCreditDestination(transaction.getTransactionDestination(), currentIndex);
        chkAccTransactionData.setEffectiveDate(Pages.tellerPage().getEffectiveDate());

        logInfo("Step 6: Click [Commit Transaction] button");
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 7: Click [OK] button");
        Actions.transactionActions().confirmTransaction();
        Pages.tellerPage().closeModal();
        expectedBalanceData.reduceAmount(transaction.getTransactionDestination().getAmount());
        overdraftLimitSum = overdraftLimitSum - transaction.getTransactionDestination().getAmount();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 8: Go to account used in source item and verify its:" +
                "- current balance" +
                "- available balance");
        Actions.clientPageActions().searchAndOpenClientByName(checkAccount.getAccountNumber());
        BalanceDataForCHKAcc actualBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        Assert.assertEquals(actualBalanceData.getCurrentBalance(), expectedBalanceData.getCurrentBalance(),
                "Current balance doesn't match!");
        Assert.assertEquals(actualBalanceData.getAvailableBalance(), expectedBalanceData.getAvailableBalance(),
                "Available balance doesn't match!");

        logInfo("Step 9: Open account on the Transactions tab and verify the committed transaction and Balance");
        Pages.accountDetailsPage().clickTransactionsTab();
        chkAccTransactionData.setBalance(expectedBalanceData.getCurrentBalance());
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithBalanceSymbol();
        Assert.assertEquals(actualTransactionData, chkAccTransactionData, "Transaction data doesn't match!");

        logInfo("Step 10: Go to Teller page again and select Account from the precondition in the Sorce item");
        logInfo("Step 11: Verify Account Quick View in the right part of the item\n" +
                "Make sure that Automatic Overdraft Limit (Available value - value after '/' sign) was degreased by the Amount of NSF");
        Actions.transactionActions().goToTellerPage();
        chkAccTransactionData.setPostingDate(Pages.tellerModalPage().getProofDateValue());
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().setWithDrawalSource(transaction.getTransactionSource(), currentIndex);

        double automaticOverdraftLimit = Double.parseDouble(Actions.transactionActions().getAutomaticOverdraftLimit());
        Assert.assertEquals(automaticOverdraftLimit, overdraftLimitSum,
                "Overdraft limit doesn't match!");
    }
}
