package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.transfers.TransfersActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.transfer.InsufficientFundsTransfer;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.WithdrawalGLDebitCHKAccBuilder;
import com.nymbus.newmodels.generation.transfers.TransferBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22676_CommitNSFTransactionOnAccountWithToOnNSFTransfer extends BaseTest {
    private Transaction transaction;
    private Transaction savingsTransaction;
    private BalanceDataForCHKAcc expectedBalanceData;
    private BalanceDataForCHKAcc expectedSavingsBalanceData;
    private TransactionData chkAccTransactionData;
    private TransactionData savingsAccTransactionData;
    private Account checkAccount;
    private Account savingsAccount;
    private double transactionAmount = 100.00;
    private double savingsTransactionAmount = 2000.00;
    private double returnTransactionAmount = 1500.00;
    private InsufficientFundsTransfer insufficientFundsTransfer;


    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Accounts
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        checkAccount = new Account().setCHKAccountData();
        savingsAccount = new Account().setSavingsAccountData();
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        Transaction depositSavingsTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        transaction = new TransactionConstructor(new WithdrawalGLDebitCHKAccBuilder()).constructTransaction();
        savingsTransaction = new TransactionConstructor(new WithdrawalGLDebitCHKAccBuilder()).constructTransaction();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        checkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);


        // Create account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);
        Pages.accountDetailsPage().clickAccountsLink();
        AccountActions.createAccount().createSavingsAccount(savingsAccount);

        // Set up transactions with account number
        depositTransaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());
        depositTransaction.getTransactionDestination().setAmount(transactionAmount);
        depositTransaction.getTransactionSource().setAmount(transactionAmount);
        transaction.getTransactionSource().setAccountNumber(checkAccount.getAccountNumber());
        transaction.getTransactionSource().setAmount(returnTransactionAmount);
        transaction.getTransactionDestination().setAmount(returnTransactionAmount);
        depositSavingsTransaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());
        depositSavingsTransaction.getTransactionDestination().setTransactionCode("209 - Deposit");
        depositSavingsTransaction.getTransactionDestination().setAmount(savingsTransactionAmount);
        depositSavingsTransaction.getTransactionSource().setAmount(savingsTransactionAmount);

        // Perform deposit transactions
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().createTransaction(depositSavingsTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set transaction with amount value
        Actions.clientPageActions().searchAndOpenClientByName(checkAccount.getAccountNumber());
        expectedBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        chkAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedBalanceData.getCurrentBalance(), returnTransactionAmount);

        Actions.clientPageActions().searchAndOpenClientByName(savingsAccount.getAccountNumber());
        expectedSavingsBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        savingsAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedSavingsBalanceData.getCurrentBalance(), savingsTransactionAmount);
        Actions.loginActions().doLogOut();

        // Set up transfer
        TransferBuilder transferBuilder = new TransferBuilder();
        insufficientFundsTransfer = transferBuilder.getInsufficientFundsTransfer();
        insufficientFundsTransfer.setFromAccount(savingsAccount);
        insufficientFundsTransfer.setToAccount(checkAccount);

        //Create transfer
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.clientPageActions().searchAndOpenIndividualClientByID(checkAccount.getAccountNumber());
        Pages.accountNavigationPage().clickTransfersTab();
        Pages.transfersPage().clickNewTransferButton();
        TransfersActions.addNewTransferActions().setInsufficientFundsTransferType(insufficientFundsTransfer);
        TransfersActions.addNewTransferActions().setInsufficientFundsFromAccount(insufficientFundsTransfer);
        TransfersActions.addNewTransferActions().setInsufficientFundsToAccount(insufficientFundsTransfer);
        Pages.newTransferPage().setNearestAmount(insufficientFundsTransfer.getNearestAmount());
        Pages.newTransferPage().setMaxAmount(insufficientFundsTransfer.getAmountToTransfer());
        Pages.newTransferPage().setTransferCharge(insufficientFundsTransfer.getTransferCharge());
        Pages.newTransferPage().clickSaveButton();
        Pages.transfersPage().clickTransferInTheListByType(insufficientFundsTransfer.getTransferType().getTransferType());
        Pages.accountNavigationPage().clickAccountsTab();
        Pages.clientDetailsPage().openAccountByNumber(checkAccount.getAccountNumber());
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22676, Commit NSF transaction on account with To On NSF transfer")
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
        logInfo("Step 4: Select account1 from preconditions in source item and specify amount > Account's " +
                "Available balance BUT less than sum of Available balance of Account1 + Available balance of Account2 " +
                "(used as account FROM in NSF transfer from preconditions) (e.g. $1500)");
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

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 8: Go to Account1 used in source item and verify its:" +
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

        logInfo("Step 10: Go to Account2 (used as account FROM in NSF transfer from preconditions) " +
                "and open it on Instructions tab;\n" +
                "Verify generated Hold");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccount.getAccountNumber());
        Pages.accountDetailsPage().clickInstructionsTab();
        int instructionNumber = 2;
        Pages.accountInstructionsPage().clickInstructionInListByIndex(instructionNumber);
        String holdAmount = Pages.accountInstructionsPage().getHoldAmount();
        Assert.assertEquals(Double.parseDouble(holdAmount), returnTransactionAmount - transactionAmount,
                "Amount doesn't match!");

        String notes = Pages.accountInstructionsPage().getNotes();
        String expectedNotes = "Overdraft Protection for Account #" +
                checkAccount.getAccountNumber().substring(checkAccount.getAccountNumber().length() - 4);
        Assert.assertEquals(notes, expectedNotes, "Notes doesn't match!");

        String createdInstructionExpirationDate = Pages.accountInstructionsPage().getCreatedInstructionExpirationDate(instructionNumber);
        Assert.assertEquals(createdInstructionExpirationDate, DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "Expiration date doesn't match!");

        logInfo("Step 11: Verify available balance of Account2 (used as account FROM in NSF transfer from preconditions)");
        Pages.accountDetailsPage().clickDetailsTab();
        BalanceDataForCHKAcc actualSavingsBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        Assert.assertEquals(actualSavingsBalanceData.getAvailableBalance(),
                savingsTransactionAmount - (returnTransactionAmount - transactionAmount) -
                Double.parseDouble(insufficientFundsTransfer.getTransferCharge()),
                "Available balance doesn't match!");
    }

}
