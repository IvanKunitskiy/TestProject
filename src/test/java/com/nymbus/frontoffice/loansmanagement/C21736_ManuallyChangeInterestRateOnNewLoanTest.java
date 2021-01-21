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
import com.nymbus.newmodels.generation.tansactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.tansactions.factory.SourceFactory;
import com.nymbus.newmodels.maintenance.Tool;
import com.nymbus.newmodels.transaction.TransactionDestination;
import com.nymbus.newmodels.transaction.TransactionSource;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.pages.Pages;
import com.nymbus.util.Random;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C21736_ManuallyChangeInterestRateOnNewLoanTest extends BaseTest {

    private Account loanAccount;
    private IndividualClient client;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private double escrowPaymentValue;

    @BeforeMethod
    public void preCondition() {
        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up CHK account
        Account checkingAccount = new Account().setCHKAccountData();

        // Set up Loan account
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());

        // Set transaction data
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscCreditDestination.setAccountNumber(checkingAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(100);

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

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

    @Test(description = "C21736, Manually change interest rate on new loan")
    @Severity(SeverityLevel.CRITICAL)
    public void manuallyChangeInterestRateOnNewLoan() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getPassword(), userCredentials.getPassword());

        logInfo("Step 2: Open loan account from preconditions");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        Pages.accountDetailsPage().clickMaintenanceTab();
        AccountActions.accountMaintenanceActions().setTool(Tool.INTEREST_RATE_CHANGE);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Step 3: Set:\n" +
                "- NEW Current Effective Rate != Old Current Effective Rate\n" +
                "- Begin Earn Date = Date in the past but > = Date Opened\n" +
                "- Accrue Thru Date = Today (set Current date - 1 day by default)");
        int oldCurrentEffectiveRate = Integer.parseInt(loanAccount.getCurrentEffectiveRate());
        int newCurrentEffectiveRate = oldCurrentEffectiveRate + Random.genInt(1, 10);
        Pages.interestRateChangeModalPage().setNewCurrentEffectiveRateValue(String.valueOf(newCurrentEffectiveRate));
        Pages.interestRateChangeModalPage().setAccrueThruDate(loanAccount.getDateOpened());
        Pages.interestRateChangeModalPage().setBeginEarnDate(DateTime.getLocalDateWithPattern("MM/dd/yyyy"));

        logInfo("Step 4: Click on the 'Commit Transaction' button");
        Pages.interestRateChangeModalPage().clickCommitTransactionButton();

        logInfo("Step 5: Specify supervisor login name and password and click 'Enter'");
        Pages.supervisorModalPage().inputLogin(userCredentials.getUserName());
        Pages.supervisorModalPage().inputPassword(userCredentials.getPassword());
        Pages.supervisorModalPage().clickEnter();

        logInfo("Step 6: Pay attention at the interest amount in 'Alert Message' pop up");
        String[] daysBaseYearBase = loanAccount.getDaysBaseYearBase().replaceAll("[^0-9/]", "").split("/");
        int adjustedDays = Integer.parseInt(daysBaseYearBase[0]);
        int yearBase = Integer.parseInt(daysBaseYearBase[1]);
        int adjustmentValue = (newCurrentEffectiveRate * (newCurrentEffectiveRate - oldCurrentEffectiveRate)) / adjustedDays * yearBase;
        // TODO: Check Adjustment value and Interest Earned
        Pages.alertMessageModalPage().clickOkButton();

        logInfo("Step 7: Go to 'Transactions' tab and pay attention at the generated transaction");
        Pages.accountDetailsPage().clickTransactionsTab();
        // TODO: pay attention at the generated transaction

        logInfo("Step 8: Go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 9: Look through the Maintenance History records and make sure that there is information about interest rate change");
        AccountActions.accountMaintenanceActions().expandAllRows();
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Current effective rate") >= 1,
                "'Current effective rate' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Interest earned") >= 1,
                "'Interest earned' row count is incorrect!");
        Assert.assertTrue(Pages.accountMaintenancePage().getChangeTypeElementsCount("Date this rate change") >= 1,
                "'Date this rate change' row count is incorrect!");
    }
}
