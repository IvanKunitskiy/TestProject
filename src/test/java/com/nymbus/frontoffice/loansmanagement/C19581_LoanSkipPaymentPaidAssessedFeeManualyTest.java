package com.nymbus.frontoffice.loansmanagement;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.LoginActions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.tansactions.factory.SourceFactory;
import com.nymbus.newmodels.maintenance.Tool;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import com.nymbus.pages.webadmin.WebAdminPages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.checkerframework.checker.units.qual.A;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.swing.*;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C19581_LoanSkipPaymentPaidAssessedFeeManualyTest extends BaseTest {

    private Account loanAccount;
    private Account checkingAccount;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private final double FEE_AMOUNT = 10.000;
    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up accounts
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        checkingAccount = new Account().setCHKAccountData();
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

        // "483-Skip Fee Assessed" transaction exists for this account (bank.data.actloan -> "skipfeeearned")
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        Pages.accountDetailsPage().clickMaintenanceTab();

        AccountActions.accountMaintenanceActions().setTool(Tool.LOAN_SKIP_PAYMENT);
        Pages.accountMaintenancePage().clickToolsLaunchButton();
        Pages.loanSkipPaymentModalPage().typeNbrOfPaymentsToSkipInput(String.valueOf(3));
        System.out.println(FEE_AMOUNT);
        Pages.loanSkipPaymentModalPage().typeFeeAmount(String.valueOf(FEE_AMOUNT));
        Selenide.sleep(10000);
        Actions.loanSkipPaymentModalActions().setExtendMaturityToggleToNo();
        Actions.loanSkipPaymentModalActions().setFeeAddOnPaymentToggleToNo();
        Pages.loanSkipPaymentModalPage().clickCommitTransactionButton();

        Actions.loginActions().doLogOut();

    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 19581, testRunName = TEST_RUN_NAME)
    @Test(description = "C19581, Loan Skip Payment: Paid Assessed Fee Manualy")
    @Severity(SeverityLevel.CRITICAL)
    public void loanSkipPaymentPaidAssessedFeeManualyTest() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open 'Teller' screen");
        Actions.transactionActions().goToTellerPage();
        Selenide.refresh();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Fill in the following fields and click the [Commit Transaction]");
        logInfo("Step 4: Close Transaction Receipt popup");
        int tempIndex = 1;

        Pages.tellerPage().clickGLDebitButton();
        Actions.transactionActions().fillSourceAccountNumber("0-0", tempIndex );
        Actions.transactionActions().fillSourceAccountCode(TransactionCode.GL_DEBIT.getTransCode(), tempIndex);
        Selenide.sleep(6000);
        Actions.transactionActions().fillSourceAmount(String.format("%.2f", 1.000), tempIndex);
        Pages.tellerPage().clickSourceDetailsArrow(tempIndex);
        Pages.tellerPage().typeSourceNotesValue(tempIndex, "Test");

        Pages.tellerPage().clickMiscCreditButton();
        Actions.transactionActions().fillDestinationAccountNumber(loanAccount.getAccountNumber(), tempIndex);
        Actions.transactionActions().fillDestinationAccountCode(TransactionCode.SKIP_FEE_PAYMENT_482.getTransCode(), tempIndex);
        Actions.transactionActions().fillDestinationAmount(String.format("%.2f", 1.000), tempIndex);

        Pages.tellerPage().clickCommitButton();
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();

        logInfo("Step 5: Open loan account from preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());

        logInfo("Step 6: Go to the 'Transactions' tab");
        Pages.accountDetailsPage().clickTransactionsTab();

        logInfo("Step 7: Verify generated transaction");
        // "482-Skip Fee Payment"
        String transactionCode482 = Pages.accountTransactionPage().getTransactionCodeByIndex(1);
        TestRailAssert.assertTrue(transactionCode482.equals(TransactionCode.SKIP_FEE_PAYMENT_482.getTransCode()),
                new CustomStepResult("'Transaction code' is  valid", "'Transaction code' is not valid"));
        double transactionAmount482 = AccountActions.retrievingAccountData().getAmountValue(1);
        TestRailAssert.assertTrue(transactionAmount482 == FEE_AMOUNT / 10,
                new CustomStepResult("'Amount' is  valid", "'Amount' is not valid"));

        logInfo("Step 8: Go to the 'Payment Info' tab and check 'Loan Skip Payment Charges' Payment Due record");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        // "Loan Skip Payment Charges" Payment Due record
        String paidAmountDue = Pages.accountPaymentInfoPage().getAmountDueFromRecordByIndex(3);
        TestRailAssert.assertTrue(Double.parseDouble(paidAmountDue) == 0.00,
                new CustomStepResult("'Amount Due' is valid", "'Amount Due' is not valid"));

        String paidStatus = Pages.accountPaymentInfoPage().getStatusFromRecordByIndex(3);
        TestRailAssert.assertTrue(paidStatus.equals("Paid"),
                new CustomStepResult("'Status' is valid", "'Status' is not valid"));

        logInfo("Step 9: Log in to the webadmin -> RulesUI Query Analyzer");
        logInfo("Step 10: Search with DQL:\n" +
                "from: bank.data.actloan\n" +
                "select: (databean)CREATEDBY, (databean)CREATEDWHEN, accountid, skipfeeearned, skipfeepaid\n" +
                "where:\n" +
                "- .accountid->accountnumber: accountnumberfrom_preconditions_\n" +
                "orderBy: -id\n" +
                "deletedIncluded: true\n" +
                "\n" +
                "and verify the amount of paid fee (bank.data.actloan -> \"skipfeepaid\")");
        String skipFeePayment = WebAdminActions.webAdminUsersActions().getAccountsWithFeeSkipPayment(loanAccount.getAccountNumber());
        TestRailAssert.assertTrue(Double.parseDouble(skipFeePayment) == 1.0,
                new CustomStepResult("'skipfeepaid' is valid", "'skipfeepaid' is not valid"));

    }
}
