package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
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
@Owner("Dmytro")
public class C25378_ProcessLoanPaymentTransactionTest extends BaseTest {
    private Account loanAccount;
    private Account checkAccount;
    private Transaction transaction;
    private double transactionAmount = 1001.00;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private String clientRootId;
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private String accruedInterest;
    private double paidInterest;
    private double balance;


    @BeforeMethod
    public void precondition() {
        // Set up Client and Accounts
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        checkAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        loanAccount.setNextPaymentBilledDueDate(DateTime.getDateWithFormatPlusDays(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), "MM/dd/yyyy", "MM/dd/yyyy", 3));
        String dateOpened = loanAccount.getDateOpened();
        loanAccount.setDateOpened(DateTime.getDateMinusDays(dateOpened, 1));
        checkAccount.setDateOpened(DateTime.getDateMinusMonth(loanAccount.getDateOpened(), 1));

        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        transaction = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();

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
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);
        clientRootId = ClientsActions.createClient().getClientIdFromUrl();

        // Set up transactions with account number
        depositTransaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());
        depositTransaction.getTransactionDestination().setAmount(transactionAmount);
        depositTransaction.getTransactionSource().setAmount(transactionAmount);
        transaction.getTransactionSource().setAccountNumber(checkAccount.getAccountNumber());
        transaction.getTransactionSource().setAmount(transactionAmount);
        transaction.getTransactionSource().setTransactionCode(TransactionCode.LOAN_PAYMENT_114.getTransCode());
        transaction.getTransactionDestination().setAmount(transactionAmount);
        transaction.getTransactionDestination().setTransactionCode("416 - Payment");
        transaction.getTransactionDestination().setAccountNumber(loanAccount.getAccountNumber());
        int transactionAmount = 12000;
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscDebitSource.setAmount(transactionAmount);
        miscCreditDestination.setAccountNumber(checkAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(transactionAmount);

        // Perform deposit transactions
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        // Perform transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().setMiscDebitSource(miscDebitSource, 0);
        Actions.transactionActions().setMiscCreditDestination(miscCreditDestination, 0);
        Pages.tellerPage().setEffectiveDate(loanAccount.getDateOpened());
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();
        Pages.tellerPage().closeModal();

        // Create nonTellerTransactions
        Actions.nonTellerTransaction().generatePaymentDueRecord(clientRootId);

        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        accruedInterest = Pages.accountDetailsPage().getAccruedInterest();
        paidInterest = Double.parseDouble(Pages.accountDetailsPage().getInterestPaidToDate());
        balance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance());

        Actions.loginActions().doLogOutProgrammatically();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25378, testRunName = TEST_RUN_NAME)
    @Test(description = "C25378, Process 416 loan payment transaction", enabled = false)
    @Severity(SeverityLevel.CRITICAL)
    public void process416LoanPaymentTransaction() {
        logInfo("Step 1: Log in to SmartCore");
        SelenideTools.openUrl(Constants.URL);
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to \"Teller\" page");
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Log in to the proof date");
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
        int currentIndex = 0;
        Actions.transactionActions().setMiscDebitSourceForWithDraw(transaction.getTransactionSource(), currentIndex);
        Actions.transactionActions().setMiscCreditDestination(transaction.getTransactionDestination(), currentIndex);
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 5: Close Transaction Receipt popup");
        Pages.tellerPage().closeModal();

        logInfo("Step 6: Open loan account from preconditions on the \"Details\" tab");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        Pages.accountDetailsPage().clickTransactionsTab();
        double interestValueForPaid = AccountActions.retrievingAccountData().getInterestMinusValue(1);
        String postingDateValue = Pages.accountTransactionPage().getPostingDateValue(1);
        double escrowMinusValue = AccountActions.retrievingAccountData().getEscrowMinusValue(1);
        Pages.accountDetailsPage().clickDetailsTab();

        logInfo("Step 7: Pay attention to the following fields:\n" +
                "- Next Payment Billed Due Date\n" +
                "- Interest paid to date\n" +
                "- Date Last Payment\n" +
                "- Date interest paid thru\n" +
                "- Current Balance\n" +
                "- Accrued Interest");
        String nextDueDate = Pages.accountDetailsPage().getNextDueDate();
        TestRailAssert.assertTrue(nextDueDate.equals(DateTime.getDatePlusMonth(loanAccount.getNextPaymentBilledDueDate(), 1)),
                new CustomStepResult("'NextPaymentBilledDueDate' is not valid", "'NextPaymentBilledDueDate' is valid"));
        double paidInterest = Double.parseDouble(Pages.accountDetailsPage().getInterestPaidToDate());
        TestRailAssert.assertTrue(paidInterest == (this.paidInterest+interestValueForPaid),
                new CustomStepResult("Paid interest is not valid",
                "Paid interest is  valid"));
        String dateLastPayment = Pages.accountDetailsPage().getDateLastPayment();
        TestRailAssert.assertTrue(dateLastPayment.equals(postingDateValue),
                new CustomStepResult("'DateLastPayment' is not valid", "'DateLastPayment' is valid"));
        String dateInterestPaidThru = Pages.accountDetailsPage().getDateInterestPaidThru();
        TestRailAssert.assertTrue(dateInterestPaidThru.equals(DateTime.getDateMinusDays(loanAccount.getNextPaymentBilledDueDate(), 1)),
                new CustomStepResult("'DateInterestPaidThru' is not valid", "'DateInterestPaidThru' is valid"));
        String currentBalance = Pages.accountDetailsPage().getCurrentBalance();
        TestRailAssert.assertTrue(Double.parseDouble(currentBalance) == (balance -transaction.getTransactionSource().getAmount()
                        + interestValueForPaid + escrowMinusValue),
                new CustomStepResult("Current balance is not valid", "Current balance is valid"));
        String actualAccruedInterest = Pages.accountDetailsPage().getAccruedInterest();
        TestRailAssert.assertTrue(actualAccruedInterest.equals(String.format("%.2f", (Double.parseDouble(accruedInterest)
                        - interestValueForPaid))),
                new CustomStepResult("Accrued interest is not valid", "Accrued interest is valid"));
        String nextPaymentBilledDueDate = Pages.accountDetailsPage().getNextPaymentBilledDueDate();

        logInfo("Step 8: Open \"Payment Info\" tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 9: Click on the Payment Due record in the \"Payments Due\" section and pay attention " +
                "to the Payment Due Details");
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        String amountDue = Pages.accountPaymentInfoPage().getDisabledAmountDue();
        TestRailAssert.assertTrue(amountDue.equals("$ 0.00"),
                new CustomStepResult("Amount due is not valid", "Amount due is valid"));

        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().paidStatusIsVisibility(),
                new CustomStepResult("Paid is not visible", "Paid is visible"));
        String datePaymentPaidInFull = Pages.accountPaymentInfoPage().getDatePaymentPaidInFull();
        TestRailAssert.assertTrue(datePaymentPaidInFull.equals(dateLastPayment),
                new CustomStepResult("Date Payment Paid In Full is not valid",
                        "Date Payment Paid In Full is valid"));

        String dueDate = Pages.accountPaymentInfoPage().getDisabledDueDate();

        TestRailAssert.assertTrue(dueDate.equals(DateTime.getDateMinusMonth(nextPaymentBilledDueDate, 1)),
                new CustomStepResult("Due date is not valid", "Due date is valid"));

        logInfo("Step 10: Pay attention to the 'Transactions' section");
        String paymentDate = Pages.accountPaymentInfoPage().getPaymentDate();
        TestRailAssert.assertTrue(paymentDate.equals(dateLastPayment),
                new CustomStepResult("Payment date is not valid", "Payment date is valid"));
        String interest = Pages.accountPaymentInfoPage().getInterest();
        TestRailAssert.assertTrue(interestValueForPaid== Double.parseDouble(interest),
                new CustomStepResult("Interest is not valid", "Interest due is valid"));
        String amount = Pages.accountPaymentInfoPage().getAmount();
        String escrow = Pages.accountPaymentInfoPage().getEscrow();
        String principal = Pages.accountPaymentInfoPage().getPrincipal();
        double sum = Double.parseDouble(interest) + Double.parseDouble(escrow) + Double.parseDouble(principal);
        TestRailAssert.assertTrue(Double.parseDouble(amount) == sum,
                new CustomStepResult("Amount sum is not valid", "Amount sum is valid"));
        TestRailAssert.assertTrue(Double.parseDouble(amount) == transactionAmount,
                new CustomStepResult("Amount is not valid", "Amount is valid"));
        String status = Pages.accountPaymentInfoPage().getStatus();
        TestRailAssert.assertTrue(status.equals("416 Payment"),
                new CustomStepResult("Status is not valid", "Status is valid"));

        logInfo("Step 11: Go to Transaction tab and verify payment portions");
        Pages.accountDetailsPage().clickTransactionsTab();
        double amountValue = AccountActions.retrievingAccountData().getAmountValue(1);
        TestRailAssert.assertTrue(amountValue == transactionAmount,
                new CustomStepResult("Amount is not valid", "Amount is valid"));
        double principalValue = AccountActions.retrievingAccountData().getPrincipalValue(1);
        TestRailAssert.assertTrue(principalValue == Double.parseDouble(principal),
                new CustomStepResult("Principal is not valid", "Principal is valid"));
        double interestValue = AccountActions.retrievingAccountData().getInterestMinusValue(1);
        TestRailAssert.assertTrue(interestValue == Double.parseDouble(interest),
                new CustomStepResult("Interest is not valid", "Interest is valid"));
        double escrowValue = AccountActions.retrievingAccountData().getEscrowMinusValue(1);
        TestRailAssert.assertTrue(escrowValue == Double.parseDouble(escrow),
                new CustomStepResult("Escrow is not valid", "Escrow is valid"));

    }
}
