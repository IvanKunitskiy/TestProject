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
import com.nymbus.newmodels.backoffice.Check;
import com.nymbus.newmodels.backoffice.FullCheck;
import com.nymbus.newmodels.cashier.CashierDefinedTransactions;
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
import com.nymbus.pages.settings.SettingsPage;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22710_BackOfficeOfficialChecksVoidOfficialCheckFromClientAccountWithFee extends BaseTest {
    private Transaction transaction;
    private BalanceDataForCHKAcc expectedSavingsBalanceData;
    private TransactionData savingsAccTransactionData;
    private Account savingsAccount;
    private double savingsTransactionAmount = 200.00;
    private double returnTransactionAmount = 100.00;
    private double fee = 5.00;
    private int checkAccountNumber;
    private Check check;
    private String name = "John";
    private FullCheck fullCheck;
    private String checkNumber;


    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Accounts
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        savingsAccount = new Account().setSavingsAccountData();
        Transaction depositSavingsTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        transaction = new TransactionConstructor(new WithdrawalGLDebitCHKAccBuilder()).constructTransaction();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);


        // Create account
        AccountActions.createAccount().createSavingsAccount(savingsAccount);

        // Set up transactions with account number
        transaction.getTransactionSource().setAmount(returnTransactionAmount);
        transaction.getTransactionDestination().setAmount(returnTransactionAmount);
        transaction.getTransactionSource().setAccountNumber(savingsAccount.getAccountNumber());
        depositSavingsTransaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());
        depositSavingsTransaction.getTransactionDestination().setTransactionCode("209 - Deposit");
        depositSavingsTransaction.getTransactionDestination().setAmount(savingsTransactionAmount);
        depositSavingsTransaction.getTransactionSource().setAmount(savingsTransactionAmount);

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

        // Set transaction with amount value
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccount.getAccountNumber());
        expectedSavingsBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        savingsAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "+", expectedSavingsBalanceData.getCurrentBalance(), returnTransactionAmount);

        //Check Official check and printer
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().clickViewAllPrinters();
        SettingsPage.printerPage().clickToPrinter("Xerox_Phaser_3260 Moscow 2nd floor");
        boolean isOfficial = SettingsPage.printerPage().checkIsOfficialPrinter();
        Assert.assertTrue(isOfficial,"Is not official printer");
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().clickViewControls();
        checkAccountNumber = Integer.parseInt(SettingsPage.officialComtrolPage().checkAccountNumber());

        //New Checks
        check = new Check();
        check.setDate(DateTime.getLocalDateOfPattern("MM/dd/yyyy"));
        check.setCheckType("Cashier Check");
        check.setPayee(name);
        check.setPurchaser(client.getInitials());
        check.setInitials(client.getNameForDebitCard());
        check.setAmount(returnTransactionAmount);
        check.setStatus("Outstanding");
        fullCheck = new FullCheck();
        fullCheck.fromCheck(check);
        fullCheck.setFee(fee);
        fullCheck.setCashPurchased("No");
        fullCheck.setRemitter("exautotest exautotest");
        fullCheck.setBranch(savingsAccount.getBankBranch());
        fullCheck.setDocumentType(client.getIndividualClientDetails().getDocuments().get(0).getIdType().getIdType());
        fullCheck.setDocumentID(client.getIndividualClientDetails().getDocuments().get(0).getIdNumber());
        fullCheck.setPhone(client.getIndividualClientDetails().getPhones().get(0).getPhoneNumber());
        Actions.loginActions().doLogOutProgrammatically();

        //Check CDT template
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        boolean templateNotExists = Actions.cashierDefinedActions().checkCDTTemplateIsExist(CashierDefinedTransactions.OFFICIAL_CHECK_FROM_SAVINGS);
        if (templateNotExists){
            boolean isCreated = Actions.cashierDefinedActions().createOfficialCheckFromSavings();
            Assert.assertTrue(isCreated, "CDT template not created");
        }

        //Create CDT  transaction
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().openProofDateLoginModalWindow();
        String bankBranch = Pages.tellerModalPage().getBankBranch();
        fullCheck.setBranch(bankBranch);
        Actions.transactionActions().doLoginProofDate();
        Pages.aSideMenuPage().waitForASideMenu();
        Pages.aSideMenuPage().clickCashierDefinedTransactionsMenuItem();
        Actions.cashierDefinedActions().createOfficialTransaction(CashierDefinedTransactions.OFFICIAL_CHECK_FROM_SAVINGS,
                transaction, false, name);
        expectedSavingsBalanceData.reduceAmount(transaction.getTransactionDestination().getAmount());
        Actions.transactionActions().clickCommitButton();
        Pages.verifyConductorModalPage().clickVerifyButton();
        checkNumber = Pages.confirmModalPage().getReprintCheckNumber();
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        Pages.confirmModalPage().clickYes();
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        Pages.confirmModalPage().clickYes();
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        Pages.confirmModalPage().clickNo();
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        Actions.loginActions().doLogOut();

    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 22710, testRunName = TEST_RUN_NAME)
    @Test(description = "C22710, BackOffice->Official Checks: Void official check from client account with fee")
    @Severity(SeverityLevel.CRITICAL)
    public void printTellerReceiptWithoutBalance() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Back Office -> Official Checks and search for the Transaction from the preconditions");
        Pages.aSideMenuPage().clickBackOfficeMenuItem();
        Pages.backOfficePage().clickOfficialChecks();

        logInfo("Step 3: Open Official check on Details");
        Pages.checkPage().clickToCheck(checkNumber);
        FullCheck fullCheckFromBankOffice = Actions.backOfficeActions().getFullCheckFromBankOffice();

        logInfo("Step 4: Click [Void] button");
        Pages.fullCheckPage().clickVoid();
        Assert.assertTrue(Pages.fullCheckPage().checkConfirmation(), "Confirmation is not present");
        Assert.assertFalse(Pages.fullCheckPage().isVoidDisabled(),"Void button is not disabled");

        logInfo("Step 5: Select Yes option and verify the Status field");
        Pages.fullCheckPage().clickYes();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        Assert.assertEquals(Pages.fullCheckPage().getStatus(),"Void", "Status doesn't match");


        logInfo("Step 6: Go to account used in DEBIT item and verify its:\n" +
                "- current balance\n" +
                "- available balance");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccount.getAccountNumber());
        BalanceDataForCHKAcc actualSavBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        expectedSavingsBalanceData.addAmount(returnTransactionAmount);
        Assert.assertEquals(actualSavBalanceData.getCurrentBalance(), expectedSavingsBalanceData.getCurrentBalance(),
                "Current balance doesn't match!");
        Assert.assertEquals(actualSavBalanceData.getAvailableBalance(), expectedSavingsBalanceData.getAvailableBalance(),
                "Available balance doesn't match!");

        logInfo("Step 7: Open account on the Transactions tab and verify the committed transaction");
        Pages.accountDetailsPage().clickTransactionsTab();
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualSavTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithBalanceSymbol();
        Assert.assertEquals(actualSavTransactionData, savingsAccTransactionData, "Transaction data doesn't match!");
//[TransactionData(postingDate=06/07/2021, effectiveDate=06/07/2021, amountSymbol=-, balance=100.0, amount=100.0)]
//[TransactionData(postingDate=06/07/2021, effectiveDate=06/07/2021, amountSymbol=+, balance=100.0, amount=5.0)]
    }


}
