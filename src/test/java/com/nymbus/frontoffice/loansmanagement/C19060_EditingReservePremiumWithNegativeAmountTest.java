package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Functions;
import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.loanaccount.LoanReserve;
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

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Petro")
public class C19060_EditingReservePremiumWithNegativeAmountTest extends BaseTest {

    private Account loanAccount;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private final String AMOUNT = "300000";

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
        Account checkingAccount = new Account().setCHKAccountData();
        checkingAccount.setDateOpened(DateTime.getDateMinusMonth(loanAccount.getDateOpened(), 1));

        // Set up deposit transaction
        int transactionAmount = 12000;
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscDebitSource.setAmount(transactionAmount);
        miscCreditDestination.setAccountNumber(checkingAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(transactionAmount);

        // Loan reserve
        LoanReserve loanReserve = new LoanReserve();
        loanReserve.setReservePremiumAmortizationCode(Generator.getRandomStringNumber(7));
        loanReserve.setBalanceDefinition("Deferred Costs");

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

        // Create accounts
        AccountActions.createAccount().createCHKAccount(checkingAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);

        // Perform deposit transaction
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().setMiscDebitSource(miscDebitSource, 0);
        Actions.transactionActions().setMiscCreditDestination(miscCreditDestination, 0);
        Pages.tellerPage().setEffectiveDate(loanAccount.getDateOpened());
        Actions.transactionActions().clickCommitButtonWithProofDateModalVerification();
        Pages.tellerPage().closeModal();

        Pages.aSideMenuPage().clickLoansMenuItem();
        Pages.loansPage().waitForLoanPageLoaded();
        Pages.loansPage().clickViewAllLoanReserveLink();
        Pages.loanReservePage().clickAddNewButton();
        Pages.addNewLoanReservePage().typeReservePremiumAmortizationCodeField(loanReserve.getReservePremiumAmortizationCode());
        Pages.addNewLoanReservePage().clickBalanceDefinitionSelectorButton();
        Pages.addNewLoanReservePage().clickBalanceDefinitionSelectorOption(loanReserve.getBalanceDefinition());
        Pages.addNewLoanReservePage().clickSaveChangesButton();
        Actions.loginActions().doLogOutProgrammatically();

        // Loan account with "Reserve/Premium Amount < 0" exists (455X - Add R/P Expense is generated during adding R/P to the account)
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        Pages.accountDetailsPage().clickMaintenanceTab();
        AccountActions.accountMaintenanceActions().setTool(Tool.RESERVE_PREMIUM_PROCESSING);
        Pages.accountMaintenancePage().clickToolsLaunchButton();
        Pages.reservePremiumProcessingModalPage().clickAddNewLoanReservePremiumButton();
        Pages.reservePremiumProcessingModalPage().setEffectiveDate(DateTime.getLocalDateOfPattern("MM/dd/yyyy"));
        Pages.reservePremiumProcessingModalPage().setReservePremiumAmount("-" + AMOUNT);
        Actions.reservePremiumProcessingModalPageActions().setDeferredYesNoSwitchValueToYes();
        Actions.reservePremiumProcessingModalPageActions().setRandomReservePremiumCode("DE");
        Pages.reservePremiumProcessingModalPage().setReservePremiumTerm("3");
        Pages.reservePremiumProcessingModalPage().setReservePremiumDeferringStartDate(DateTime.getLocalDateOfPattern("MM/dd/yyyy"));
        Actions.reservePremiumProcessingModalPageActions().setIrsReportablePointsPaidSwitchValueToNo();
        Pages.reservePremiumProcessingModalPage().clickCommitTransactionButton();
        Pages.reservePremiumProcessingModalPage().clickCloseButton();
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Loans Management";

    @TestRailIssue(issueID = 19059, testRunName = TEST_RUN_NAME)
    @Test(description="C19059, Add new 'Reserve/Premium' with negative amount")
    @Severity(SeverityLevel.CRITICAL)
    public void addNewReservePremiumWithNegativeAmount() {

        logInfo("Step 1: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open account from preconditions on the 'Maintenance' page");
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        Pages.accountDetailsPage().clickMaintenanceTab();

        logInfo("Step 3: Select 'Reserve/Premium Processing' in 'Tools' widget and click the [Launch] button");
        AccountActions.accountMaintenanceActions().setTool(Tool.RESERVE_PREMIUM_PROCESSING);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Step 4: Select existing 'Reserve/Premium' record and click on the 'Edit' button");
        Pages.reservePremiumProcessingModalPage().clickReservePremiumRecordFromTableByIndex(1);
        Pages.reservePremiumProcessingModalPage().clickEditButton();

        logInfo("Step 5: Change 'Reserve/Premium Term' to any other value, differ from original\n" +
                "Enter the positive amount in 'Adjustment Amount' field (Adjustment amount < Reserve/Premium Amount)");
        Pages.reservePremiumProcessingModalPage().setReservePremiumTerm("6");
        final String ADJUSTMENT_AMOUNT = "1500";
        Pages.reservePremiumProcessingModalPage().setAdjustmentAmount(ADJUSTMENT_AMOUNT);
        double reservePremiumAmount = Double.parseDouble(Pages.reservePremiumProcessingModalPage().getReservePremiumAmount());
        TestRailAssert.assertTrue(Functions.getStringValueWithOnlyDigits(reservePremiumAmount).equals(AMOUNT),
                new CustomStepResult("'Reserve/Premium Amount' is valid", "'Reserve/Premium Amount' is not valid"));
        Pages.reservePremiumProcessingModalPage().clickCommitTransactionButton();
        Pages.reservePremiumProcessingModalPage().clickCloseButton();

        logInfo("Step 6: Open account from preconditions on the 'Transactions' tab");
        Pages.accountDetailsPage().clickTransactionsTab();

        logInfo("Step 7: Verify committed transaction");
        String transactionCode = Pages.accountTransactionPage().getTransactionCodeByIndex(1);
        TestRailAssert.assertTrue(transactionCode.equals(TransactionCode.SUB_RP_EXPENSE_456X.getTransCode()),
                new CustomStepResult("'Transaction code' is valid", "'Transaction code' is not valid"));
        double transactionAmount = AccountActions.retrievingAccountData().getAmountValue(1);
        TestRailAssert.assertTrue(Functions.getStringValueWithOnlyDigits(transactionAmount).equals(AMOUNT),
                new CustomStepResult("'Amount' is valid", "'Amount' is not valid"));
    }
}
