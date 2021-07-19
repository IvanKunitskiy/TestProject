package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Functions;
import com.nymbus.core.utils.SelenideTools;
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
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitMiscCreditBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import com.nymbus.pages.webadmin.WebAdminPages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C18802_PaymentDueRecordGenerationForNonCyclePrincipalAndInterestBillLoanTest extends BaseTest {

    private Account loanAccount;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private String clientRootId;
    private double escrowPaymentValue;
    private String activePaymentAmount;

    @BeforeMethod
    public void preCondition() {
        // Set up Client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        //Set up accounts
        Account chkAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        loanAccount.setPaymentAmountType(PaymentAmountType.PRIN_AND_INT.getPaymentAmountType());
        loanAccount.setCommitmentTypeAmt(CommitmentTypeAmt.NONE.getCommitmentTypeAmt());
        loanAccount.setPaymentBilledLeadDays("1");
        chkAccount.setDateOpened(DateTime.getDateMinusMonth(loanAccount.getDateOpened(), 1));

        // Deposit transaction
        double transactionAmount = 1001.00;
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        depositTransaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        depositTransaction.getTransactionDestination().setAmount(transactionAmount);
        depositTransaction.getTransactionSource().setAmount(transactionAmount);

        // 411 - transaction
        int transaction109Amount = 12000;
        Transaction transaction_109 = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        transaction_109.getTransactionSource().setAccountNumber(loanAccount.getAccountNumber());
        transaction_109.getTransactionSource().setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        transaction_109.getTransactionSource().setAmount(transaction109Amount);
        transaction_109.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        transaction_109.getTransactionDestination().setAmount(transaction109Amount);
        transaction_109.getTransactionDestination().setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);

        // Set chk product
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));
        escrowPaymentValue = Actions.loanProductOverviewActions().getLoanProductEscrowPaymentValue(loanProductName);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create accounts
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);
        clientRootId = ClientsActions.createClient().getClientIdFromUrl();
        Pages.accountDetailsPage().clickPaymentInfoTab();
        activePaymentAmount = Pages.accountPaymentInfoPage().getActivePaymentAmount();

        // Perform transaction to increase chk account 'Available Balance'
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

        Actions.loginActions().doLogOutProgrammatically();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 18802, testRunName = TEST_RUN_NAME)
    @Test(description = "C18802, Payment Due Record: generation for non cycle Principal and Interest (bill) loan")
    @Severity(SeverityLevel.CRITICAL)
    public void paymentDueRecordGenerationForNonCyclePrincipalAndInterestBillLoan() {

        logInfo("Step 1: Log in to Nymbus");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Run this special request in the Swagger for generating Payment Due record only after creating loan account");
        String[] actions = { "request", "generatePaymentDue" };
        Actions.nonTellerTransactionActions().performPaymentDueRecordForNonCyclePrincipalAndInterestLoan(actions, clientRootId);
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        double cBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalanceFromHeaderMenu());
        String daysBaseYearBase = Pages.accountDetailsPage().getDaysBaseYearBase();
        int yearBase = Integer.parseInt(daysBaseYearBase.split("/")[1].substring(0, 3));

        logInfo("Step 3: Log in to the webadmin -> RulesUI Query Analyzer");
        logInfo("Step 4: Search with DQL");
        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL);
        SelenideTools.switchTo().window(1);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        WebAdminActions.webAdminUsersActions().queryPaymentDueData(loanAccount.getAccountNumber());

        double paymentAmount = Double.parseDouble(WebAdminPages.rulesUIQueryAnalyzerPage().getAmountByIndex(1));
        double escrow = Double.parseDouble(WebAdminPages.rulesUIQueryAnalyzerPage().getEscrowByIndex(1));
        double interest = Double.parseDouble(WebAdminPages.rulesUIQueryAnalyzerPage().getInterestByIndex(1));
        double principal = Double.parseDouble(WebAdminPages.rulesUIQueryAnalyzerPage().getPrincipalByIndex(1));
        double currentEffectiveRate = Double.parseDouble(loanAccount.getCurrentEffectiveRate());
        int daysBase = DateTime.getDaysBetweenTwoDates(loanAccount.getDateOpened(), loanAccount.getNextPaymentBilledDueDate(), false);
        double actualInterest = cBalance * (currentEffectiveRate / 100) / yearBase * daysBase;
        double actualInterestRounded = Functions.roundToTwoDecimalPlaces(actualInterest);
        String dueDate = WebAdminPages.rulesUIQueryAnalyzerPage().getDueDateByIndex(1);
        String accountId = WebAdminPages.rulesUIQueryAnalyzerPage().getAccountIdByIndex(1);
        String dateAssessed = WebAdminPages.rulesUIQueryAnalyzerPage().getDateAssessedByIndex(1);
        String paymentDueType = WebAdminPages.rulesUIQueryAnalyzerPage().getPaymentDueTypeByIndex(1);
        String paymentDueStatus = WebAdminPages.rulesUIQueryAnalyzerPage().getPaymentDueStatusByIndex(1);

        WebAdminActions.loginActions().doLogout();
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);

        logInfo("Step 5: Check bank.data.paymentdue");
        TestRailAssert.assertTrue(accountId.equals(clientRootId),
                new CustomStepResult("'Account id' is not valid", "'Account id' is valid"));
        String dueDateWithFormat = DateTime.getDateWithFormat(dueDate, "yyyy-MM-dd", "MM/dd/yyyy");
        TestRailAssert.assertTrue(dueDateWithFormat.equals(loanAccount.getNextPaymentBilledDueDate()),
                new CustomStepResult("'Due date' is not valid", "'Due date' is valid"));
        TestRailAssert.assertTrue(principal == paymentAmount - escrow - interest,
                new CustomStepResult("'Principal' is not valid", "'Principal' is valid"));
        TestRailAssert.assertTrue(interest == actualInterestRounded,
                new CustomStepResult("'Interest' is not valid", "'Interest' is valid"));
        TestRailAssert.assertTrue(escrow == escrowPaymentValue,
                new CustomStepResult("'Escrow' is not valid", "'Escrow' is valid"));
        TestRailAssert.assertTrue(paymentAmount == Double.parseDouble(activePaymentAmount),
                new CustomStepResult("'Amount' is not valid", "'Amount' is valid"));
        String dateAssessedByFormatted = DateTime.getDateWithFormat(dateAssessed, "yyyy-MM-dd", "MM/dd/yyyy");
        TestRailAssert.assertTrue(dateAssessedByFormatted.equals(loanAccount.getNextPaymentBilledDueDate()),
                new CustomStepResult("'dateassessed' is not valid", "'dateassessed' is valid"));
        TestRailAssert.assertTrue(paymentDueType.equals("Prin & Int (Bill)"),
                new CustomStepResult("'paymentduetype' is not valid", "'paymentduetype' is valid"));
        TestRailAssert.assertTrue(paymentDueStatus.equals("Active"),
                new CustomStepResult("'paymentduestatus' is not valid", "'paymentduestatus' is valid"));

        logInfo("Step 6: Open account from precondition on 'Payment info' tab");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 7: Verify that generated PD record is displayed");
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        TestRailAssert.assertTrue(Double.parseDouble(Pages.accountPaymentInfoPage().getDisabledInterest()) ==
                        actualInterestRounded, new CustomStepResult("'Interest' is not valid", "'Interest' is valid"));
        TestRailAssert.assertTrue(Double.parseDouble(Pages.accountPaymentInfoPage().getDisabledPrincipal()) ==
                        paymentAmount - escrow - interest, new CustomStepResult("'Principal' is not valid", "'Principal' is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getDueDateFromRecordByIndex(1).equals(loanAccount.getNextPaymentBilledDueDate()),
                new CustomStepResult("'Due Date' is not valid", "'Due Date' is valid"));
        TestRailAssert.assertTrue(Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(1).equals("Active"),
                new CustomStepResult("'Status' is not valid", "'Status' is valid"));
    }
}
