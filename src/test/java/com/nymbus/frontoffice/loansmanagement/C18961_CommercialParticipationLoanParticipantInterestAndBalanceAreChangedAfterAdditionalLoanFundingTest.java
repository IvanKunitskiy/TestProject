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
public class C18961_CommercialParticipationLoanParticipantInterestAndBalanceAreChangedAfterAdditionalLoanFundingTest extends BaseTest {

    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private String loanParticipant;
    private Account loanAccount;
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private final TransactionSource transaction410MiscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination transaction410MiscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private int transaction410Amount;

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
        loanAccount.setPaymentAmountType(PaymentAmountType.PRIN_AND_INT.getPaymentAmountType());
        loanAccount.setPaymentBilledLeadDays("1");
        loanAccount.setCycleLoan(false);

        // Set up CHK account
        Account checkingAccount = new Account().setCHKAccountData();
        checkingAccount.setDateOpened(DateTime.getDateMinusMonth(loanAccount.getDateOpened(), 1));

        // Increase chk balance transaction
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        depositTransaction.getTransactionDestination().setAccountNumber(checkingAccount.getAccountNumber());
        int transactionAmount = 12000;
        depositTransaction.getTransactionDestination().setAmount(transactionAmount);
        depositTransaction.getTransactionSource().setAmount(transactionAmount);

        // Set up loan -> CHK transaction
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscDebitSource.setAmount(transactionAmount);
        miscCreditDestination.setAccountNumber(checkingAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(transactionAmount);

        // Set up 410 transaction
        transaction410Amount = 5000;
        transaction410MiscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        transaction410MiscDebitSource.setTransactionCode(TransactionCode.ADD_ON_410.getTransCode());
        transaction410MiscDebitSource.setAmount(transaction410Amount);
        transaction410MiscCreditDestination.setAccountNumber(checkingAccount.getAccountNumber());
        transaction410MiscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        transaction410MiscCreditDestination.setAmount(transaction410Amount);

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

        // Make sure that participant exist in the system
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

        // Perform transaction to increase balances
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

    @TestRailIssue(issueID = 18961, testRunName = TEST_RUN_NAME)
    @Test(description="C18961, Commercial Participation Loan: Participant interest and balance are changed after additional loan funding (410 - transaction)")
    @Severity(SeverityLevel.CRITICAL)
    public void commercialParticipationLoanParticipantInterestAndBalanceAreChangedAfterAdditionalLoanFunding() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open loan account from preconditions");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);

        logInfo("Step 3: Go to Maintenance -> Tools -> Loan Participations");
        Pages.accountDetailsPage().clickMaintenanceTab();
        AccountActions.accountMaintenanceActions().setTool(Tool.LOAN_PARTICIPATIONS);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Step 4: Click Add New Participation");
        Pages.participationsModalPage().clickAddNewParticipationsButton();

        logInfo("Step 5: Specify:\n" +
                "- Participant = any Participant from the drop-down box\n" +
                "- Percentage Sold = f.e. 20\n" +
                "- Sold Date = after 'Date Opened' and before 'Next Payment Billed Lead Days' (f.e. 03/15/2021)\n" +
                "and click 'Save Changes'");
        Actions.participationsActions().setParticipant(loanParticipant);
        Pages.participationsModalPage().setPercentageSold("20");
        Pages.participationsModalPage().setSoldDate(loanAccount.getDateOpened());
        Pages.participationsModalPage().clickSaveButton();

        logInfo("Step 6: Select Participant in left part of the screen and click 'Sell' button");
        Pages.participationsModalPage().clickParticipantRowByIndex(1);
        Pages.participationsModalPage().clickSellButton();
        String alertMessageModalText = Pages.alertMessageModalPage().getAlertMessageModalText();
        TestRailAssert.assertTrue(alertMessageModalText.equalsIgnoreCase("The Sell will post transactions that can be viewed on Transaction History"),
                new CustomStepResult("'Modal text' is not valid", "'Modal text' is valid"));
        Pages.alertMessageModalPage().clickOkButton();
        Pages.participationsModalPage().waitForSoldStatusVisibleByIndex(1);
        TestRailAssert.assertTrue(Pages.participationsModalPage().getParticipantStatusByIndex(1).equals("Sold"),
                new CustomStepResult("'Status' is not valid", "'Status' is valid"));
        Pages.participationsModalPage().clickCloseButton();

        // Get data
        Pages.accountDetailsPage().clickDetailsTab();
        double currentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalanceFromHeaderMenu());
        double participationPercentSold = Integer.parseInt(Pages.accountDetailsPage().getParticipationPercentSold()) / (double) 100;
        double currentEffectiveRate = Double.parseDouble(Pages.accountDetailsPage().getCurrentEffectiveRate());
        String yearBase = Pages.accountDetailsPage().getDaysBaseYearBase().split("/")[1].replaceAll("[^0-9]", "");

        logInfo("Step 7: Go to the 'Transactions' tab and verify generated transaction");
        Pages.accountDetailsPage().clickTransactionsTab();
        String effectiveDate471Value = Pages.accountTransactionPage().getEffectiveDateValue(1, 1);
        String transactionCode = Pages.accountTransactionPage().getTransactionCodeByIndex(1);
        double transaction471Amount = AccountActions.retrievingAccountData().getAmountValue(1);
        TestRailAssert.assertTrue(transactionCode.equals(TransactionCode.PARTICIPATION_SELL_471.getTransCode()),
                new CustomStepResult("'Transaction code' is not valid", "'Transaction code' is valid"));
        TestRailAssert.assertTrue(effectiveDate471Value.equals(loanAccount.getDateOpened()),
                new CustomStepResult("'Effective date' is not valid", "'Effective date' is valid"));
        TestRailAssert.assertTrue(transaction471Amount == currentBalance * participationPercentSold,
                new CustomStepResult("'Amount' is not valid", "'Amount' is valid"));

        logInfo("Step 8: Go to 'Teller' screen");
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 9: Log in to the proof date");
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 10: Fill in the following fields and click the [Commit Transaction] :\n" +
                "Sources -> Misc Debit:\n" +
                "'Account Number' - Loan zccount from precondition\n" +
                "'Transaction Code' - '410 - Add-On'\n" +
                "Amount - any valid (e.g. $ 5000.00)\n" +
                "Destinations -> Misc Credit:\n" +
                "Account number - active CHK or SAV account from precondition\n" +
                "'Transaction Code' - specify trancode ('109-Deposit' for CHK or '209-Deposit for SAV)\n" +
                "'Amount' - specify the same amount (e.g. $ 5000.00)\n" +
                "Effective Date = after 'Date Opened' and before 'Next Payment Billed Lead Days' (e.g. 03/25/2021)");
        Actions.transactionActions().setMiscDebitSource(transaction410MiscDebitSource, 0);
        Actions.transactionActions().setMiscCreditDestination(transaction410MiscCreditDestination, 0);
        Pages.tellerPage().setEffectiveDate(loanAccount.getDateOpened());
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();

        logInfo("Step 11: Close Transaction Receipt popup");
        Pages.tellerPage().closeModal();

        logInfo("Step 12: Open loan account from preconditions");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);

        logInfo("Step 13: Go to the 'Transactions' tab and check generated transactions");
        Pages.accountDetailsPage().clickTransactionsTab();

        TestRailAssert.assertTrue(Pages.accountTransactionPage().getTransactionCodeByIndex(1).equals(TransactionCode.ADD_ON_410.getTransCode()),
                new CustomStepResult("'Transaction code' is not valid", "'Transaction code' is valid"));
        TestRailAssert.assertTrue(Pages.accountTransactionPage().getEffectiveDateValue(1, 1).equals(loanAccount.getDateOpened()),
                new CustomStepResult("'Effective date' is not valid", "'Effective date' is valid"));
        TestRailAssert.assertTrue(AccountActions.retrievingAccountData().getAmountValue(1) == (double) transaction410Amount,
                new CustomStepResult("'Amount' is not valid", "'Amount' is valid"));

        double transaction475Amount = AccountActions.retrievingAccountData().getAmountValue(2);
        String postingDate475Value = Pages.accountTransactionPage().getPostingDateValue(2);
        String effectiveDate475Value = Pages.accountTransactionPage().getEffectiveDateValue(2, 1);
        TestRailAssert.assertTrue(Pages.accountTransactionPage().getTransactionCodeByIndex(2).equals(TransactionCode.ADDITIONAL_SELL_475.getTransCode()),
                new CustomStepResult("'Transaction code' is not valid", "'Transaction code' is valid"));
        TestRailAssert.assertTrue(effectiveDate475Value.equals(loanAccount.getDateOpened()),
                new CustomStepResult("'Effective date' is not valid", "'Effective date' is valid"));
        TestRailAssert.assertTrue(transaction475Amount == transaction410Amount * participationPercentSold,
                new CustomStepResult("'Amount' is not valid", "'Amount' is valid"));

        logInfo("Step 14: Log in to the webadmin -> RulesUI Query Analyzer");
        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL);
        SelenideTools.switchTo().window(1);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 15: Search with DQL::\n" +
                "count: 10\n" +
                "from: bank.data.actmst.participant\n" +
                "where:\n" +
                "- .accountid->accountnumber: accountnumberfrom_preconditions_\n" +
                "orderBy: -id\n" +
                "deletedIncluded: true");
        String interestearned = WebAdminActions.webAdminUsersActions().getParticipantInterestearnedValueByIndex(loanAccount.getAccountNumber(), 1);
        String previousInterestearned = WebAdminActions.webAdminUsersActions().getParticipantInterestearnedValueByIndex(loanAccount.getAccountNumber(), 2);
        String participantbalance = WebAdminActions.webAdminUsersActions().getParticipantBalanceValueByIndex(loanAccount.getAccountNumber(), 1);

        logInfo("Step 16: Check bank.data.actmst.participant -> interestearned and participantbalance values");
        double effectiveRate = currentEffectiveRate / 100;
        double yearBaseValue = Double.parseDouble(yearBase);
        int days = DateTime.getDaysBetweenTwoDates(effectiveDate475Value, postingDate475Value, false);
        double expectedInterestEarned = (transaction475Amount * effectiveRate / yearBaseValue * days) + Double.parseDouble(previousInterestearned);

        TestRailAssert.assertTrue(Double.parseDouble(participantbalance) == transaction471Amount + transaction475Amount,
                new CustomStepResult("'participantbalance' is not valid", "'participantbalance' is valid"));
        TestRailAssert.assertTrue(Functions.getDoubleWithFormatAndFloorRounding(Double.parseDouble(interestearned), "##.##") ==
                        Functions.getDoubleWithFormatAndFloorRounding(expectedInterestEarned, "##.##"),
                new CustomStepResult("'interestearned' is not valid", "'interestearned' is valid"));
    }
}
