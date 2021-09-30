package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.PaymentAmountType;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.generation.transactions.builder.MiscDebitMiscCreditBuilder;
import com.nymbus.newmodels.generation.transactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.transactions.factory.SourceFactory;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C25481_ProcessEc416LoanPrepaymentTransactionPrinIntBillTest extends BaseTest {

    private Account loanAccount;
    private Transaction transaction_416;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private final String TEST_RUN_NAME = "Loans Management";
    private String nextPaymentBilledDueDate;
    private double interest;
    private int transaction109Amount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up accounts
        Account chkAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        loanAccount.setPaymentAmountType(PaymentAmountType.PRINCIPAL_AND_INTEREST.getPaymentAmountType());

        // Set proper dates
        String localDate = DateTime.getLocalDateOfPattern("MM/dd/yyyy");
        String dateMinusMonth = DateTime.getDateMinusMonth(localDate, 1);
        loanAccount.setDateOpened(DateTime.getDateWithFormatPlusDays(dateMinusMonth, "MM/dd/yyyy", "MM/dd/yyyy", 2));
        loanAccount.setNextPaymentBilledDueDate(DateTime.getDatePlusMonth(loanAccount.getDateOpened(), 1));
        loanAccount.setPaymentBilledLeadDays("1");
        chkAccount.setDateOpened(DateTime.getDateMinusMonth(loanAccount.getDateOpened(), 1));

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);
        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);

        // Set up deposit transaction
        int depositTransactionAmount = 12000;
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        depositTransaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        depositTransaction.getTransactionDestination().setAmount(depositTransactionAmount);
        depositTransaction.getTransactionSource().setAmount(depositTransactionAmount);

        // Set up 109 transaction
        transaction109Amount = 12000;
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscDebitSource.setAmount(transaction109Amount);
        miscCreditDestination.setAccountNumber(chkAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(transaction109Amount);

        // Set up 416 transaction
        transaction_416 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_416.getTransactionSource().setTransactionCode(TransactionCode.LOAN_PAYMENT_114.getTransCode());
        transaction_416.getTransactionSource().setAccountNumber(chkAccount.getAccountNumber());
        transaction_416.getTransactionDestination().setTransactionCode(TransactionCode.PAYMENT_416.getTransCode());
        transaction_416.getTransactionDestination().setAccountNumber(loanAccount.getAccountNumber());

        // Perform deposit transaction
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Perform 109 transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().setMiscDebitSource(miscDebitSource, 0);
        Actions.transactionActions().setMiscCreditDestination(miscCreditDestination, 0);
        Pages.tellerPage().setEffectiveDate(loanAccount.getDateOpened());
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();

        // Retrieve amount due value
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        Pages.accountDetailsPage().clickPaymentInfoTab();
        double activePaymentAmount = Double.parseDouble(Pages.accountPaymentInfoPage().getActivePaymentAmount());
        transaction_416.getTransactionSource().setAmount(activePaymentAmount);
        transaction_416.getTransactionDestination().setAmount(activePaymentAmount);

        // Perform 416 transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().setMiscDebitSourceForWithDraw(transaction_416.getTransactionSource(), 0);
        Actions.transactionActions().setMiscCreditDestination(transaction_416.getTransactionDestination(), 0);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();

        // Retrieve values
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        nextPaymentBilledDueDate = Pages.accountDetailsPage().getNextPaymentBilledDueDate();
        Pages.accountDetailsPage().clickPaymentInfoTab();
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        interest = Double.parseDouble(Pages.accountPaymentInfoPage().getInterest());
        Actions.loginActions().doLogOut();
    }

    @TestRailIssue(issueID = 25481, testRunName = TEST_RUN_NAME)
    @Test(description = "C25481, Process EC 416 loan prepayment transaction. Prin&Int (bill)")
    @Severity(SeverityLevel.CRITICAL)
    public void processEc416LoanPrepaymentTransactionPrinIntBill() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to 'Journal' page in the 'Menu' on the left side of the screen");
        Actions.transactionActions().loginTeller();
        Actions.journalActions().goToJournalPage();

        logInfo("Step 3: Find and select a transaction from precondition 4");
        Actions.journalActions().applyFilterByAccountNumber(transaction_416.getTransactionDestination().getAccountNumber());
        Actions.journalActions().clickLastTransaction();

        logInfo("Step 4: Click on the 'Error Correct' button");
        Pages.journalDetailsPage().clickErrorCorrectButton();
        Pages.journalDetailsPage().waitForErrorCorrectButtonInvisibility();
        TestRailAssert.assertTrue(Actions.journalActions().getTransactionState().equals("Void"),
                new CustomStepResult("Transaction state hasn't changed", "Transaction state has changed"));

        logInfo("Step 5: Open loan account from preconditions on the 'Details' tab");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        String latestNextPaymentBilledDueDate = Pages.accountDetailsPage().getNextPaymentBilledDueDate();

        logInfo("Step 6: Pay attention to the following fields:\n" +
                "- Next Payment Billed Due Date\n" +
                "- Interest paid to date\n" +
                "- Date Last Payment\n" +
                "- Date interest paid thru\n" +
                "- Current Balance\n" +
                "- Accrued Interest\n");
        String dateMinusMonth = DateTime.getDateMinusMonth(nextPaymentBilledDueDate, 1); // As PaymentFrequency == MONTHLY
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getNextPaymentBilledDueDate().equals(dateMinusMonth),
                new CustomStepResult("'Next Payment Billed Due Date' is not valid", "'Next Payment Billed Due Date' is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getInterestPaidToDate().equals("0.00"),
                new CustomStepResult("'Interest Paid To Date' is not valid", "'Interest Paid To Date' is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getDateInterestPaidThru().isEmpty(),
                new CustomStepResult("'Date interest paid thru' is not valid", "'Date interest paid thru' is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getDateLastPayment().isEmpty(),
                new CustomStepResult("'Date last payment' is not valid", "'Date last payment' is valid"));
        TestRailAssert.assertTrue(Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance()) == transaction109Amount,
                new CustomStepResult("'Current Balance' is not valid", "'Current Balance' is valid"));
        double actualAccruedInterest = Double.parseDouble(Pages.accountDetailsPage().getAccruedInterest());
        TestRailAssert.assertTrue(actualAccruedInterest == interest,
                new CustomStepResult("'Accrued Interest' is not valid", "'Accrued Interest' is valid"));

        logInfo("Step 7: Open 'Payment Info' tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 8: Check Payment Due record from preconditions");
        logInfo("Step 9: Click on the Payment Due record and check fields in the 'Payment Due Details' section");
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        double paymentAmount = Double.parseDouble(Pages.accountPaymentInfoPage().getActivePaymentAmount());
        double amountDue = Double.parseDouble(Pages.accountPaymentInfoPage().getAmountDueFromRecordByIndex(1));
        TestRailAssert.assertTrue(amountDue == paymentAmount,
                new CustomStepResult("Amount due is not valid", "Amount due is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(1).equals("Inactive Due to EC"),
                new CustomStepResult("'Status' is not valid", "'Status' is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getDatePaymentPaidInFull().isEmpty(),
                new CustomStepResult("Date Payment Paid In Full is not valid", "Date Payment Paid In Full is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getDisabledDueDate().equals(latestNextPaymentBilledDueDate),
                new CustomStepResult("'Due Date' is not valid", "'Due Date' is valid"));
        interest = Double.parseDouble(Pages.accountPaymentInfoPage().getInterest());
        double principal = Double.parseDouble(Pages.accountPaymentInfoPage().getPrincipal());

        logInfo("Step 10: Pay attention to the 'Transactions' section below 'Payment Due Details'");
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getAmountTotal().equals("0.00"),
                new CustomStepResult("Amount is not valid", "Amount is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getStatus().equals("416-Payment-EC"),
                new CustomStepResult("Status is not valid", "Status is valid"));

        logInfo("Steep 11: Go to 'Transactions' tab and verify transactions");
        Pages.accountDetailsPage().clickTransactionsTab();
        double amountValue = AccountActions.retrievingAccountData().getAmountValue(1);
        TestRailAssert.assertTrue(amountValue == amountDue,
                new CustomStepResult("Amount is not valid", "Amount is valid"));
        TestRailAssert.assertTrue(AccountActions.retrievingAccountData().getBalanceValue(1) == principal,
                new CustomStepResult("Principal is not valid", "Principal is valid"));
        TestRailAssert.assertTrue(AccountActions.retrievingAccountData().getInterestMinusValue(1) == interest,
                new CustomStepResult("Interest is not valid", "Interest is valid"));
    }
}
