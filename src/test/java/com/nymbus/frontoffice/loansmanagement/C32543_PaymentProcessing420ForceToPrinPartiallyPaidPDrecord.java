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
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C32543_PaymentProcessing420ForceToPrinPartiallyPaidPDrecord extends BaseTest {
    private Account loanAccount;
    private Account chkAccount;
    private Transaction transaction_416;
    private Transaction transaction_420;
    private String clientRootId;
    private final String loanProductName = "Test Loan Product";
    private String dueRecordAmountDue = "";
    private final String loanProductInitials = "TLP";
    private final String TEST_RUN_NAME = "Loans Management";

    @BeforeMethod
    public void preConditions(){

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


        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        Pages.accountDetailsPage().clickPaymentInfoTab();
        Pages.accountPaymentInfoPage().clickLastPaymentDueRecord();
        dueRecordAmountDue = Pages.accountPaymentInfoPage().getDisabledAmountDue().replaceAll("[^0-9.]", "");
        Actions.transactionActions().goToTellerPage();

        // Set up 416 transaction
        double transactionAmount = Double.parseDouble(dueRecordAmountDue);
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

        Actions.loginActions().doLogOutProgrammatically();
    }

    @TestRailIssue(issueID = 32543, testRunName = TEST_RUN_NAME)
    @Test(description = "C32543, Payment Processing: 420 - Force To Prin, Partially Paid PD record")
    @Severity(SeverityLevel.CRITICAL)
    public void paymentProcessing420ForceToPrinPartiallyPaidPdRecord() {
        logInfo("Step 1: Log in to the NYMBUS");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to the 'Teller' screen");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        double currentBalanceBefore = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance());

        Pages.accountDetailsPage().clickPaymentInfoTab();
        Pages.accountPaymentInfoPage().clickLastPaymentDueRecord();
        double dueRecordPrincipal2 = Double.parseDouble(Pages.accountPaymentInfoPage().getDisabledPrincipal());

        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Log in to the proof date");
        Actions.transactionActions().doLoginTeller();
        Pages.tellerPage().setEffectiveDate(DateTime.getDateMinusDays(loanAccount.getNextPaymentBilledDueDate(), Integer.parseInt(loanAccount.getPaymentBilledLeadDays())));

        logInfo("Step 4: Commit '420 - Force To Prin' transaction with the following fields:" +
                "Sources -> Misc Debit:\n" +
                "\n" +
                "\"Account Number\" - active CHK or SAV account from preconditions\n" +
                "\"Transaction Code\" - \"114 - Loan Payment\"\n" +
                "Amount > Payment Info -> Payments Due record from preconditions -> Principal portion of the PD record with Loan from preconditions\n" +
                "Destinations -> Misc Credit:\n" +
                "\n" +
                "Account number - Loan account from preconditions\n" +
                "\"Transaction Code\" - \"420 - Force To Prin\"\n" +
                "\"Amount\" - specify the same amount");

        // modify due records principal to produce 406 transaction
        double transactionAmount = dueRecordPrincipal2 - 1;

        // Set up 420 transaction
        transaction_420 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_420.getTransactionSource().setTransactionCode(TransactionCode.LOAN_PAYMENT_114.getTransCode());
        transaction_420.getTransactionSource().setAccountNumber(chkAccount.getAccountNumber());
        transaction_420.getTransactionSource().setAmount(transactionAmount);
        transaction_420.getTransactionDestination().setTransactionCode(TransactionCode.FORCE_TO_PRIN_420.getTransCode());
        transaction_420.getTransactionDestination().setAccountNumber(loanAccount.getAccountNumber());
        transaction_420.getTransactionDestination().setAmount(transactionAmount);

        // Perform 420 transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().setMiscDebitSourceForWithDraw(transaction_420.getTransactionSource(), 0);
        Actions.transactionActions().setMiscCreditDestination(transaction_420.getTransactionDestination(), 0);
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 5: Close Transaction Receipt popup");
        Pages.tellerPage().closeModal();

        logInfo("Step 6: Open loan account from preconditions on the \"Transactions\" tab");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        Pages.accountDetailsPage().clickTransactionsTab();
        Pages.accountTransactionPage().waitForTransactionSection();

        Selenide.sleep(10000000);

        String transactionAmount_420 = Pages.accountTransactionPage().getAmountValue(1) + Pages.accountTransactionPage().getAmountFractionalValue(1);
        String transactionAmount_406 = Pages.accountTransactionPage().getAmountValue(2) + Pages.accountTransactionPage().getAmountFractionalValue(2);

        TestRailAssert.assertTrue(Pages.accountTransactionPage().getTransactionCodeByIndex(1)
                        .equals(String.valueOf(TransactionCode.FORCE_TO_PRIN_420.getTransCode())),
                new CustomStepResult("'Transaction Code' code is not valid", "'Transaction Code' code is valid"));
        TestRailAssert.assertTrue(transactionAmount_420.equals(String.valueOf(dueRecordPrincipal2)),
                new CustomStepResult("'Transaction Amount' is not valid", "'Transaction Amount' is valid"));

        TestRailAssert.assertTrue(Pages.accountTransactionPage().getTransactionCodeByIndex(2)
                        .equals(String.valueOf(TransactionCode.PRIN_PAYM_ONLY_406.getTransCode())),
                new CustomStepResult("'Transaction Code' code is not valid", "'Transaction Code' code is valid"));
        TestRailAssert.assertTrue(transactionAmount_406.equals((transactionAmount - Double.parseDouble(transactionAmount_420)) + "0"),
                new CustomStepResult("'Transaction Amount' is not valid", "'Transaction Amount' is valid"));

        Pages.accountDetailsPage().clickDetailsTab();
        double currentBalanceAfter = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance());
        TestRailAssert.assertTrue(String.valueOf(currentBalanceAfter).equals(String.valueOf(currentBalanceBefore - (Double.parseDouble(transactionAmount_420) + Double.parseDouble(transactionAmount_406)))),
                new CustomStepResult("'Current Balance' is not valid", "'Current Balance' is valid"));

        logInfo("Step 7: Go to the 'Payment Info' tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 8: Verify existing Payment Due record");
        Pages.accountPaymentInfoPage().clickPaymentDueRecordByIndex(1);

        String amount = Pages.accountPaymentInfoPage().getAmount();
        String principal = Pages.accountPaymentInfoPage().getPrincipal();
        String interest = Pages.accountPaymentInfoPage().getInterest();
        String escrow = Pages.accountPaymentInfoPage().getEscrow();
        String tranCodeStatus = Pages.accountPaymentInfoPage().getStatus();

        TestRailAssert.assertTrue(amount.equals(String.valueOf(dueRecordPrincipal2)),
                new CustomStepResult("'Amount' is not valid", "'Amount' is valid"));
        TestRailAssert.assertTrue(principal.equals(String.valueOf(dueRecordPrincipal2)),
                new CustomStepResult("'Principal' is not valid", "'Principal' is valid"));
        TestRailAssert.assertTrue(interest.isEmpty(),
                new CustomStepResult("'Interest' is not valid", "'Interest' is valid"));
        TestRailAssert.assertTrue(escrow.isEmpty(),
                new CustomStepResult("'Escrow' is not valid", "'Escrow' is valid"));
        TestRailAssert.assertTrue(tranCodeStatus.equals("420 Force To Prin"),
                new CustomStepResult("'Tran Code/Status' is not valid", "'Tran Code/Status' is valid"));

    }
}
