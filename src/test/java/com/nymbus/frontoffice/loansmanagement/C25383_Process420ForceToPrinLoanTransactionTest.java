package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Functions;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.PaymentAmountType;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitDepositCHKAccBuilder;
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
public class C25383_Process420ForceToPrinLoanTransactionTest extends BaseTest {

    private Account loanAccount;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private Transaction transaction_420;
    private double currentBalance;
    private String nextPaymentBilledDueDate;
    private double transaction420Amount;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up account
        Account chkAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        loanAccount.setPaymentAmountType(PaymentAmountType.PRIN_AND_INT.getPaymentAmountType());

        // Set up transactions
        final double depositTransactionAmount = 1001.00;
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        depositTransaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        depositTransaction.getTransactionDestination().setAmount(depositTransactionAmount);
        depositTransaction.getTransactionSource().setAmount(depositTransactionAmount);

        int transaction109Amount = 12000;
        Transaction transaction_109 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_109.getTransactionSource().setAccountNumber(loanAccount.getAccountNumber());
        transaction_109.getTransactionSource().setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        transaction_109.getTransactionSource().setAmount(transaction109Amount);
        transaction_109.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        transaction_109.getTransactionDestination().setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        transaction_109.getTransactionDestination().setAmount(transaction109Amount);

        transaction420Amount = 0;
        transaction_420 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_420.getTransactionSource().setAccountNumber(chkAccount.getAccountNumber());
        transaction_420.getTransactionSource().setTransactionCode(TransactionCode.LOAN_PAYMENT_114.getTransCode());
        transaction_420.getTransactionDestination().setAccountNumber(loanAccount.getAccountNumber());
        transaction_420.getTransactionDestination().setTransactionCode(TransactionCode.FORCE_TO_PRIN_420.getTransCode());

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

        // Create account
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);
        String clientRootId = ClientsActions.createClient().getClientIdFromUrl();

        // Perform deposit transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        // Re-login
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Perform 109 transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(transaction_109);
        Pages.tellerPage().setEffectiveDate(loanAccount.getDateOpened());
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();
        Pages.tellerPage().closeModal();

        // Create nonTellerTransactions
        Actions.nonTellerTransaction().generatePaymentDueRecord(clientRootId);

        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        nextPaymentBilledDueDate = Pages.accountDetailsPage().getNextPaymentBilledDueDate();
        currentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalanceFromHeaderMenu());

        Pages.accountDetailsPage().clickPaymentInfoTab();
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        transaction420Amount = Double.parseDouble(Pages.accountPaymentInfoPage().getDisabledPrincipal());
        transaction_420.getTransactionDestination().setAmount(transaction420Amount);
        transaction_420.getTransactionSource().setAmount(transaction420Amount);

        Actions.loginActions().doLogOutProgrammatically();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25383, testRunName = TEST_RUN_NAME)
    @Test(description = "C25383, Process 420 - Force To Prin loan transaction")
    @Severity(SeverityLevel.CRITICAL)
    public void process420ForceToPrinLoanTransaction() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to 'Teller' page");
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Log in to the proof date");
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 4: Commit 420 transaction with the following fields:\n" +
                "\n" +
                "    Sources -> Misc Debit:\n" +
                "        \"Account Number\" - active CHK or SAV account from preconditions\n" +
                "        \"Transaction Code\" - \"114 - Loan Payment\"\n" +
                "        Amount = Payment Info -> Payments Due record from preconditions-> Principal amount with loan account from preconditions\n" +
                "\n" +
                "    Destinations -> Misc Credit:\n" +
                "        Account number - Loan account from preconditions\n" +
                "        \"Transaction Code\" - \"420 - Force To Prin\"\n" +
                "        \"Amount\" - specify the same amount\n");
        Actions.transactionActions().createTransaction(transaction_420);
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 5: Close Transaction Receipt popup");
        Pages.tellerPage().closeModal();

        logInfo("Step 6: Open loan account from preconditions on the 'Details' tab");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());

        logInfo("Step 7: Pay attention to the following fields:\n" +
                "- Next Payment Billed Due Date\n" +
                "- Date Last Payment\n" +
                "- Current Balance");
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getNextPaymentBilledDueDate().equals(nextPaymentBilledDueDate),
                new CustomStepResult("'Next Payment Billed Due Date' is not valid", "'Next Payment Billed Due Date' is valid"));
        String dateTransactionPosted = DateTime.getLocalDateOfPattern("MM/dd/yyyy");
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getDateLastPayment().equals(dateTransactionPosted),
                new CustomStepResult("'Date Last Payment' is not valid", "'Date Last Payment' is valid"));
        TestRailAssert.assertTrue(Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance()) == currentBalance - transaction420Amount,
                new CustomStepResult("'Current Balance' is not valid", "'Current Balance' is valid"));

        logInfo("Step 8: Open 'Payment Info' tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 9: Check Payment Due record from preconditions");
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getDueDateFromRecordByIndex(1).equals(loanAccount.getNextPaymentBilledDueDate()),
                new CustomStepResult("'Due Date' is not valid", "'Due Date' is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getPaymentDueTypeFromRecordByIndex(1).equals(loanAccount.getPaymentAmountType()),
                new CustomStepResult("'Payment Due Type' is not valid", "'Payment Due Type' is valid"));
        double paymentAmount = Double.parseDouble(Pages.accountPaymentInfoPage().getActivePaymentAmount());
        double amountDue = Double.parseDouble(Pages.accountPaymentInfoPage().getAmountDueFromRecordByIndex(1));
        TestRailAssert.assertTrue(amountDue == Functions.roundToTwoDecimalPlaces(paymentAmount - transaction420Amount),
                new CustomStepResult("Amount due is not valid", "Amount due is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(1).equals("Partially Paid"),
                new CustomStepResult("'Status' is not valid", "'Status' is valid"));

        logInfo("Step 10: Click on the Payment Due record and check fields in the 'Payment Due Details' section");
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getDatePaymentPaidInFull().isEmpty(),
                new CustomStepResult("Date Payment Paid In Full is not valid", "Date Payment Paid In Full is valid"));
        String expectedPaymentDueDate = DateTime.getDateMinusMonth(nextPaymentBilledDueDate, 1);
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getDisabledDueDate().equals(expectedPaymentDueDate),
                new CustomStepResult("'Due Date' is not valid", "'Due Date' is valid"));

        logInfo("Step 11: Pay attention at the 'Transactions'");
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getPaymentDate().equals(DateTime.getLocalDateOfPattern("MM/dd/yyyy")),
                new CustomStepResult("Payment date is not valid", "Payment date is valid"));
        double amount = Double.parseDouble(Pages.accountPaymentInfoPage().getAmount());
        TestRailAssert.assertTrue(amount == transaction420Amount,
                new CustomStepResult("Amount is not valid", "Amount is valid"));
        String principal = Pages.accountPaymentInfoPage().getDisabledPrincipal();
        TestRailAssert.assertTrue(Double.parseDouble(principal) == transaction420Amount,
                new CustomStepResult("Principal is not valid", "Principal is valid"));
        String totalTransactionInterest = Pages.accountPaymentInfoPage().getInterestTotal();
        TestRailAssert.assertTrue(totalTransactionInterest.equals("0.00"),
                new CustomStepResult("'Interest' is not valid", "'Interest' is valid"));
        String totalTransactionEscrow = Pages.accountPaymentInfoPage().getEscrowTotal();
        TestRailAssert.assertTrue(totalTransactionEscrow.equals("0.00"),
                new CustomStepResult("'Escrow' is not valid", "'Escrow' is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getStatus().equals("420 Force To Prin"),
                new CustomStepResult("Status is not valid", "Status is valid"));

        logInfo("Step 12: Go to Transaction tab and verify payment portions");
        Pages.accountDetailsPage().clickTransactionsTab();
        double amountValue = AccountActions.retrievingAccountData().getAmountValue(1);
        TestRailAssert.assertTrue(amountValue == transaction420Amount,
                new CustomStepResult("Amount is not valid", "Amount is valid"));
        double principalValue = AccountActions.retrievingAccountData().getBalanceValue(1);
        TestRailAssert.assertTrue(Double.parseDouble(principal) == principalValue,
                new CustomStepResult("Principal is not valid", "Principal is valid"));
        TestRailAssert.assertTrue(AccountActions.retrievingAccountData().getInterestMinusValue(1) == 0.00,
                new CustomStepResult("Interest is not valid", "Interest is valid"));
        TestRailAssert.assertTrue(AccountActions.retrievingAccountData().getEscrowMinusValue(1) == 0.00,
                new CustomStepResult("Escrow is not valid", "Escrow is valid"));
    }
}
