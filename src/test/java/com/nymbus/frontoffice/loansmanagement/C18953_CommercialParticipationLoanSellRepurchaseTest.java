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
import com.nymbus.pages.settings.SettingsPage;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C18953_CommercialParticipationLoanSellRepurchaseTest extends BaseTest {

    private String loanParticipant;
    private Account loanAccount;
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up loan account
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());

        // Set up CHK account
        Account checkingAccount = new Account().setCHKAccountData();
        checkingAccount.setDateOpened(DateTime.getDateMinusMonth(loanAccount.getDateOpened(), 1));

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

        // Make sure that Settings - > Bank Control Settings -> Loans -> "Commercial Participation - Lite" = Yes
        Pages.aSideMenuPage().waitForASideMenu();
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().clickViewSettingsLink();
        SettingsPage.bankControlPage().clickLoansTab();
        TestRailAssert.assertTrue(SettingsPage.bankControlLoansPage().getCommercialParticipation().equals("on"),
                new CustomStepResult("'Commercial Participation' is not valid", "'Commercial Participation' is valid"));

        // Participant exists in the system
        Pages.aSideMenuPage().clickLoansMenuItem();
        Pages.loansPage().waitForLoanPageLoaded();
        Pages.loansPage().clickViewAllLoanParticipants();
        TestRailAssert.assertTrue(Pages.loanParticipantsPage().getLoanParticipantCount() > 0,
                new CustomStepResult("'Loan Participant' list is empty", "'Loan Participant' exist in the system"));
        loanParticipant = Pages.loanParticipantsPage().getLoanParticipantNameByIndex(1);
        Pages.aSideMenuPage().clickClientMenuItem();

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

        // Perform transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().setMiscDebitSource(miscDebitSource, 0);
        Actions.transactionActions().setMiscCreditDestination(miscCreditDestination, 0);
        Pages.tellerPage().setEffectiveDate(loanAccount.getDateOpened());
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 18953, testRunName = TEST_RUN_NAME)
    @Test(description="C18953, Commercial Participation Loan - Sell Repurchase")
    @Severity(SeverityLevel.CRITICAL)
    public void commercialParticipationLoanSellRepurchase() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open loan account from preconditions -> Maintenance -> Tools -> Loan Participations");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        Pages.accountDetailsPage().clickMaintenanceTab();
        AccountActions.accountMaintenanceActions().setTool(Tool.LOAN_PARTICIPATIONS);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Steep 3: Click Add New Participation");
        Pages.participationsModalPage().clickAddNewParticipationsButton();

        logInfo("Step 4: Specify:\n" +
                "- Participant = any Participant from the drop-down box\n" +
                "- Percentage Sold = f.e. 20\n" +
                "- Sold Date = in the past (f.e. Date Opened of Loan account)\n" +
                "and click 'Save Changes'");
        Actions.participationsActions().setParticipant(loanParticipant);
        Pages.participationsModalPage().setPercentageSold("20");
        Pages.participationsModalPage().setSoldDate(loanAccount.getDateOpened());
        Pages.participationsModalPage().clickSaveButton();

        logInfo("Step 5: Select Participant in left part of the screen and click 'Sell' button");
        Pages.participationsModalPage().clickParticipantRowByIndex(1);
        Pages.participationsModalPage().clickSellButton();
        String alertMessageModalText = Pages.alertMessageModalPage().getAlertMessageModalText();
        TestRailAssert.assertTrue(alertMessageModalText.equals("The Sell will post transactions that can be viewed on Transaction History"),
                new CustomStepResult("'Modal text' is not valid", "'Modal text' is valid"));
        Pages.alertMessageModalPage().clickOkButton();
        Pages.participationsModalPage().waitForSoldStatusVisibleByIndex(1);
        TestRailAssert.assertTrue(Pages.participationsModalPage().getParticipantStatusByIndex(1).equals("Sold"),
                new CustomStepResult("'Status' is not valid", "'Status' is valid"));

        logInfo("Step 6: Close 'Participations' tool");
        double participantBalance = Double.parseDouble(Pages.participationsModalPage().getParticipantBalance());
        Pages.participationsModalPage().clickCloseButton();

        // Get data
        Pages.accountDetailsPage().clickDetailsTab();
        double currentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalanceFromHeaderMenu());
        double participationPercentSold = Integer.parseInt(Pages.accountDetailsPage().getParticipationPercentSold()) / (double) 100;
        double currentEffectiveRate = Double.parseDouble(Pages.accountDetailsPage().getCurrentEffectiveRate());
        String yearBase = Pages.accountDetailsPage().getDaysBaseYearBase().split("/")[1].replaceAll("[^0-9]", "");

        logInfo("Step 7: Go to the 'Transactions' tab and verify generated transaction");
        Pages.accountDetailsPage().clickTransactionsTab();
        String effectiveDateValue = Pages.accountTransactionPage().getEffectiveDateValue(1, 1);
        String postingDateValue = Pages.accountTransactionPage().getPostingDateValue(1);
        String transactionCode = Pages.accountTransactionPage().getTransactionCodeByIndex(1);
        double amountValue = AccountActions.retrievingAccountData().getAmountValue(1);
        TestRailAssert.assertTrue(transactionCode.equals(TransactionCode.PARTICIPATION_SELL_471.getTransCode()),
                new CustomStepResult("'Transaction code' is not valid", "'Transaction code' is valid"));
        TestRailAssert.assertTrue(effectiveDateValue.equals(loanAccount.getDateOpened()),
                new CustomStepResult("'Effective date' is not valid", "'Effective date' is valid"));
        TestRailAssert.assertTrue(amountValue == currentBalance * participationPercentSold,
                new CustomStepResult("'Amount' is not valid", "'Amount' is valid"));

        logInfo("Step 8: Log in to Webadmin -> RulesUI Query Analyzer and search with DQL:\n" +
                "count: 10\n" +
                "from: bank.data.actmst.participant\n" +
                "where:\n" +
                "- .accountid->accountnumber: loan account number from preconditions\n" +
                "orderBy: -id\n" +
                "deletedIncluded: true");
        logInfo("Step 9: Check bank.data.actmst.participant -> interestearned value");
        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL);
        SelenideTools.switchTo().window(1);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        int days = DateTime.getDaysBetweenTwoDates(effectiveDateValue, postingDateValue, false);
        String interestearned = WebAdminActions.webAdminUsersActions().getParticipantInterestearnedValueByIndex(loanAccount.getAccountNumber(), 1);
        double actualInterestEarned = participantBalance * (currentEffectiveRate / 100) / Double.parseDouble(yearBase) * days;
        double interestEarnedRounded = Functions.roundToTwoDecimalPlaces(Double.parseDouble(interestearned));
        double actualInterestEarnedRounded = Functions.roundToTwoDecimalPlaces(actualInterestEarned);
        TestRailAssert.assertTrue(interestEarnedRounded == actualInterestEarnedRounded,
                new CustomStepResult("'interestearned' is valid", String.format("'interestearned' is not valid. " +
                        "Expected %s, actual: %s", interestEarnedRounded, actualInterestEarned)));

        WebAdminActions.loginActions().doLogoutProgrammatically();
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);

        logInfo("Step 10: Go back to loan account from preconditions -> Maintenance -> Tools -> 'Loan Participations' tool");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        Pages.accountDetailsPage().clickMaintenanceTab();
        AccountActions.accountMaintenanceActions().setTool(Tool.LOAN_PARTICIPATIONS);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Step 11: Select Participant record in the left part of the screen and click the 'Repurchase' button");
        Pages.participationsModalPage().clickParticipantRowByIndex(1);
        Pages.participationsModalPage().clickRepurchaseButton();
        Pages.alertMessageModalPage().clickOkButton();
        Pages.participationsModalPage().waitForRepurchaseStatusVisibleByIndex(1);

        logInfo("Step 12: Close 'Participations' tool");
        Pages.participationsModalPage().clickCloseButton();

        logInfo("Step 13: Go to the 'Transactions' tab and verify generated transaction");
        Pages.accountDetailsPage().clickTransactionsTab();
        TestRailAssert.assertTrue(Pages.accountTransactionPage().getTransactionCodeByIndex(1).equals(TransactionCode.PARTICIPATION_REPURCHASE_472.getTransCode()),
                new CustomStepResult("'Transaction code' is not valid", "'Transaction code' is valid"));
        TestRailAssert.assertTrue(Pages.accountTransactionPage().getEffectiveDateValue(1).equals(DateTime.getLocalDateOfPattern("MM/dd/yyyy")),
                new CustomStepResult("'Effective date' is not valid", "'Effective date' is valid"));
        TestRailAssert.assertTrue(AccountActions.retrievingAccountData().getAmountValue(1) == participantBalance,
                new CustomStepResult("'Amount' is not valid", "'Amount' is valid"));

        logInfo("Step 14: Log in to Webadmin -> RulesUI Query Analyzer and search with DQL:\n" +
                "count: 10\n" +
                "from: bank.data.actmst.participant\n" +
                "where:\n" +
                "- .accountid->accountnumber: loan account number from preconditions\n" +
                "orderBy: -id\n" +
                "deletedIncluded: true");
        logInfo("Step 15: Check bank.data.actmst.participant -> interestearned and participantbalance values");
        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL);
        SelenideTools.switchTo().window(1);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        interestearned = WebAdminActions.webAdminUsersActions().getParticipantInterestearnedValueByIndex(loanAccount.getAccountNumber(), 1);
        String participantbalance = WebAdminActions.webAdminUsersActions().getParticipantBalanceValueByIndex(loanAccount.getAccountNumber(), 1);

        TestRailAssert.assertTrue(Double.parseDouble(interestearned) == 0.00,
                new CustomStepResult("'interestearned' is not valid", "'interestearned' is valid"));
        TestRailAssert.assertTrue(Double.parseDouble(participantbalance) == 0.00,
                new CustomStepResult("'interestearned' is not valid", "'interestearned' is valid"));

        WebAdminActions.loginActions().doLogoutProgrammatically();
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);
    }
}
