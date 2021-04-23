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
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.MiscDebitMiscCreditBuilder;
import com.nymbus.newmodels.generation.tansactions.factory.DestinationFactory;
import com.nymbus.newmodels.generation.tansactions.factory.SourceFactory;
import com.nymbus.newmodels.transaction.Transaction;
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
public class C25422_ChargeOffProcessingChargeOffReversalForPartiallyChargedOffLoansTest extends BaseTest {

    private Account loanAccount;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private final TransactionSource tellerTransactionSource = new TransactionSource();
    private final TransactionDestination tellerTransactionDestination = new TransactionDestination();
    private double currentBalance;
    private double chargedOffAmount;
    private Transaction miscDebitMiscCreditTransaction;

    @BeforeMethod
    public void preCondition() {

        // Set up client
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();

        // Set up accounts
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setProduct(loanProductName);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());

        Account checkingAccount = new Account().setCHKAccountData();
        checkingAccount.setDateOpened(DateTime.getDateMinusMonth(loanAccount.getDateOpened(), 1));

        // Set up transactions
        int transactionAmount = 12000;
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscDebitSource.setAmount(transactionAmount);
        miscCreditDestination.setAccountNumber(checkingAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(transactionAmount);

        double tellerOperationAmount = 10.00;
        tellerTransactionSource.setAccountNumber("0-0-Dummy");
        tellerTransactionSource.setTransactionCode(TransactionCode.GL_DEBIT_860.getTransCode());
        tellerTransactionSource.setAmount(tellerOperationAmount);
        tellerTransactionDestination.setAccountNumber(loanAccount.getAccountNumber());
        tellerTransactionDestination.setTransactionCode(TransactionCode.CHARGE_OFF_429.getTransCode());
        tellerTransactionDestination.setAmount(tellerOperationAmount);

        double miscDebitAmount = tellerOperationAmount - 5.00; // "Amount" =< "Amount charged off" of loan account
        miscDebitMiscCreditTransaction = new TransactionConstructor(new MiscDebitMiscCreditBuilder()).constructTransaction();
        miscDebitMiscCreditTransaction.getTransactionSource().setAccountNumber(loanAccount.getAccountNumber());
        miscDebitMiscCreditTransaction.getTransactionSource().setTransactionCode(TransactionCode.CHARGE_OFF_REVERSAL_430.getTransCode());
        miscDebitMiscCreditTransaction.getTransactionSource().setAmount(miscDebitAmount);

        miscDebitMiscCreditTransaction.getTransactionDestination().setAccountNumber(checkingAccount.getAccountNumber());
        miscDebitMiscCreditTransaction.getTransactionDestination().setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscDebitMiscCreditTransaction.getTransactionDestination().setAmount(miscDebitAmount);

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

        String cdtTemplateName = get429TemplateName();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Pages.aSideMenuPage().clickCashierDefinedTransactions();
        Actions.transactionActions().doLoginTeller();
        Actions.cashierDefinedActions().setTellerOperation(cdtTemplateName);
        Actions.cashierDefinedActions().setTransactionSource(tellerTransactionSource, 0);
        Actions.cashierDefinedActions().setTransactionDestination(tellerTransactionDestination, 0);
        Actions.cashierDefinedActions().clickCommitButton();
        Pages.alertMessageModalPage().clickOkButton();
        Actions.loginActions().doLogOutProgrammatically();

        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        currentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalanceFromHeaderMenu());
        chargedOffAmount = Double.parseDouble(Pages.accountDetailsPage().getChargedOffAmount());
        Actions.loginActions().doLogOutProgrammatically();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 25426, testRunName = TEST_RUN_NAME)
    @Test(description="C25422, Charge Off processing: Charge Off Reversal for Partially Charged Off loans")
    @Severity(SeverityLevel.CRITICAL)
    public void commercialParticipationLoanSellRepurchase() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to the 'Teller' screen" );
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Log in to the proof date");
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 4: Fill in the following fields and click the [Commit Transaction] :\n" +
                "Sources -> Misc Debit:\n" +
                "'Account Number' - loan account from precondition 4\n" +
                "'Transaction Code' - '430-Charge off Reversal'\n" +
                "'Amount' =< 'Amount charged off' of loan account\n" +
                "Destinations -> Misc Credit:\n" +
                "Account number - active CHK or SAV account from precondition 3\n" +
                "'Transaction Code' - specify trancode ('109-Deposit' for CHK or '209-Deposit for SAV)\n" +
                "'Amount' - specify the same amount");
        Actions.transactionActions().setMiscDebitSource(miscDebitMiscCreditTransaction.getTransactionSource(), 0);
        Actions.transactionActions().setMiscCreditDestination(miscDebitMiscCreditTransaction.getTransactionDestination(), 0);
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();

        logInfo("Step 5: Close Transaction Receipt popup");
        Pages.tellerPage().closeModal();

        logInfo("Step 6: Open loan account from preconditions");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());

        logInfo("Step 7: Go to the 'Transactions' tab and verify generated transaction");
        Pages.accountDetailsPage().clickTransactionsTab();
        String transactionCode = Pages.accountTransactionPage().getTransactionCodeByIndex(1);
        TestRailAssert.assertTrue(transactionCode.equals(TransactionCode.CHARGE_OFF_REVERSAL_430.getTransCode()),
                new CustomStepResult("'Transaction code' is not valid", "'Transaction code' is valid"));
        double amountValue = AccountActions.retrievingAccountData().getAmountValue(1);
        TestRailAssert.assertTrue(amountValue == miscDebitMiscCreditTransaction.getTransactionSource().getAmount(),
                new CustomStepResult("'Amount' is not valid", "'Amount' is valid"));

        logInfo("Step 7: Go to the 'Details' tab and verify the following fields:\n" +
                "'Current Balance'\n" +
                "'Account Status'\n" +
                "'Charged Off Amount'");
        Pages.accountDetailsPage().clickDetailsTab();
        double actualCurrentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalanceFromHeaderMenu());
        TestRailAssert.assertTrue(actualCurrentBalance == currentBalance + miscDebitMiscCreditTransaction.getTransactionSource().getAmount(),
                new CustomStepResult("Account 'current balance' is not valid", "Account 'current balance' is not valid"));
        double actualChargedOffAmount = Double.parseDouble(Pages.accountDetailsPage().getChargedOffAmount());
        TestRailAssert.assertTrue(actualChargedOffAmount == chargedOffAmount - miscDebitMiscCreditTransaction.getTransactionSource().getAmount(),
                new CustomStepResult("Account 'charged off amount' is not valid", "Account 'charged off amount' is not valid"));
        TestRailAssert.assertTrue(Pages.accountDetailsPage().getAccountStatus().equals("Active"),
                new CustomStepResult("Account 'status' is not valid", "Account 'status' is not valid"));
    }

    private String get429TemplateName() {
        final String TRN_CODE = "429";
        int index = 1;

        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL);
        SelenideTools.switchTo().window(1);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        String cdtTemplateName = WebAdminActions.webAdminUsersActions().getCdtTemplateByIndex(index, TRN_CODE);
        WebAdminActions.loginActions().doLogoutProgrammatically();
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);

        return cdtTemplateName;
    }
}
