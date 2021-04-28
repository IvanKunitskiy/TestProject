package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Functions;
import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.InterestMethod;
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
public class C19577_LoanSkipPaymentPostSkipPaymentWithFeeTest extends BaseTest {

    private Account loanAccount;
    private Account checkAccount;
    private double transactionAmount = 1001.00;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private int balance;
    private final String AMOUNT = "3000";


    @BeforeMethod
    public void precondition() {
        // Set up Client and Accounts
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        checkAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setPaymentAmountType(PaymentAmountType.INTEREST_ONLY.getPaymentAmountType());
        loanAccount.setInterestMethod(InterestMethod.AMORTIZED.name());
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

    @TestRailIssue(issueID = 19577, testRunName = TEST_RUN_NAME)
    @Test(description = "C19577,Loan Skip Payment: Post Skip Payment with Fee")
    @Severity(SeverityLevel.CRITICAL)
    public void teaserRateProcessing() {
        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open account from preconditions on the 'Maintenance' page");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        Pages.accountDetailsPage().clickMaintenanceTab();

        logInfo("Step 3: Launch \"Loan Skip Payment\" tool");
        AccountActions.accountMaintenanceActions().setTool(Tool.RESERVE_PREMIUM_PROCESSING);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Step 4: Select existing \"Reserve/Premium\" record and click on the \"Edit\" button");
        Pages.reservePremiumProcessingModalPage().clickReservePremiumRecordFromTableByIndex(1);
        Pages.reservePremiumProcessingModalPage().clickEditButton();

        logInfo("Step 5: Fill in the following fields and \"Commit Transaction\"\n" +
                "1. Change \"Reserve/Premium Term\" to any other value, differ from original\n" +
                "2. Enter the negative amount in \"Adjustment Amount\" field (Adjustment amount < Reserve/Premium Amount)");
        String term ="6";
        Pages.reservePremiumProcessingModalPage().setReservePremiumTerm(term);
        final String ADJUSTMENT_AMOUNT = "-1500";
        Pages.reservePremiumProcessingModalPage().setAdjustmentAmount(ADJUSTMENT_AMOUNT);
        Pages.reservePremiumProcessingModalPage().clickCommitTransactionButton();
        Pages.reservePremiumProcessingModalPage().clickReservePremiumRecordFromTableByIndex(1);
        double originalAmount = Double.parseDouble(Pages.reservePremiumProcessingModalPage().getReservePremiumOriginalAmount());
        String decreasedAmount = Integer.parseInt(AMOUNT) + Integer.parseInt(ADJUSTMENT_AMOUNT) + "";
        TestRailAssert.assertTrue(Functions.getStringValueWithOnlyDigits(originalAmount).equals(decreasedAmount),
                new CustomStepResult("Original amount is valid",
                        String.format("'Original Amount' is not valid. Expected %s, found - %s",
                                decreasedAmount, Functions.getStringValueWithOnlyDigits(originalAmount))));
        double unAmortizedAmount = Double.parseDouble(Pages.reservePremiumProcessingModalPage().getReservePremiumUnamortized());
        TestRailAssert.assertTrue(Functions.getStringValueWithOnlyDigits(unAmortizedAmount).equals(decreasedAmount),
                new CustomStepResult("UnAmortized amount is valid",
                        String.format("'UnAmortized Amount' is not valid. Expected %s, found - %s",
                                decreasedAmount, Functions.getStringValueWithOnlyDigits(unAmortizedAmount))));
        String maturityDate = Pages.reservePremiumProcessingModalPage().getReservePremiumMaturityDate();
        TestRailAssert.assertTrue(maturityDate.equals(DateTime.getDatePlusMonth(loanAccount.getDateOpened(),
                2 + Integer.parseInt(term))),
                new CustomStepResult("Maturity date is valid", "Maturity date is not valid"));
        Pages.reservePremiumProcessingModalPage().clickCloseButton();

        logInfo("Step 6: Open account from preconditions on the \"Transactions\" tab");
        Pages.reservePremiumProcessingModalPage().clickCloseButton();
        Pages.accountDetailsPage().clickTransactionsTab();

        logInfo("Step 7: Verify committed transaction");
        String transactionCode = Pages.accountTransactionPage().getTransactionCodeByIndex(1);
        String transCode = TransactionCode.ADD_RP_INCOME_452I.getTransCode();
        TestRailAssert.assertTrue(transactionCode.equals(transCode),
                new CustomStepResult("'Transaction code' is valid",
                        String.format("'Transaction code' is not valid. Expected %s, found - %s",
                                transCode, transactionCode)));
        double transactionAmount = AccountActions.retrievingAccountData().getAmountValue(1);
        String stringValueWithOnlyDigits = Functions.getStringValueWithOnlyDigits(transactionAmount);
        TestRailAssert.assertTrue(stringValueWithOnlyDigits.equals(decreasedAmount),
                new CustomStepResult("'Amount' is valid", String.format("'Amount' is not valid. Expected %s, found - %s",
                        decreasedAmount, stringValueWithOnlyDigits)));
    }

}
