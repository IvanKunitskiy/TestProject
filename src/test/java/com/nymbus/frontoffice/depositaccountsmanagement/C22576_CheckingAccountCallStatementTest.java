package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.pdftest.PDF;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static com.codeborne.pdftest.PDF.containsText;

public class C22576_CheckingAccountCallStatementTest extends BaseTest {

    private IndividualClient client;
    private Account chkAccount;
    private Transaction transaction;
    private String clientID;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up account
        chkAccount = new Account().setCHKAccountData();

        // Set up transaction with account number
        transaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();

        // Create client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCHKAccount(chkAccount);
        clientID = Pages.clientDetailsPage().getClientID();

        // Create transaction
        transaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        transaction.getTransactionDestination().setTransactionCode("109 - Deposit");

        // Create transaction and logout
        Actions.transactionActions().performGLDebitMiscCreditTransaction(transaction);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22576, Checking account call statement")
    @Severity(SeverityLevel.CRITICAL)
    public void viewClientLevelCallStatement() {

        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Search for the CHK account from the precondition and open it on Transactions tab");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccount);
        Pages.accountNavigationPage().clickTransactionsTab();

        logInfo("Step 3: Click [Call Statement] button");
        Pages.transactionsPage().clickTheCallStatementButton();

        logInfo("Step 4: Look through the CHK Call Statement data and verify it contains correct data");
        // TODO: parse statement file
        Actions.mainActions().switchToTab(1);
        Pages.accountStatementPage().waitForLoadingSpinnerInvisibility();
        File f = Pages.accountStatementPage().downloadCallStatementPDF();
        PDF pdf = new PDF(f.getAbsoluteFile());

//      - Date - current business date (b.d.bcfile->DateFileUpdatedThrough+1)
        Assert.assertTrue(containsText("06/01/20").matches(pdf)); // Date hardcoded

//      1. Client Section:
//      - Member number - Client ID from clients profile (customer->ACCOUNTNUMBER)
        Assert.assertTrue(containsText(clientID).matches(pdf));
//      - Account number - masked account# (last 4 digits are displayed)
        String accountNumber = chkAccount.getAccountNumber();
        Assert.assertTrue(containsText("*******" + accountNumber.substring(7)).matches(pdf));
//      - Branch - Bank Branch Number on account
        Assert.assertTrue(containsText(chkAccount.getBankBranch()).matches(pdf));
//      - Client Name - Client's First Name, Middle Name, Last Name
        Assert.assertTrue(containsText(client.getFullName()).matches(pdf));
//      - Client Address - Address selected on Account (Account Holders and Signers section)
        Assert.assertTrue(containsText(client.getIndividualType().getAddresses().get(0).getAddress()).matches(pdf));
//      - Phone - Clients Phone
        Assert.assertTrue(containsText(client.getIndividualClientDetails().getPhones().get(0).getPhoneNumber()).matches(pdf));

//      2. Account Section:
//      Account Type - selected account's Account Type
        Assert.assertTrue(containsText("Basic Business Checking").matches(pdf));
//      Officer - selected account's Current Officer
        Assert.assertTrue(containsText("AC").matches(pdf));
//      Opened - selected account's Date Opened
        Assert.assertTrue(containsText("05/01/2020").matches(pdf));
//      Dividend Rate - selected account's Interest Rate
        Assert.assertTrue(containsText("0.0000").matches(pdf));
//      YTD Dividends Paid - selected account's Interest Paid Year to date value
        Assert.assertTrue(containsText("0.00").matches(pdf));
//      Dividends Paid Last Year - selected account's Interest Paid Last Year value
        Assert.assertTrue(containsText("0.00").matches(pdf));
//      YTD Taxes withheld- selected account's Taxes Withheld YTD value
        Assert.assertTrue(containsText("0.00").matches(pdf));

//      3. Transactions section (all posted transactions in this statement cycle):
//      Date - Transaction Posting Date for committed transaction from transactions tab
        Assert.assertTrue(containsText("05/31/20").matches(pdf));
//      Transaction Description - Transaction Code for committed transaction from transactions tab
        Assert.assertTrue(containsText("Balance Last Statement").matches(pdf));
//      Debits - transaction amount of Debit transaction (Amount is displayed with red color and with '-' sign for such transactions)
        Assert.assertTrue(containsText("0.00").matches(pdf));
//      Credits - transaction amount of Credit transaction (Amount is displayed with green color and with '+' sign for such transactions)
        Assert.assertTrue(containsText("0.00").matches(pdf));
//      Balance - Balance for committed transaction from transactions tab
        Assert.assertTrue(containsText("0.00").matches(pdf));
//      Totals - calculated # of all transactions, total Debits and Total Credits
        Assert.assertTrue(containsText("").matches(pdf));
//      Overdraft Charged Off - selected account's Overdraft Charged Off value
        Assert.assertTrue(containsText("").matches(pdf));
    }
}
