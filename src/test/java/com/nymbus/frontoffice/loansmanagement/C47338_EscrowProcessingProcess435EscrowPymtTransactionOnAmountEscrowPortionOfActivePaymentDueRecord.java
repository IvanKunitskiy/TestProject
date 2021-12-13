package com.nymbus.frontoffice.loansmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.settings.LoansActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
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
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import jdk.internal.net.http.common.Log;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sun.jvm.hotspot.debugger.Page;

import javax.swing.*;

public class C47338_EscrowProcessingProcess435EscrowPymtTransactionOnAmountEscrowPortionOfActivePaymentDueRecord extends BaseTest {

    private Account loanAccount;
    private Account chkAccount;
    private String clientRootId;
    double escrowAmount;
    Transaction transaction_435;
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

        // Add escrow Payment row with Effective Date = Date Opened (f.e. 05/17/2021), Frequency = same as for P&I
        // row (f.e. Monthly), Amount = any (f.e. $ 200.00)
        escrowAmount = 100;
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        Pages.accountDetailsPage().clickPaymentInfoTab();
        Pages.accountPaymentInfoPage().clickEditButtonAtOtherPayments();
        SelenideTools.sleep(5);
        Pages.accountPaymentInfoPage().clickOtherPaymentsAddNewButton();
        Pages.accountPaymentInfoPage().setOtherPaymentsEffectiveDate(loanAccount.getDateOpened());
        Pages.accountPaymentInfoPage().typeOtherPaymentsAmountValue(escrowAmount + "0");
        Pages.accountPaymentInfoPage().typeAmountValue((Double.parseDouble(loanAccount.getPaymentAmount()) - escrowAmount) + "0");
        Actions.loanPaymentInfoActions().setOtherPaymentsPaymentType("Escrow");
        Actions.loanPaymentInfoActions().setOtherPaymentsFrequency("Monthly");
        Pages.accountPaymentInfoPage().clickSaveButton();
        Actions.loginActions().doLogOutProgrammatically();

        // Generate Payment Due record
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.nonTellerTransaction().generatePaymentDueRecord(clientRootId);
        Actions.loginActions().doLogOutProgrammatically();
    }

    @TestRailIssue(issueID = 47338, testRunName = TEST_RUN_NAME)
    @Test(description = "C47338, Escrow Processing: Process \"435 - Escrow Pymt\" transaction on amount = Escrow portion of Active Payment Due record")
    @Severity(SeverityLevel.CRITICAL)
    public void paymentDueRecordsErrorCorrectPartialPaymentTransactionOnAmortizedLoanWithGeneratedPaymentDueRecordsCycleNo() {
        logInfo("Step 1: Log in to the NYMBUS");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to the \"Teller\" screen");
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Log in to the proof date");
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 4: Commit \"435 - Escrow Pymt\" transaction with the following fields:\n" +
                "Sources -> Misc Debit:\n" +
                "\"Account Number\" - active CHK or SAV account from preconditions\n" +
                "\"Transaction Code\" - \"114 - Loan Payment\"\n" +
                "Amount = Escrow portion of PD record\n" +
                "Destinations -> Misc Credit:\n" +
                "Account number - Loan account from preconditions\n" +
                "\"Transaction Code\" - \"435 - Escrow Pymt\"\n" +
                "\"Amount\" - specify the same amount");

        // Set up 435 transaction
        transaction_435 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_435.getTransactionSource().setTransactionCode(TransactionCode.LOAN_PAYMENT_114.getTransCode());
        transaction_435.getTransactionSource().setAccountNumber(chkAccount.getAccountNumber());
        transaction_435.getTransactionSource().setAmount(escrowAmount);
        transaction_435.getTransactionDestination().setTransactionCode(TransactionCode.ESCROW_PYMT_435.getTransCode());
        transaction_435.getTransactionDestination().setAccountNumber(loanAccount.getAccountNumber());
        transaction_435.getTransactionDestination().setAmount(escrowAmount);

        // Perform 435 transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().setMiscDebitSourceForWithDraw(transaction_435.getTransactionSource(), 0);
        Actions.transactionActions().setMiscCreditDestination(transaction_435.getTransactionDestination(), 0);
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 5: Close Transaction Receipt popup");
        Pages.tellerPage().closeModal();

        logInfo("Step 6: Open loan account from preconditions on the \"Transactions\" tab");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        Pages.accountDetailsPage().clickTransactionsTab();
        Pages.accountTransactionPage().waitForTransactionSection();

        String transactionAmount_435 = Pages.accountTransactionPage().getAmountValue(1) + Pages.accountTransactionPage().getAmountFractionalValue(1);

        TestRailAssert.assertTrue(Pages.accountTransactionPage().getTransactionCodeByIndex(1)
                        .equals(String.valueOf(TransactionCode.ESCROW_PYMT_435.getTransCode())),
                new CustomStepResult("'Transaction Code' code is not valid", "'Transaction Code' code is valid"));
        TestRailAssert.assertEquals(escrowAmount + "0", transactionAmount_435,
                new CustomStepResult("'Transaction Amount' is not valid", "'Transaction Amount' is valid"));

        logInfo("Step 7: Go to the \"Payment Info\" tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 8: Verify existing Payment Due record");
        double recordAmountActual = Double.parseDouble(Pages.accountPaymentInfoPage().getDueAmount());
        double recordAmountExpected = Double.parseDouble(loanAccount.getPaymentAmount()) - escrowAmount;
        String recordStatus = Pages.accountPaymentInfoPage().getDueStatus();

        TestRailAssert.assertEquals(recordAmountActual, recordAmountExpected,
                new CustomStepResult("'Payment Due Record Amount' is not valid", "'Payment Due Record Amount' is valid"));
        TestRailAssert.assertEquals(recordStatus, "Partially Paid",
                new CustomStepResult("'Payment Due Record Status' is not valid", "'Payment Due Record Status' is valid"));

        logInfo("Step 9: Click on the Payment Due record and check the \"Transactions\" section");
        Pages.accountPaymentInfoPage().clickPaymentDueRecordByIndex(1);
        double escrow = Double.parseDouble(Pages.accountPaymentInfoPage().getEscrow());
        String tranCodeStatus = Pages.accountPaymentInfoPage().getStatus();

        TestRailAssert.assertEquals(escrowAmount, escrow,
                new CustomStepResult("'Escrow' is not valid", "'Escrow' is valid"));
        TestRailAssert.assertEquals(tranCodeStatus, "435 Escrow Pymt",
                new CustomStepResult("'Tran Code/Status' is not valid", "'Tran Code/Status' is valid"));

        logInfo("Step 10: Go to the \"Details\" tab and check the \"Escrow Balance\" field");
        Pages.accountDetailsPage().clickDetailsTab();
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);

        double actualEscrowBalance = Double.parseDouble(Pages.accountDetailsPage().getEscrowBalance());

        TestRailAssert.assertEquals(actualEscrowBalance, escrowAmount,
                new CustomStepResult("'Escrow Balance' is not valid", "'Escrow Balance' is valid"));
    }
}
