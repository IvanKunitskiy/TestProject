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
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.generation.transactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.transactions.factory.SourceFactory;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.stream.Stream;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Petro")
public class C37313_PrintTellerReceiptWithoutBalanceTest extends BaseTest {

    private int printBalanceOnReceiptValue;

    private Transaction chkTransaction;
    private final TransactionSource miscDebit = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCredit = DestinationFactory.getMiscCreditDestination();
    private final TransactionDestination deposit = DestinationFactory.getDepositDestination();
    private final double miscDebitAmount = 50.00;
    private final double miscCreditAmount = 25.00;
    private final double depositAmount = 25.00;

    @BeforeMethod
    public void preCondition() {
        // Set up Client A and Client B
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient clientA = individualClientBuilder.buildClient();
        IndividualClient clientB = individualClientBuilder.buildClient();

        // Set up accounts
        Account clientACheckingAccount = new Account().setCHKAccountData();
        Account clientASavingsAccount = new Account().setSavingsAccountData();
        Account clientBCheckingAccount = new Account().setCHKAccountData();

        // Set up transaction for increasing the CHK account balance
        chkTransaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        chkTransaction.getTransactionDestination().setAccountNumber(clientACheckingAccount.getAccountNumber());

        // Set Data for transaction
        miscDebit.setAccountNumber(clientACheckingAccount.getAccountNumber());
        miscDebit.setTransactionCode(TransactionCode.WITHDRAWAL_116.getTransCode());
        miscDebit.setAmount(miscDebitAmount);

        miscCredit.setAccountNumber(clientASavingsAccount.getAccountNumber());
        miscCredit.setTransactionCode(TransactionCode.ATM_DEPOSIT_209.getTransCode());
        miscCredit.setAmount(miscCreditAmount);

        deposit.setAccountNumber(clientBCheckingAccount.getAccountNumber());
        deposit.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        deposit.setAmount(depositAmount);

        // Get 'Print Balance On Receipt' value
        printBalanceOnReceiptValue = WebAdminActions.webAdminUsersActions().getPrintBalanceOnReceiptValue();

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set the client A and B account data
        Pages.aSideMenuPage().clickSettingsMenuItem();
        SettingsPage.mainPage().clickViewProfile();
        String bankBranch = SettingsPage.viewUserPage().getBankBranch();

        clientACheckingAccount.setBankBranch(bankBranch);
        clientASavingsAccount.setBankBranch(bankBranch);
        clientBCheckingAccount.setBankBranch(bankBranch);

        clientACheckingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));
        clientASavingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));
        clientBCheckingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client A
        ClientsActions.individualClientActions().createClient(clientA);
        ClientsActions.individualClientActions().setClientDetailsData(clientA);
        ClientsActions.individualClientActions().setDocumentation(clientA);
        clientA.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create accounts for client A
        AccountActions.createAccount().createCHKAccount(clientACheckingAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createSavingsAccount(clientASavingsAccount);
        Pages.aSideMenuPage().clickClientMenuItem();

        // Create client B
        ClientsActions.individualClientActions().createClient(clientB);
        ClientsActions.individualClientActions().setClientDetailsData(clientB);
        ClientsActions.individualClientActions().setDocumentation(clientB);
        clientB.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create accounts for client B
        AccountActions.createAccount().createCHKAccount(clientBCheckingAccount);

        // Perform transaction to assign the money to CHK account
        Actions.transactionActions().performGLDebitMiscCreditTransaction(chkTransaction);
        Actions.loginActions().doLogOutProgrammatically();
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 37313, testRunName = TEST_RUN_NAME)
    @Test(description = "C37313, Print teller receipt without balance (Accounts do not have a client in common)")
    @Severity(SeverityLevel.CRITICAL)
    public void printTellerReceiptWithoutBalance() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller screen and log in to proof date");
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Select such fund types:\n" +
                "Source: Misc Debit\n" +
                "Destination: Misc Credit and Deposit");
        logInfo("Step 4: Select:\n" +
                "CHK Account of Client A in Misc Debit item\n" +
                "Savings Account of Client A in Misc Credit item\n" +
                "CHK/Savings Account of Client B in Deposit Item");
        logInfo("Step 5: Select:\n" +
                "109/209 trancode - for Deposit Item and amount < CHK Account of Client A Available Balance\n" +
                "209 trancode - for Misc Credit Item and amount < CHK Account of Client A Available Balance\n" +
                "116 trancode - for Misc Debit Item and Amount = Total Amount of Items in Destination (< Account's Available Balance)");
        Actions.transactionActions().setMiscDebitSource(miscDebit, 0);
        Actions.transactionActions().setMiscCreditDestination(miscCredit, 0);
        Actions.transactionActions().setDepositDestination(deposit, 1);

        logInfo("Step 6: Click [Commit Transaction] button and confirm verify screen");
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 7: Verify Print Balances on Receipt checkbox");
        logInfo("Step 8: If Print Balance On Receipt=0, then check Print Balances on Receipt checkbox " +
                "and check Additional account balances on the receipt.\n" +
                "If Print Balance On Receipt=1, then just verify Additional account balances on the receipt");
        Actions.balanceInquiryActions().checkPrintBalanceOnReceipt(printBalanceOnReceiptValue);
        File balanceInquiryImageFile = Actions.balanceInquiryActions().saveBalanceInquiryImage();
        String balanceInquiryImageData = Actions.balanceInquiryActions().readBalanceInquiryImage(balanceInquiryImageFile);

        logInfo("Step 9: Verify that Additional account balances are NOT displayed for 109/209 - Deposit for CHK/Savings account of Client B");
        String[] beginningBalance = getBalance(balanceInquiryImageData, "Beginning Balance");
        String[] endingBalance = getBalance(balanceInquiryImageData, "Ending Balance");
        String[] availableBalance = getBalance(balanceInquiryImageData, "Available Balance");

        Assert.assertEquals(beginningBalance.length, 2, "'Beginning Balance' is displayed not only for client A accounts");
        Assert.assertEquals(endingBalance.length, 2, "'Ending Balance' is displayed not only for client A accounts");
        Assert.assertEquals(availableBalance.length, 2, "'Available Balance' is displayed not only for client A accounts");

        Assert.assertEquals(Functions.getStringValueWithOnlyDigits(chkTransaction.getTransactionSource().getAmount()),
                beginningBalance[0], "'Beginning Balance' of CHK account does not match");
        double endingAvailableBalance = chkTransaction.getTransactionSource().getAmount() - miscDebitAmount;
        Assert.assertEquals(Functions.getStringValueWithOnlyDigits(endingAvailableBalance), endingBalance[0],
                "'Ending Balance' of CHK account does not match");
        Assert.assertEquals(Functions.getStringValueWithOnlyDigits(endingAvailableBalance), availableBalance[0],
                "'Available Balance' of CHK account does not match");

        Assert.assertEquals(Functions.getStringValueWithOnlyDigits(0.00), beginningBalance[1],
                "'Beginning Balance' of Savings account does not match");
        Assert.assertEquals(Functions.getStringValueWithOnlyDigits(miscCreditAmount), endingBalance[1],
                "'Ending Balance' of Savings account does not match");
        Assert.assertEquals(Functions.getStringValueWithOnlyDigits(miscCreditAmount), availableBalance[1],
                "'Available Balance' of Savings account does not match");
    }

    @AfterMethod(description = "Delete the downloaded image.")
    public void postCondition() {
        logInfo("Deleting the downloaded image...");
        Functions.cleanDirectory(System.getProperty("user.dir") + "/screenshots/");
    }

    private String[] getBalance(String data, String balanceType) {
        return Stream.of(data.split("\n"))
                .filter(line -> line.contains(balanceType))
                .map(lineWithBalance -> lineWithBalance.replaceAll("[^0-9]", "").trim())
                .toArray(String[]::new);
    }
}
