package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
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
public class C25382_ProcessIntPayOnlyPaymentTransaction extends BaseTest {
    private Account loanAccount;
    private Account checkAccount;
    private Transaction transaction;
    private double transactionAmount = 1001.00;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private String clientRootId;
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private int balance;
    private String amountDue;


    @BeforeMethod
    public void precondition() {
        // Set up Client and Accounts
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        checkAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setEscrow("$ 0.00");
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        transaction = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        checkAccount.setDateOpened(loanAccount.getDateOpened());

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
        depositTransaction.setTransactionDate(loanAccount.getDateOpened());
        transaction.getTransactionSource().setAccountNumber(checkAccount.getAccountNumber());
        transaction.getTransactionSource().setTransactionCode(TransactionCode.LOAN_PAYMENT_114.getTransCode());
        transaction.getTransactionDestination().setTransactionCode(TransactionCode.INT_PAY_ONLY_407.getTransCode());
        transaction.getTransactionDestination().setAccountNumber(loanAccount.getAccountNumber());
        balance = 12000;
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscDebitSource.setAmount(balance);
        miscCreditDestination.setAccountNumber(checkAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(balance);

        // Perform deposit transactions
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        //Pages.tellerPage().setEffectiveDate(depositTransaction.getTransactionDate());
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Perform transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().setMiscDebitSource(miscDebitSource, 0);
        Actions.transactionActions().setMiscCreditDestination(miscCreditDestination, 0);
        Pages.tellerPage().setEffectiveDate(loanAccount.getDateOpened());
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();
        Pages.tellerPage().closeModal();

        // Create nonTellerTransactions
        Actions.nonTellerTransaction().generatePaymentDueRecord(clientRootId);

        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        Pages.accountDetailsPage().clickPaymentInfoTab();
        amountDue = Pages.accountPaymentInfoPage().getAmountDueTable();
        Actions.loginActions().doLogOutProgrammatically();

        transactionAmount = WebAdminActions.webAdminTransactionActions().getInterestEarned(userCredentials,loanAccount);
        transaction.getTransactionSource().setAmount(transactionAmount);
        transaction.getTransactionDestination().setAmount(transactionAmount);
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25382, testRunName = TEST_RUN_NAME)
    @Test(description = "C25382, Process 407 - Int Pay Only payment transaction")
    @Severity(SeverityLevel.CRITICAL)
    public void process416LoanPaymentTransaction() {
        logInfo("Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to 'Teller' screen");
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Commit 416 transaction with the following fields:\n" +
                "Sources -> Misc Debit:\n" +
                "\"Account Number\" - active CHK or SAV account from preconditions\n" +
                "\"Transaction Code\" - \"114 - Loan Payment\"\n" +
                "Amount <= Interest earned with loan account from preconditions\n" +
                "Destinations -> Misc Credit:\n" +
                "Account number - Loan account from preconditions\n" +
                "\"Transaction Code\" - \"407 - Int Pay Only\"\n" +
                "\"Amount\" - specify the same amount");
        int currentIndex = 0;
        Actions.transactionActions().setMiscDebitSourceForWithDraw(transaction.getTransactionSource(), currentIndex);
        Actions.transactionActions().setMiscCreditDestination(transaction.getTransactionDestination(), currentIndex);
        Pages.tellerPage().setEffectiveDate(transaction.getTransactionDate());
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 4: Close Transaction Receipt popup");
        Pages.tellerPage().closeModal();

        logInfo("Step 5: Open loan account from preconditions on the \"Details\" tab");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());

        logInfo("Step 6: Pay attention to the following fields:\n" +
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
        TestRailAssert.assertTrue((int)paidInterest == (int)transactionAmount, new CustomStepResult("Paid interest is not valid",
                "Paid interest is  valid"));
        String dateLastPayment = Pages.accountDetailsPage().getDateLastPayment();
        TestRailAssert.assertTrue(dateLastPayment.isEmpty(),
                new CustomStepResult("'DateLastPayment' is not valid", "'DateLastPayment' is valid"));
        String dateInterestPaidThru = Pages.accountDetailsPage().getDateInterestPaidThru();
        String dailyInterestFactor = Pages.accountDetailsPage().getDailyInterestFactor();
        TestRailAssert.assertTrue(dateInterestPaidThru.equals(DateTime.getDateMinusDays(loanAccount.getDateOpened(),
                ((int)(-transaction.getTransactionSource().getAmount()/Double.parseDouble(dailyInterestFactor))+1))),
                new CustomStepResult("'DateInterestPaidThru' is not valid", "'DateInterestPaidThru' is valid"));
        String currentBalance = Pages.accountDetailsPage().getCurrentBalance();
        TestRailAssert.assertTrue(Double.parseDouble(currentBalance) == balance,
                new CustomStepResult("Current balance is not valid", "Current balance is valid"));
        String actualAccruedInterest = Pages.accountDetailsPage().getAccruedInterest();
        TestRailAssert.assertTrue(actualAccruedInterest.equals("0.00"),
                new CustomStepResult("Accrued interest is not valid", "Accrued interest is valid"));

        logInfo("Step 7: Open \"Payment Info\" tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 8: Click on the Payment Due record in the \"Payments Due\" section and pay attention " +
                "to the Payment Due Details");
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        String amountDue = Pages.accountPaymentInfoPage().getAmountDueTable();
        TestRailAssert.assertTrue(amountDue.equals(this.amountDue),
                new CustomStepResult("Amount due is not valid", "Amount due is valid"));

        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().paymentStatusIsVisibility("Prin & Int (Bill)"),
                new CustomStepResult("Paid is not visible", "Paid is visible"));
        String datePaymentPaidInFull = Pages.accountPaymentInfoPage().getDatePaymentPaidInFull();
        TestRailAssert.assertTrue(datePaymentPaidInFull.equals(dateLastPayment),
                new CustomStepResult("Date Payment Paid In Full is not valid",
                        "Date Payment Paid In Full is valid"));
        String dueDate = Pages.accountPaymentInfoPage().getDisabledDueDate();

        logInfo("Step 9: Pay attention to the 'Transactions' section");
        logInfo("Step 10: Go to Transaction tab and verify payment portions");
        Pages.accountDetailsPage().clickTransactionsTab();
        double amountValue = AccountActions.retrievingAccountData().getAmountValue(1);
        TestRailAssert.assertTrue(String.format("%.2f", amountValue).equals(String.format("%.2f", transactionAmount)),
                new CustomStepResult("Amount is not valid", "Amount is valid"));
        double principalValue = AccountActions.retrievingAccountData().getBalanceValue(1);
        TestRailAssert.assertTrue(principalValue == 0.00,
                new CustomStepResult("Principal is not valid", "Principal is valid"));
        double interestValue = AccountActions.retrievingAccountData().getInterestMinusValue(1);
        TestRailAssert.assertTrue(interestValue == amountValue,
                new CustomStepResult("Interest is not valid", "Interest is valid"));
        double escrowValue = AccountActions.retrievingAccountData().getEscrowMinusValue(1);
        TestRailAssert.assertTrue(escrowValue == 0.00,
                new CustomStepResult("Escrow is not valid", "Escrow is valid"));

    }
}
