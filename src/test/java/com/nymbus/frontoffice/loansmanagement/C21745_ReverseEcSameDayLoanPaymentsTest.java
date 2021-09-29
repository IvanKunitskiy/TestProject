package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.CommitmentTypeAmt;
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
import com.nymbus.util.Random;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C21745_ReverseEcSameDayLoanPaymentsTest extends BaseTest {

    private Account loanAccount;
    private Transaction transaction_416;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private String nextPaymentBilledDueDate;
    private double currentBalance;
    private int numberOfPaymentsReceived;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        //Set up accounts
        Account chkAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        loanAccount.setPaymentAmountType(PaymentAmountType.PRIN_AND_INT.getPaymentAmountType());
        loanAccount.setCommitmentTypeAmt(CommitmentTypeAmt.NONE.getCommitmentTypeAmt());

        String dateOpened = loanAccount.getDateOpened();
        loanAccount.setDateOpened(DateTime.getDateMinusDays(dateOpened, 1));
        loanAccount.setNextPaymentBilledDueDate(DateTime.getDatePlusMonth(loanAccount.getDateOpened(), 1));
        chkAccount.setDateOpened(DateTime.getDateMinusMonth(loanAccount.getDateOpened(), 1));

        // Chk acc transaction
        double transactionAmount = 1001.00;
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        depositTransaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        depositTransaction.getTransactionDestination().setAmount(transactionAmount);
        depositTransaction.getTransactionSource().setAmount(transactionAmount);

        // 109 - transaction
        int transaction109Amount = 12000;
        Transaction transaction_109 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_109.getTransactionSource().setAccountNumber(loanAccount.getAccountNumber());
        transaction_109.getTransactionSource().setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        transaction_109.getTransactionSource().setAmount(transaction109Amount);
        transaction_109.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        transaction_109.getTransactionDestination().setAmount(transaction109Amount);
        transaction_109.getTransactionDestination().setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());

        // 416 - transaction
        transaction_416 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_416.getTransactionSource().setAccountNumber(chkAccount.getAccountNumber());
        transaction_416.getTransactionDestination().setAccountNumber(loanAccount.getAccountNumber());
        transaction_416.getTransactionSource().setTransactionCode(TransactionCode.LOAN_PAYMENT_114.getTransCode());
        transaction_416.getTransactionDestination().setTransactionCode(TransactionCode.PAYMENT_416.getTransCode());
        transaction_416.getTransactionSource().setAmount(transactionAmount);
        transaction_416.getTransactionDestination().setAmount(transactionAmount);

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);
        Actions.loginActions().doLogOut();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create accounts
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);
        String clientRootId = ClientsActions.createClient().getClientIdFromUrl();

        // Perform transaction to increase chk account 'Available Balance'
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

        // Perform non-teller transaction
        Actions.nonTellerTransaction().generatePaymentDueRecord(clientRootId);

        // Get accrued interest
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        nextPaymentBilledDueDate = Pages.accountDetailsPage().getNextPaymentBilledDueDate();
        currentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalanceFromHeaderMenu());
        numberOfPaymentsReceived = AccountActions.accountDetailsActions().getNumberOfPaymentsReceived();
        Pages.accountDetailsPage().clickPaymentInfoTab();
        double amountDue = Double.parseDouble(Pages.accountPaymentInfoPage().getAmountDueFromRecordByIndex(1));

        // assign amount to 416 transaction more than due date
        double transaction416Amount = amountDue + Random.genInt(10, 50);
        transaction_416.getTransactionDestination().setAmount(transaction416Amount);
        transaction_416.getTransactionSource().setAmount(transaction416Amount);

        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 21745 , testRunName = TEST_RUN_NAME)
    @Test(description = "C21745, Reverse (EC) same day loan payments")
    @Severity(SeverityLevel.CRITICAL)
    public void reverseEcSameDayLoanPayments() {

        logInfo("Step 1: Log in to Nymbus");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to 'Teller' screen");
        logInfo("Step 3: Log in to the proof date");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 4: Commit 416 transaction with the following fields:\n" +
                "Sources -> Misc Debit:\n" +
                "\"Account Number\" - active CHK or SAV account from preconditions\n" +
                "\"Transaction Code\" - \"114 - Loan Payment\"\n" +
                "Amount = Payment Info -> Payments Due record from preconditions -> Amount Due\n" +
                "Destinations -> Misc Credit:\n" +
                "Account number - Loan account from preconditions\n" +
                "\"Transaction Code\" - \"416 - Payment\"\n" +
                "\"Amount\" - specify the same amount");
        logInfo("Step 5: Close Transaction Receipt popup");
        Actions.transactionActions().createTransaction(transaction_416);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        logInfo("Step 6: Open loan account from preconditions on the 'Details' tab");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());

        // Get values from Payments Due record after performing transaction
        Pages.accountDetailsPage().clickPaymentInfoTab();
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        double interest = Double.parseDouble(Pages.accountPaymentInfoPage().getInterest());
        double principal = Double.parseDouble(Pages.accountPaymentInfoPage().getPrincipal());
        double escrow = Double.parseDouble(Pages.accountPaymentInfoPage().getEscrow());

        Pages.accountDetailsPage().clickDetailsTab();

        logInfo("Step 7: Pay attention to the following fields:\n" +
                "- Next Payment Billed Due Date\n" +
                "- Interest paid to date\n" +
                "- # payments received\n" +
                "- Current Balance");
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getNextPaymentBilledDueDate().equals(nextPaymentBilledDueDate),
                new CustomStepResult("'Next Payment Billed Due Date' is not valid", "'Next Payment Billed Due Date' is valid"));
        double interestPaidToDate = Double.parseDouble(Pages.accountDetailsPage().getInterestPaidToDate());
        TestRailAssert.assertTrue(interestPaidToDate == interest,
                new CustomStepResult("'Interest Paid To Date' is not valid", "'Interest Paid To Date' is valid"));
        double actualCurrentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance());
        TestRailAssert.assertTrue(actualCurrentBalance == currentBalance - principal,
                new CustomStepResult("'Current Balance' is not valid", "'Current Balance' is valid"));
        int currentNumberOfPaymentsReceived = Integer.parseInt(Pages.accountDetailsPage().getNumberOfPaymentsReceived());
        TestRailAssert.assertTrue(currentNumberOfPaymentsReceived == numberOfPaymentsReceived + 1,
                new CustomStepResult("'Date interest paid thru' is not valid", "'Date interest paid thru' is valid"));

        logInfo("Step 8: Go to the 'Transactions' tab and verify payment portions");
        Pages.accountDetailsPage().clickTransactionsTab();
        TestRailAssert.assertTrue(AccountActions.retrievingAccountData().getBalanceValue(1) == principal,
                new CustomStepResult("Principal is not valid", "Principal is valid"));
        TestRailAssert.assertTrue(AccountActions.retrievingAccountData().getInterestMinusValue(1) == interest,
                new CustomStepResult("Interest is not valid", "Interest is valid"));
        TestRailAssert.assertTrue(AccountActions.retrievingAccountData().getEscrowMinusValue(1) == escrow,
                new CustomStepResult("Escrow is not valid", "Escrow is valid"));

        logInfo("Step 9: Go to 'Journal' page in the 'Menu' on the left side of the screen");
        Actions.journalActions().goToJournalPage();

        logInfo("Step 10: Find and select a transaction from precondition 4");
        Actions.journalActions().applyFilterByAccountNumber(transaction_416.getTransactionDestination().getAccountNumber());
        Actions.journalActions().clickLastTransaction();

        logInfo("Step 11: Click on the 'Error Correct' button");
        Pages.journalDetailsPage().clickErrorCorrectButton();
        Pages.journalDetailsPage().waitForErrorCorrectButtonInvisibility();
        TestRailAssert.assertTrue(Actions.journalActions().getTransactionState().equals("Void"),
                new CustomStepResult("Transaction state hasn't changed", "Transaction state has changed"));

        logInfo("Step 12: Open loan account from preconditions on the 'Details' tab");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);

        logInfo("Step 13: ay attention to the following fields:\n" +
                "- Next Payment Billed Due Date\n" +
                "- Interest paid to date\n" +
                "- # payments received\n" +
                "- Current Balance");
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getNextPaymentBilledDueDate().equals(nextPaymentBilledDueDate),
                new CustomStepResult("'Next Payment Billed Due Date' is not valid", "'Next Payment Billed Due Date' is valid"));
        TestRailAssert.assertTrue(Double.parseDouble(Pages.accountDetailsPage().getInterestPaidToDate()) == interestPaidToDate - interest,
                new CustomStepResult("'Interest Paid To Date' is not valid", "'Interest Paid To Date' is valid"));
        actualCurrentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance());
        TestRailAssert.assertTrue(actualCurrentBalance == currentBalance,
                new CustomStepResult("'Current Balance' is not valid", "'Current Balance' is valid"));
        currentNumberOfPaymentsReceived = AccountActions.accountDetailsActions().getNumberOfPaymentsReceived();
        TestRailAssert.assertTrue(currentNumberOfPaymentsReceived == numberOfPaymentsReceived,
                new CustomStepResult("'Date interest paid thru' is not valid", "'Date interest paid thru' is valid"));

        logInfo("Step 14: Open account from preconditions on the 'Payment Info' tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 15: Verify status of the payment due record for the current payment perіod");
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(1).equals("Active"),
                new CustomStepResult("'Status' is not valid", "'Status' is valid"));
    }
}
