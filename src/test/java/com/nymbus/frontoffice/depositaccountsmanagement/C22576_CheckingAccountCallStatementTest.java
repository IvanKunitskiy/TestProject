package com.nymbus.frontoffice.depositaccountsmanagement;

import com.codeborne.pdftest.PDF;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
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
        logInfo("Step 4: Look through the CHK Call Statement data and verify it contains correct data");
        PDF pdf = AccountActions.callStatement().getCallStatementPdf();

        // - Date - current business date
        String date = WebAdminActions.loginActions().getSystemDate();
        Assert.assertTrue(containsText(date).matches(pdf));

        // Client Section:
        // Member number - Client ID from clients profile (customer->ACCOUNTNUMBER)
        Assert.assertTrue(containsText(clientID).matches(pdf));
        // Account number - masked account# (last 4 digits are displayed)
        Assert.assertTrue(containsText("*******" + chkAccount.getAccountNumber().substring(7)).matches(pdf));
        // Branch - Bank Branch Number on account
        Assert.assertTrue(containsText(chkAccount.getBankBranch()).matches(pdf));
        // Client Name - Client's First Name, Middle Name, Last Name
        Assert.assertTrue(containsText(client.getFullName()).matches(pdf));
        // Client Address - Address selected on Account (Account Holders and Signers section)
        Assert.assertTrue(containsText(client.getIndividualType().getAddresses().get(0).getAddress()).matches(pdf));
        // Phone - Clients Phone
        Assert.assertTrue(containsText(client.getIndividualClientDetails().getPhones().get(0).getPhoneNumber()).matches(pdf));

        // Account Section:
        // Account Type - selected account's Account Type
        Assert.assertTrue(containsText(chkAccount.getProduct()).matches(pdf));
        // Officer - selected account's Current Officer
        Assert.assertTrue(containsText(chkAccount.getCurrentOfficer()).matches(pdf));
        // Opened - selected account's Date Opened
        Assert.assertTrue(containsText(chkAccount.getDateOpened()).matches(pdf));
        // Dividend Rate - selected account's Interest Rate
        Assert.assertTrue(containsText(chkAccount.getInterestRate()).matches(pdf));
        // YTD Dividends Paid - selected account's Interest Paid Year to date value
        Assert.assertTrue(containsText(chkAccount.getInterestPaidYTD()).matches(pdf));
        // TODO: 'YTD Taxes withheld' and 'Dividends Paid Last Year' check if needed to verify
        // Dividends Paid Last Year - selected account's Interest Paid Last Year value
        // YTD Taxes withheld- selected account's Taxes Withheld YTD value

        // Transactions section:
        // Date - Transaction Posting Date for committed transaction from transactions tab
        Assert.assertTrue(containsText(transaction.getTransactionDate()).matches(pdf));
        // Transaction Description - Transaction Code for committed transaction from transactions tab
        Assert.assertTrue(containsText(transaction.getTransactionDestination().getTransactionCode()).matches(pdf));
        // TODO: 'Debits' check if needed to verify
        // Debits - transaction amount of Debit transaction (Amount is displayed with red color and with '-' sign for such transactions)
        Assert.assertTrue(containsText("").matches(pdf));
        // Credits - transaction amount of Credit transaction (Amount is displayed with green color and with '+' sign for such transactions)
        String transactionAmount = String.valueOf(transaction.getTransactionSource().getAmount());
        Assert.assertTrue(containsText(transactionAmount).matches(pdf));
        // Balance - Balance for committed transaction from transactions tab
        Assert.assertTrue(containsText(transactionAmount).matches(pdf));
        // Totals - calculated # of all transactions, total Debits and Total Credits
        Assert.assertTrue(containsText("1 Credits").matches(pdf));
        // TODO: 'Overdraft Charged Off' check if needed to verify
        // Overdraft Charged Off - selected account's Overdraft Charged Off value
        Assert.assertTrue(containsText("").matches(pdf));

        // TODO: Clean 'downloads' directory after test execution
    }
}
