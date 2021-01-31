package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.tansactions.factory.SourceFactory;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.stream.Stream;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Petro")
public class C25238_PrintTellerReceiptWithBalanceForDebitItemsTest extends BaseTest {

    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionSource withdrawalSource = SourceFactory.getWithdrawalSource();
    private final TransactionSource checkSource = SourceFactory.getCheckSource();
    private final TransactionDestination glCreditDestination = DestinationFactory.getGLCreditDestination();
    private int printBalanceOnReceiptValue;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up client accounts
        Account checkingAccount = new Account().setCHKAccountData();
        Account savingsAccount = new Account().setSavingsAccountData();
        Account cdAccount = new Account().setCdAccountData();

        // Set up transaction for increasing the CHK account balance
        Transaction chkTransaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        chkTransaction.getTransactionDestination().setAccountNumber(checkingAccount.getAccountNumber());

        // Set up transaction for increasing the Savings account balance
        Transaction savingsTransaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        savingsTransaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());
        savingsTransaction.getTransactionDestination().setTransactionCode(TransactionCode.ATM_DEPOSIT_209.getTransCode());

        // Set up transaction for increasing the CD account balance
        Transaction cdTransaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        cdTransaction.getTransactionDestination().setAccountNumber(cdAccount.getAccountNumber());
        cdTransaction.getTransactionDestination().setTransactionCode(TransactionCode.NEW_CD_311.getTransCode());

        // Set up Misc Debit source
        miscDebitSource.setAccountNumber(cdAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.DEBIT_MEM0_319.getTransCode());
        miscDebitSource.setAmount(25.00);
        double miscDebitAmount = miscDebitSource.getAmount();

        // Set up Withdrawal source
        withdrawalSource.setAccountNumber(savingsAccount.getAccountNumber());
        withdrawalSource.setTransactionCode(TransactionCode.WITHDRAWAL_216.getTransCode());
        withdrawalSource.setAmount(25.00);
        double withdrawalAmount = miscDebitSource.getAmount();

        // Set up Check source
        checkSource.setAccountNumber(checkingAccount.getAccountNumber());
        checkSource.setTransactionCode(TransactionCode.CHECK.getTransCode());
        checkSource.setAmount(25.00);
        double checkAmount = miscDebitSource.getAmount();

        // Set up transaction destination
        double glCreditAmount = miscDebitAmount + withdrawalAmount + checkAmount;
        glCreditDestination.setAmount(glCreditAmount);

        // Get 'Print Balance On Receipt' value
        printBalanceOnReceiptValue = WebAdminActions.webAdminUsersActions().getPrintBalanceOnReceiptValue();

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set the client A and B account data
        Pages.aSideMenuPage().clickSettingsMenuItem();
        SettingsPage.mainPage().clickViewProfile();
        String bankBranch = SettingsPage.viewUserPage().getBankBranch();

        checkingAccount.setBankBranch(bankBranch);
        savingsAccount.setBankBranch(bankBranch);
        cdAccount.setBankBranch(bankBranch);

        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));
        cdAccount.setProduct(Actions.productsActions().getProduct(Products.CD_PRODUCTS, AccountType.CD, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create accounts
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Pages.accountNavigationPage().clickCustomerProfileInBreadCrumb();

        AccountActions.createAccount().createSavingsAccount(savingsAccount);
        Pages.accountNavigationPage().clickCustomerProfileInBreadCrumb();

        AccountActions.createAccount().createCDAccount(cdAccount);
        Pages.accountNavigationPage().clickCustomerProfileInBreadCrumb();

        // Perform transactions to assign the money to accounts
        Actions.transactionActions().performGLDebitMiscCreditTransaction(chkTransaction);
        Actions.loginActions().doLogOutProgrammatically();

        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().performGLDebitMiscCreditTransaction(savingsTransaction);
        Actions.loginActions().doLogOutProgrammatically();

        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().performGLDebitMiscCreditTransaction(cdTransaction);
        Actions.loginActions().doLogOutProgrammatically();

    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 25238, testRunName = TEST_RUN_NAME)
    @Test(description = "C25238, Print teller receipt with balance for Debit Items (except 128-Check)")
    @Severity(SeverityLevel.CRITICAL)
    public void printTellerReceiptWithBalanceForDebitItems() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller screen and log in to proof date");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Select such fund types in the Source:\n" +
                "- Misc Debit (and CD account from the precondition)\n" +
                "- Withdrawal (and Savings account from the precondition)\n" +
                "- Check (and CHK account from the precondition)");
        logInfo("Step 4: Select such trancodes and amounts for Source items:\n" +
                "- 319 - Debit Memo for CD account, amount any < CD Account's Available Balance\n" +
                "- 216 - Withdrawal for Savings account, amount any < Savings Account's Available Balance\n" +
                "- 128-Check for CHK account, amount any < CHK Account's Available Balance, fill in Check number - any numeric value");
        Actions.transactionActions().setMiscDebitSource(miscDebitSource, 0);
        Actions.transactionActions().setWithDrawalSource(withdrawalSource, 1);
        Actions.transactionActions().setCheckSource(checkSource, 2);

        logInfo("Step 5: Select GL/Credit in the Destination, any GL account, Amount = Total Debit amount\n" +
                "(so Debit and Credit items will be balanced), any Notes");
        Actions.transactionActions().setGLCreditDestination(glCreditDestination, 0);

        logInfo("Step 6: Click [Commit Transaction] button and confirm verify screen");
        Actions.transactionActions().clickCommitButton();
        Pages.verifyConductorModalPage().clickVerifyButton();

        logInfo("Step 7: Verify Print Balances on Receipt checkbox");
        logInfo("Step 8: If Print Balance On Receipt=0,\n" +
                "then check Print Balances on Receipt checkbox and check Additional account balances on the receipt\n" +
                "If Print Balance On Receipt=1, then just verify Additional account balances on the receipt");
        Actions.balanceInquiryActions().checkPrintBalanceOnReceipt(printBalanceOnReceiptValue);

        logInfo("Step 9: Look through the receipt and verify that Account Balances are not displayed in the Receipt for 128-Check for CHK account");
        File balanceInquiryImageFile = Actions.balanceInquiryActions().saveBalanceInquiryImage();
        String balanceInquiryImageData = Actions.balanceInquiryActions().readBalanceInquiryImage(balanceInquiryImageFile);

        Assert.assertEquals(getAmountOfLinesWithText(balanceInquiryImageData, "Beginning Balance"), 2,
                "'Beginning Balance' count is not correct");
        Assert.assertEquals(getAmountOfLinesWithText(balanceInquiryImageData, "Ending Balance"), 2,
                "'Ending Balance' count is not correct");
        Assert.assertEquals(getAmountOfLinesWithText(balanceInquiryImageData, "Available Balance"), 2,
                "'Available Balance' count is not correct");

        String withdrawal = Actions.balanceInquiryActions().getNumericValueFromReceiptStringByName(balanceInquiryImageData, "Withdrawal");
        Assert.assertEquals(withdrawal, Functions.getStringValueWithOnlyDigits(withdrawalSource.getAmount()),
                "'Withdrawal' amount is not correct");
        String check = Actions.balanceInquiryActions().getNumericValueFromReceiptStringByName(balanceInquiryImageData, "Check");
        Assert.assertEquals(check, Functions.getStringValueWithOnlyDigits(checkSource.getAmount()),
                "'Check' amount is not correct");
        String debitMemo = Actions.balanceInquiryActions().getNumericValueFromReceiptStringByName(balanceInquiryImageData, "Debit Memo");
        Assert.assertEquals(debitMemo, Functions.getStringValueWithOnlyDigits(miscDebitSource.getAmount()),
                "'Debit Memo' amount is not correct");
    }

    private int getAmountOfLinesWithText(String data, String text) {
        return (int) Stream.of(data.split("\n")).filter(line -> line.contains(text)).count();
    }
}
