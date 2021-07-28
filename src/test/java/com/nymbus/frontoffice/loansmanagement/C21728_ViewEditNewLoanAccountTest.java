package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.loans.DaysBaseYearBase;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.PaymentAmountType;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.tansactions.factory.SourceFactory;
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
public class C21728_ViewEditNewLoanAccountTest extends BaseTest {

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

        // Set up CHK account
        Account checkingAccount = new Account().setCHKAccountData();
        checkingAccount.setDateOpened(DateTime.getDateMinusMonth(DateTime.getLocalDateWithPattern("MM/dd/yyyy"), 1));
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        loanAccount.setPaymentAmountType(PaymentAmountType.PRINCIPAL_AND_INTEREST.getPaymentAmountType());
        loanAccount.setDaysBaseYearBase(DaysBaseYearBase.DAY_YEAR_360_360.getDaysBaseYearBase());
        loanAccount.setAdjustableRate(false);



        // Set transaction data
        final int transactionAmount = 12000;
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

        // Create checking account and logout
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
        Actions.loginActions().doLogOutProgrammatically();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25323, testRunName = TEST_RUN_NAME)
    @Test(description="C25323, Loan - Create and Fund: Principal and Interest")
    @Severity(SeverityLevel.CRITICAL)
    public void viewEditNewLoanAccount() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open loan account from preconditions on 'Details' tab");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());

        logInfo("Step 3: Pay attention at the loan account fields");
        TestRailAssert.assertTrue(Pages.accountDetailsPage().isBalanceAndInterestVisible(),
                new CustomStepResult("'Balances and Interest' group is not visible",
                        "'Balances and Interest' group is visible"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().isAccountSettingsVisible(),
                new CustomStepResult("'Account Settings' group is not visible",
                        "'Account Settings' group is visible"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().isCreditLimitVisible(),
                new CustomStepResult("'Credit Limit' group is not visible",
                        "'Credit Limit' group is visible"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().isRatePaymentChangeVisible(),
                new CustomStepResult("'Rate/Payment Change' group is not visible",
                        "'Rate/Payment Change' group is visible"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().isLateChargeVisible(),
                new CustomStepResult("'Late Charge' group is not visible",
                        "'Late Charge' group is visible"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().isLoanCodesReportingVisible(),
                new CustomStepResult("'Loan Codes & Reporting' group is not visible",
                        "'Loan Codes & Reporting' group is visible"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().isUserDefinedFieldsVisible(),
                new CustomStepResult("'User Defined Fields' group is not visible",
                        "'User Defined Fields' group is visible"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().isAccountInfoStatisticsVisible(),
                new CustomStepResult("'Account Info/Statistics' group is not visible",
                        "'Account Info/Statistics' group is visible"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().isCollectionsVisible(),
                new CustomStepResult("'Collections' group is not visible",
                        "'Collections' group is visible"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().isOtherLoanSettingsVisible(),
                new CustomStepResult("'Other Loan Settings' group is not visible",
                        "'Other Loan Settings' group is visible"));

        logInfo("Step 4: Click the 'Edit' button");
        Pages.accountDetailsPage().clickEditButton();

        logInfo("Step 5: Change the value in the 'Days Base/Year Base' field to '365/365or366'");
        loanAccount.setDaysBaseYearBase(DaysBaseYearBase.DAY_YEAR_365_365_OR_366.getDaysBaseYearBase());
        AccountActions.editAccount().setDaysBaseYearBase(loanAccount);

        logInfo("Step 6: Change Adjustable Rate to 'Yes'");
        AccountActions.editAccount().enableAdjustableRateSwitch();
        Pages.editAccountPage().isLastPaymentChangeDateDisabled();
        Pages.editAccountPage().isPendingVariablePaymentAmountDisabled ();
        Pages.editAccountPage().isPriorVariablePaymentAmountDisabled();

        logInfo("Step 7: Fill in all appeared fields with valid values (use future dates for 'Date' fields)");
        AccountActions.editAccount().fillInPaymentChangeInputFieldsInLoanAccountEditMode(loanAccount);

        logInfo("Step 8: Click 'Save'");
        Pages.editAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getChangePaymentWithRateChange().equalsIgnoreCase("no"),
                new CustomStepResult("'Change Payment with Rate Change' group is not valid",
                        "'Change Payment with Rate Change' group is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getRateChangeFrequency().equals(loanAccount.getRateChangeFrequency()),
                new CustomStepResult("'Rate Change Frequency' group is not valid",
                        "'Rate Change Frequency' group is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getPaymentChangeFrequency().equals(loanAccount.getPaymentChangeFrequency()),
                new CustomStepResult("'Payment Change Frequency' group is not valid",
                        "'Payment Change Frequency' group is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getNextRateChangeDate().equals(loanAccount.getNextRateChangeDate()),
                new CustomStepResult("'Next Rate Change Date' group is not valid",
                        "'Next Rate Change Date' group is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getRateChangeLeadDays().equals(loanAccount.getRateChangeLeadDays()),
                new CustomStepResult("'Rate Change Lead Days' group is not valid",
                        "'Rate Change Lead Days' group is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getNextPaymentChangeDate().equals(loanAccount.getNextPaymentChangeDate()),
                new CustomStepResult("'Next Payment Change Date' group is not valid",
                        "'Next Payment Change Date' group is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getPaymentChangeLeadDays().equals(loanAccount.getPaymentChangeLeadDays()),
                new CustomStepResult("'Payment Change Lead Days' group is not valid",
                        "'Payment Change Lead Days' group is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getRateIndex().equals(loanAccount.getRateIndex()),
                new CustomStepResult("'Rate Index' group is not valid",
                        "'Rate Index' group is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getRateMargin().equals(loanAccount.getRateMargin()),
                new CustomStepResult("'Rate Margin' group is not valid",
                        "'Rate Margin' group is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getMinRate().equals(loanAccount.getMinRate()),
                new CustomStepResult("'Min Rate' group is not valid",
                        "'Min Rate' group is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getMaxRate().equals(loanAccount.getMaxRate()),
                new CustomStepResult("'Max Rate' group is not valid",
                        "'Max Rate' group is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getMaxRateChangeUpDown().equals(loanAccount.getMaxRateChangeUpDown()),
                new CustomStepResult("'Max rate change up/down' group is not valid",
                        "'Max rate change up/down' group is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getMaxRateLifetimeCap().equals(loanAccount.getMaxRateLifetimeCap()),
                new CustomStepResult("'Max rate lifetime cap' group is not valid",
                        "'Max rate lifetime cap' group is valid"));
        TestRailAssert.assertTrue(("." + Pages.accountDetailsPage().getRateRoundingFactor()).equals(loanAccount.getRateRoundingFactor()),
                new CustomStepResult("'Rate rounding factor' group is not valid",
                        "'Rate rounding factor' group is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getRateRoundingMethod().equals(loanAccount.getRateRoundingMethod()),
                new CustomStepResult("'Rate rounding factor' group is not valid",
                        "'Rate rounding factor' group is valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getOriginalInterestRate().equals(loanAccount.getOriginalInterestRate()),
                new CustomStepResult("'Original interest rate' group is not valid",
                        "'Original interest rate' group is valid"));
    }
}
