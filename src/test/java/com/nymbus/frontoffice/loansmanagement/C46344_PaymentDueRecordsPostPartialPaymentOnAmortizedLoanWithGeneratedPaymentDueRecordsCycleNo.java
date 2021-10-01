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
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C46344_PaymentDueRecordsPostPartialPaymentOnAmortizedLoanWithGeneratedPaymentDueRecordsCycleNo extends BaseTest {

    private Account loanAccount;
    private Account chkAccount;
    private String clientRootId;
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

        Actions.loginActions().doLogOutProgrammatically();

    }

    @TestRailIssue(issueID = 46344, testRunName = TEST_RUN_NAME)
    @Test(description = "C46344, Payment Due Records: Post partial payment on Amortized loan with generated Payment Due Records | Cycle == 'No'")
    @Severity(SeverityLevel.CRITICAL)
    public void paymentDueRecordsPostPartialPaymentOnAmortizedLoanWithGeneratedPaymentDueRecordsCycleNo() {

        logInfo("Step 1: Log in to the NYMBUS");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to the 'Teller' screen");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Log in to the proof date");
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 4: Fill in the following fields and click the [Commit Transaction] :\n" +
                "Sources:\n" +
                "\n" +
                "\"Account number\": active CHK or SAV account from precondition #3\n" +
                "\"Transaction Code\": specify trancode \"116 - Withdrawal\"\n" +
                "\"Amount\": less than Payment amount for loan from precondition #4 (f.e. Payment Amount == 1,000.00, transaction amount == \"500.00\")\n" +
                "Destinations:\n" +
                "\n" +
                "\"Account Number\": loan account from precondition #4\n" +
                "\"Transaction Code\": - \"416 - Payment\"\n" +
                "\"Amount\": the same amount as in Sources");

        // set up 416 transaction
        double transactionAmount = 500.00;

        transaction_416 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_416.getTransactionSource().setTransactionCode(TransactionCode.WITHDRAWAL_116.getTransCode());
        transaction_416.getTransactionSource().setAccountNumber(chkAccount.getAccountNumber());
        transaction_416.getTransactionSource().setAmount(transactionAmount);
        transaction_416.getTransactionDestination().setTransactionCode(TransactionCode.PAYMENT_416.getTransCode());
        transaction_416.getTransactionDestination().setAccountNumber(loanAccount.getAccountNumber());
        transaction_416.getTransactionDestination().setAmount(transactionAmount);

        // perform 416 transaction
        Actions.transactionActions().setMiscDebitSourceForWithDraw(transaction_416.getTransactionSource(), 0);
        Actions.transactionActions().setMiscCreditDestination(transaction_416.getTransactionDestination(), 0);

        Actions.transactionActions().clickCommitButton();

        logInfo("Step 5: Close Transaction Receipt popup");
        Pages.tellerPage().closeModal();

        logInfo("Step 6: Open loan account from preconditions on the Payment Info tab");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);

        Pages.accountDetailsPage().clickTransactionsTab();
        Pages.accountTransactionPage().waitForTransactionSection();

        String transactionPrincipal = Pages.accountTransactionPage().getPrincipalValue(1) + Pages.accountTransactionPage().getPrincipalFractionalPartValue(1);
        String transactionInterest = Pages.accountTransactionPage().getInterestValue(1) + Pages.accountTransactionPage().getInterestMinusFractionalValue(1);

        Pages.accountDetailsPage().clickDetailsTab();
        Pages.accountDetailsPage().waitForEditButton();
        String nextPaymentBilledDueDateBefore = Pages.accountDetailsPage().getNextPaymentBilledDueDate();

        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 7: Click on the Payment Due Record with Status == 'Partially Paid' in the 'Payments Due' section");
        Pages.accountPaymentInfoPage().clickPaymentDueRecordByIndex(1);

        logInfo("Step 8: Verify Amount Due, Status fields values in the 'Payment Due Details' section");
        String amountDueActual = Pages.accountPaymentInfoPage().getDisabledAmountDue().replaceAll("[^.0-9]", "");
        double amountDueExpected = Double.parseDouble(loanAccount.getPaymentAmount()) - transactionAmount;
        String status = Pages.accountPaymentInfoPage().getDisabledStatus();

        TestRailAssert.assertTrue(amountDueActual.equals(amountDueExpected + "0"),
                new CustomStepResult("'Amount Due' is valid", "'Amount Due' is not valid"));
        TestRailAssert.assertTrue(status.equals("Partially Paid"),
                new CustomStepResult("'Status' is valid", "'Status' is not valid"));

        logInfo("Step 9: Verify that the transaction record generated by the transaction from Step 4 is present in the 'Transactions' section");
        String paymentDate = Pages.accountPaymentInfoPage().getPaymentDate();
        String interest = Pages.accountPaymentInfoPage().getInterest();
        String principal = Pages.accountPaymentInfoPage().getPrincipal();
        String escrow = Pages.accountPaymentInfoPage().getEscrow();
        String amount = Pages.accountPaymentInfoPage().getAmount();
        String tranCodeStatus = Pages.accountPaymentInfoPage().getStatus();

        TestRailAssert.assertTrue(DateTime.getLocalDateOfPattern("MM/dd/yyyy").equals(paymentDate),
                new CustomStepResult("'Payment Date' is valid", "'Payment Date' is not valid"));
        TestRailAssert.assertTrue(amount.equals(transactionAmount + "0"),
                new CustomStepResult("'Amount' is valid", "'Amount' is not valid"));
        TestRailAssert.assertTrue(principal.equals(transactionPrincipal),
                new CustomStepResult("'Principal' is valid", "'Principal' is not valid"));
        TestRailAssert.assertTrue(interest.equals(transactionInterest),
                new CustomStepResult("'Interest' is valid", "'Interest' is not valid"));
        TestRailAssert.assertTrue(escrow.isEmpty(),
                new CustomStepResult("'Escrow' is valid", "'Escrow' is not valid"));
        TestRailAssert.assertTrue(tranCodeStatus.equals("416 Payment"),
                new CustomStepResult("'Tran Code/Status' is valid", "'Tran Code/Status' is not valid"));


        logInfo("Step 10: Open loan account from precondition on the Details tab");
        Pages.accountDetailsPage().clickDetailsTab();

        logInfo("Step 11: Verify 'Daily interest factor', 'Next Payment Billed Due Date', and 'Interest paid to date' fields values");

        double currentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance());
        String dailyInterestFactorExpected = Functions.getDoubleWithNDecimalPlaces(currentBalance * 0.1 / 360, 4);

        String dailyInterestFactorActual = Pages.accountDetailsPage().getDailyInterestFactor();
        String nextPaymentBilledDueDateActual = Pages.accountDetailsPage().getNextPaymentBilledDueDate();
        String interestPaidToDateActual = Pages.accountDetailsPage().getInterestPaidToDate();

        TestRailAssert.assertTrue(dailyInterestFactorExpected.equals(dailyInterestFactorActual),
                new CustomStepResult("'Daily interest factor' is valid", "'Daily interest factor' is not valid"));
        TestRailAssert.assertTrue(nextPaymentBilledDueDateActual.equals(nextPaymentBilledDueDateBefore),
                new CustomStepResult("'Next Payment Billed Due Date' is valid", "'Next Payment Billed Due Date' is not valid"));
        TestRailAssert.assertTrue(interestPaidToDateActual.equals(transactionInterest),
                new CustomStepResult("'Interest Paid to Date' is valid", "'Interest Paid to Date' is not valid"));
    }
}
