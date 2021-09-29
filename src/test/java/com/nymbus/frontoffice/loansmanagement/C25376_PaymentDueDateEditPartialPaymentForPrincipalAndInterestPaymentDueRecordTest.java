package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.PaymentAmountType;
import com.nymbus.newmodels.account.loanaccount.PaymentDueData;
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
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C25376_PaymentDueDateEditPartialPaymentForPrincipalAndInterestPaymentDueRecordTest extends BaseTest {

    private Account loanAccount;
    private Account checkAccount;
    private Transaction transaction;
    private double transactionAmount = 1001.00;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private String clientRootId;
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private double balance;


    @BeforeMethod
    public void precondition() {
        // Set up Client and Accounts
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        checkAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setPaymentAmountType(PaymentAmountType.PRIN_AND_INT.getPaymentAmountType());
        loanAccount.setPaymentBilledLeadDays(String.valueOf(1));
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
        transaction.getTransactionDestination().setTransactionCode(TransactionCode.PAYMENT_416.getTransCode());
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
        Actions.loginActions().doLogOutProgrammatically();

        // Generate Payment Due Record
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.nonTellerTransaction().generatePaymentDueRecord(clientRootId);
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        PaymentDueData paymentDueData = Actions.clientPageActions().getPaymentDueInfo(loanAccount);
        paymentDueData.setAccountId(Integer.parseInt(clientRootId));
        PaymentDueData actualPaymentDueData = WebAdminActions.webAdminTransactionActions().checkPaymentDue(userCredentials, loanAccount);
        Actions.loginActions().doLogOutProgrammatically();

        // Check account balance
        while (actualPaymentDueData.getAmount() > balance) {
            Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
            Actions.transactionActions().goToTellerPage();
            Actions.transactionActions().doLoginTeller();
            Actions.transactionActions().createTransaction(depositTransaction);
            Actions.transactionActions().clickCommitButton();
            Pages.tellerPage().closeModal();
            Pages.aSideMenuPage().clickClientMenuItem();
            Actions.clientPageActions().searchAndOpenAccountByAccountNumber(checkAccount.getAccountNumber());
            balance = Double.parseDouble(Pages.accountDetailsPage().getAvailableBalance());
            Actions.loginActions().doLogOutProgrammatically();
        }
        transaction.getTransactionSource().setAmount(actualPaymentDueData.getAmount()/2);
        transaction.getTransactionDestination().setAmount(actualPaymentDueData.getAmount()/2);
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25376, testRunName = TEST_RUN_NAME)
    @Test(description = "C25376, Payment Due Date: Edit Partial Payment for Principal and Interest (Bill) payment due record")
    @Severity(SeverityLevel.CRITICAL)
    public void paymentDueDateEditPartialPaymentForPrincipalAndInterestPaymentDueRecord() {
        logInfo("Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to 'Teller' screen");
        Pages.aSideMenuPage().clickTellerMenuItem();

        logInfo("Step 3: Log in to the proof date");
        Actions.transactionActions().doLoginProofDate();

        logInfo("Step 4: Commit 416 transaction with the following fields:\n" +
                "\n" +
                "Sources -> Misc Debit:\n" +
                "\n" +
                "\"Account Number\" - active CHK or SAV account from preconditions\n" +
                "\"Transaction Code\" - \"114 - Loan Payment\"\n" +
                "Amount < Payment Info -> Payments Due record from preconditions -> Amount of principal/interest/escrow\n" +
                "Destinations -> Misc Credit:\n" +
                "\n" +
                "Account number - Loan account from preconditions\n" +
                "\"Transaction Code\" - \"416 - Payment\"\n" +
                "\"Amount\" - specify the same amount");
        int currentIndex = 0;
        Actions.transactionActions().setMiscDebitSourceForWithDraw(transaction.getTransactionSource(), currentIndex);
        Actions.transactionActions().setMiscCreditDestination(transaction.getTransactionDestination(), currentIndex);
        Pages.tellerPage().setEffectiveDate(transaction.getTransactionDate());
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 5: Close Transaction Receipt popup");
        Pages.tellerPage().closeModal();

        logInfo("Step 6: Open loan account from preconditions");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());

        logInfo("Step 7: Open \"Payment Info\" tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 8: Check Payment Due record in the \"Payments Due\" section");
        String paymentDueType = Pages.accountPaymentInfoPage().getPaymentDueTypeFromRecordByIndex(1);
        TestRailAssert.assertTrue(paymentDueType.equals("Prin & Int (Bill)"),
                new CustomStepResult("'paymentduetype' is not valid", "'paymentduetype' is valid"));

        String paymentStatus = Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(1);
        TestRailAssert.assertTrue(paymentStatus.equals("Partially Paid"),
                new CustomStepResult("'paymentstatus' is not valid", "'paymentstatus' is valid"));

        String paymentAmount = Pages.accountPaymentInfoPage().getAmountDueFromRecordByIndex(1);
        TestRailAssert.assertTrue(paymentAmount.equals(String.format("%.2f",transaction.getTransactionSource().getAmount())),
                new CustomStepResult("'paymentdue' is not valid", "'paymentdue' is valid"));

        logInfo("Step 9: Select P&I payment due record from and click on the \"Edit\" button in the \"Payment Due Details\" section");
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        String disabledPrincipal = Pages.accountPaymentInfoPage().getDisabledPrincipal();
        Pages.accountPaymentInfoPage().clickEditPaymentDueButton();

        logInfo("Step 10: Change \"Principal\", \"Interest\", \"Escrow\" to the value < paid amount from step 4 and click \"Save\"");
        Pages.accountPaymentInfoPage().inputPrincipal(transaction.getTransactionSource().getAmount()/2 + "");
        Pages.accountPaymentInfoPage().clickSaveSecButton();
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().errorMessagesIsVisible(),
                new CustomStepResult("'error' is not valid", "'error' is valid"));
        paymentStatus = Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(1);
        TestRailAssert.assertTrue(paymentStatus.equals("Partially Paid"),
                new CustomStepResult("'paymentstatus' is not valid", "'paymentstatus' is valid"));
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        String actualDisabledPrincipal = Pages.accountPaymentInfoPage().getDisabledPrincipal();
        TestRailAssert.assertTrue(actualDisabledPrincipal.equals(disabledPrincipal),
                new CustomStepResult("'principal' is not valid", "'principal' is valid"));


        logInfo("Step 11: Edit this record one more time and change \"Principal\", \"Interest\", \"Escrow\" to " +
                "the value > paid amount from step 4 and click \"Save\"");
        Pages.accountPaymentInfoPage().clickEditPaymentDueButton();
        String principal = transaction.getTransactionSource().getAmount() / 2 + transaction.getTransactionSource().getAmount() + "";
        Pages.accountPaymentInfoPage().inputPrincipal(principal);
        Pages.accountPaymentInfoPage().clickSaveSecButton();
        paymentStatus = Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(1);
        TestRailAssert.assertTrue(paymentStatus.equals("Partially Paid"),
                new CustomStepResult("'paymentstatus' is not valid", "'paymentstatus' is valid"));
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        actualDisabledPrincipal = Pages.accountPaymentInfoPage().getDisabledPrincipal();
        TestRailAssert.assertTrue(actualDisabledPrincipal.equals(principal),
                new CustomStepResult("'principal' is not valid", "'principal' is valid"));

        logInfo("Step 12: Edit this record one more time and change \"Principal\", \"Interest\", \"Escrow\" " +
                "to the value = paid amount from step 4 and click \"Save\"");
        Pages.accountPaymentInfoPage().clickEditPaymentDueButton();
        principal = Pages.accountPaymentInfoPage().getPrincipal();
        Pages.accountPaymentInfoPage().inputPrincipal(principal);
        Pages.accountPaymentInfoPage().clickSaveSecButton();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        paymentStatus = Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(1);
        TestRailAssert.assertTrue(paymentStatus.equals("Paid"),
                new CustomStepResult("'paymentstatus' is not valid", "'paymentstatus' is valid"));
        actualDisabledPrincipal = Pages.accountPaymentInfoPage().getDisabledPrincipal();
        TestRailAssert.assertTrue(actualDisabledPrincipal.equals(principal),
                new CustomStepResult("'principal' is not valid", "'principal' is valid"));

        logInfo("Step 13: Log in to the WebAdmin");
        logInfo("Step 14: Go to RulesUI Query Analyzer");
        logInfo("Step 15: Search with DQL:\n" +
                "\n" +
                "count: 10\n" +
                "from: bank.data.actloan\n" +
                "select: rootid, createdwhen, createdby, accountid, nextduedate, principalnextpaymentdate\n" +
                "where:\n" +
                "- .accountid->accountnumber: accountnumber_from_precondition\n" +
                "\n" +
                "deletedIncluded: true\n" +
                "\n" +
                "and verify that \"principalnextpaymentdate\" was changed after manually editing the Payment Due record");
        WebAdminActions.webAdminTransactionActions().checkErrorPrincipalNextPaymentDate(userCredentials, loanAccount);
    }
}
