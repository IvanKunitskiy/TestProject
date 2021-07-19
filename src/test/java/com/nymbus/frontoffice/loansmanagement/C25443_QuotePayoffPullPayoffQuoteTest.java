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
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.nymbus.core.utils.Functions.getDoubleWithFormatAndFloorRounding;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C25443_QuotePayoffPullPayoffQuoteTest extends BaseTest {

    private Account loanAccount;
    private IndividualClient client;
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up CHK account
        Account checkingAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        String dateOpened = loanAccount.getDateOpened();
        loanAccount.setDateOpened(DateTime.getDateMinusDays(dateOpened, 1));
        checkingAccount.setDateOpened(DateTime.getDateMinusMonth(loanAccount.getDateOpened(), 1));

        // Set transaction data
        final int TRANSACTION_AMOUNT = 12000;
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscDebitSource.setAmount(TRANSACTION_AMOUNT);
        miscCreditDestination.setAccountNumber(checkingAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(TRANSACTION_AMOUNT);

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);
        Actions.loginActions().doLogOut();

        // Set CHK product
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create accounts and logout
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);
        Actions.loginActions().doLogOut();

        // Perform transaction
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

    @TestRailIssue(issueID = 25443, testRunName = TEST_RUN_NAME)
    @Test(description = "C25443, Quote Payoff: Pull payoff quote")
    @Severity(SeverityLevel.CRITICAL)
    public void interestRateChangeManuallyChangeInterestRateOnConvertedLoanAccount() {

        logInfo("Step 1: Log in to Nymbus");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open loan account from preconditions");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount);
        String currentBalance = Pages.accountDetailsPage().getCurrentBalance();
        String accruedInterest = Pages.accountDetailsPage().getAccruedInterest();
        String dailyInterestFactor = Pages.accountDetailsPage().getDailyInterestFactor();
        String currentEffectiveRate = Pages.accountDetailsPage().getCurrentEffectiveRate();

        logInfo("Step 3: Go to Maintenance -> Tools -> Quote Payoff");
        Pages.accountDetailsPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickChooseToolSelectorButton();
        Pages.accountMaintenancePage().clickChooseToolOption(Tool.QUOTE_PAYOFF.getTool());
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Step 4: Check 'Select Payoff Date' and select any other date");
        int days = 1;
        String payoffDateValue = Pages.quotePayoffModalPage().getSelectPayoffDateValue();
        String newPayoffDateValue = DateTime.getDateWithFormatPlusDays(payoffDateValue, "MM/dd/yyyy", "MM/dd/yyyy", days);
        Pages.quotePayoffModalPage().typeToSelectPayoffDateValue(newPayoffDateValue);

        logInfo("Step 5: Check calculated values in fields for rows: Date, Payoff, Balance, Interest, Late Charge, Escrow, Insurance, Skip Fees, Other Charges");
        TestRailAssert.assertTrue(Pages.quotePayoffModalPage().getDateByRowIndex(1).equals(newPayoffDateValue),
                new CustomStepResult("'Date' is not valid", "'Date' is valid"));
        String interest = Pages.quotePayoffModalPage().getInterestByRowIndex(1);
        String balance = Pages.quotePayoffModalPage().getBalanceByRowIndex(1);
        String lateCharges = Pages.quotePayoffModalPage().getLateChargeByRowIndex(1);
        String otherCharges = Pages.quotePayoffModalPage().getOtherChargesByRowIndex(1);
        String escrow = Pages.quotePayoffModalPage().getEscrowByRowIndex(1);
        String insurance = Pages.quotePayoffModalPage().getInsuranceByRowIndex(1);
        String skipFee = Pages.quotePayoffModalPage().getSkipFeesByRowIndex(1);

        String payoff = Pages.quotePayoffModalPage().getPayoff();
        double expectedPayoff = Double.parseDouble(interest) + Double.parseDouble(balance) + Double.parseDouble(lateCharges)
                + Double.parseDouble(otherCharges) + Double.parseDouble(escrow) + Double.parseDouble(insurance) + Double.parseDouble(skipFee);
        double actualPayoff = Double.parseDouble(Pages.quotePayoffModalPage().getPayoffByRowIndex(1));
        TestRailAssert.assertTrue(actualPayoff == getDoubleWithFormatAndFloorRounding(expectedPayoff, "###.##"),
                new CustomStepResult("'Payoff' value is not valid", "'Payoff' value is valid"));
        TestRailAssert.assertTrue(balance.equals(currentBalance),
                new CustomStepResult("'Current Balance' value is not valid", "'Current Balance' value is not valid"));
        double actualInterest = Double.parseDouble(Pages.quotePayoffModalPage().getInterestByRowIndex(1));
        double expectedInterest = Double.parseDouble(accruedInterest) + (Double.parseDouble(dailyInterestFactor) * (days + 1));
        TestRailAssert.assertTrue(actualInterest == getDoubleWithFormatAndFloorRounding(expectedInterest, "###.##"),
                new CustomStepResult("'Interest' value is not valid", "'Interest' value is not valid"));

        // Values from fields below are evaluated to '0.00' as the account is brand new
        String defaultZero = "0.00";
        TestRailAssert.assertTrue(Pages.quotePayoffModalPage().getLateChargeByRowIndex(1).equals(defaultZero),
                new CustomStepResult("The 'Late Charge' value is not valid", "The 'Late Charge' value is valid"));
        TestRailAssert.assertTrue(Pages.quotePayoffModalPage().getEscrowByRowIndex(1).equals(defaultZero),
                new CustomStepResult("The 'Escrow' value is not valid", "The 'Escrow' value is valid"));
        TestRailAssert.assertTrue(Pages.quotePayoffModalPage().getInsuranceByRowIndex(1).equals(defaultZero),
                new CustomStepResult("The 'Insurance' value is not valid", "The 'Insurance' value is valid"));
        TestRailAssert.assertTrue(Pages.quotePayoffModalPage().getSkipFeesByRowIndex(1).equals(defaultZero),
                new CustomStepResult("The 'Skip Fees' value is not valid", "The 'Skip Fees' value is valid"));
        TestRailAssert.assertTrue(Pages.quotePayoffModalPage().getOtherChargesByRowIndex(1).equals(defaultZero),
                new CustomStepResult("The 'Other Charges' value is not valid", "The 'Other Charges' value is valid"));

        logInfo("Step 6: Check 'Account Details' section");
        TestRailAssert.assertTrue(Pages.quotePayoffModalPage().getPayoffDate().equals(newPayoffDateValue),
                new CustomStepResult("'Payoff Date' is not valid", "'Payoff Date' is valid"));
        TestRailAssert.assertTrue(Pages.quotePayoffModalPage().getClientName().equals(client.getIndividualType().getLastName() +
                " " + client.getIndividualType().getFirstName()),
                new CustomStepResult("'Client Name' is not valid", "'Client Name' is valid"));
        TestRailAssert.assertTrue(Pages.quotePayoffModalPage().getAccountNumber().equals(loanAccount.getAccountNumber()),
                new CustomStepResult("'Account #' is not valid", "'Payoff Date' is valid"));
        TestRailAssert.assertTrue(Pages.quotePayoffModalPage().getBalance().equals(currentBalance),
                new CustomStepResult("'Balance' is not valid", "'Current Balance' is valid"));
        TestRailAssert.assertTrue(payoff.equals(Pages.quotePayoffModalPage().getPayoffByRowIndex(1)),
                new CustomStepResult("'Payoff' is not valid", "'Payoff' is valid"));
        TestRailAssert.assertTrue(Pages.quotePayoffModalPage().getInterestRate().equals(currentEffectiveRate),
                new CustomStepResult("'Interest rate' is not valid", "'Interest rate' is valid"));
        TestRailAssert.assertTrue(Pages.quotePayoffModalPage().getInterestBase().equals(""),
                new CustomStepResult("'Interest base' is not valid", "'Interest base' is valid"));
        TestRailAssert.assertTrue(Pages.quotePayoffModalPage().getOtherCharges().equals(Pages.quotePayoffModalPage().getOtherChargesByRowIndex(1)),
                new CustomStepResult("'Other Charges' is not valid", "'Other Charges' is valid"));
        // Validated as 0, as there was no interest paid previously
        TestRailAssert.assertTrue(Pages.quotePayoffModalPage().getInterestPaidToDate().equals(defaultZero),
                new CustomStepResult("'Other Charges' is not valid", "'Other Charges' is valid"));

        logInfo("Step 7: Check 'Print Payoff Letter' section");
        TestRailAssert.assertFalse(Pages.quotePayoffModalPage().isDateFieldDisabled(),
                new CustomStepResult("'Date' field is disabled", "'Date' field is enabled"));
        TestRailAssert.assertFalse(Pages.quotePayoffModalPage().isNameFieldDisabled(),
                new CustomStepResult("'Name' field is disabled", "'Name' field is enabled"));
        TestRailAssert.assertFalse(Pages.quotePayoffModalPage().isAddressFieldDisabled(),
                new CustomStepResult("'Address' field is disabled", "'Address' field is enabled"));
    }
}
