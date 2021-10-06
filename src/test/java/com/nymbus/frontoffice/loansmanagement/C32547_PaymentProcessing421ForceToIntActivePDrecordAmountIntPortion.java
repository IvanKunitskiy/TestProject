package com.nymbus.frontoffice.loansmanagement;

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
public class C32547_PaymentProcessing421ForceToIntActivePDrecordAmountIntPortion extends BaseTest {

    private Account loanAccount;
    private Account chkAccount;
    private Transaction transaction_421;
    private String clientRootId;
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

        Actions.loginActions().doLogOutProgrammatically();
    }

    @TestRailIssue(issueID = 32547, testRunName = TEST_RUN_NAME)
    @Test(description = "C32547, Payment Processing: 421 - Force To Int, no PD records")
    @Severity(SeverityLevel.CRITICAL)
    public void paymentProcessing421ForceToIntActivePDrecordAmountIntPortion() {

        logInfo("Step 1: Log in to the NYMBUS");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to the 'Teller' screen");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);

        Pages.accountDetailsPage().clickPaymentInfoTab();
        Pages.accountPaymentInfoPage().clickLastPaymentDueRecord();
        String interestPortion = Pages.accountPaymentInfoPage().getDisabledInterest();

        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Log in to the proof date");
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 4: Commit '421 - Force To Prin' transaction with the following fields:\n" +
                "Sources -> Misc Debit:\n" +
                "\n" +
                "'Account Number' - active CHK or SAV account from preconditions\n" +
                "'Transaction Code' - '114 - Loan Payment'\n" +
                "Amount > Payment Info -> Payments Due record from preconditions -> Interest portion of the PD record with Loan from preconditions\n" +
                "Destinations -> Misc Credit:\n" +
                "\n" +
                "Account number - Loan account from preconditions\n" +
                "'Transaction Code' - '421 - Force To Prin'\n" +
                "'Amount' - specify the same amount");
        // Set up 421 transaction
        double transactionAmount = Double.parseDouble(interestPortion) + 1;
        transaction_421 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_421.getTransactionSource().setTransactionCode(TransactionCode.LOAN_PAYMENT_114.getTransCode());
        transaction_421.getTransactionSource().setAccountNumber(chkAccount.getAccountNumber());
        transaction_421.getTransactionSource().setAmount(transactionAmount);
        transaction_421.getTransactionDestination().setTransactionCode(TransactionCode.FORCE_TO_INT_421.getTransCode());
        transaction_421.getTransactionDestination().setAccountNumber(loanAccount.getAccountNumber());
        transaction_421.getTransactionDestination().setAmount(transactionAmount);

        // Perform 421 transaction
        Actions.transactionActions().goToTellerPage();
        Pages.tellerPage().setEffectiveDate(DateTime.getDateMinusDays(loanAccount.getNextPaymentBilledDueDate(), Integer.parseInt(loanAccount.getPaymentBilledLeadDays())));
        Actions.transactionActions().setMiscDebitSourceForWithDraw(transaction_421.getTransactionSource(), 0);
        Actions.transactionActions().setMiscCreditDestination(transaction_421.getTransactionDestination(), 0);
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 5: Close Transaction Receipt popup");
        Pages.tellerPage().closeModal();

        logInfo("Step 6: Open Loan from preconditions on Transactions tab");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        Pages.accountDetailsPage().clickTransactionsTab();
        Pages.accountTransactionPage().waitForTransactionSection();


        String transactionRecordAmount1 = Pages.accountTransactionPage().getAmountValue(1) + Pages.accountTransactionPage().getAmountFractionalValue(1);
        String transactionRecordAmount2 = Pages.accountTransactionPage().getAmountValue(2) + Pages.accountTransactionPage().getAmountFractionalValue(2);

        double transactionRecordAmount2Actual = transactionAmount - Double.parseDouble(transactionRecordAmount1);

        TestRailAssert.assertEquals(Pages.accountTransactionPage().getTransactionCodeByIndex(1), TransactionCode.FORCE_TO_INT_421.getTransCode(),
                new CustomStepResult("'Transaction record' is valid", "'Transaction record' is not valid"));
        TestRailAssert.assertEquals(transactionRecordAmount1, interestPortion,
                new CustomStepResult("'Transaction record amount' is valid", "'Transaction record amount' is not valid"));
        TestRailAssert.assertEquals(Pages.accountTransactionPage().getTransactionCodeByIndex(2), TransactionCode.INT_PAY_ONLY_407.getTransCode(),
                new CustomStepResult("'Transaction record' is valid", "'Transaction record' is not valid"));
        TestRailAssert.assertEquals(transactionRecordAmount2, transactionRecordAmount2Actual + "0",
                new CustomStepResult("'Transaction record amount' is valid", "'Transaction record amount' is not valid"));

        logInfo("Step 7: Go to the 'Payment Info' tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 8: Verify existing Payment Due record");
        double paymentDueRecordAmount = Double.parseDouble(loanAccount.getPaymentAmount()) - Double.parseDouble(interestPortion);
        TestRailAssert.assertEquals(Pages.accountPaymentInfoPage().getAmountDueFromRecordByIndex(1), String.valueOf(paymentDueRecordAmount),
                new CustomStepResult("'Payment Due Amount' is valid", "'Payment Due Amount' is not valid"));
        TestRailAssert.assertEquals(Pages.accountPaymentInfoPage().getDueStatus(), "Partially Paid",
                new CustomStepResult("'Payment Due Status' is valid", "'Payment Due Status' is not valid"));

        logInfo("Step 9: Click on the Payment Due record and check field in the 'Payment Due Details' section");
        Pages.accountPaymentInfoPage().clickLastPaymentDueRecord();
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getDisabledDatePaid().isEmpty(),
                new CustomStepResult("'Date Payment Paid in Full' is valid", "'Date Payment Paid in Full' is not valid"));

        logInfo("Step 10: Pay attention to the 'Transactions' section");
        String amount = Pages.accountPaymentInfoPage().getAmount();
        String principal = Pages.accountPaymentInfoPage().getPrincipal();
        String interest = Pages.accountPaymentInfoPage().getInterest();
        String escrow = Pages.accountPaymentInfoPage().getEscrow();
        String tranCodeStatus = Pages.accountPaymentInfoPage().getStatus();

        TestRailAssert.assertEquals(amount, interestPortion,
                new CustomStepResult("'Amount' is not valid", "'Amount' is valid"));
        TestRailAssert.assertTrue(principal.isEmpty(),
                new CustomStepResult("'Principal' is not valid", "'Principal' is valid"));
        TestRailAssert.assertEquals(interest, interestPortion,
                new CustomStepResult("'Interest' is not valid", "'Interest' is valid"));
        TestRailAssert.assertTrue(escrow.isEmpty(),
                new CustomStepResult("'Escrow' is not valid", "'Escrow' is valid"));
        TestRailAssert.assertEquals(tranCodeStatus, "421 Force To Int",
                new CustomStepResult("'Tran Code/Status' is not valid", "'Tran Code/Status' is valid"));

        TestRailAssert.assertFalse(AccountActions.accountPaymentInfoActions().isRecordWithSpecificStatusPresent(TransactionCode.INT_PAY_ONLY_407.getTransCode()),
                new CustomStepResult("Record with '407 - Int Only' code is present", "Record with '407 - Int Only' code is not present"));
    }

}
