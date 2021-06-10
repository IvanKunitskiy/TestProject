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
import com.nymbus.pages.webadmin.WebAdminPages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Dmytro")
public class C25352_TeaserRateProcessingSetupTeaserRateChangeTypeCalculatedRateTest extends BaseTest {

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
        loanAccount.setCycleLoan(true);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        loanAccount.setCurrentEffectiveRateIsTeaser(true);
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

    @TestRailIssue(issueID = 25352, testRunName = TEST_RUN_NAME)
    @Test(description = "C25352, Teaser Rate Processing - Setup (Teaser Rate Change Type = Calculated Rate)")
    @Severity(SeverityLevel.CRITICAL)
    public void teaserRateProcessing() {
        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open Loan account from preconditions");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());

        logInfo("Step 3: Go to the 'Maintenance' screen");
        Pages.accountDetailsPage().clickMaintenanceTab();

        logInfo("Step 4: Click to the 'Tools' dropdown and select the 'Teaser Rate Setup' widget after that " +
                "click to the 'Launch' button");
        AccountActions.accountMaintenanceActions().setTool(Tool.TEASER_RATE_SETUP);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Step 5: Check the list of required and optional fields by default");
        Actions.loansActions().checkTeaserFields();
        TestRailAssert.assertTrue(Pages.teaserModalPage().getCountRequiredStars() == 3,
                new CustomStepResult("Required fields is equals", "Required fields is not equals"));

        logInfo("Step 6: Select 'Calculated Rate' in the 'Teaser Rate Change Type' drop-down");
        Pages.teaserModalPage().clickChangeRateArrow();
        Pages.teaserModalPage().clickCalculatedRateOption();

        logInfo("Step 7: Check the fields in the 'Teaser Rate Setup' widget after selecting 'Teaser Rate Change Type'");
        TestRailAssert.assertTrue(Pages.teaserModalPage().getCountRequiredStars() == 5,
                new CustomStepResult("Required fields is equals", "Required fields is not equals"));
        Pages.teaserModalPage().noteRateIsDisabled();

        logInfo("Step 8: Fill the following required fields:\n" +
                "- Effective Date = in the past but not later than 'Date Opened' of the loan account\n" +
                "- Teaser Rate Expire Date = current date or in the future\n" +
                "- Note Rate = Greater or Less than 'Current effective rate' on loan account\n" +
                "- Rate Change Lead Days => 0 (e.g. 1)");
        String effectiveDate = DateTime.getDateMinusDays(loanAccount.getDateOpened(), -1);
        Pages.teaserModalPage().inputEffectiveDate(effectiveDate);
        String expirationDate = DateTime.getDatePlusMonth(loanAccount.getDateOpened(), 1);
        Pages.teaserModalPage().inputExpirationDate(expirationDate);
        String rateChangeLeadDays = "1";
        Pages.teaserModalPage().inputRateChangeLeadDays(rateChangeLeadDays);
        Pages.teaserModalPage().clickRateIndexArrow();
        Pages.teaserModalPage().clickRateIndexOption();

        logInfo("Step 9: Click to the 'Done' button on 'Teaser Rate Setup' pop-up");
        Pages.teaserModalPage().clickDoneButton();
        String rateIndexValue = Pages.teaserModalPage().getRateIndexValue();

        logInfo("Step 10: Log in to the webadmin -> RulesUI Query Analyzer");
        logInfo("Step 11: Search with DQL:\n" +
                "\n" +
                "count: 10\n" +
                "from: bank.data.actloan.teaser\n" +
                "where:\n" +
                "- .accountid->accountnumber: accountnumberfrom_preconditions_\n" +
                "orderBy: -id\n" +
                "deletedIncluded: true\n" +
                "\n" +
                "Check that all value of fields correspond to the values \u200B\u200Badded by the user");
        WebAdminActions.webAdminUsersActions().openTeaserUrlByCLientId(clientRootId, userCredentials);

        int index = 2;
        String effectiveDateFromTeaser = WebAdminPages.rulesUIQueryAnalyzerPage().getEffectiveDateFromTeaser(index);
        effectiveDateFromTeaser = DateTime.getDateWithFormat(effectiveDateFromTeaser, "yyyy-MM-dd", "MM/dd/yyyy");
        TestRailAssert.assertTrue(effectiveDateFromTeaser.equals(effectiveDate),
                new CustomStepResult("Effective Date is equals", "Effective Date is not equals"));
        String expirationDateFromTeaser = WebAdminPages.rulesUIQueryAnalyzerPage().getExpirationDateFromTeaser(index);
        expirationDateFromTeaser = DateTime.getDateWithFormat(expirationDateFromTeaser, "yyyy-MM-dd", "MM/dd/yyyy");
        TestRailAssert.assertTrue(expirationDateFromTeaser.equals(expirationDate),
                new CustomStepResult("Expiration Date is equals", "Expiration Date is not equals"));
        String noteRateFromTeaser = WebAdminPages.rulesUIQueryAnalyzerPage().getRateIndexFromTeaser(index);
        TestRailAssert.assertTrue(noteRateFromTeaser.equals(rateIndexValue),
                new CustomStepResult("Note Rate is equals", "Note Rate is not equals"));
        String rateChangeLeadDaysFromTeaser = WebAdminPages.rulesUIQueryAnalyzerPage().getRateChangeLeadDaysFromTeaser(index);
        TestRailAssert.assertTrue(rateChangeLeadDaysFromTeaser.equals(rateChangeLeadDays),
                new CustomStepResult("Rate Change Lead Days is equals", "Rate Change Lead Days is not equals"));
        String rateChangeTypeFromTeaser = WebAdminPages.rulesUIQueryAnalyzerPage().getRateChangeTypeFromTeaser(1);
        TestRailAssert.assertTrue(rateChangeTypeFromTeaser.equals("Calculated Rate"),
                new CustomStepResult("Rate Change Type is equals", "Rate Change Type is not equals"));

    }



}