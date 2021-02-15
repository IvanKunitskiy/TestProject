package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
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
import com.nymbus.newmodels.generation.tansactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.tansactions.factory.SourceFactory;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
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
public class C18799_PaymentDueRecordGenerationForNonCyclePrincipalAndInterestLoanTest extends BaseTest {

    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private String clientRootId;
    private Account loanAccount;
    private String activePaymentAmount;
    private String paymentBilledLeadDays;
    private double escrowPaymentValue;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up CHK account
        Account checkingAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        loanAccount.setPaymentAmountType(PaymentAmountType.PRINCIPAL_AND_INTEREST.getPaymentAmountType());
        loanAccount.setPaymentBilledLeadDays("1");

        // Set transaction data
        int transactionAmount = 12000;
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscDebitSource.setAmount(transactionAmount);
        miscCreditDestination.setAccountNumber(checkingAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(transactionAmount);

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);
        Actions.loginActions().doLogOut();

        // Set the product
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        escrowPaymentValue = Actions.loanProductOverviewActions().getLoanProductEscrowPaymentValue(loanProductName);
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create accounts get the data and logout
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);
        clientRootId = ClientsActions.createClient().getClientIdFromUrl();
        paymentBilledLeadDays = Pages.accountDetailsPage().getPaymentBilledLeadDays();
        Pages.accountDetailsPage().clickPaymentInfoTab();
        activePaymentAmount = Pages.accountPaymentInfoPage().getActivePaymentAmount();

        // Perform transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().setMiscDebitSource(miscDebitSource, 0);
        Actions.transactionActions().setMiscCreditDestination(miscCreditDestination, 0);
        Pages.tellerPage().setEffectiveDate(loanAccount.getDateOpened());
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 18799, testRunName = TEST_RUN_NAME)
    @Test(description = "C18799, Payment Due Record: generation for non cycle Principal and Interest loan")
    @Severity(SeverityLevel.CRITICAL)
    public void paymentDueRecordGenerationForNonCyclePrincipalAndInterestLoan() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Run EOD for 'Next Payment Billed Due Date' - 'Payment Billed Lead Days'");
        String[] actions = { "request", "generatePaymentDue" };
        Actions.nonTellerTransactionActions().performPaymentDueRecordForNonCyclePrincipalAndInterestLoan(actions, clientRootId);

        logInfo("Step 3: Log in to the webadmin -> RulesUI Query Analyzer");
        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL);
        SelenideTools.switchTo().window(1);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 4: Search with DQL:\n" +
                "\n" +
                "count: 10\n" +
                "from: bank.data.paymentdue\n" +
                "where:\n" +
                "- .accountid->accountnumber: (accountnumber of created loan account)\n" +
                "orderBy: -id\n" +
                "deleteincluded: true\n");
        logInfo("Step 5: Check fields in the bank.data.paymentdue");
        WebAdminActions.webAdminUsersActions().queryDueData(loanAccount.getAccountNumber());

        TestRailAssert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getAccountIdByIndex(1).equals(clientRootId),
                new CustomStepResult("'Account id' is not valid", "'Account id' is valid"));
        String dueDateWithFormat = DateTime.getDateWithFormat(WebAdminPages.rulesUIQueryAnalyzerPage().getDueDateByIndex(1), "yyyy-MM-dd", "MM/dd/yyyy ");
        System.out.println("|" + dueDateWithFormat + "|");
        System.out.println("|" + loanAccount.getNextPaymentBilledDueDate() + "|");
        TestRailAssert.assertTrue(dueDateWithFormat.equals(loanAccount.getNextPaymentBilledDueDate()),
                new CustomStepResult("'Due date' is not valid", "'Due date' is valid"));
        TestRailAssert.assertTrue(Double.parseDouble(WebAdminPages.rulesUIQueryAnalyzerPage().getEscrowByIndex(1)) == escrowPaymentValue,
                new CustomStepResult("'Escrow' is not valid", "'Escrow' is valid"));
        TestRailAssert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getAmountByIndex(1).equals(activePaymentAmount),
                new CustomStepResult("'Amount' is not valid", "'Amount' is valid"));
        String dateMinusDays = DateTime.getDateMinusDays(loanAccount.getNextPaymentBilledDueDate(), Integer.parseInt(paymentBilledLeadDays));
        TestRailAssert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getDateAssessedByIndex(1).equals(dateMinusDays),
                new CustomStepResult("'dateassessed' is not valid", "'dateassessed' is valid"));
        TestRailAssert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getPaymentDueTypeByIndex(1).equals("Principal & Interest"),
                new CustomStepResult("'paymentduetype' is not valid", "'paymentduetype' is valid"));
        TestRailAssert.assertTrue(WebAdminPages.rulesUIQueryAnalyzerPage().getPaymentDueStatusByIndex(1).equals("Active"),
                new CustomStepResult("'paymentduestatus' is not valid", "'paymentduestatus' is valid"));
    }
}
