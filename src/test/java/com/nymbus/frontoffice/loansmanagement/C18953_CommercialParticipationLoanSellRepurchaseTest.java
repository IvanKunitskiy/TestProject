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
    private IndividualClient client;
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private double escrowPaymentValue;
    private String clientRootId;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

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
        System.out.println(SettingsPage.bankControlLoansPage().getCommercialParticipation());
        TestRailAssert.assertTrue(SettingsPage.bankControlLoansPage().getCommercialParticipation().equals("on"),
                new CustomStepResult("'Commercial Participation' is not valid", "'Commercial Participation' is valid"));

        // Participant exists in the system
        Pages.aSideMenuPage().clickLoansMenuItem();
        Pages.loansPage().waitForLoanPageLoaded();
        Pages.loansPage().clickViewAllLoanParticipants();
        System.out.println(Pages.loanParticipantsPage().getLoanParticipantNameByIndex(1));
        TestRailAssert.assertTrue(Pages.loanParticipantsPage().getLoanParticipantCount() > 0,
                new CustomStepResult("'Loan Participant' list is empty", "'Loan Participant' exist in the system"));
        loanParticipant = Pages.loanParticipantsPage().getLoanParticipantNameByIndex(1);
        Pages.aSideMenuPage().clickClientMenuItem();

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);
        Actions.loginActions().doLogOut();

        // Get escrow payment value for the loan product
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        escrowPaymentValue = Actions.loanProductOverviewActions().getLoanProductEscrowPaymentValue(loanProductName);

        // Set the product
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
        clientRootId = ClientsActions.createClient().getClientIdFromUrl();

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
                "- Percentage Sold = f.e. 20%\n" +
                "- Sold Date = in the past (f.e. Date Opened of Loan account)\n" +
                "and click 'Save Changes'");
        Actions.participationsActions().setParticipant(loanParticipant);
        Pages.participationsModalPage().setPercentageSold("20");
        Pages.participationsModalPage().setSoldDate(loanAccount.getDateOpened());

        logInfo("Step 5: Select Participant in left part of the screen and click 'Sell' button");
        Pages.participationsModalPage().clickParticipantRowByIndex(1);
        Pages.participationsModalPage().clickSellButton();
        String alertMessageModalText = Pages.alertMessageModalPage().getAlertMessageModalText();
        TestRailAssert.assertTrue(alertMessageModalText.equals("The Sell will post transactions that can be viewed on Transaction History"),
                new CustomStepResult("'Modal text' is not valid", "'Modal text' is valid"));
        Pages.alertMessageModalPage().clickOkButton();
        TestRailAssert.assertTrue(Pages.participationsModalPage().getParticipantStatusByIndex(1).equals("Sold"),
                new CustomStepResult("'Status' is not valid", "'Status' is valid"));

        logInfo("Step 6: Close 'Participations' tool");
        Pages.participationsModalPage().clickCloseButton();

        logInfo("Step 7: Go to the 'Transactions' tab and verify generated transaction");
        Pages.accountDetailsPage().clickTransactionsTab();
        Pages.transactionsPage().isTransactionWithDescriptionVisibleInList(TransactionCode.PARTICIPATION_SELL_471.getTransCode());

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

        String interestearned = WebAdminActions.webAdminUsersActions().getParticipantInterestearnedValueByIndex(loanAccount.getAccountNumber(), 1);

        WebAdminActions.loginActions().doLogoutProgrammatically();
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);

        // TODO: Check bank.data.actmst.participant -> interestearned value

        logInfo("Step 10: Go back to loan account from preconditions -> Maintenance -> Tools -> 'Loan Participations' tool");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        Pages.accountDetailsPage().clickMaintenanceTab();
        AccountActions.accountMaintenanceActions().setTool(Tool.LOAN_PARTICIPATIONS);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Step 11: Select Participant record in the left part of the screen and click the 'Repurchase' button");
    }
}
