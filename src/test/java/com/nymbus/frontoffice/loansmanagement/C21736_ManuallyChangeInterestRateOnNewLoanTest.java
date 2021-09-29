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
import com.nymbus.testrail.TestRailIssue;
import com.nymbus.util.Random;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.DecimalFormat;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C21736_ManuallyChangeInterestRateOnNewLoanTest extends BaseTest {

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

        // Set up CHK account
        Account checkingAccount = new Account().setCHKAccountData();

        // Set up Loan account
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        loanAccount.setNextPaymentBilledDueDate(DateTime.getLocalDatePlusMonthsWithPatternAndLastDay(loanAccount.getDateOpened(), 1, "MM/dd/yyyy"));
        String dateOpened = loanAccount.getDateOpened();
        loanAccount.setDateOpened(DateTime.getDateMinusDays(dateOpened, 1));
        checkingAccount.setDateOpened(DateTime.getDateMinusMonth(loanAccount.getDateOpened(), 1));

        // Set transaction data
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscDebitSource.setAmount(12000);
        miscCreditDestination.setAccountNumber(checkingAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(12000);

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

        // Create checking, loan account and logout
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);
        Actions.loginActions().doLogOut();

        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
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

    @TestRailIssue(issueID = 21736, testRunName = TEST_RUN_NAME)
    @Test(description = "C21736, Manually change interest rate on new loan")
    @Severity(SeverityLevel.CRITICAL)
    public void manuallyChangeInterestRateOnNewLoan() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getPassword(), userCredentials.getPassword());

        logInfo("Step 2: Open loan account from preconditions -> Maintenance -> Tools -> Interest Rate Change");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        double currentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalance());
        double accruedInterest = Double.parseDouble(Pages.accountDetailsPage().getAccruedInterest());
        Pages.accountDetailsPage().clickMaintenanceTab();
        AccountActions.accountMaintenanceActions().setTool(Tool.INTEREST_RATE_CHANGE);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Step 3: Click [Add Back Dated Rate Change] button");
        Pages.interestRateChangeModalPage().clickAddBackDatedRateChangeButton();

        logInfo("Step 4: Set:\n" +
                "- NEW Current Effective Rate != Old Current Effective Rate\n" +
                "- Begin Earn Date = Date in the past but > = Date Opened\n" +
                "- Accrue Thru Date = Today (set Current date - 1 day by default)");
        int oldCurrentEffectiveRate = Integer.parseInt(loanAccount.getCurrentEffectiveRate());
        double newCurrentEffectiveRate = oldCurrentEffectiveRate + Random.genInt(1, 10);
        int days = 1;
        Pages.interestRateChangeModalPage().setNewCurrentEffectiveRateValue(String.valueOf(newCurrentEffectiveRate));
        Pages.interestRateChangeModalPage().setBeginEarnDate(loanAccount.getDateOpened());
        String dateMinusDays = DateTime.getDateMinusDays(WebAdminActions.loginActions().getSystemDate(), 1);
        Assert.assertTrue(Pages.interestRateChangeModalPage().getAccrueThruDate().equals(dateMinusDays),
                "'Current effective rate' row count is incorrect!");

        logInfo("Step 5: Click on the 'Commit Transaction' button");
        Pages.interestRateChangeModalPage().clickCommitTransactionButton();

        logInfo("Step 6: Specify supervisor login name and password and click 'Enter'");
        Pages.supervisorModalPage().inputLogin(userCredentials.getUserName());
        Pages.supervisorModalPage().inputPassword(userCredentials.getPassword());
        Pages.supervisorModalPage().clickEnter();

        logInfo("Step 7: Pay attention at the interest amount in 'Alert Message' pop up");
        String[] daysBaseYearBase = loanAccount.getDaysBaseYearBase().replaceAll("[^0-9/]", "").split("/");
        int yearBase = Integer.parseInt(daysBaseYearBase[1]);
        double adjustmentAmount = currentBalance * (newCurrentEffectiveRate / 100 - (double) oldCurrentEffectiveRate / 100) / yearBase * (
                DateTime.getDaysBetweenTwoDates(loanAccount.getDateOpened(), WebAdminActions.loginActions().getSystemDate(),false));
        adjustmentAmount = roundAmountToTwoDecimals(adjustmentAmount);
        double interestEarned = accruedInterest + adjustmentAmount;

        String alertMessageModalText = Pages.alertMessageModalPage().getAlertMessageModalText();

        Assert.assertTrue(alertMessageModalText.contains(String.format("%.2f", adjustmentAmount)),
                String.format("'Adjustment Amount' is calculated incorrect. Actual %s, expected %s",alertMessageModalText,String.format("%.2f", adjustmentAmount)));
        Assert.assertTrue(alertMessageModalText.contains(String.format("%.2f", interestEarned)), String.format("'Interest Earned' is calculated incorrect. " +
                "Expected %s, Actual %s.", alertMessageModalText,String.format("%.2f", interestEarned)));
        Pages.alertMessageModalPage().clickOkButton();

        logInfo("Step 8: Go to 'Transactions' tab and pay attention at the generated transaction");
        Pages.interestRateChangeModalPage().clickCloseButton();
        Pages.accountDetailsPage().clickTransactionsTab();
        String transactionCode = Pages.accountTransactionPage().getTransactionCodeByIndex(1);
        checkTransactionCode(transactionCode, newCurrentEffectiveRate, oldCurrentEffectiveRate);

        logInfo("Step 9: Go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 10: Look through the Maintenance History records and make sure that there is information about interest rate change");
        AccountActions.accountMaintenanceActions().expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Current effective rate") >= 1,
                "'Current effective rate' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest earned") >= 1,
                "'Interest earned' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date this rate change") >= 1,
                "'Date this rate change' row count is incorrect!");
    }

    private void checkTransactionCode(String transactionCode, double newCurrentEffectiveRate, int oldCurrentEffectiveRate) {
        if (newCurrentEffectiveRate > oldCurrentEffectiveRate) {
            Assert.assertEquals(TransactionCode.ADD_TO_IENC_409.getTransCode(), transactionCode,
                    "Transaction code is not equal to '408 - Take From IENC'");
        } else {
            Assert.assertEquals(TransactionCode.TAKE_FROM_IENC_408.getTransCode(), transactionCode,
                    "Transaction code is not equal to '409 - Add To IENC'");
        }
    }

    private double roundAmountToTwoDecimals(double amount) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(amount));
    }
}
