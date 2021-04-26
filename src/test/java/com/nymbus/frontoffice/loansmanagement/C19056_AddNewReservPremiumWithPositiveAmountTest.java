package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Generator;
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
import com.nymbus.newmodels.generation.tansactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.tansactions.factory.SourceFactory;
import com.nymbus.newmodels.maintenance.Tool;
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
public class C19056_AddNewReservPremiumWithPositiveAmountTest extends BaseTest {

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
        loanAccount.setCycleCode(Generator.genInt(1, 20) + "");
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

        //Create New Loan Reserve
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        String code = "autotest";
        if (!Actions.loanReserveActions().isLoanReserveExists(code)) {
            Pages.loansReservePage().clickAddNewButton();
            Pages.loansReservePage().inputAuthorizationCode(code);
            Pages.loansReservePage().inputAmortizationType("Straight Line");
            Pages.loansReservePage().inputPremiumType("Loan costs");
            Pages.loansReservePage().inputBalanceDefinition("Deferred Fees");
            Pages.loansReservePage().clickSaveButton();
        }
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25352, testRunName = TEST_RUN_NAME)
    @Test(description = "C25352, Teaser Rate Processing - Setup (Teaser Rate Change Type = Calculated Rate)")
    @Severity(SeverityLevel.CRITICAL)
    public void teaserRateProcessing() {
        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open account from preconditions on the 'Maintenance' page");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        Pages.accountDetailsPage().clickMaintenanceTab();

        logInfo("Step 3: Select 'Reserve/Premium Processing' in 'Tools' widget and click the [Launch] button");
        AccountActions.accountMaintenanceActions().setTool(Tool.RESERVE_PREMIUM_PROCESSING);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Step 4: Click the [+ Add New Loan Reserve/Premium] button");
        Pages.reservePremiumProcessingModalPage().clickAddNewButton();

        logInfo("Step 5: Fill in all required fields:\n" +
                "\n" +
                "'Effective Date' < = Current Date\n" +
                "'Reserve/Premium Amount' = any positive amount (e.g. $ 3,000.00)\n" +
                "'Deferred Yes/No' = Yes\n" +
                "'Reserve/Premium Code' = any existing code in the the drop down (e.g 'DE)\n" +
                "'Reserve/Premium Term' > 0 (e.g. 3)\n" +
                "'Reserve/Premium Deferring Start Date' > = Current Date\n" +
                "'GL Offset' = any value\n" +
                "'IRS Reportable Points Paid' = No\n" +
                "and 'Commit Transaction'");
        String origAmount = "30.00";
        String term = "3";
        Actions.loanReserveActions().inputLoanReserveFields(loanAccount, origAmount, term);

        logInfo("Step 6: Select created Reserve/Premium");
        Pages.reservePremiumProcessingModalPage().clickRecord("autotest");
        String amount = Pages.reservePremiumProcessingModalPage().getAmount();
        TestRailAssert.assertTrue(amount.equals(""),
                new CustomStepResult("Amount is valid", "Amount is not valid"));
        String originalAmount = Pages.reservePremiumProcessingModalPage().getOriginalAmount();
        TestRailAssert.assertTrue(originalAmount.equals(origAmount),
                new CustomStepResult("Original amount is valid", "Original amount is not valid"));
        String unAmortizedAmount = Pages.reservePremiumProcessingModalPage().getUnAmortizedAmount();
        TestRailAssert.assertTrue(unAmortizedAmount.equals(origAmount),
                new CustomStepResult("UnAmortized amount is valid", "UnAmortized amount is not valid"));
        String maturityDate = Pages.reservePremiumProcessingModalPage().getMaturityDate();
        TestRailAssert.assertTrue(maturityDate.equals(DateTime.getDatePlusMonth(loanAccount.getDateOpened(),
                2 + Integer.parseInt(term))),
                new CustomStepResult("Maturity date is valid", "Maturity date is not valid"));

        logInfo("Step 7: Open Account from preconditions on the 'Transaction' tab");
        Pages.reservePremiumProcessingModalPage().clickCloseButton();
        Pages.accountDetailsPage().clickTransactionsTab();

        logInfo("Step 8: Verify generated transaction");


    }

}
