package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
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
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C22744_BackOfficeOfficialChecksVoidOfficialCheckFromClientAccountWithFeeWaivedWithoutTellerSession extends BaseTest {
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
        Actions.loginActions().doLogin(Constants.NOT_TELLER_USERNAME, Constants.NOT_TELLER_PASSWORD);

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
                "-", expectedSavingsBalanceData.getCurrentBalance(), returnTransactionAmount);

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
        Actions.loginActions().doLogOut();

        //Create CDT  transaction
        Actions.loginActions().doLogin(Constants.NOT_TELLER_USERNAME, Constants.NOT_TELLER_PASSWORD);
        Pages.aSideMenuPage().waitForASideMenu();
        Pages.aSideMenuPage().clickCashierDefinedTransactionsMenuItem();
        Actions.cashierDefinedActions().createOfficialTransaction(CashierDefinedTransactions.OFFICIAL_CHECK_FROM_SAVINGS,
                transaction, true, name);
        expectedSavingsBalanceData.reduceAmount(transaction.getTransactionDestination().getAmount());
        Actions.transactionActions().clickCommitButton();
        Pages.verifyConductorModalPage().clickVerifyButton();
        checkNumber = Pages.confirmModalPage().getReprintCheckNumber();
        Pages.confirmModalPage().clickYes();
        Pages.confirmModalPage().clickYes();
        Pages.confirmModalPage().clickNo();
        Actions.transactionActions().clickCommitButton();
        Pages.verifyConductorModalPage().clickVerifyButton();
        Pages.confirmModalPage().clickNo();
        Actions.loginActions().doLogOut();

    }

    @Test(description = "C22744, BackOffice->Official Checks: Void official check from client account with fee waived without Teller Session")
    @Severity(SeverityLevel.CRITICAL)
    public void printTellerReceiptWithoutBalance() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.NOT_TELLER_USERNAME, Constants.NOT_TELLER_PASSWORD);

        logInfo("Step 2: Go to Back Office -> Official Checks");
        Pages.aSideMenuPage().clickBackOfficeMenuItem();
        Pages.backOfficePage().clickOfficialChecks();

        logInfo("Step 3: Find official check from preconditions and open it on details");
        Pages.checkPage().clickToCheck(checkNumber);
        FullCheck fullCheckFromBankOffice = Actions.backOfficeActions().getFullCheckFromBankOffice();

        logInfo("Step 4: Click [Void] button");
        Pages.fullCheckPage().clickVoid();
        Assert.assertTrue(Pages.fullCheckPage().checkConfirmation(), "Confirmation is not present");
        Assert.assertFalse(Pages.fullCheckPage().checkVoidIsDisabled(),"Void button is not disabled");

        logInfo("Step 5: Select Yes option and verify the Status field");
        Pages.fullCheckPage().clickYes();
        Assert.assertEquals(Pages.fullCheckPage().getStatus(),"Void", "Status doesn't match");


        logInfo("Step 6: Go to account used in DEBIT item and verify its:\n" +
                "- current balance\n" +
                "- available balance");
        Actions.transactionActions().goToTellerPage();
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccount.getAccountNumber());
        BalanceDataForCHKAcc actualSavBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        Assert.assertEquals(actualSavBalanceData.getCurrentBalance(), expectedSavingsBalanceData.getCurrentBalance(),
                "Current balance doesn't match!");
        Assert.assertEquals(actualSavBalanceData.getAvailableBalance(), expectedSavingsBalanceData.getAvailableBalance(),
                "Available balance doesn't match!");

        logInfo("Step 7: Open account on the Transactions tab and verify the committed transaction");
        Pages.accountDetailsPage().clickTransactionsTab();
        savingsAccTransactionData.setBalance(returnTransactionAmount);
        AccountActions.retrievingAccountData().goToTransactionsTab();
        TransactionData actualSavTransactionData = AccountActions.retrievingAccountData().getSecondTransactionDataWithBalanceSymbol();
        Assert.assertEquals(actualSavTransactionData, savingsAccTransactionData, "Transaction data doesn't match!");

    }




}
