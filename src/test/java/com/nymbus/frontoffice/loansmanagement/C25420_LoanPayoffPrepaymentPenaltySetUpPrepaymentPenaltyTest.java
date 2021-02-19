package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.loans.BalanceUsed;
import com.nymbus.actions.loans.PenaltyCalculationType;
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
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C25420_LoanPayoffPrepaymentPenaltySetUpPrepaymentPenaltyTest extends BaseTest {

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
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());

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

        // Get escrow payment value for the loan product
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

    @TestRailIssue(issueID = 25420, testRunName = TEST_RUN_NAME)
    @Test(description = "C25420, Loan Payoff Prepayment Penalty: Set up prepayment penalty (Original balance + Fixed Percentage)")
    @Severity(SeverityLevel.CRITICAL)
    public void loanPayoffPrepaymentPenaltySetUpPrepaymentPenalty() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open loan account from preconditions");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        Pages.accountDetailsPage().clickAmountDueInquiryButton();
        Pages.amountDueInquiryModalPage().clickCloseButton();

        logInfo("Step 3: Go to maintenance -> Tool -> Loan Payoff Charges");
        Pages.accountDetailsPage().clickMaintenanceTab();
        AccountActions.accountMaintenanceActions().setTool(Tool.LOAN_PAYOFF_PREPAYMENT_PENALTY);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Step 4: Set up Loan Payoff Prepayment Penalty:" +
                "Effective Date = in the past\n" +
                "Expire Date = in the future\n" +
                "Balance Used = Original balance\n" +
                "Penalty Calculation Type = Fixed Percentage\n" +
                "Fixed Percentage = 10\n" +
                "Minimum Penalty - empty\n" +
                "Maximum Penalty - empty");
        String currentDate = DateTime.getLocalDateOfPattern("MM/dd/yyyy");
        Pages.loanPayoffPrepaymentPenaltyModalPage().typeEffectiveDate(DateTime.getDateMinusDays(currentDate, 1));
        Pages.loanPayoffPrepaymentPenaltyModalPage().typeExpireDate(DateTime.getDateWithFormatPlusDays(currentDate, "MM/dd/yyyy", "MM/dd/yyyy", 1));
        Actions.loanPayoffActions().setBalanceUsed(BalanceUsed.ORIGINAL_BALANCE);
        Actions.loanPayoffActions().setPenaltyCalculationType(PenaltyCalculationType.FIXED_PERCENTAGE);
        Pages.loanPayoffPrepaymentPenaltyModalPage().typeFixedPercentage("10");
        Pages.loanPayoffChargesModalPage().clickDoneButton();
        double prepaymentPenalty = Double.parseDouble(Pages.loanPayoffPrepaymentPenaltyModalPage().getPrepaymentPenaltyAmount());
        Pages.loanPayoffChargesModalPage().clickCloseButton();

        logInfo("Step 6: Go to 'Details' tab -> click on the 'Amount Due Inquiry' button and note 'Payoff Charges' field");
        Pages.accountDetailsPage().clickDetailsTab();
        Pages.accountDetailsPage().clickAmountDueInquiryButton();
        double actualPrepaymentPenalty = Double.parseDouble(Pages.amountDueInquiryModalPage().getPrepaymentPenalty());
        Assert.assertEquals(prepaymentPenalty, actualPrepaymentPenalty, "'Payoff Charges' value is not valid");
    }
}
