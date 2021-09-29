package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
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
public class C19579_LoanSkipPaymentPostSkipPaymentWithMaturityExtensionTest extends BaseTest {

    private Account loanAccount;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();

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

    @TestRailIssue(issueID = 19579, testRunName = TEST_RUN_NAME)
    @Test(description = "C19579, Loan Skip Payment: Post Skip Payment with Maturity Extension")
    @Severity(SeverityLevel.CRITICAL)
    public void loanSkipPaymentPostSkipPaymentWithMaturityExtensionTest() {
        logInfo("Step 1: Log into the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open loan account from preconditions on \"Maintenance\" page");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        Pages.accountDetailsPage().clickMaintenanceTab();

        logInfo("Step 3: Launch \"Loan Skip Payment\" tool");
        AccountActions.accountMaintenanceActions().setTool(Tool.LOAN_SKIP_PAYMENT);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Step 4: Specify fields:\n" +
                "\"NBR of Payments to Skip\" (should be in range from 1 to 6)\n" +
                "Fee Amount = blank\n" +
                "Fee Add-on Payment = NO\n" +
                "Extend Maturity = YES");
        int nmbrToSkip = 3;
        Pages.loanSkipPaymentModalPage().typeNbrOfPaymentsToSkipInput(String.valueOf(nmbrToSkip));
        Actions.loanSkipPaymentModalActions().setFeeAddOnPaymentToggleToNo();
        Actions.loanSkipPaymentModalActions().setExtendMaturityToggleToYes();
        String currentDueDate = Pages.loanSkipPaymentModalPage().getCurrentDueDate();
        String maturityDate = Pages.loanSkipPaymentModalPage().getMaturityDate();


        logInfo("Step 5: Click the [Commit Transaction] button");
        Pages.loanSkipPaymentModalPage().clickCommitTransactionButton();

        logInfo("Step 6: Launch \"Loan Skip Payment\" tool again");
        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        Pages.accountDetailsPage().clickMaintenanceTab();
        AccountActions.accountMaintenanceActions().setTool(Tool.LOAN_SKIP_PAYMENT);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Step 7: Verify the following fields:\n" +
                "\"Current Due Date\"\n" +
                "\"Maturity Date\"\n" +
                "\"NBR Skips This Year\"");
        String actualCurrentDueDate = Pages.loanSkipPaymentModalPage().getCurrentDueDate();
        TestRailAssert.assertTrue(actualCurrentDueDate.equals(DateTime.getDatePlusMonth(currentDueDate, nmbrToSkip)),
                new CustomStepResult("'Current Due Date' is  valid", "'Current Due Date' is not valid"));

        int actualNumberSkipsThisYear = Integer.parseInt(Pages.loanSkipPaymentModalPage().getNbrSkipsThisYear());
        TestRailAssert.assertTrue(actualNumberSkipsThisYear == nmbrToSkip,
                new CustomStepResult("'NBR Skips This Year' is  valid", "'NBR Skips This Year' is not valid"));

        String actualMaturityDate = Pages.loanSkipPaymentModalPage().getMaturityDate();
        TestRailAssert.assertTrue(actualMaturityDate.equals(DateTime.getDatePlusMonth(maturityDate, nmbrToSkip)),
                new CustomStepResult("'Maturity Date' is  valid", "'Maturity Date' is not valid"));

    }
}
