package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
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
import com.nymbus.util.Random;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C25381_Process406PrinPayOnlyPaymentTransactionTest extends BaseTest {

    private Account chkAccount;
    private Account loanAccount;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private String clientRootId;
    private Transaction transaction_109;
    private String accruedInterest;
    private Transaction transaction_406;
    private String dateLastPayment;
    private String nextPaymentBilledDueDate;
    private double currentBalance;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up account
        chkAccount = new Account().setCHKAccountData();
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
        transaction_109 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_109.getTransactionSource().setAccountNumber(loanAccount.getAccountNumber());
        transaction_109.getTransactionSource().setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        transaction_109.getTransactionSource().setAmount(transaction109Amount);
        transaction_109.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        transaction_109.getTransactionDestination().setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        transaction_109.getTransactionDestination().setAmount(transaction109Amount);

        int transaction406Amount = transaction109Amount - Random.genInt(1, 20);
        transaction_406 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_406.getTransactionSource().setAccountNumber(chkAccount.getAccountNumber());
        transaction_406.getTransactionSource().setTransactionCode(TransactionCode.LOAN_PAYMENT_114.getTransCode());
        transaction_406.getTransactionSource().setAmount(transaction109Amount - Random.genInt(1, 20));
        transaction_406.getTransactionDestination().setAccountNumber(loanAccount.getAccountNumber());
        transaction_406.getTransactionDestination().setTransactionCode(TransactionCode.PRIN_PAYM_ONLY_406.getTransCode());
        transaction_406.getTransactionDestination().setAmount(transaction406Amount);

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
        clientRootId = ClientsActions.createClient().getClientIdFromUrl();

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
        accruedInterest = Pages.accountDetailsPage().getAccruedInterest();
        dateLastPayment = Pages.accountDetailsPage().getDateLastPayment();
        nextPaymentBilledDueDate = Pages.accountDetailsPage().getNextPaymentBilledDueDate();
        currentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalanceFromHeaderMenu());

        Actions.loginActions().doLogOutProgrammatically();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25413, testRunName = TEST_RUN_NAME)
    @Test(description = "C25381, Process 406 - Prin Pay Only payment transaction")
    @Severity(SeverityLevel.CRITICAL)
    public void process406PrinPayOnlyPaymentTransaction() {

        logInfo("Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to 'Teller' screen");
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Log in to the proof date");
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 4: Commit 406 transaction with the following fields:\n" +
                "\n" +
                "    Sources -> Misc Debit:\n" +
                "        \"Account Number\" - active CHK or SAV account from preconditions\n" +
                "        \"Transaction Code\" - \"114 - Loan Payment\"\n" +
                "        Amount < current balance with loan account from preconditions\n" +
                "\n" +
                "    Destinations -> Misc Credit:\n" +
                "        Account number - Loan account from preconditions\n" +
                "        \"Transaction Code\" - \"406 - Prin Paym Only\"\n" +
                "        \"Amount\" - specify the same amount\n" +
                "\n");
        int currentIndex = 0;
        Actions.transactionActions().setMiscDebitSourceForWithDraw(transaction_406.getTransactionSource(), currentIndex);
        Actions.transactionActions().setMiscCreditDestination(transaction_406.getTransactionDestination(), currentIndex);
        Pages.tellerPage().setEffectiveDate(transaction_406.getTransactionDate());
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 5: Close Transaction Receipt popup");
        Pages.tellerPage().closeModal();

        logInfo("Step 6: Open loan account from preconditions on 'Details' tab");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());

        logInfo("Step 7: Pay attention to the following fields:\n" +
                "- Next Payment Billed Due Date\n" +
                "- Date Last Payment\n" +
                "- Current Balance");
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getNextPaymentBilledDueDate().equals(nextPaymentBilledDueDate),
                new CustomStepResult("'Next Payment Billed Due Date' is not valid", "'Next Payment Billed Due Date' is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getNextPaymentBilledDueDate().equals(dateLastPayment),
                new CustomStepResult("'Date Last Payment' is not valid", "'Date Last Payment' is valid"));
        double actualCurrentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance());
        System.out.println(actualCurrentBalance);
        System.out.println(currentBalance);
        System.out.println(transaction_406.getTransactionDestination().getAmount());
        TestRailAssert.assertTrue(actualCurrentBalance == currentBalance - transaction_406.getTransactionDestination().getAmount(),
                new CustomStepResult("'Date Last Payment' is not valid", "'Date Last Payment' is valid"));

        logInfo("Step 8: Open 'Payment Info' tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 9: Click on the Payment Due record in the 'Payments Due' section and pay attention to the Payment Due Details");
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        // None of the Payment Due fields is affected by "406 - Prin Paym Only" transaction

        logInfo("Step 10: Pay attention at the 'Transactions'");
        // "406 - Prin Paym Only" transaction is NOT displayed in the 'Transaction' list

        logInfo("Step 11: Go to Transactions tab and verify generated transaction");
        Pages.accountDetailsPage().clickTransactionsTab();
        // "406 - Prin Paym Only" transaction is generated with Principal only portion amount == amount paid in step 4
    }
}
