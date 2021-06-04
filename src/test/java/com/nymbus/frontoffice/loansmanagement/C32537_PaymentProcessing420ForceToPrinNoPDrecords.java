package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
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
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitMiscCreditBuilder;
import com.nymbus.newmodels.generation.tansactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.tansactions.factory.SourceFactory;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sun.jvm.hotspot.debugger.Page;

import javax.net.ssl.SSLContext;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C32537_PaymentProcessing420ForceToPrinNoPDrecords extends BaseTest {

    private Account loanAccount;
    private Account chkAccount;
    private Transaction transaction_420;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private final String TEST_RUN_NAME = "Loans Management";
    private String nextPaymentBilledDueDate;
    private double interest;
    private int transaction109Amount;

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
        loanAccount.setPaymentAmountType(PaymentAmountType.PRINCIPAL_AND_INTEREST.getPaymentAmountType());

        // Set proper dates
        String localDate = DateTime.getLocalDateOfPattern("MM/dd/yyyy");
        String dateMinusMonth = DateTime.getDateMinusMonth(localDate, 1);
        loanAccount.setDateOpened(DateTime.getDateWithFormatPlusDays(dateMinusMonth, "MM/dd/yyyy", "MM/dd/yyyy", 2));
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

        // Set up deposit transaction
        int depositTransactionAmount = 12000;
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        depositTransaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        depositTransaction.getTransactionDestination().setAmount(depositTransactionAmount);
        depositTransaction.getTransactionSource().setAmount(depositTransactionAmount);

        // Perform deposit transaction
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
    }

    @TestRailIssue(issueID = 32537, testRunName = TEST_RUN_NAME)
    @Test(description = "C32537, Payment Processing: 420 - Force To Prin, no PD records")
    @Severity(SeverityLevel.CRITICAL)
    public void processEc416LoanPrepaymentTransactionPrinIntBill() {

        logInfo("Step 1: Log in to the NYMBUS");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to the 'Teller' screen");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        System.out.println("--------------------- " + Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance()));
        double currentBalanceBefore = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance());

        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Log in to the proof date");
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 4: Commit \"420 - Force To Prin\" transaction with the following fields:\n" +
                "1. Sources -> Misc Debit:\n" +
                "\"Account Number\" - active CHK or SAV account from preconditions\n" +
                "\"Transaction Code\" - \"114 - Loan Payment\"\n" +
                "Amount < = Principal Portion amount with Loan from preconditions (f.e. $ 500.00)\n" +
                "2. Destinations -> Misc Credit:\n" +
                "Account number - Loan account from preconditions\n" +
                "\"Transaction Code\" - \"420 - Force To Prin\"\n" +
                "\"Amount\" - specify the same amount");

        double PAYMENT_AMOUNT = 120.00;
        // Set up 416 transaction
        transaction_420 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_420.getTransactionSource().setTransactionCode(TransactionCode.LOAN_PAYMENT_114.getTransCode());
        transaction_420.getTransactionSource().setAccountNumber(chkAccount.getAccountNumber());
        transaction_420.getTransactionSource().setAmount(PAYMENT_AMOUNT);
        transaction_420.getTransactionDestination().setTransactionCode(TransactionCode.FORCE_TO_PRIN_420.getTransCode());
        transaction_420.getTransactionDestination().setAccountNumber(loanAccount.getAccountNumber());
        transaction_420.getTransactionDestination().setAmount(PAYMENT_AMOUNT);

        // Perform 416 transaction
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
        TestRailAssert.assertTrue(Pages.accountTransactionPage().getTransactionCodeByIndex(1)
                        .equals(String.valueOf(TransactionCode.PRIN_PAYM_ONLY_406.getTransCode())),
                new CustomStepResult("'Transaction Code' code is not valid", "'Transaction Code' code is valid"));
        System.out.println(Double.parseDouble(Pages.accountTransactionPage().getBalanceValue(1)));

        Pages.accountDetailsPage().clickDetailsTab();
        double currentBalanceAfter = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance());
        System.out.println(currentBalanceAfter);
        System.out.println(currentBalanceBefore - PAYMENT_AMOUNT);
        TestRailAssert.assertTrue(currentBalanceBefore - PAYMENT_AMOUNT == currentBalanceAfter,
                new CustomStepResult("Payment Amount is not valid", "Payment Amount is valid"));

        logInfo("Step 7: Go to the \"Payment Info\" tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();
        SelenideTools.sleep(100000000);

        logInfo("Step 8: Verify the \"Payment Due\" section");
        Assert.assertFalse(Boolean.parseBoolean(Pages.accountPaymentInfoPage().getDueDateFromRecordByIndex(1)));
    }

}
