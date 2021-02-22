package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.CommitmentTypeAmt;
import com.nymbus.newmodels.account.loanaccount.PaymentAmountType;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitMiscCreditBuilder;
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
public class C25380_Process416LoanPaymentTransactionInterestOnlyOverAmountDueTest extends BaseTest {

    private Account loanAccount;
    private Account chkAccount;
    private Transaction transaction_109;
    private Transaction transaction_416;
    private double transactionAmount = 1001.00;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private String clientRootId;
    private String accruedInterest;
    private String dateLastPayment;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        //Set up accounts
        chkAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        loanAccount.setPaymentAmountType(PaymentAmountType.INTEREST_ONLY.getPaymentAmountType());
        loanAccount.setCommitmentTypeAmt(CommitmentTypeAmt.NONE.getCommitmentTypeAmt());

        // 109 - deposit transaction
        transaction_109 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        int transaction109Amount = 12000;
        transaction_109 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_109.getTransactionSource().setAccountNumber(loanAccount.getAccountNumber());
        transaction_109.getTransactionSource().setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        transaction_109.getTransactionSource().setAmount(transaction109Amount);
        transaction_109.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        transaction_109.getTransactionDestination().setAmount(transaction109Amount);
        transaction_109.getTransactionDestination().setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());

        // 416 - transaction
        transaction_416 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        int transaction416Amount = 12000;
        transaction_416.getTransactionSource().setAccountNumber(chkAccount.getAccountNumber());
        transaction_416.getTransactionSource().setAmount(transaction416Amount);
        transaction_416.getTransactionSource().setTransactionCode(TransactionCode.LOAN_PAYMENT_114.getTransCode());
        transaction_416.getTransactionDestination().setAccountNumber(loanAccount.getAccountNumber());
        transaction_416.getTransactionDestination().setAmount(transaction416Amount);
        transaction_416.getTransactionDestination().setTransactionCode(TransactionCode.PAYMENT_416.getTransCode());

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
        clientRootId = ClientsActions.createClient().getClientIdFromUrl();
        dateLastPayment = Pages.accountDetailsPage().getDateLastPayment();

        // Perform '109 - deposit' transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(transaction_109);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        // Re-login to refresh teller session
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Create nonTellerTransactions
        Actions.nonTellerTransaction().generatePaymentDueRecord(clientRootId);

        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        accruedInterest = Pages.accountDetailsPage().getAccruedInterest();

        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25380, testRunName = TEST_RUN_NAME)
    @Test(description = "C25380, Process 416 loan payment transaction. Interest Only (bill). Over Amount Due.")
    @Severity(SeverityLevel.CRITICAL)
    public void process416LoanPaymentTransactionInterestOnlyOverAmountDue() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to 'Teller' page");
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Log in to the proof date");
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 4: Commit 416 transaction with the following fields:\n" +
                "    Sources -> Misc Debit:\n" +
                "        \"Account Number\" - active CHK or SAV account from preconditions\n" +
                "        \"Transaction Code\" - \"114 - Loan Payment\"\n" +
                "        Amount = Payment Info -> Payments Due record from preconditions -> Amount Due\n" +
                "    Destinations -> Misc Credit:\n" +
                "        Account number - Loan account from preconditions\n" +
                "        \"Transaction Code\" - \"416 - Payment\"\n" +
                "        \"Amount\" - specify the same amount\n");
        logInfo("Step 5: Close Transaction Receipt popup");
        Actions.transactionActions().createTransaction(transaction_416);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        logInfo("Step 6: Open loan account from preconditions on the 'Payment Info' tab");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 7: Check Payment Due record from preconditions");
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getPaymentDueType(1).equals(loanAccount.getNextPaymentBilledDueDate()),
                new CustomStepResult("'Due Date' is not valid", "'Due Date' is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getPaymentDueTypeFromRecordByIndex(1).equals(loanAccount.getPaymentAmountType()),
                new CustomStepResult("'Payment Due Type' is not valid", "'Payment Due Type' is valid"));
        String amountDueFromRecordPaymentDueRecord = Pages.accountPaymentInfoPage().getAmountDueFromRecordByIndex(1);
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getAmountDueFromRecordByIndex(1).equals("$ 0.00"),
                new CustomStepResult("Amount due is not valid", "Amount due is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(1).equals("Paid"),
                new CustomStepResult("'Status' is not valid", "'Status' is valid"));

        logInfo("Step 8: Click on the Payment Due record and check fields in the 'Payment Due Details' section");
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getPaymentDueType().equals(loanAccount.getPaymentAmountType()),
                new CustomStepResult("'Payment Due Type' is not valid", "'Payment Due Type' is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getInterest().equals(accruedInterest),
                new CustomStepResult("'Interest' is not valid", "'Interest' is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getPrincipal().equals("$ 0.00"),
                new CustomStepResult("'Principal' is not valid", "'Principal' is valid"));
        // TODO: PaymentAmount
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getPaymentAmount().equals(amountDueFromRecordPaymentDueRecord),
                new CustomStepResult("'Payment Amount' is not valid", "'Payment Amount' is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getAmountDue().equals("$ 0.00"),
                new CustomStepResult("'Amount due' is not valid", "'Amount due' is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getDatePaymentPaidInFull().equals(loanAccount.getNextPaymentBilledDueDate()),
                new CustomStepResult("Date Payment Paid In Full is not valid", "Date Payment Paid In Full is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getDueDate().equals(loanAccount.getNextPaymentBilledDueDate()),
                new CustomStepResult("'Due Date' is not valid", "'Due Date' is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getStatus().equals("Paid"),
                new CustomStepResult("Paid is not visible", "Paid is visible"));

        logInfo("Step 9: Pay attention to the 'Transactions' section");
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getPaymentDate().equals(dateLastPayment),
                new CustomStepResult("Payment date is not valid", "Payment date is valid"));
        String interest = Pages.accountPaymentInfoPage().getInterest();
        TestRailAssert.assertTrue(interest.equals(accruedInterest),
                new CustomStepResult("Interest is not valid", "Interest due is valid"));
        String amount = Pages.accountPaymentInfoPage().getAmount();
        String escrow = Pages.accountPaymentInfoPage().getEscrow();
        String principal = Pages.accountPaymentInfoPage().getPrincipal();
        double sum = Double.parseDouble(interest) + Double.parseDouble(escrow) + Double.parseDouble(principal);
        TestRailAssert.assertTrue(Double.parseDouble(amount) == sum,
                new CustomStepResult("Amount sum is not valid", "Amount sum is valid"));
        TestRailAssert.assertTrue(Double.parseDouble(amount) == transactionAmount,
                new CustomStepResult("Amount is not valid", "Amount is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getStatus().equals("416 Payment"),
                new CustomStepResult("Status is not valid", "Status is valid"));

        logInfo("Step 11: Go to the 'Transactions' tab and verify generated transactions");
        Pages.accountDetailsPage().clickTransactionsTab();
        double amountValue = AccountActions.retrievingAccountData().getAmountValue(1);
        TestRailAssert.assertTrue(amountValue == transactionAmount,
                new CustomStepResult("Amount is not valid", "Amount is valid"));
        double principalValue = AccountActions.retrievingAccountData().getBalanceValue(1);
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
