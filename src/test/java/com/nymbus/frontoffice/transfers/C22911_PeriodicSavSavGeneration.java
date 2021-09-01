package com.nymbus.frontoffice.transfers;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.transfers.TransfersActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.transfer.Transfer;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.generation.transfers.TransferBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceData;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transfers")
@Owner("Dmytro")
public class C22911_PeriodicSavSavGeneration extends BaseTest {

    private Account savingsAccount;
    private Account savingsAccount2;
    private String clientID;
    private Transfer transfer;
    private final double amount = 3000.00;
    private TransactionData savingsAccTransactionData;
    private TransactionData savingsAccTransactionData2;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        Transaction depositSavingsTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();

        // Set up accounts
        savingsAccount = new Account().setSavingsAccountData();
        savingsAccount2 = new Account().setSavingsAccountData();

        // Set up transfer
        TransferBuilder transferBuilder = new TransferBuilder();
        transfer = transferBuilder.getTransfer();
        transfer.setAmount("2000.00");
        transfer.setFromAccount(savingsAccount);
        transfer.setToAccount(savingsAccount2);
        transfer.setNextDateOfTransfer(WebAdminActions.loginActions().getSystemDate());


        // Create client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Set products
        savingsAccount2.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));

        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        clientID = Pages.clientDetailsPage().getClientID();

        // Create Savings accounts and logout
        AccountActions.createAccount().createSavingsAccount(savingsAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createSavingsAccount(savingsAccount2);

        depositSavingsTransaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());
        depositSavingsTransaction.getTransactionDestination().setTransactionCode("209 - Deposit");
        depositSavingsTransaction.getTransactionDestination().setAmount(amount);
        depositSavingsTransaction.getTransactionSource().setAmount(amount);

        // Perform deposit transactions
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositSavingsTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        depositSavingsTransaction.getTransactionDestination().setAccountNumber(savingsAccount2.getAccountNumber());
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositSavingsTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();
        savingsAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", amount - Double.parseDouble(transfer.getAmount()), Double.parseDouble(transfer.getAmount()));
        savingsAccTransactionData2 = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "+", amount + Double.parseDouble(transfer.getAmount()), Double.parseDouble(transfer.getAmount()));
    }

    private final String TEST_RUN_NAME = "Transfers";

    @TestRailIssue(issueID = 22911, testRunName = TEST_RUN_NAME)
    @Test(description = "C22911, Periodic Sav > Sav generation (one time pay)")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewClientTransfer() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the Client from the precondition and open his profile on the Transfers tab");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(clientID);
        Pages.accountNavigationPage().clickTransfersTab();

        logInfo("Step 3: Click [New Transfer] button");
        Pages.transfersPage().clickNewTransferButton();

        logInfo("Step 4: Select:\n" +
                "- Transfer Type: Transfer\n" +
                "- Next Date of Transfer - Current Date\n" +
                "- From Account - Savings Account#1 from the precondition\n" +
                "- To Account - Savings Account#2 from the precondition\n" +
                "- Frequency - One Time pay\n" +
                "- Amount - any (but less then Savings Account#1 Available Balance)\n" +
                "click [Save] button");
        TransfersActions.addNewTransferActions().setTransferType(transfer);
        TransfersActions.addNewTransferActions().setTransferFromAccount(transfer);
        TransfersActions.addNewTransferActions().setTransferToAccount(transfer);
        Pages.newTransferPage().setNextDateOfTransfer(transfer.getNextDateOfTransfer());
        TransfersActions.addNewTransferActions().setTransferFrequency(transfer);
        Pages.newTransferPage().setAmount(transfer.getAmount());
        Pages.newTransferPage().clickSaveButton();
        Actions.loginActions().doLogOut();

        logInfo("Step 5: Search for the Savings account#1 from the precondition (From Account) and verify its:\n" +
                "- current balance\n" +
                "- available balance");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccount.getAccountNumber());
        BalanceData actualSavBalanceData = AccountActions.retrievingAccountData().getBalanceData();

        TestRailAssert.assertTrue(actualSavBalanceData.getCurrentBalance() == (amount - Double.parseDouble(transfer.getAmount())),
                new CustomStepResult("Current balance doesn't match!", "Current balance is match!"));
        TestRailAssert.assertTrue(actualSavBalanceData.getAvailableBalance() == (amount - Double.parseDouble(transfer.getAmount())),
                new CustomStepResult("Available balance doesn't match!", "Available balance is match!"));

        logInfo("Step 6: Open From Account on the Transactions tab and verify committed transaction");
        Pages.accountDetailsPage().clickTransactionsTab();
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualSavTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithBalanceSymbol();
        TestRailAssert.assertTrue(actualSavTransactionData.equals(savingsAccTransactionData),
                new CustomStepResult("Transaction data doesn't match!", String.format("Transaction data is match! Expected %s, actual %s.",
                        savingsAccTransactionData, actualSavTransactionData)));

        logInfo("Step 7: Search for the Savings account#2 from the precondition (To Account) and verify its:\n" +
                "- current balance\n" +
                "- available balance");
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccount2.getAccountNumber());
        actualSavBalanceData = AccountActions.retrievingAccountData().getBalanceData();

        TestRailAssert.assertTrue(actualSavBalanceData.getCurrentBalance() == (amount + Double.parseDouble(transfer.getAmount())),
                new CustomStepResult("Current balance doesn't match!", "Current balance is match!"));
        TestRailAssert.assertTrue(actualSavBalanceData.getAvailableBalance() == (amount + Double.parseDouble(transfer.getAmount())),
                new CustomStepResult("Available balance doesn't match!", "Available balance is match!"));

        logInfo("Step 8: Open To Account on the Transactions tab and verify committed transaction");
        Pages.accountDetailsPage().clickTransactionsTab();
        AccountActions.retrievingAccountData().goToTransactionsTab();
        actualSavTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithBalanceSymbol();
        TestRailAssert.assertTrue(actualSavTransactionData.equals(savingsAccTransactionData2),
                new CustomStepResult("Transaction data doesn't match!", "Transaction data is match!"));
    }
}
