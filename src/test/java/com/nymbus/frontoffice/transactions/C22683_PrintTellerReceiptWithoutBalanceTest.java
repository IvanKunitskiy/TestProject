package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
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
import com.nymbus.newmodels.settings.teller.TellerLocation;
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

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Petro")
public class C22683_PrintTellerReceiptWithoutBalanceTest extends BaseTest {

    private IndividualClient client;
    private Account checkingAccount;
    private Account savingsAccount;
    private TellerLocation tellerLocation;
    private String userId;
    private int printBalanceOnReceiptValue;
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionSource cashInSource = SourceFactory.getCashInSource();
    private final TransactionDestination depositDestination = DestinationFactory.getDepositDestination();

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up accounts
        checkingAccount = new Account().setCHKAccountData();
        savingsAccount = new Account().setSavingsAccountData();

        // Set up transaction for increasing the CHK account balance
        Transaction chkTransaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        chkTransaction.getTransactionDestination().setAccountNumber(checkingAccount.getAccountNumber());

        // Set up transaction for increasing the Savings account balance
        Transaction savingsTransaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        savingsTransaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());
        savingsTransaction.getTransactionSource().setAmount(200);
        savingsTransaction.getTransactionDestination().setAmount(200);

        // Set up Misc Debit source
        miscDebitSource.setAccountNumber(checkingAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.DEPOSIT_CORR_104.getTransCode());
        miscDebitSource.setAmount(25.00);
        double miscDebitAmount = miscDebitSource.getAmount();

        // Set up Cash In source
        cashInSource.setAccountNumber("0-0-Dummy");
        cashInSource.setTransactionCode(TransactionCode.CASH_IN_850.getTransCode());
        double cashInAmount = cashInSource.getAmount();

        // Set up Deposit destination
        depositDestination.setAccountNumber(savingsAccount.getAccountNumber());
        depositDestination.setTransactionCode(TransactionCode.NORMAL_DEPOSIT_211.getTransCode());
        depositDestination.setAmount(miscDebitAmount + cashInAmount);

        // Get 'Print Balance On Receipt' value
        printBalanceOnReceiptValue = WebAdminActions.webAdminUsersActions().getPrintBalanceOnReceiptValue();

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set the account data
        Pages.aSideMenuPage().clickSettingsMenuItem();
        SettingsPage.mainPage().clickViewProfile();

        userId = SettingsPage.viewUserPage().getUSERIDValue();
        String bankBranch = SettingsPage.viewUserPage().getBankBranch();

        checkingAccount.setBankBranch(bankBranch);
        savingsAccount.setBankBranch(bankBranch);
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));
        Actions.loginActions().doLogOut();

        // Get bank info
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Pages.aSideMenuPage().clickSettingsMenuItem();
        SettingsPage.mainPage().clickViewAllTellerLocationsLink();
        SettingsPage.tellerLocationOverviewPage().clickRowByBranchName(bankBranch);
        tellerLocation = Actions.tellerBankBranchOverviewActions().getTellerLocation(bankBranch);
        Pages.aSideMenuPage().clickClientMenuItem();

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create CHK and Savings account
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createSavingsAccount(savingsAccount);

        // Perform transaction to assign the money to CHK account
        Actions.transactionActions().performGLDebitMiscCreditTransaction(chkTransaction);
        Actions.loginActions().doLogOutProgrammatically();

        // Perform transaction to assign the money to Savings account
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().performGLDebitMiscCreditTransaction(chkTransaction);
        Actions.loginActions().doLogOutProgrammatically();
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 22683, testRunName = TEST_RUN_NAME)
    @Test(description = "C22683, Print teller receipt without balance")
    @Severity(SeverityLevel.CRITICAL)
    public void printTellerReceiptWithoutBalance() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller screen and log in to proof date");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Select some fund types, e.g.:\n" +
                "- Source: Cash-In, Check\n" +
                "- Destination: Deposit");
        logInfo("Step 4: Select CHK account from the precondition in Check item and\n" +
                "Savings account from the precondition in Deposit Item");
        logInfo("Step 5: Select 128-Check Trancode for Check Item and\n" +
                "209-Deposit trancode for Deposit Item)");
        logInfo("Step 6: Specify different amounts for items , e.g.\n" +
                "- cash in == $10\n" +
                "- check == $25\n" +
                "- deposit == $35");
        Actions.transactionActions().setMiscDebitSource(miscDebitSource, 0);
        Actions.transactionActions().setCashInSource(cashInSource);
        Actions.transactionActions().setDepositDestination(depositDestination, 0);

        logInfo("Step 7: Click [Commit Transaction] button and confirm verify screen");
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 8: Verify Print Balances on Receipt checkbox");
        Pages.verifyConductorModalPage().clickVerifyButton();

        logInfo("Step 9: If Print Balance On Receipt=0, do not check Print Balances on Receipt checkbox\n" +
                "If Print Balance On Receipt=1, then uncheck Print Balances on Receipt checkbox");
        Actions.balanceInquiryActions().uncheckPrintBalanceOnReceipt(printBalanceOnReceiptValue);

        logInfo("Step 10: Look at generated transaction receipt");
        File balanceInquiryImageFile = Actions.balanceInquiryActions().saveBalanceInquiryImage();
        String balanceInquiryImageData = Actions.balanceInquiryActions().readBalanceInquiryImage(balanceInquiryImageFile);

        Assert.assertTrue(Pages.balanceInquiryModalPage().isTransactionWasSuccessfullyCompletedLabelVisible(),
                "The 'Transaction was successfully completed.' label is not visible");

        // General info:
        Assert.assertTrue(balanceInquiryImageData.contains(tellerLocation.getBankName()),
                "'Bank name' does not match");
        Assert.assertTrue(balanceInquiryImageData.contains(tellerLocation.getCity()),
                "'City' does not match");
        Assert.assertTrue(balanceInquiryImageData.contains(tellerLocation.getState()),
                "'State' does not match");
        Assert.assertTrue(balanceInquiryImageData.contains(tellerLocation.getZipCode()),
                "'Zip code' does not match");
        Assert.assertEquals(getNumericValueFromReceiptStringByName(balanceInquiryImageData, "Client #:"),
                client.getIndividualType().getClientID(), "'Client #' used in transaction does not match");
        String currentDate = DateTime.getLocalDateOfPattern("MM/dd/yyyy");
        Assert.assertTrue(getLineContainingValue(balanceInquiryImageData, "Current date").contains(currentDate),
                "'Current date' used in transaction does not match");
        Assert.assertTrue(getLineContainingValue(balanceInquiryImageData, "Posting date").contains(currentDate),
                "'Posting date' used in transaction does not match");
        Assert.assertEquals(getNumericValueFromReceiptStringByName(balanceInquiryImageData, "Teller ID:"), userId,
                "Teller # (UserID) is not valid");
        String transactionNumber = WebAdminActions.webAdminTransactionActions().getTransactionNumber(userCredentials, checkingAccount);
        Assert.assertEquals(getNumericValueFromReceiptStringByName(balanceInquiryImageData, "Transaction"),
                transactionNumber.replaceAll("[^0-9]", ""), "Transaction number is not equal");

        // Fund details
        Assert.assertEquals(getNumericValueFromReceiptStringByNameAndOmitDollarSign(balanceInquiryImageData, "Cash In"),
                Functions.getStringValueWithOnlyDigits(cashInSource.getAmount()),
                "'Cash In' Values not equal");
        Assert.assertEquals(getNumericValueFromReceiptStringByNameAndOmitDollarSign(balanceInquiryImageData, "Checks"),
                Functions.getStringValueWithOnlyDigits(0.00), "'Checks' Values not equal");
        Assert.assertEquals(getNumericValueFromReceiptStringByNameAndOmitDollarSign(balanceInquiryImageData, "Cash Out"),
                Functions.getStringValueWithOnlyDigits(0.00), "'Cash Out' Values not equal");
        Assert.assertEquals(getNumericValueFromReceiptStringByNameAndOmitDollarSign(balanceInquiryImageData, "Net Amount"),
                Functions.getStringValueWithOnlyDigits(cashInSource.getAmount()),
                "'Net Amount' Values not equal");

        // Additional section for each transaction item with regular account used and the following formatting:
        String lastFourOfChkAcc = checkingAccount.getAccountNumber().substring(checkingAccount.getAccountNumber().length() - 4);
        String chkProductType = checkingAccount.getProductType().split(" ")[0].toUpperCase();
        Assert.assertEquals(getNumericValueFromReceiptStringByName(balanceInquiryImageData, chkProductType), lastFourOfChkAcc,
                "CHK Account number is not valid");

        String chkFundTypeName = getFundTypeName(miscDebitSource.getTransactionCode());
        double chkAmount = miscDebitSource.getAmount();
        Assert.assertEquals(getNumericValueFromReceiptStringByName(balanceInquiryImageData, chkFundTypeName),
                Functions.getStringValueWithOnlyDigits(chkAmount), "CHK transaction amount is not valid");

        String lastFourOfSavingsAcc = savingsAccount.getAccountNumber().substring(savingsAccount.getAccountNumber().length() - 4);
        String savingsProductType = savingsAccount.getProductType().split(" ")[0].toUpperCase();
        Assert.assertEquals(getNumericValueFromReceiptStringByName(balanceInquiryImageData, savingsProductType), lastFourOfSavingsAcc,
                "CHK Savings number is not valid");

        String savingsFundTypeName = getFundTypeName(depositDestination.getTransactionCode());
        double savingsAmount = depositDestination.getAmount();
        Assert.assertEquals(getNumericValueFromReceiptStringByName(balanceInquiryImageData, savingsFundTypeName),
                Functions.getStringValueWithOnlyDigits(savingsAmount), "Savings transaction amount is not valid");

        // Bank's slogan, e.g. "Bank healthy, be happy!" label
        Assert.assertTrue(balanceInquiryImageData.contains("Bank healthy, be happy!"),
                "Receipt does not contain 'Bank healthy, be happy!' slogan");
    }

    private String getFundTypeName(String transactionCode) {
        return transactionCode.replaceAll("[^a-zA-z ]", "").trim();
    }

    private String getNumericValueFromReceiptStringByName(String data, String propName) {
        for (String line : data.split("\n")) {
            if (line.contains(propName)) {
                String[] l = line.split(" ");
                return l[l.length - 1].replaceAll("[^0-9]", "");
            }
        }
        return null;
    }

    private static String getNumericValueFromReceiptStringByNameAndOmitDollarSign(String data, String propName) {
        for (String line : data.split("\n")) {
            if (line.contains(propName)) {
                String[] l = line.split(" ");
                return l[l.length - 1].replaceAll("5", "").replaceAll("[^0-9]", "");
            }
        }
        return null;
    }

    private String getLineContainingValue(String data, String value) {
        for (String line : data.split("\n")) {
            if (line.contains(value)) {
                return line;
            }
        }
        return null;
    }

    @AfterMethod(description = "Delete the downloaded image.")
    public void postCondition() {
        logInfo("Deleting the downloaded image...");
        Functions.cleanDirectory(System.getProperty("user.dir") + "/screenshots/");
    }
}
