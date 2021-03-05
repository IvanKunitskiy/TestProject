package com.nymbus.frontoffice.loansmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitMiscCreditBuilder;
import com.nymbus.newmodels.generation.tansactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.tansactions.factory.SourceFactory;
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
public class C25419_ProcessEc416LoanPaymentTransactionPrinIntBillTest extends BaseTest {
    private Account loanAccount;
    private Account checkAccount;
    private Transaction transaction_416;
//    private double transaction416Amount = 1001.00;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private String clientRootId;
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private String accruedInterest;

    private final String TEST_RUN_NAME = "Loans Management";
    private String nextPaymentBilledDueDate;
    private double currentBalance;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up accounts
        checkAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);
        Actions.loginActions().doLogOut();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        checkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
//        ClientsActions.individualClientActions().createClient(client);
//        ClientsActions.individualClientActions().setClientDetailsData(client);
//        ClientsActions.individualClientActions().setDocumentation(client);

        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenIndividualClientByID("62102");

        // Create account
        AccountActions.createAccount().createCHKAccount(checkAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);
        clientRootId = ClientsActions.createClient().getClientIdFromUrl();

        // Set up deposit transaction
        int depositTransactionAmount = 12000;
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        depositTransaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());
        depositTransaction.getTransactionDestination().setAmount(depositTransactionAmount);
        depositTransaction.getTransactionSource().setAmount(depositTransactionAmount);

        // Set up 109 transaction
        int transaction109Amount = 12000;
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscDebitSource.setAmount(transaction109Amount);
        miscCreditDestination.setAccountNumber(checkAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(transaction109Amount);

        // Set up 416 transaction
        transaction_416 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_416.getTransactionSource().setTransactionCode(TransactionCode.LOAN_PAYMENT_114.getTransCode());
        transaction_416.getTransactionSource().setAccountNumber(checkAccount.getAccountNumber());
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

        // Perform 109 transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().setMiscDebitSource(miscDebitSource, 0);
        Actions.transactionActions().setMiscCreditDestination(miscCreditDestination, 0);
        Pages.tellerPage().setEffectiveDate(loanAccount.getDateOpened());
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();
        Pages.tellerPage().closeModal();

        // Perform nonTellerTransactions
        Actions.nonTellerTransaction().generatePaymentDueRecord(clientRootId);
        Actions.loginActions().doLogOutProgrammatically();

        // Get 'amount due' for 416 transaction amount
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        nextPaymentBilledDueDate = Pages.accountDetailsPage().getNextPaymentBilledDueDate();
        currentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalanceFromHeaderMenu());
        accruedInterest = Pages.accountDetailsPage().getAccruedInterest();


        Pages.accountDetailsPage().clickPaymentInfoTab();
        double amountDue = Double.parseDouble(Pages.accountPaymentInfoPage().getAmountDueFromRecordByIndex(1));
        transaction_416.getTransactionSource().setAmount(amountDue);
        transaction_416.getTransactionDestination().setAmount(amountDue);
        System.out.println(amountDue);
        Selenide.sleep(6000);
        Actions.loginActions().doLogOut();

        // Perform 416 transaction
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().setMiscDebitSourceForWithDraw(transaction_416.getTransactionSource(), 0);
        Actions.transactionActions().setMiscCreditDestination(transaction_416.getTransactionDestination(), 0);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();
    }

    @TestRailIssue(issueID = 25419, testRunName = TEST_RUN_NAME)
    @Test(description = "C25419, Process EC 416 loan payment transaction. Prin&Int (bill)")
    @Severity(SeverityLevel.CRITICAL)
    public void processEc416LoanPaymentTransactionPrinIntBill() {

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

        logInfo("Step 6: Pay attention to the following fields:\n" +
                "- Next Payment Billed Due Date\n" +
                "- Interest paid to date\n" +
                "- Date Last Payment\n" +
                "- Date interest paid thru\n" +
                "- Current Balance\n" +
                "- Accrued Interest\n");
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getNextPaymentBilledDueDate().equals(loanAccount.getNextPaymentBilledDueDate()),
                new CustomStepResult("'Next Payment Billed Due Date' is not valid", "'Next Payment Billed Due Date' is valid"));
//        String dateTransactionPosted = DateTime.getLocalDateOfPattern("MM/dd/yyyy");
//        TestRailAssert.assertTrue(Pages.accountDetailsPage().getDateLastPayment().equals(dateTransactionPosted),
//                new CustomStepResult("'Date Last Payment' is not valid", "'Date Last Payment' is valid"));
//        TestRailAssert.assertTrue(Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance()) == currentBalance - transaction420Amount,
//                new CustomStepResult("'Current Balance' is not valid", "'Current Balance' is valid"));

        TestRailAssert.assertTrue(Pages.accountDetailsPage().getNextPaymentBilledDueDate().equals(nextPaymentBilledDueDate),
                new CustomStepResult("'Next Payment Billed Due Date' is not valid", "'Next Payment Billed Due Date' is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getInterestPaidToDate().equals(0.00),
                new CustomStepResult("'Interest Paid To Date' is not valid", "'Interest Paid To Date' is valid"));
        String dateTransactionPosted = DateTime.getLocalDateOfPattern("MM/dd/yyyy");
//        TestRailAssert.assertTrue(Pages.accountDetailsPage().getDateLastPayment().equals(dateBeforeTransactionPosted),
//                new CustomStepResult("'Date Last Payment' is not valid", "'Date Last Payment' is valid"));
        String dateInterestPaidThru = Pages.accountDetailsPage().getDateInterestPaidThru();
        String dateBeforeTransactionPosted = DateTime.getDateMinusDays(dateTransactionPosted, 1);
        TestRailAssert.assertTrue(dateInterestPaidThru.equals(dateBeforeTransactionPosted),
                new CustomStepResult("'Date interest paid thru' is not valid", "'Date interest paid thru' is valid"));
        double actualAccruedInterest = Double.parseDouble(Pages.accountDetailsPage().getAccruedInterest());
        double prevAccruedInterest = Double.parseDouble(accruedInterest);
//        TestRailAssert.assertTrue(actualAccruedInterest == prevAccruedInterest - transaction421Amount,
//                new CustomStepResult("'Accrued Interest' is not valid", "'Accrued Interest' is valid"));
        TestRailAssert.assertTrue(Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance()) == currentBalance,
                new CustomStepResult("'Current Balance' is not valid", "'Current Balance' is valid"));


        logInfo("Step 7: Open 'Payment Info' tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 8: Check Payment Due record from preconditions");
        double paymentAmount = Double.parseDouble(Pages.accountPaymentInfoPage().getActivePaymentAmount());
        double amountDue = Double.parseDouble(Pages.accountPaymentInfoPage().getAmountDueFromRecordByIndex(1));
        TestRailAssert.assertTrue(amountDue == paymentAmount,
                new CustomStepResult("Amount due is not valid", "Amount due is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(1).equals("Active"),
                new CustomStepResult("'Status' is not valid", "'Status' is valid"));

        logInfo("Step 9: Click on the Payment Due record and check fields in the 'Payment Due Details' section");
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getDatePaymentPaidInFull().isEmpty(),
                new CustomStepResult("Date Payment Paid In Full is not valid", "Date Payment Paid In Full is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getDueDate().equals(Pages.accountPaymentInfoPage().getPaymentDate()),
                new CustomStepResult("'Due Date' is not valid", "'Due Date' is valid"));
        double interest = Double.parseDouble(Pages.accountPaymentInfoPage().getDisabledInterest());
        double principal = Double.parseDouble(Pages.accountPaymentInfoPage().getDisabledPrincipal());

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
        TestRailAssert.assertTrue(AccountActions.retrievingAccountData().getBalanceValue(1) == interest,
                new CustomStepResult("Principal is not valid", "Principal is valid"));
        TestRailAssert.assertTrue(AccountActions.retrievingAccountData().getInterestMinusValue(1) == principal,
                new CustomStepResult("Interest is not valid", "Interest is valid"));
    }
}
