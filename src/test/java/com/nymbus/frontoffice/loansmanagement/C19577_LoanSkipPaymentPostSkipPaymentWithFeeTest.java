package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
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
        loanAccount.setPaymentAmountType(PaymentAmountType.PRIN_AND_INT.getPaymentAmountType());
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

        logInfo("Step 3: Launch 'Loan Skip Payment' tool");
        AccountActions.accountMaintenanceActions().setTool(Tool.LOAN_SKIP_PAYMENT);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Step 4: Specify fields:\n" +
                "\n" +
                "'NBR of Payments to Skip' (should be in range from 1 to 6)\n" +
                "Fee Amount = any valid amount\n" +
                "Fee Add-on Payment = NO\n" +
                "Extend Maturity = NO");
        int nmbrToSkip = 3;
        Pages.loanSkipPaymentModalPage().typeNbrOfPaymentsToSkipInput(String.valueOf(nmbrToSkip));
        double feeAmount = 10.000;
        Pages.loanSkipPaymentModalPage().typeFeeAmount(String.valueOf(feeAmount));
        Actions.loanSkipPaymentModalActions().setExtendMaturityToggleToNo();
        Actions.loanSkipPaymentModalActions().setFeeAddOnPaymentToggleToNo();
        String currentDueDate = Pages.loanSkipPaymentModalPage().getCurrentDueDate();
        String maturityDate = Pages.loanSkipPaymentModalPage().getMaturityDate();

        logInfo("Step 5: Click the [Commit Transaction] button");
        Pages.loanSkipPaymentModalPage().clickCommitTransactionButton();

        logInfo("Step 6: Launch the 'Loan Skip Payment' tool again");
        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        Pages.accountDetailsPage().clickMaintenanceTab();
        AccountActions.accountMaintenanceActions().setTool(Tool.LOAN_SKIP_PAYMENT);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Step 7: Verify the following fields:\n" +
                "'Current Due Date'\n" +
                "'Maturity Date'\n" +
                "'NBR Skips This Year'");
        String actualCurrentDueDate = Pages.loanSkipPaymentModalPage().getCurrentDueDate();
        TestRailAssert.assertTrue(actualCurrentDueDate.equals(DateTime.getDatePlusMonth(currentDueDate, nmbrToSkip)),
                new CustomStepResult("'Current Due Date' is  valid", "'Current Due Date' is not valid"));
        TestRailAssert.assertTrue(Pages.loanSkipPaymentModalPage().getMaturityDate().equals(maturityDate),
                new CustomStepResult("'Maturity Date' is  valid", "'Maturity Date' is not valid"));
        int actualNmbrSkipsThisYear = Integer.parseInt(Pages.loanSkipPaymentModalPage().getNbrSkipsThisYear());
        TestRailAssert.assertTrue(actualNmbrSkipsThisYear == nmbrToSkip,
                new CustomStepResult("'NBR Skips This Year' is  valid", "'NBR Skips This Year' is not valid"));

        logInfo("Step 8: Go to the 'Transactions' tab");
        Pages.loanSkipPaymentModalPage().clickCancelButton();
        Pages.accountDetailsPage().clickTransactionsTab();

        logInfo("Step 9: Verify generated transactions");
        // "482-Skip Fee Payment"
        String transactionCode482 = Pages.accountTransactionPage().getTransactionCodeByIndex(1);
        TestRailAssert.assertTrue(transactionCode482.equals(TransactionCode.SKIP_FEE_ASSESSED_483.getTransCode()),
                new CustomStepResult("'Transaction code' is  valid", "'Transaction code' is not valid"));
        double transactionAmount482 = AccountActions.retrievingAccountData().getAmountValue(1);
        TestRailAssert.assertTrue(transactionAmount482 == feeAmount / 10,
                new CustomStepResult("'Amount' is  valid", "'Amount' is not valid"));

        logInfo("Step 10: Go to the 'Payment Info' tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 11: Verify generated Payment Due records");
        // "P&I Payment Due" record
        String paymentDueType = Pages.accountPaymentInfoPage().getPaymentDueTypeFromRecordByIndex(1);
        TestRailAssert.assertTrue(paymentDueType.equals(loanAccount.getPaymentAmountType()),
                new CustomStepResult("'Payment Due Type' is  valid", "'Payment Due Type' is not valid"));

        double activePaymentAmount = Double.parseDouble(Pages.accountPaymentInfoPage().getActivePaymentAmount());
        double amountDue = Double.parseDouble(Pages.accountPaymentInfoPage().getAmountDueFromRecordByIndex(1));
        TestRailAssert.assertTrue(amountDue == activePaymentAmount,
                new CustomStepResult("'Amount Due' is valid", "'Amount Due' is not valid"));

        String status = Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(1);
        TestRailAssert.assertTrue(status.equals("Skip"),
                new CustomStepResult("'Status' is valid", "'Status' is not valid"));

        // "Loan Skip Payment Charges" Payment Due record
        String dueDateFromRecordByIndex = Pages.accountPaymentInfoPage().getDueDateFromRecordByIndex(3);
        TestRailAssert.assertTrue(dueDateFromRecordByIndex.equals(WebAdminActions.loginActions().getSystemDate()),
                new CustomStepResult("'Due Date' is valid", "'Due Date' is not valid"));

        String paidAmountDue = Pages.accountPaymentInfoPage().getAmountDueFromRecordByIndex(3);
        TestRailAssert.assertTrue(Double.parseDouble(paidAmountDue) == feeAmount / 10,
                new CustomStepResult("'Amount Due' is valid", "'Amount Due' is not valid"));

        String paidStatus = Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(3);
        TestRailAssert.assertTrue(paidStatus.equals("Active"),
                new CustomStepResult("'Status' is valid", "'Status' is not valid"));

    }

}
