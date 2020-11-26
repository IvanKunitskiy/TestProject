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
import com.nymbus.newmodels.cashier.CashierDefinedTransactions;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.WithdrawalGLDebitCHKAccBuilder;
import com.nymbus.newmodels.settings.teller.TellerLocation;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

public class C22714_CDTTellerSessionCommitSimpleCDTWithPrintNoticeOnEntry extends BaseTest {
    private Transaction transaction;
    private Transaction savingsTransaction;
    private BalanceDataForCHKAcc expectedBalanceData;
    private BalanceDataForCHKAcc expectedSavingsBalanceData;
    private TransactionData chkAccTransactionData;
    private TransactionData savingsAccTransactionData;
    private Account checkAccount;
    private Account savingsAccount;
    private double transactionAmount = 200.00;
    private double savingsTransactionAmount = 200.00;
    private double returnTransactionAmount = 100.00;
    private double fee = 3.00;
    private TellerLocation tellerLocation;
    private String userId;
    private IndividualClient client;


    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Accounts
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();
        checkAccount = new Account().setCHKAccountData();
        savingsAccount = new Account().setSavingsAccountData();
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        Transaction depositSavingsTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        transaction = new TransactionConstructor(new WithdrawalGLDebitCHKAccBuilder()).constructTransaction();
        savingsTransaction = new TransactionConstructor(new WithdrawalGLDebitCHKAccBuilder()).constructTransaction();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        Pages.aSideMenuPage().clickSettingsMenuItem();
        SettingsPage.mainPage().clickViewProfile();

        userId = SettingsPage.viewUserPage().getUSERIDValue();
        String bankBranch = SettingsPage.viewUserPage().getBankBranch();

        checkAccount.setBankBranch(bankBranch);
        savingsAccount.setBankBranch(bankBranch);
        checkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));
        Actions.loginActions().doLogOut();


        // Get bank info
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Pages.aSideMenuPage().clickSettingsMenuItem();
        SettingsPage.mainPage().clickViewAllTellerLocationsLink();
        SettingsPage.tellerLocationOverviewPage().clickRowByBranchName(bankBranch);
        tellerLocation = Actions.tellerBankBranchOverviewActions().getTellerLocation(bankBranch);
        Pages.aSideMenuPage().clickClientMenuItem();

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
    }


    @Test(description = "C226714, CDT+Teller Session - Commit simple CDT with print notice == on entry  ")
    @Severity(SeverityLevel.CRITICAL)
    public void printTellerReceiptWithoutBalance() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Cashier Defined Transactions screen and log in to proof date");
        Actions.transactionActions().loginTeller();
        Pages.aSideMenuPage().waitForASideMenu();
        Pages.aSideMenuPage().clickCashierDefinedTransactionsMenuItem();

        logInfo("Step 3: Search for template from preconditions and select it");
        logInfo("Step 4: Click on [Waive Fee] toggle button");
        logInfo("Step 5: Specify accounts from preconditions in source and destination line items;\n" +
                "set transaction amount less than Debit Account's Available Balance");
        Actions.cashierDefinedActions().createTransaction(CashierDefinedTransactions.TRANSFER_FROM_SAV_TO_CHK_Print_Notice_On_Entry,
                transaction, false);
        expectedBalanceData.reduceAmount(transaction.getTransactionDestination().getAmount());
        expectedSavingsBalanceData.addAmount(transaction.getTransactionDestination().getAmount());

        logInfo("Step 6: Click [Commit Transaction] button");
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 7: Verify the following fields are printed on the Notice 1st Page:\n" +
                "- Bank information to the left in the header\n" +
                "- CDT Template name\n" +
                "- Proof Date\n" +
                "- Account number - below the Bank Info\n" +
                "- Transaction details to the right in the header\n" +
                "- Name and Address of the Owner of Debit account at the bottom of the body");
        File file = Actions.noticeActions().saveNoticeImage();
        String noticeData = Actions.balanceInquiryActions().readBalanceInquiryImage(file);

        Assert.assertTrue(noticeData.contains(tellerLocation.getBankName()),"'Bank name' does not match");
        Assert.assertTrue(noticeData.contains(tellerLocation.getCity()),
                "'City' does not match");
        Assert.assertTrue(noticeData.contains(tellerLocation.getState()),
                "'State' does not match");
        Assert.assertTrue(noticeData.contains(tellerLocation.getZipCode()),
                "'Zip code' does not match");
        Assert.assertTrue(noticeData.contains(tellerLocation.getPhoneNumber()),
                "'Zip code' does not match");
        Assert.assertTrue(noticeData.contains(CashierDefinedTransactions.TRANSFER_FROM_SAV_TO_CHK_Print_Notice_On_Entry.getProduct()),
                "'CDT Template' doesn't match");
        Actions.transactionActions().openProofDateLoginModalWindow();
        Assert.assertTrue(noticeData.contains(Pages.tellerModalPage().getProofDateValue()),
                "'Proof date' doesn't match");
        Assert.assertTrue(noticeData.contains(savingsAccount.getAccountNumber()), "'Debit card number' doesn't" +
                " match");
        Assert.assertTrue(noticeData.contains(client.getIndividualType().getFirstName()), "'Name' doesn't" +
                " match");
        Assert.assertTrue(noticeData.contains(client.getIndividualType().getAddresses().stream().findFirst().get().getAddress()),
                "'Debit card number' doesn't match");




    }

}
