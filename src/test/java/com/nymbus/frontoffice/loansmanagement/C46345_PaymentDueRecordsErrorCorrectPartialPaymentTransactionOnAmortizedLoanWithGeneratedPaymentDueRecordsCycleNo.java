package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.loans.DaysBaseYearBase;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.CommitmentTypeAmt;
import com.nymbus.newmodels.account.loanaccount.InterestMethod;
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
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C46345_PaymentDueRecordsErrorCorrectPartialPaymentTransactionOnAmortizedLoanWithGeneratedPaymentDueRecordsCycleNo extends BaseTest {

    private Account loanAccount;
    private Account chkAccount;
    private String clientRootId;
    String currentBalanceBefore;
    double transactionAmount;
    String interestPaidToDateAfter416;
    String nextPaymentBilledDueDateBefore;
    Transaction transaction_416;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private final String TEST_RUN_NAME = "Loans Management";

    @BeforeMethod
    public void preConditions() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up accounts
        chkAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        loanAccount.setPaymentAmountType(PaymentAmountType.PRIN_AND_INT.getPaymentAmountType());

        // fields needed for 'Amortized' loan account
        loanAccount.setInterestMethod(InterestMethod.AMORTIZED.getInterestMethod());
        loanAccount.setCommitmentTypeAmt(CommitmentTypeAmt.NONE.getCommitmentTypeAmt());
        loanAccount.setCommitmentAmt("");
        loanAccount.setDaysBaseYearBase(DaysBaseYearBase.DAY_YEAR_360_360.getDaysBaseYearBase());

        // Set proper dates
        loanAccount.setNextPaymentBilledDueDate(DateTime.getDatePlusMonth(loanAccount.getDateOpened(), 1));
        loanAccount.setPaymentBilledLeadDays("1");
        loanAccount.setCycleLoan(false);
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

        // 109 - transaction
        int transaction109Amount = 12000;
        Transaction transaction_109 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_109.getTransactionSource().setAccountNumber(loanAccount.getAccountNumber());
        transaction_109.getTransactionSource().setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        transaction_109.getTransactionSource().setAmount(transaction109Amount);
        transaction_109.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        transaction_109.getTransactionDestination().setAmount(transaction109Amount);
        transaction_109.getTransactionDestination().setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());

        // Perform deposit transaction
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        // Re-login to refresh teller session
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Perform '109 - deposit' transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(transaction_109);
        Pages.tellerPage().setEffectiveDate(loanAccount.getDateOpened());
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();
        Pages.tellerPage().closeModal();

        // Generate Payment Due record
        Actions.nonTellerTransaction().generatePaymentDueRecord(clientRootId);

        // Get 'Current Balance' value before 'Partially Paid' transaction
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        currentBalanceBefore = Pages.accountDetailsPage().getCurrentBalance();

        // Re-login to refresh teller session
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // set up 416 transaction
        transactionAmount = 500.00;

        transaction_416 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_416.getTransactionSource().setTransactionCode(TransactionCode.WITHDRAWAL_116.getTransCode());
        transaction_416.getTransactionSource().setAccountNumber(chkAccount.getAccountNumber());
        transaction_416.getTransactionSource().setAmount(transactionAmount);
        transaction_416.getTransactionDestination().setTransactionCode(TransactionCode.PAYMENT_416.getTransCode());
        transaction_416.getTransactionDestination().setAccountNumber(loanAccount.getAccountNumber());
        transaction_416.getTransactionDestination().setAmount(transactionAmount);

        // perform 416 transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().setMiscDebitSourceForWithDraw(transaction_416.getTransactionSource(), 0);
        Actions.transactionActions().setMiscCreditDestination(transaction_416.getTransactionDestination(), 0);

        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        // Get 'Interest paid to date' and 'Next Payment Billed Due Date' after 416 transaction
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        interestPaidToDateAfter416 = Pages.accountDetailsPage().getInterestPaidToDate();
        nextPaymentBilledDueDateBefore = Pages.accountDetailsPage().getNextPaymentBilledDueDate();

        Actions.loginActions().doLogOutProgrammatically();

    }

    @TestRailIssue(issueID = 46345, testRunName = TEST_RUN_NAME)
    @Test(description = "C46345, Payment Due Records: Error Correct partial payment transaction on Amortized loan with generated Payment Due Records | Cycle == 'No'")
    @Severity(SeverityLevel.CRITICAL)
    public void paymentDueRecordsErrorCorrectPartialPaymentTransactionOnAmortizedLoanWithGeneratedPaymentDueRecordsCycleNo() {

        logInfo("Step 1: Log in to the NYMBUS");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to the 'Journal'");
        Actions.transactionActions().loginTeller();
        Actions.journalActions().goToJournalPage();

        logInfo("Step 3: Open transaction record that was created after transaction caused Payment Due Record from precondition #5 to change Status to 'Partially Paid'");
        Actions.journalActions().applyFilterByAccountNumber(loanAccount.getAccountNumber());
        Pages.journalPage().waitForMainSpinnerInvisibility();

        Actions.journalActions().clickLastTransaction();
        Pages.journalPage().waitForMainSpinnerInvisibility();

        logInfo("Step 4: Click [Error Correct] button");
        Pages.journalDetailsPage().clickErrorCorrectButton();
        Pages.journalDetailsPage().waitForCreateErrorApplying();

        logInfo("Step 5: Open loan account from precondition #4 on the 'Transactions' tab");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);

        Pages.accountDetailsPage().waitForEditButton();
        String currentBalanceActual = Pages.accountDetailsPage().getCurrentBalance();

        Pages.accountDetailsPage().clickTransactionsTab();
        Pages.accountTransactionPage().waitForTransactionSection();

        logInfo("Step 6: Look at the generated transactions and verify:\n" +
                "Transaction Code;\n" +
                "Amount;\n" +
                "Balance;\n" +
                "EC;\n" +
                "Current Balance");
        String transactionAmountActual = Pages.accountTransactionPage().getAmountValue(1) + Pages.accountTransactionPage().getAmountFractionalValue(1);


        String transFromPreconditionsEffectiveDate = Pages.accountTransactionPage().getEffectiveDateValue(2);
        String transFromPreconditionsInterest = Pages.accountTransactionPage().getInterestValue(2) + Pages.accountTransactionPage().getInterestMinusFractionalValue(2);
        String transFromPreconditionsPrincipal = Pages.accountTransactionPage().getPrincipalValue(2) + Pages.accountTransactionPage().getPrincipalFractionalPartValue(2);
        String transFromPreconditionsAmount = Pages.accountTransactionPage().getAmountValue(2) + Pages.accountTransactionPage().getAmountFractionalValue(2);

        TestRailAssert.assertTrue(transactionAmountActual.equals(transactionAmount + "0"),
                new CustomStepResult("'Amount' is valid", "'Amount' is not valid"));
        TestRailAssert.assertTrue(currentBalanceActual.equals(currentBalanceBefore),
                new CustomStepResult("'Current Balance' is valid", "'Current Balance' is not valid"));
        TestRailAssert.assertTrue(currentBalanceActual.equals(currentBalanceBefore),
                new CustomStepResult("'Current Balance' is valid", "'Current Balance' is not valid"));
        TestRailAssert.assertTrue(Pages.accountTransactionPage().getTransactionCodeByIndex(1).equals(TransactionCode.PAYMENT_416.getTransCode()),
                new CustomStepResult("'Transaction Code' is valid", "'Transaction Code' is not valid"));
        TestRailAssert.assertTrue(Pages.accountTransactionPage().getEcColumnValue(1).equals("EC"),
                new CustomStepResult("'EC' is in 'EC' column", "'EC' is not in 'EC' column"));

        logInfo("Step 7: Open loan account from precondition on the Payment Info tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 8: Look at the Payments Due section of the screen for Payment Due Record from precondition #5 and click on it.");
        Pages.accountPaymentInfoPage().clickLastPaymentDueRecord();

        logInfo("Step 9: Verify Amount Due, Status fields values for 'Payment Due Details' section");
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getAmountDueFromRecordByIndex(1).equals(loanAccount.getPaymentAmount()),
                new CustomStepResult("'Amount Due' is valid", "'Amount Due' is invalid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getDueStatus().equals("Active"),
                new CustomStepResult("'Status' is valid", "'Status' is invalid"));

        logInfo("Step 10: Verify that transaction record generated by the transaction caused Payment Due Record from precondition #5 to change Status to 'Partially Paid' is present in the Transactions section");
        String paymentDate = Pages.accountPaymentInfoPage().getPaymentDate();
        String interest = Pages.accountPaymentInfoPage().getInterest();
        String principal = Pages.accountPaymentInfoPage().getPrincipal();
        String escrow = Pages.accountPaymentInfoPage().getEscrow();
        String amount = Pages.accountPaymentInfoPage().getAmount();
        String tranCodeStatus = Pages.accountPaymentInfoPage().getStatus();

        TestRailAssert.assertTrue(transFromPreconditionsEffectiveDate.equals(paymentDate),
                new CustomStepResult("'Payment Date' is valid", "'Payment Date' is not valid"));
        TestRailAssert.assertTrue(amount.equals(transFromPreconditionsAmount),
                new CustomStepResult("'Amount' is valid", "'Amount' is not valid"));
        TestRailAssert.assertTrue(principal.equals(transFromPreconditionsPrincipal),
                new CustomStepResult("'Principal' is valid", "'Principal' is not valid"));
        TestRailAssert.assertTrue(interest.equals(transFromPreconditionsInterest),
                new CustomStepResult("'Interest' is valid", "'Interest' is not valid"));
        TestRailAssert.assertTrue(escrow.isEmpty(),
                new CustomStepResult("'Escrow' is valid", "'Escrow' is not valid"));
        TestRailAssert.assertTrue(tranCodeStatus.equals("416-Payment-EC"),
                new CustomStepResult("'Tran Code/Status' is valid", "'Tran Code/Status' is not valid"));


        logInfo("Step 11: Open loan account from precondition on the Details tab");
        Pages.accountDetailsPage().clickDetailsTab();
        Pages.accountDetailsPage().waitForEditButton();

        logInfo("Step 12: Verify 'Daily interest factor', 'Next Payment Billed Due Date', and 'Interest paid to date' fields values");
        double currentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance());
        String dailyInterestFactorExpected = Functions.getDoubleWithNDecimalPlaces(currentBalance * 0.1 / 360, 4);
        String interestPaidToDateExpected = (Double.parseDouble(interestPaidToDateAfter416) - Double.parseDouble(transFromPreconditionsInterest)) + "0";

        String dailyInterestFactorActual = Pages.accountDetailsPage().getDailyInterestFactor();
        String nextPaymentBilledDueDateActual = Pages.accountDetailsPage().getNextPaymentBilledDueDate();
        String interestPaidToDateActual = Pages.accountDetailsPage().getInterestPaidToDate();

        TestRailAssert.assertTrue(dailyInterestFactorExpected.equals(dailyInterestFactorActual),
                new CustomStepResult("'Daily interest factor' is valid", "'Daily interest factor' is not valid"));
        TestRailAssert.assertTrue(nextPaymentBilledDueDateActual.equals(nextPaymentBilledDueDateBefore),
                new CustomStepResult("'Next Payment Billed Due Date' is valid", "'Next Payment Billed Due Date' is not valid"));
        TestRailAssert.assertTrue(interestPaidToDateActual.equals(interestPaidToDateExpected),
                new CustomStepResult("'Interest Paid to Date' is valid", "'Interest Paid to Date' is not valid"));
    }

}

