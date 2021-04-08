package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Generator;
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
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class C18912_CommercialParticipationLoanLoanParticipationToolTest extends BaseTest {

    private Account loanAccount;
    private Account checkAccount;
    private double transactionAmount = 1001.00;
    private final String loanProductName = "Test Loan Product";
    private final String loanProductInitials = "TLP";
    private String clientRootId;
    private final TransactionSource miscDebitSource = SourceFactory.getMiscDebitSource();
    private final TransactionDestination miscCreditDestination = DestinationFactory.getMiscCreditDestination();
    private int balance;


    @BeforeMethod
    public void precondition() {
        // Set up Client and Accounts
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        checkAccount = new Account().setCHKAccountData();
        loanAccount = new Account().setLoanAccountData();
        loanAccount.setPaymentAmountType(PaymentAmountType.INTEREST_ONLY.getPaymentAmountType());
        loanAccount.setPaymentBilledLeadDays(String.valueOf(1));
        loanAccount.setProduct(loanProductName);
        loanAccount.setEscrow("$ 0.00");
        loanAccount.setCycleCode(Generator.genInt(1, 20) + "");
        loanAccount.setCycleLoan(true);
        loanAccount.setMailCode(client.getIndividualClientDetails().getMailCode().getMailCode());
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();
        checkAccount.setDateOpened(loanAccount.getDateOpened());

        // Login to the system
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Check that a Loan product exist with the following editable fields (Readonly? = NO) and create if not exist
        Actions.loanProductOverviewActions().checkLoanProductExistAndCreateIfFalse(loanProductName, loanProductInitials);
        Actions.loginActions().doLogOut();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        //Check "Commercial Participation - Lite" = Yes
        Actions.loansActions().clickCommercialParticipationLiteYes();
        Pages.aSideMenuPage().clickClientMenuItem();

        //Create Loan Participant
        Actions.loansActions().createAutotestParticipant();

        // Set products
        checkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);
        Pages.accountNavigationPage().clickAccountsInBreadCrumbs();
        AccountActions.createAccount().createLoanAccount(loanAccount);
        clientRootId = ClientsActions.createClient().getClientIdFromUrl();

        // Set up transactions with account number
        depositTransaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());
        depositTransaction.getTransactionDestination().setAmount(transactionAmount);
        depositTransaction.getTransactionSource().setAmount(transactionAmount);
        depositTransaction.setTransactionDate(loanAccount.getDateOpened());
        balance = 12000;
        miscDebitSource.setAccountNumber(loanAccount.getAccountNumber());
        miscDebitSource.setTransactionCode(TransactionCode.NEW_LOAN_411.getTransCode());
        miscDebitSource.setAmount(balance);
        miscCreditDestination.setAccountNumber(checkAccount.getAccountNumber());
        miscCreditDestination.setTransactionCode(TransactionCode.ATM_DEPOSIT_109.getTransCode());
        miscCreditDestination.setAmount(balance);

        // Perform deposit transactions
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

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

    @TestRailIssue(issueID = 18912, testRunName = TEST_RUN_NAME)
    @Test(description = "C18912, Commercial Participation Loan - Loan Participation tool")
    @Severity(SeverityLevel.CRITICAL)
    public void commercialParticipationLoanLoanParticipationTool() {
        logInfo("Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Open loan account from preconditions -> Maintenance -> Tools -> Loan Participations");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        String accruedInterest = Pages.accountDetailsPage().getAccruedInterest();
        Pages.accountDetailsPage().clickMaintenanceTab();
        AccountActions.accountMaintenanceActions().setTool(Tool.LOAN_PARTICIPATIONS);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Step 3: Note left part of the screen");
        Pages.accountMaintenancePage().checkLeftParticipant();

        logInfo("Step 4: Note right part of the screen");
        Pages.accountMaintenancePage().checkRightParticipant();

        logInfo("Step 5: Click \"+ Add New Participation\"");
        Pages.accountMaintenancePage().clickAddNewButton();
        TestRailAssert.assertTrue(Pages.accountMaintenancePage().isStatusDisabled(), new CustomStepResult(
                "Status is disabled", "Status is enabled"));

        logInfo("Step 6: Specify:\n" +
                "- Participant = any Participant from the drop-down box\n" +
                "- Percentage Sold = any Percentage\n" +
                "- Servicing Fee = any Percentage\n" +
                "- Sold Date = in the past\n" +
                "Please note if sold date is not selected current date will be set\n" +
                "and click Save Changes");
        Pages.accountMaintenancePage().selectParticipant();
        String percent = "20";
        Pages.accountMaintenancePage().inputPercentageSold(percent);
        String servicingFee = "5";
        Pages.accountMaintenancePage().inputServicingFee(servicingFee);
        Pages.accountMaintenancePage().inputSoldDate(loanAccount.getDateOpened());
        Pages.accountMaintenancePage().clickSaveButton();

        logInfo("Step 7: \t\n" +
                "Select Participant in left part of the screen");
        Pages.accountMaintenancePage().clickAutotestRecord();
        Pages.accountMaintenancePage().checkPendingStatus();
        TestRailAssert.assertTrue(Pages.accountMaintenancePage().checkServicingFee(servicingFee), new CustomStepResult(
                "Servicing Fee is correct", "Servicing Fee is not correct"));
        TestRailAssert.assertTrue(Pages.accountMaintenancePage().checkPercentageSold(percent), new CustomStepResult(
                "Percentage sold is correct", "Percentage sold is not correct"));
        TestRailAssert.assertTrue(Pages.accountMaintenancePage().checkPartBalance(""), new CustomStepResult(
                "Part Balance is correct", "Part Balance is not correct"));
        TestRailAssert.assertTrue(Pages.accountMaintenancePage().checkSoldDate(loanAccount.getDateOpened()), new CustomStepResult(
                "Sold date is correct", "Sold date is not correct"));
        TestRailAssert.assertTrue(Pages.accountMaintenancePage().checkRepurchaseDate(""), new CustomStepResult(
                "Repurchase date is correct", "Repurchase date is not correct"));
        TestRailAssert.assertTrue(Pages.accountMaintenancePage().checkRepurchaseAmount(""), new CustomStepResult(
                "Repurchase amount is correct", "Repurchase amount is not correct"));
        TestRailAssert.assertTrue(Pages.accountMaintenancePage().checkPartAccruedInterest("0.00"), new CustomStepResult(
                "Participant accrued interest is correct", "Participant accrued interest is not correct"));
        TestRailAssert.assertTrue(Pages.accountMaintenancePage().checkFIOwnedAccruedInterest(accruedInterest), new CustomStepResult(
                "FI owned accrued interest is correct", "FI owned accrued interest is not correct"));
        TestRailAssert.assertTrue(Pages.accountMaintenancePage().checkPartServicingFee("0.00"), new CustomStepResult(
                "Part Servicing Fee is correct", "Part Servicing Fee is not correct"));
        TestRailAssert.assertTrue(Pages.accountMaintenancePage().isRepurchaseButtonDisabled(), new CustomStepResult(
                "Repurchase button is disabled", "Repurchase button is enabled"));

        logInfo("Step 8: Click \"Sell\" button");
        Pages.accountMaintenancePage().clickSellButton();




    }


}
