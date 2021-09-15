package com.nymbus.frontoffice.loansmanagement;

import com.codeborne.selenide.Selenide;
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
public class C33831_PaymentDueRecordTransactionHistoryEC416PaymentTransactionForLoanWithPaidPartiallyPaidPD extends BaseTest {

    private Account loanAccount;
    private Account chkAccount;
    private Transaction transaction_416;
    private String clientRootId;
    double transactionAmount;
    String dueRecordAmountDue;
    String dueRecordPaymentDueDate;
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

        // Set proper dates
        String localDate = DateTime.getLocalDateOfPattern("MM/dd/yyyy");
        loanAccount.setDateOpened(DateTime.getDateMinusMonth(localDate, 1));
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

        clientRootId = ClientsActions.createClient().getClientIdFromUrl();

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

        // make 416 transaction
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);

        Pages.accountDetailsPage().clickPaymentInfoTab();
        Pages.accountPaymentInfoPage().clickLastPaymentDueRecord();
        dueRecordAmountDue = Pages.accountPaymentInfoPage().getDisabledAmountDue().replaceAll("[^0-9.]", "");

        // Set up 416 transaction
        transactionAmount = Double.parseDouble(dueRecordAmountDue);
        transaction_416 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_416.getTransactionSource().setTransactionCode(TransactionCode.LOAN_PAYMENT_114.getTransCode());
        transaction_416.getTransactionSource().setAccountNumber(chkAccount.getAccountNumber());
        transaction_416.getTransactionSource().setAmount(transactionAmount);
        transaction_416.getTransactionDestination().setTransactionCode(TransactionCode.PAYMENT_416.getTransCode());
        transaction_416.getTransactionDestination().setAccountNumber(loanAccount.getAccountNumber());
        transaction_416.getTransactionDestination().setAmount(transactionAmount);

        // Perform 416 transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().setMiscDebitSourceForWithDraw(transaction_416.getTransactionSource(), 0);
        Actions.transactionActions().setMiscCreditDestination(transaction_416.getTransactionDestination(), 0);
        Actions.transactionActions().clickCommitButton();

        Pages.tellerPage().closeModal();

        // Get Due Date
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);

        Pages.accountDetailsPage().clickPaymentInfoTab();
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        dueRecordPaymentDueDate = Pages.accountPaymentInfoPage().getDisabledDueDate();

        Actions.loginActions().doLogOutProgrammatically();
    }

    @TestRailIssue(issueID = 33831, testRunName = TEST_RUN_NAME)
    @Test(description = "C33831, Payment Due Record:Transaction History: EC \"416 - Payment\" transaction for loan with Paid/Partially Paid PD")
    @Severity(SeverityLevel.CRITICAL)
    public void paymentDueRecordTransactionHistoryEC416PaymentTransactionForLoanWithPaidPartiallyPaidPD() {

        logInfo("Step 1: Log in to the NYMBUS");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to \"Journal\" screen");
        Pages.aSideMenuPage().clickJournalMenuItem();
        Actions.transactionActions().doLoginTeller();
        Pages.journalPage().clickItemInTable(1);

        logInfo("Step 3: Find and select a transaction from precondition 4 and Press [Error Correct] button");
        Pages.journalDetailsPage().clickErrorCorrectButton();
        Pages.journalDetailsPage().waitForCreateErrorApplying();
        TestRailAssert.assertTrue(Pages.journalDetailsPage().getItemState(1).equals("Void"),
                new CustomStepResult("Transaction state is valid", "Transaction state is not valid"));

        logInfo("Step 4: Open account from preconditions on the \"Transactions\" tab");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        Pages.accountDetailsPage().clickTransactionsTab();
        Pages.accountTransactionPage().waitForTransactionSection();

        logInfo("Step 5: Expand the \"416 - Payment\" transaction with description = EC and look at the \"Item Due Date\" and \"Amount Due\" value");
        Pages.accountTransactionPage().clickTransactionRecordByIndex(1);
        String amountDue = Pages.accountTransactionPage().getCheckAmountDue().replaceAll("[^0-9.]", "");
        String transactionPrincipal = Pages.accountTransactionPage().getPrincipalValue(1) + Pages.accountTransactionPage().getPrincipalFractionalPartValue(1);
        String transactionInterest = Pages.accountTransactionPage().getInterestValue(1) + Pages.accountTransactionPage().getInterestMinusFractionalValue(1);

        TestRailAssert.assertTrue("0".equals(amountDue),
                new CustomStepResult("Check's 'Amount Due' is not valid", "Check's 'Amount Due' is valid"));
        TestRailAssert.assertTrue(dueRecordPaymentDueDate
                        .equals(DateTime.getDateWithFormat(Pages.accountTransactionPage().getCheckItemDueDate().replaceAll("[^0-9-]", ""), "MM-dd-yyyy", "MM/dd/yyyy")),
                new CustomStepResult("Check's 'Item Due Date' is not valid", "Check's 'Amount Due' is valid"));

        logInfo("Step 6: Go to the \"Payment Info\" tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 7: Check Payment Due record in the \"Payment Dues\" section");
        Pages.accountPaymentInfoPage().clickLastPaymentDueRecord();

        TestRailAssert.assertTrue((Pages.accountPaymentInfoPage().getDueAmount().replaceAll("[^0-9.]", "")).equals(loanAccount.getPaymentAmount()),
                new CustomStepResult("'Due Record Amount' is not valid", "'Due Record Amount' is valid"));
        TestRailAssert.assertTrue((Pages.accountPaymentInfoPage().getDueStatus()).equals("Active"),
                new CustomStepResult("'Due Record Amount' is not valid", "'Due Record Amount' is valid"));

        logInfo("Step 8: Click on the Active Payment Due record and look at the \"Transactions\" section");
        String amount = Pages.accountPaymentInfoPage().getAmount();
        String principal = Pages.accountPaymentInfoPage().getPrincipal();
        String interest = Pages.accountPaymentInfoPage().getInterest();
        String escrow = Pages.accountPaymentInfoPage().getEscrow();
        String tranCodeStatus = Pages.accountPaymentInfoPage().getStatus();
        String paymentDate = Pages.accountPaymentInfoPage().getPaymentDate();

        // total
        String amountTotal = Pages.accountPaymentInfoPage().getAmountTotal();
        String principalTotal = Pages.accountPaymentInfoPage().getPrincipalTotal();
        String interestTotal = Pages.accountPaymentInfoPage().getInterestTotal();
        String escrowTotal = Pages.accountPaymentInfoPage().getEscrowTotal();

        TestRailAssert.assertTrue(paymentDate.equals(dueRecordPaymentDueDate),
                new CustomStepResult("'Payment Date' is not valid", "'Payment Date' is valid"));
        TestRailAssert.assertTrue(amount.equals(transactionAmount + "0"),
                new CustomStepResult("'Amount' is not valid", "'Amount' is valid"));
        TestRailAssert.assertTrue(principal.equals(transactionPrincipal),
                new CustomStepResult("'Principal' is not valid", "'Principal' is valid"));
        TestRailAssert.assertTrue(interest.equals(transactionInterest),
                new CustomStepResult("'Interest' is not valid", "'Interest' is valid"));
        TestRailAssert.assertTrue(escrow.isEmpty(),
                new CustomStepResult("'Escrow' is not valid", "'Escrow' is valid"));
        TestRailAssert.assertTrue(tranCodeStatus.equals("416-Payment-EC"),
                new CustomStepResult("'Tran Code/Status' is not valid", "'Tran Code/Status' is valid"));

        // total
        TestRailAssert.assertTrue(amountTotal.equals("0.00"),
                new CustomStepResult("'Amount' total is not valid", "'Amount' total is valid"));
        TestRailAssert.assertTrue(principalTotal.equals("0.00"),
                new CustomStepResult("'Principal' total is not valid", "'Principal' total is valid"));
        TestRailAssert.assertTrue(interestTotal.equals("0.00"),
                new CustomStepResult("'Interest' total is not valid", "'Interest' total is valid"));
        TestRailAssert.assertTrue(escrowTotal.equals("0.00"),
                new CustomStepResult("'Escrow' total is not valid", "'Escrow' total is valid"));


    }
}
