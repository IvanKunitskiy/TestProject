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
public class C46264_LoanVerifyTheAccountBalancesOnTheReceiptForTransactions extends BaseTest {
    private Account loanAccount;
    private Account chkAccount;
    private String localDate;
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
        localDate = DateTime.getLocalDateOfPattern("MM/dd/yyyy");
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

    @TestRailIssue(issueID = 46264, testRunName = TEST_RUN_NAME)
    @Test(description = "C46264, Loan: Verify the Account Balances on the Receipt for transactions")
    @Severity(SeverityLevel.CRITICAL)
    public void loanVerifyTheAccountBalancesOnTheReceiptForTransactions() {
        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to the 'Teller' screen");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Log in to the proof date");
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 4: Fill in the following fields and click the [Commit Transaction] :\n" +
                "Sources -> Misc Debit:\n" +
                "\n" +
                "\"Account Number\" - loan account from preconditions\n" +
                "\"Transaction Code\" - \"411 - New Loan\"\n" +
                "Amount - any valid (e.g. $12000.00)\n" +
                "Destinations -> Misc Credit:\n" +
                "\n" +
                "Account number - active CHK or SAV account from precondition\n" +
                "\"Transaction Code\" - specify trancode (\"109-Deposit\" for CHK or \"209-Deposit for SAV)\n" +
                "\"Amount\" - specify the same amount (e.g. $12000.00)\n" +
                "Effective Date = the same as \"Date Opened\" for loan account");
        // 109 - transaction
        int transaction109Amount = 12000;
        Transaction transaction_109 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_109.getTransactionSource().setAccountNumber(loanAccount.getAccountNumber());
        transaction_109.getTransactionSource().setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        transaction_109.getTransactionSource().setAmount(transaction109Amount);
        transaction_109.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        transaction_109.getTransactionDestination().setAmount(transaction109Amount);
        transaction_109.getTransactionDestination().setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());

        // Perform '109 - deposit' transaction
        Actions.transactionActions().createTransaction(transaction_109);
        Pages.tellerPage().setEffectiveDate(loanAccount.getDateOpened());
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();

        logInfo("Step 5: Check the Account Balances on the Transaction Receipt popup");

        logInfo("Step 6: Close Transaction Receipt popup");
        Pages.tellerPage().closeModal();

        logInfo("Step 7: Open loan account on the \"Transactions\" tab and verify that transaction is written on the transactions history page");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        Pages.accountDetailsPage().clickTransactionsTab();

        String transactionAmount = Pages.accountTransactionPage().getAmountValue(1) + Pages.accountTransactionPage().getAmountFractionalValue(1);
        String postingDate = Pages.accountTransactionPage().getPostingDateValue(1);
        String transactionEffectiveDate = Pages.accountTransactionPage().getEffectiveDateValue(1);

        System.out.println(transactionAmount + " ----------------------");
        System.out.println(transaction109Amount + ".00" + " ----------------------");
        System.out.println(postingDate + " ----------------------");
        System.out.println(localDate + " ----------------------");
        System.out.println(transactionEffectiveDate + " ----------------------");
        System.out.println(loanAccount.getDateOpened() + " ----------------------");

        TestRailAssert.assertTrue(transactionAmount.equals(transaction109Amount + ".00"),
                new CustomStepResult("'Transaction Amount' is valid", "'Transaction Amount' is not valid"));
        TestRailAssert.assertTrue(postingDate.equals(localDate),
                new CustomStepResult("'Posting Date' is valid", "'Posting Date' is not valid"));
        TestRailAssert.assertTrue(transactionEffectiveDate.equals(loanAccount.getDateOpened()),
                new CustomStepResult("'Effective Date' is valid", "'Effective Date' is not valid"));

        logInfo("Step 8: Go to the 'Teller' screen");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 9: Fill in the following fields and click the [Commit Transaction] :\n" +
                "Sources -> Misc Debit:\n" +
                "\n" +
                "\"Account Number\" - active CHK or SAV account from precondition\n" +
                "\"Transaction Code\" - specify trancode (\"114-Loan Payment\" for CHK account or \"214-Loan Payment\" for SAV account)\n" +
                "Amount - specify the same amount as in Destinations\n" +
                "Destinations -> Misc Credit:\n" +
                "\n" +
                "Account number - loan account from preconditions\n" +
                "\"Transaction Code\" - any loan payment transaction (f.e. \"416 - Payment\" )\n" +
                "\"Amount\" - any valid (Payment amount - in this case)\n" +
                "Effective Date = Current system date");

        double PAYMENT_AMOUNT = 120.00;

        // Set up 416 transaction
        Transaction transaction_116 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_116.getTransactionSource().setTransactionCode(TransactionCode.LOAN_PAYMENT_114.getTransCode());
        transaction_116.getTransactionSource().setAccountNumber(chkAccount.getAccountNumber());
        transaction_116.getTransactionSource().setAmount(PAYMENT_AMOUNT);
        transaction_116.getTransactionDestination().setTransactionCode(TransactionCode.FORCE_TO_PRIN_420.getTransCode());
        transaction_116.getTransactionDestination().setAccountNumber(loanAccount.getAccountNumber());
        transaction_116.getTransactionDestination().setAmount(PAYMENT_AMOUNT);

        // Perform 416 transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().setMiscDebitSourceForWithDraw(transaction_116.getTransactionSource(), 0);
        Actions.transactionActions().setMiscCreditDestination(transaction_116.getTransactionDestination(), 0);
        Actions.transactionActions().clickCommitButton();

        logInfo("Step 10: Check the Account Balances on the Transaction Receipt popup");
        SelenideTools.sleep(36000);
    }
}
