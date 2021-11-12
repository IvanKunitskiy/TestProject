package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.cashier.CashierDefinedTransactions;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceData;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Petro")
public class C22701_JournalPerformEcForSimpleCdtTransferWithFeeTest extends BaseTest {

    private Account savingsAccount;
    private Account chkAccount;
    private BalanceData savingsBalanceData;
    private BalanceDataForCHKAcc chkBalanceData;
    private TransactionData savingsAccTransactionData;
    private TransactionData chkAccTransactionData;
    private final double fee = 10.00;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up account data
        chkAccount = new Account().setCHKAccountData();
        savingsAccount = new Account().setSavingsAccountData();

        // Set up transaction data for increasing the accounts balances
        Transaction savingsTransaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        savingsTransaction.getTransactionDestination().setTransactionCode(TransactionCode.ATM_DEPOSIT_209.getTransCode());
        savingsTransaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());

        TransactionSource savingsToChkTransactionSource = new TransactionSource();
        savingsToChkTransactionSource.setAccountNumber(savingsAccount.getAccountNumber());
        savingsToChkTransactionSource.setTransactionCode(TransactionCode.DEBIT_TRANSFER_221.getTransCode());
        savingsToChkTransactionSource.setAmount(10.00);

        TransactionDestination savingsToChkTransactionDestination = new TransactionDestination();
        savingsToChkTransactionDestination.setAccountNumber(chkAccount.getAccountNumber());
        savingsToChkTransactionDestination.setTransactionCode(TransactionCode.CREDIT_TRANSFER_101.getTransCode());
        savingsToChkTransactionDestination.setAmount(10.00);

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set the account data
        Pages.aSideMenuPage().clickSettingsMenuItem();
        SettingsPage.mainPage().clickViewProfile();
        String bankBranch = SettingsPage.viewUserPage().getBankBranch();
        savingsAccount.setBankBranch(bankBranch);
        chkAccount.setBankBranch(bankBranch);
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));
        Pages.aSideMenuPage().clickClientMenuItem();
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));
        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create Savings account
        AccountActions.createAccount().createSavingsAccount(savingsAccount);

        // Perform transaction to assign money to savings account
        Actions.transactionActions().performGLDebitMiscCreditTransaction(savingsTransaction);
        Actions.loginActions().doLogOutProgrammatically();

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Get savings balance data
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccount.getAccountNumber());
        savingsBalanceData = AccountActions.retrievingAccountData().getBalanceData();

        // Create CHK account
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        AccountActions.createAccount().createCHKAccount(chkAccount);

        //Check CDT template
        boolean templateNotExists = Actions.cashierDefinedActions().checkCDTTemplateIsExist(CashierDefinedTransactions.TRANSFER_FROM_SAV_TO_CHK);
        if (templateNotExists){
            boolean isCreated = Actions.cashierDefinedActions().createIncomingWireToSavings();
            Assert.assertTrue(isCreated, "CDT template not created");
        }

        // Create simple CDT transfer with fee transaction
        Pages.aSideMenuPage().clickCashierDefinedTransactions();
        Actions.transactionActions().doLoginTeller();
        Actions.cashierDefinedActions().setTellerOperation(CashierDefinedTransactions.TRANSFER_FROM_SAV_TO_CHK.getOperation());
        performCdtWithFeeTransaction(savingsToChkTransactionSource, savingsToChkTransactionDestination, 0);
        Actions.loginActions().doLogOutProgrammatically();

        // Get checking balance data
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenClientByName(chkAccount.getAccountNumber());
        chkBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        // Update retrieved balances after transaction
        savingsAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "+", savingsBalanceData.getCurrentBalance(), fee);
        chkAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", chkBalanceData.getCurrentBalance() - fee, fee);
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 22701, testRunName = TEST_RUN_NAME)
    @Test(description = "C22701, Journal - Perform EC for simple CDT transfer with fee")
    @Severity(SeverityLevel.CRITICAL)
    public void journalPerformEcForSimpleCdtTransferWithFeeTest() {

        logInfo("Step 1: Log in to the system as User1 from preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to the Journal page and log in to the proof date");
        Actions.journalActions().goToJournalPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Search for the transaction from the preconditions and click on it to open on Details");
        Actions.journalActions().applyFilterByAccountNumber(savingsAccount.getAccountNumber());
        Actions.journalActions().clickLastTransaction();

        logInfo("Step 4: Press [Error Correct] button");
        Pages.journalDetailsPage().clickErrorCorrectButton();
        Pages.journalDetailsPage().waitForErrorCorrectButtonInvisibility();
        SelenideTools.sleep(Constants.SMALL_TIMEOUT);
        chkBalanceData.reduceAmount(fee);

        logInfo("Step 5: Go to account used in DEBIT item and verify its:\n" +
                "- current balance\n" +
                "- available balance");
        AccountActions.editAccount().navigateToAccountDetails(savingsAccount.getAccountNumber(), false);
        BalanceData actualSavingsBalanceData = AccountActions.retrievingAccountData().getBalanceData();
        Assert.assertEquals(actualSavingsBalanceData.getCurrentBalance(), savingsBalanceData.getCurrentBalance(),
                "Current balance doesn't match!");
        Assert.assertEquals(actualSavingsBalanceData.getAvailableBalance(), savingsBalanceData.getAvailableBalance(),
                "Available balance doesn't match!");

        logInfo("Step 6: Open account on the Transactions tab and verify the transaction");
        Pages.accountDetailsPage().clickTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualSavingsTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset, 1);
        Assert.assertEquals(actualSavingsTransactionData, savingsAccTransactionData, "Transaction data doesn't match!");

        logInfo("Step 7: Go to account used in CREDIT item and verify its:\n" +
                "- current balance\n" +
                "- available balance");
        Pages.aSideMenuPage().clickClientMenuItem();
        AccountActions.editAccount().navigateToAccountDetails(chkAccount.getAccountNumber(), false);
        BalanceDataForCHKAcc actualChkBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Assert.assertEquals(actualChkBalanceData.getCurrentBalance(), chkBalanceData.getCurrentBalance(),
                "Current balance doesn't match!");
        Assert.assertEquals(actualChkBalanceData.getAvailableBalance(), chkBalanceData.getAvailableBalance(),
                "Available balance doesn't match!");

        logInfo("Step 8: Open account on the Transactions tab and verify the transaction");
        Pages.accountDetailsPage().clickTransactionsTab();
        offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualChkTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset, 1);
        Assert.assertEquals(actualChkTransactionData, chkAccTransactionData, "Transaction data doesn't match!");
    }

    public void performCdtWithFeeTransaction(TransactionSource source, TransactionDestination destination, int index) {
        Actions.cashierDefinedActions().setTransactionSource(source, index);
        Actions.cashierDefinedActions().setTransactionDestination(destination, index);
        Actions.cashierDefinedActions().clickCommitButton();
        Pages.alertMessageModalPage().clickOkButton();
    }
}
