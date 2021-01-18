package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.loans.DaysBaseYearBase;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.CommitmentTypeAmt;
import com.nymbus.newmodels.account.loanaccount.InterestMethod;
import com.nymbus.newmodels.account.loanaccount.LoanClassCode;
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
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C25351_LoanCreateAndFundAmortizedTest extends BaseTest {

    private Account loanAccount;
    private IndividualClient client;
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private double escrowPaymentValue;

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
        loanAccount.setLoanClassCode(LoanClassCode.MORTGAGE_LOAN.getLoanClassCode());
        loanAccount.setInterestMethod(InterestMethod.AMORTIZED.getInterestMethod());
        loanAccount.setDaysBaseYearBase(DaysBaseYearBase.DAY_YEAR_360_360.getDaysBaseYearBase());
        loanAccount.setCommitmentTypeAmt(CommitmentTypeAmt.NONE.getCommitmentTypeAmt());

        // Set transaction data
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscCreditDestination.setAccountNumber(checkingAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(100);

        // Login to the system
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);
        Actions.loginActions().doLogOut();

        // Get escrow payment value for the loan product
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        escrowPaymentValue = Actions.loanProductOverviewActions().getLoanProductEscrowPaymentValue(loanProductName);

        // Set the product
        checkingAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create a client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create checking account and logout
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C25315, Loan - Create and Fund: P&I (bill)")
    @Severity(SeverityLevel.CRITICAL)
    public void loanCreateAndFundPi() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 2: Open 'Client' from preconditions on the 'Accounts' tab");
        Actions.clientPageActions().searchAndOpenIndividualClientByID(client.getIndividualType().getClientID());
        Pages.clientDetailsPage().clickAccountsTab();

        logInfo("Step 3: Select 'Account' option in the 'Add New' drop-down");
        AccountActions.createAccount().setAddNewOption(loanAccount);

        logInfo("Step 4: Select 'Product Type' - 'Loan Account'");
        AccountActions.createAccount().setProductType(loanAccount);

        logInfo("Step 5: Select the product from preconditions in the 'Product' drop-down");
        AccountActions.createAccount().setProduct(loanAccount);

        logInfo("Step 6: Specify all required fields and change values for some default fields. e.g.");
        AccountActions.createAccount().setAccountType(loanAccount);
        Pages.addAccountPage().setAccountNumberValue(loanAccount.getAccountNumber());
        AccountActions.createAccount().setOriginatingOfficer(loanAccount);
        AccountActions.createAccount().setCurrentOfficer(loanAccount);
        AccountActions.createAccount().setMailCode(loanAccount);
        Pages.addAccountPage().setDateOpenedValue(loanAccount.getDateOpened());
        AccountActions.createAccount().setBankBranch(loanAccount);
        AccountActions.createAccount().setLoanClassCode(loanAccount);
        Pages.addAccountPage().setPaymentAmount(loanAccount.getPaymentAmount());
        AccountActions.createAccount().setPaymentAmountType(loanAccount);
        AccountActions.createAccount().setPaymentFrequency(loanAccount);
        AccountActions.createAccount().disableCycleLoanSwitch();
        Pages.addAccountPage().setNextPaymentBilledDueDate(loanAccount.getNextPaymentBilledDueDate());
        Pages.addAccountPage().setPaymentBilledLeadDays(loanAccount.getPaymentBilledLeadDays());
        Pages.addAccountPage().setCurrentEffectiveRate(loanAccount.getCurrentEffectiveRate());
        AccountActions.createAccount().setInterestMethod(loanAccount);
        AccountActions.createAccount().disableAdjustableRateSwitch();
        AccountActions.createAccount().setDaysBaseYearBase(loanAccount);
        Pages.addAccountPage().setTerm(loanAccount.getTerm());
        AccountActions.createAccount().setCommitmentTypeAmt(loanAccount);
        AccountActions.createAccount().disableLocPaymentRecalculationFlagValueSwitch();

        logInfo("Step 7: Click the [Save] button");
        Pages.addAccountPage().clickSaveAccountButton();
        Pages.accountDetailsPage().waitForFullProfileButton();

        logInfo("Step 8: Go to Account Maintenance -> Maintenance History page");
        Pages.accountNavigationPage().clickMaintenanceTab();
        Pages.accountMaintenancePage().clickViewAllMaintenanceHistoryLink();

        logInfo("Step 9: Look through the records on Maintenance History page "
                + "and check that all fields that were filled in during account creation are reported in the account Maintenance History");
        AccountActions.accountMaintenanceActions().verifyLoanAccountRecords();

        logInfo("Step 10: Go to the 'Teller' screen");
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 11: Log in to the proof date");
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 12: Fill in the following fields and click the [Commit Transaction]");
        Actions.transactionActions().setMiscDebitSource(miscDebitSource, 0);
        Actions.transactionActions().setMiscCreditDestination(miscCreditDestination, 0);
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();

        logInfo("Step 13: Close Transaction Receipt popup");
        Pages.tellerPage().closeModal();

        logInfo("Step 14: Open loan account on Transactions tab and verify that transaction is written on the transactions history page");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        Pages.accountDetailsPage().clickTransactionsTab();
        Pages.accountTransactionPage().waitForTransactionSection();
        Assert.assertTrue(Pages.accountTransactionPage().getTransactionItemsCountWithZeroOption() >= 1,
                "Transaction is not written on the transactions history page");

        logInfo("Step 15: Open loan account on the 'Payment Info' tab");
        Pages.accountDetailsPage().clickPaymentInfoTab();

        logInfo("Step 16: Verify 'P&I Payments' and 'Other Payment Settings' sections for the current loan");
        Assert.assertEquals(Pages.accountPaymentInfoPage().getPiPaymentsEffectiveDate(), loanAccount.getDateOpened(),
                "'Effective Date' is not valid");
        Assert.assertEquals(Pages.accountPaymentInfoPage().getPiPaymentsInactiveDate(), "",
                "'Inactive Date' is not valid");
        Assert.assertEquals(Pages.accountPaymentInfoPage().getPiPaymentsPaymentType(), loanAccount.getPaymentAmountType(),
                "'Payment Type' is not valid");
        Assert.assertEquals(Pages.accountPaymentInfoPage().getPiPaymentsFrequency(), loanAccount.getPaymentFrequency(),
                "'Frequency' is not valid");
        double paymentAmount = Double.parseDouble(loanAccount.getPaymentAmount());
        double actualPaymentAmount = Double.parseDouble(Pages.accountPaymentInfoPage().getPiPaymentsAmount());
        Assert.assertEquals(actualPaymentAmount, paymentAmount - escrowPaymentValue, "'Amount' is not valid");
        double activePaymentAmount = Double.parseDouble(Pages.accountPaymentInfoPage().getActivePaymentAmount());
        Assert.assertEquals(activePaymentAmount, paymentAmount, "'Active Payment Amount' is not valid");
    }
}
