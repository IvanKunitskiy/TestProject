package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.PaymentAmountType;
import com.nymbus.newmodels.account.loanaccount.PaymentDueData;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitDepositCHKAccBuilder;
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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Dmytro")
public class C18806_PaymentDueRecordGenerationForNonCycleInterestOnlyBillLoanTest extends BaseTest {

    private Account loanAccount;
    private Account checkAccount;
    private double transactionAmount = 1001.00;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private String clientRootId;
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private int balance;


    @BeforeMethod
    public void precondition() {
        // Set up Client and Accounts
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        checkAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setPaymentAmountType(PaymentAmountType.INTEREST_ONLY.getPaymentAmountType());
        loanAccount.setPaymentBilledLeadDays(String.valueOf(1));
        loanAccount.setProduct(loanProductName);
        loanAccount.setEscrow("$ 0.00");
        loanAccount.setCycleCode(Generator.genInt(1, 20)+"");
        loanAccount.setCycleLoan(true);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
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
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 18806, testRunName = TEST_RUN_NAME)
    @Test(description = "C18806, Payment Due Record: generation for non cycle Interest only (Bill) loan")
    @Severity(SeverityLevel.CRITICAL)
    public void paymentDueRecordGenerationForNonCycleInterestOnlyLoan() {
        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Run this special request in the Swagger for generating Payment Due record only " +
                "after creating loan account:\n" +
                "{\n" +
                "\"actions\": [\n" +
                "\"request\",\n" +
                "\"generatePaymentDue\"\n" +
                "],\n" +
                "\"beans\": [\n" +
                "{\n" +
                "\"fields\": {},\n" +
                "\"rootId\": 143572457, // loan->accountid\n" +
                "\"type\": \"bank.data.actmst\"\n" +
                "}\n" +
                "],\n" +
                "\"ruleType\": \"methods\"\n" +
                "}");
        Actions.nonTellerTransaction().generatePaymentDueRecord(clientRootId);

        logInfo("Step 3: Log in to the webadmin -> RulesUI Query Analyzer");
        logInfo("Step 4: Search with DQL:\n" +
                "\n" +
                "count: 10\n" +
                "from: bank.data.paymentdue\n" +
                "where:\n" +
                "- .accountid->accountnumber: (accountnumber of created loan account)\n" +
                "orderBy: -id\n" +
                "deleteincluded: true");
        logInfo("Step 5: Check bank.data.paymentdue");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        PaymentDueData paymentDueData = Actions.clientPageActions().getPaymentDueInfoIntOnlyBill(loanAccount);
        paymentDueData.setAccountId(Integer.parseInt(clientRootId));
        PaymentDueData actualPaymentDueData = WebAdminActions.webAdminTransactionActions().checkPaymentDue(userCredentials, loanAccount);

        TestRailAssert.assertTrue(paymentDueData.equals(actualPaymentDueData),
                new CustomStepResult("Payment data is valid", "Payment data is not valid"));

        logInfo("Step 6: Open account from precondition on \"Payment info\" tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 7: Verify that generated PD record is displayed");
        Pages.accountPaymentInfoPage().clickPaymentDueRecord();
        String dueDate = Pages.accountPaymentInfoPage().getDueDateFromRecordByIndex(1);
        actualPaymentDueData.setDueDate(dueDate);
        actualPaymentDueData.setInterest(Pages.accountPaymentInfoPage().getDisabledInterest());
        actualPaymentDueData.setPrincipal(Double.parseDouble(Pages.accountPaymentInfoPage().getDisabledPrincipal()));
        actualPaymentDueData.setEscrow(Double.parseDouble(Pages.accountPaymentInfoPage().getDisabledEscrow()));
        actualPaymentDueData.setPaymentDueStatus(Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(1));

        TestRailAssert.assertTrue(paymentDueData.equals(actualPaymentDueData),
                new CustomStepResult("Payment data is valid", "Payment data is not valid"));
    }

}
