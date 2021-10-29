package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.transactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.transactions.factory.SourceFactory;
import com.nymbus.newmodels.maintenance.Tool;
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
@Owner("Petro")
public class C19578_LoanSkipPaymentPostSkipPaymentWithAddOnFeeTest extends BaseTest {

    private Account loanAccount;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up accounts
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        Account checkingAccount = new Account().setCHKAccountData();
        checkingAccount.setDateOpened(DateTime.getDateMinusMonth(loanAccount.getDateOpened(), 1));

        // Set up deposit transaction
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
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create accounts
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);

        // Perform deposit transaction
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

    @TestRailIssue(issueID = 19578, testRunName = TEST_RUN_NAME)
    @Test(description="C19578, Loan Skip Payment: Post Skip Payment with Add-on Fee")
    @Severity(SeverityLevel.CRITICAL)
    public void loanSkipPaymentPostSkipPaymentWithAddOnFee() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open account from preconditions on the 'Maintenance' page");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        double currentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalanceFromHeaderMenu());
        Pages.accountDetailsPage().clickMaintenanceTab();

        logInfo("Step 3: Loan Skip Payment");
        AccountActions.accountMaintenanceActions().setTool(Tool.LOAN_SKIP_PAYMENT);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Step 4: Specify fields:\n" +
                "'NBR of Payments to Skip' (should be in range from 1 to 6)\n" +
                "Fee Amount = any\n" +
                "Fee Add-on Payment = NO\n" +
                "Extend Maturity = NO");
        int nmbrToSkip = 3;
        Pages.loanSkipPaymentModalPage().typeNbrOfPaymentsToSkipInput(String.valueOf(nmbrToSkip));
        double feeAmount = 10.000;
        Pages.loanSkipPaymentModalPage().typeFeeAmount(String.valueOf(feeAmount));
        Actions.loanSkipPaymentModalActions().setExtendMaturityToggleToNo();
        Actions.loanSkipPaymentModalActions().setFeeAddOnPaymentToggleToYes();
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

        logInfo("Step 8: Go to the 'Details' tab");
        Pages.loanSkipPaymentModalPage().clickCancelButton();
        Pages.accountDetailsPage().clickDetailsTab();

        logInfo("Step 9: Verify 'Next Payment Billed Due Date' field and 'Current Balance'");
        String nextPaymentBilledDueDate = Pages.accountDetailsPage().getNextPaymentBilledDueDate();
        TestRailAssert.assertTrue(nextPaymentBilledDueDate.equals(DateTime.getDatePlusMonth(currentDueDate, nmbrToSkip)),
                new CustomStepResult("'Next Payment Billed Due Date' is  valid", "'Next Payment Billed Due Date' is not valid"));
        String actualCurrentBalance = Pages.accountDetailsPage().getCurrentBalanceFromHeaderMenu();
        TestRailAssert.assertTrue(Double.parseDouble(actualCurrentBalance) == currentBalance + feeAmount / 10,
                new CustomStepResult("'Fee amount' is  valid", "'Fee amount' is not valid"));

        logInfo("Step 10: Go to the 'Transactions' tab");
        Pages.accountDetailsPage().clickTransactionsTab();

        logInfo("Step 11: Verify generated transactions");
        // "482-Skip Fee Payment"
        String transactionCode482 = Pages.accountTransactionPage().getTransactionCodeByIndex(1);
        TestRailAssert.assertTrue(transactionCode482.equals(TransactionCode.SKIP_FEE_PAYMENT_482.getTransCode()),
                new CustomStepResult("'Transaction code' is  valid", "'Transaction code' is not valid"));
        double transactionAmount482 = AccountActions.retrievingAccountData().getAmountValue(1);
        TestRailAssert.assertTrue(transactionAmount482 == feeAmount / 10,
                new CustomStepResult("'Amount' is  valid", "'Amount' is not valid"));

        // "470-Skip Fee Add-On"
        String transactionCode470 = Pages.accountTransactionPage().getTransactionCodeByIndex(2);
        TestRailAssert.assertTrue(transactionCode470.equals(TransactionCode.SKIP_FEE_ADDON_470.getTransCode()),
                new CustomStepResult("'Transaction code' is  valid", "'Transaction code' is not valid"));
        double transactionAmount470 = AccountActions.retrievingAccountData().getAmountValue(2);
        TestRailAssert.assertTrue(transactionAmount470 == feeAmount / 10,
                new CustomStepResult("'Amount' is  valid", "'Amount' is not valid"));

        // "483-Skip Fee Assessed"
        String transactionCode483 = Pages.accountTransactionPage().getTransactionCodeByIndex(3);
        TestRailAssert.assertTrue(transactionCode483.equals(TransactionCode.SKIP_FEE_ASSESSED_483.getTransCode()),
                new CustomStepResult("'Transaction code' is  valid", "'Transaction code' is not valid"));
        double transactionAmount483 = AccountActions.retrievingAccountData().getAmountValue(3);
        TestRailAssert.assertTrue(transactionAmount483 == feeAmount / 10,
                new CustomStepResult("'Amount' is  valid", "'Amount' is not valid"));

        logInfo("Step 12: Go to the 'Payment Info' tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 13: Verify generated Payment Due records");
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
        TestRailAssert.assertTrue(Double.parseDouble(paidAmountDue) == 0.00,
                new CustomStepResult("'Amount Due' is valid", String.format("'Amount Due' is not valid." +
                        " Expected: %s, actual: %s",paidAmountDue, 0.00)));

        String paidStatus = Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(3);
        TestRailAssert.assertTrue(paidStatus.equals("Paid"),
                new CustomStepResult("'Status' is valid", "'Status' is not valid"));
    }
}
