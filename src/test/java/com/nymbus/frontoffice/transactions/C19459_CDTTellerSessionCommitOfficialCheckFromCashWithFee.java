package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
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
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.generation.transactions.builder.WithdrawalGLDebitCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.Denominations;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;

public class C19459_CDTTellerSessionCommitOfficialCheckFromCashWithFee extends BaseTest {
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
    private String clientRootId;


    @BeforeMethod
    public void prepareTransactionData() {
        //Check CFMIntegrationEnabled
//        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
//        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(),userCredentials.getPassword());
//        if (WebAdminActions.webAdminUsersActions().isCFMIntegrationEnabled()) {
//            throw new SkipException("CFMIntegrationEnabled = 1");
//        }
//        WebAdminActions.loginActions().doLogout();
//        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();

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
        clientRootId = ClientsActions.createClient().getClientIdFromUrl();

        // Set up transactions with account number
        transaction.getTransactionSource().setAmount(returnTransactionAmount);
        transaction.getTransactionDestination().setAmount(returnTransactionAmount);
        transaction.getTransactionSource().setAccountNumber(savingsAccount.getAccountNumber());
        transaction.getTransactionSource().setDenominationsHashMap(new HashMap<>());
        transaction.getTransactionSource().getDenominationsHashMap().put(Denominations.HUNDREDS, 100.00);
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
                "-", expectedSavingsBalanceData.getCurrentBalance(), returnTransactionAmount);

        //Check Official check and printer
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().clickViewAllPrinters();
        SettingsPage.printerPage().clickToPrinter("Xerox_Phaser_3260 Moscow 2nd floor");
        boolean isOfficial = SettingsPage.printerPage().checkIsOfficialPrinter();
        Assert.assertTrue(isOfficial, "Is not official printer");
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().clickViewControls();
        checkAccountNumber = Integer.parseInt(SettingsPage.officialComtrolPage().checkAccountNumber());

        //New Checks
        check = new Check();
        check.setDate(DateTime.getLocalDateOfPattern("MM/dd/yyyy"));
        check.setCheckType("Cashiers Check");
        check.setPayee(name);
        check.setPurchaser(client.getInitials());
        check.setInitials(userCredentials.getUserName() + " " + userCredentials.getUserName());
        check.setAmount(returnTransactionAmount);
        check.setStatus("Outstanding");
        fullCheck = new FullCheck();
        fullCheck.fromCheck(check);
        fullCheck.setFee(fee);
        fullCheck.setCashPurchased("NO");
        fullCheck.setRemitter(client.getNameForDebitCard());
        fullCheck.setBranch(savingsAccount.getBankBranch());
        fullCheck.setDocumentType(client.getIndividualClientDetails().getDocuments().get(0).getIdType().getIdType());
        fullCheck.setDocumentID(client.getIndividualClientDetails().getDocuments().get(0).getIdNumber());
        fullCheck.setPhone(client.getIndividualClientDetails().getPhones().get(client.getIndividualClientDetails().getPhones().size() - 1).getPhoneNumber());
        fullCheck.setPurchaseAccount("Savings Account " + savingsAccount.getAccountNumber());

        //Check CDT template
        boolean templateNotExists = Actions.cashierDefinedActions().checkCDTTemplateIsExist(CashierDefinedTransactions.OFFICIAL_CHECK_WITH_CASH);
        if (templateNotExists){
            boolean isCreated = Actions.cashierDefinedActions().createOfficialCheckWithCash();
            Assert.assertTrue(isCreated, "CDT template not created");
        }

        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 19459, testRunName = TEST_RUN_NAME)
    @Test(description = "C19459, CDT+Teller Session - Commit official check from cash with fee")
    @Severity(SeverityLevel.CRITICAL)
    public void cDTTellerSessionCommitOfficialCheckFromCashWithFee() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Cashier Defined Transactions screen and log in to proof date");
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().openProofDateLoginModalWindow();
        String bankBranch = Pages.tellerModalPage().getBankBranch();
        fullCheck.setBranch(bankBranch);
        Actions.transactionActions().doLoginProofDate();
        Pages.aSideMenuPage().waitForASideMenu();
        Pages.aSideMenuPage().clickCashierDefinedTransactionsMenuItem();

        logInfo("Step 3: Search for template from preconditions and select it");
        logInfo("Step 4: Specify account from the precondition in the source line account number field;\n" +
                "Set transaction amount < Account's Available Balance\n" +
                "Specify Payee Info required fields:\n" +
                "Name (any value)\n" +
                "Payee Type (e.g. 'Person')");
        Actions.cashierDefinedActions().createOfficialCheckTransaction(CashierDefinedTransactions.OFFICIAL_CHECK_WITH_CASH,
                transaction, false, name);
        expectedSavingsBalanceData.reduceAmount(transaction.getTransactionDestination().getAmount());

        logInfo("Step 5: Click [Commit Transaction] button and click [Verify] button");
        Actions.transactionActions().clickCommitButton();
        Pages.verifyConductorModalPage().clickVerifyButton();
        SelenideTools.sleep(Constants.SMALL_TIMEOUT);
        Assert.assertTrue(Pages.confirmModalPage().checkReprintButton(), "Reprint check is not visible");
        String checkNumber = Pages.confirmModalPage().getReprintCheckNumber();
        check.setCheckNumber(checkNumber);
        fullCheck.setCheckNumber(checkNumber);

        logInfo("Step 6: Go to the WA Rules UI and search for the transaction header record using" +
                " bank.data.transaction.header rootid from createOfficialCheckCashierDefined method response\n" +
                "Verify transactionstatusid value");
        String transactionStatus = WebAdminActions.webAdminTransactionActions().getTransactionStatusFromHeader(userCredentials, savingsAccount);
        TestRailAssert.assertTrue(transactionStatus.equals("Void"), new CustomStepResult("Status is valid",
                "Status is not valid"));

        logInfo("Step 7: Click [Yes] button on a 'Reprint check #X?' popup");
        Pages.confirmModalPage().clickYes();
        Assert.assertTrue(Pages.confirmModalPage().checkIsCheck(), "Is check is not visible");

        logInfo("Step 8: Click [Yes] button on a 'Is check #X still usable?' popup:");
        Pages.confirmModalPage().clickYes();
        Assert.assertTrue(Pages.confirmModalPage().checkReprintButton(), "Reprint check is not visible");

        logInfo("Step 9: Click [NO] on 'Reprint check #X?' popup");
        Pages.confirmModalPage().clickNo();
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);

        SelenideTools.openUrlInNewWindow(Constants.URL.substring(0, Constants.URL.indexOf("com") + 3)
                + "/settings/#/view/bank.data.officialcheck.control");
        SelenideTools.switchToLastTab();
        int number = Integer.parseInt(SettingsPage.officialComtrolPage().checkAccountNumber());
        TestRailAssert.assertTrue(number == checkAccountNumber + 1, new CustomStepResult("Number match",
                String.format("Number doesn't match. Expected %s, actual %s", checkAccountNumber + 1, number)));

        logInfo("Step 10: Go to b.d.transaction.header again and check transactionstatusid value");
        transactionStatus = WebAdminActions.webAdminTransactionActions().getTransactionStatusFromHeader(userCredentials, savingsAccount);
        TestRailAssert.assertTrue(transactionStatus.equals("Open"), new CustomStepResult("Status is valid",
                "Status is not valid"));




        logInfo("Step 11: Go to account used in DEBIT item and verify its:\n" +
                "- current balance\n" +
                "- available balance");
        Actions.transactionActions().goToTellerPage();
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccount.getAccountNumber());
        BalanceDataForCHKAcc actualSavBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        expectedSavingsBalanceData.reduceAmount(5);
        TestRailAssert.assertTrue(actualSavBalanceData.getCurrentBalance() == expectedSavingsBalanceData.getCurrentBalance(),
                new CustomStepResult("Current balance match!", String.format("Current balance doesn't match! " +
                        "Expected %s, actual %s", expectedSavingsBalanceData.getCurrentBalance(), actualSavBalanceData.getCurrentBalance())));
        TestRailAssert.assertTrue(actualSavBalanceData.getAvailableBalance() == expectedSavingsBalanceData.getAvailableBalance(),
                new CustomStepResult("Available balance match!",
                        String.format("Available balance doesn't match! Expected %s, actual %s",
                                expectedSavingsBalanceData.getAvailableBalance(), actualSavBalanceData.getAvailableBalance())));

        logInfo("Step 12: Open account on the Transactions tab and verify the committed transaction");
        Pages.accountDetailsPage().clickTransactionsTab();
        savingsAccTransactionData.setBalance(returnTransactionAmount);
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualSavTransactionData = AccountActions.retrievingAccountData().getSecondTransactionDataWithBalanceSymbol();
        TestRailAssert.assertTrue(actualSavTransactionData.equals(savingsAccTransactionData),
                new CustomStepResult("Transaction data match!", String.format("Transaction data doesn't match!" +
                        " Actual %s, expected %s", savingsAccTransactionData, actualSavTransactionData)));

        logInfo("Step 13: Go to Back Office -> Official Checks and find generated Check from the related transaction\n" +
                "Verify Check Number, Purchaser, PAYEE, Date Issued, Initials, Check Type, Status, Amount fields");
        Pages.aSideMenuPage().clickBackOfficeMenuItem();
        Pages.backOfficePage().clickOfficialChecks();
        Check checkFromBankOffice = Actions.backOfficeActions().getCheckFromBankOffice(checkNumber);
        TestRailAssert.assertTrue(checkFromBankOffice.equals(check), new CustomStepResult("Check match",
                String.format("Check doesn't match. Expected %s, actual %s.", check, checkFromBankOffice)));

        logInfo("Step 14: Open check on Details and verify the following fields: Status, Check Number, " +
                "Remitter, Phone Number, Document Type, Document ID, Payee, Check Type, Purchase Account, " +
                "Branch, Initials, Check Amount, Fee, Date Issued, Cash Purchased");
        Pages.checkPage().clickToCheck(checkNumber);
        FullCheck fullCheckFromBankOffice = Actions.backOfficeActions().getFullCheckFromBankOffice();
        fullCheck.setCheckNumber(null);
        fullCheck.setPayee(null);
        fullCheck.setDate(null);
        fullCheck.setPurchaser(null);

        TestRailAssert.assertTrue(fullCheckFromBankOffice.equals(fullCheck), new CustomStepResult("Check details match",
                String.format("Check details doesn't match. Expected %s, actual %s.", fullCheck, fullCheckFromBankOffice)));
    }

}
