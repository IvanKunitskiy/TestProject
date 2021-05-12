package com.nymbus.frontoffice.loansmanagement;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Generator;
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
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Frontoffice")
@Feature("Loans Management")
@Owner("Dmytro")
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
        Pages.participationsModalPage().checkLeftParticipant();

        logInfo("Step 4: Note right part of the screen");
        Pages.participationsModalPage().checkRightParticipant();

        logInfo("Step 5: Click \"+ Add New Participation\"");
        Pages.participationsModalPage().clickAddNewButton();
        TestRailAssert.assertTrue(Pages.participationsModalPage().isStatusDisabled(), new CustomStepResult(
                "Status is disabled", "Status is enabled"));

        logInfo("Step 6: Specify:\n" +
                "- Participant = any Participant from the drop-down box\n" +
                "- Percentage Sold = any Percentage\n" +
                "- Servicing Fee = any Percentage\n" +
                "- Sold Date = in the past\n" +
                "Please note if sold date is not selected current date will be set\n" +
                "and click Save Changes");
        Pages.participationsModalPage().selectParticipant();
        String percent = "20";
        Pages.participationsModalPage().inputPercentageSold(percent);
        String servicingFee = "5";
        Pages.participationsModalPage().inputServicingFee(servicingFee);
        Pages.participationsModalPage().inputSoldDate(loanAccount.getDateOpened());
        Pages.participationsModalPage().clickSaveButton();

        logInfo("Step 7: \t\n" +
                "Select Participant in left part of the screen");
        Pages.participationsModalPage().clickAutotestRecord();
        Pages.participationsModalPage().checkPendingStatus();
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkServicingFee(servicingFee), new CustomStepResult(
                "Servicing Fee is correct", "Servicing Fee is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkPercentageSold(percent), new CustomStepResult(
                "Percentage sold is correct", "Percentage sold is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkPartBalance(""), new CustomStepResult(
                "Part Balance is correct", "Part Balance is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkSoldDate(loanAccount.getDateOpened()), new CustomStepResult(
                "Sold date is correct", "Sold date is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkRepurchaseDate(""), new CustomStepResult(
                "Repurchase date is correct", "Repurchase date is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkRepurchaseAmount(""), new CustomStepResult(
                "Repurchase amount is correct", "Repurchase amount is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkPartAccruedInterest("0.00"), new CustomStepResult(
                "Participant accrued interest is correct", "Participant accrued interest is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkFIOwnedAccruedInterest(accruedInterest), new CustomStepResult(
                "FI owned accrued interest is correct", "FI owned accrued interest is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkPartServicingFee("0.00"), new CustomStepResult(
                "Part Servicing Fee is correct", "Part Servicing Fee is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().isRepurchaseButtonDisabled(), new CustomStepResult(
                "Repurchase button is disabled", "Repurchase button is enabled"));

        logInfo("Step 8: Click \"Sell\" button");
        Pages.participationsModalPage().clickSellButton();
        String alertMessageModalText = Pages.alertMessageModalPage().getAlertMessageModalText();
        TestRailAssert.assertTrue(alertMessageModalText.equals("The Sell will post transactions that can be viewed on Transaction History"),
                new CustomStepResult("'Modal text' is not valid", "'Modal text' is valid"));
        Pages.alertMessageModalPage().clickOkButton();
        Pages.participationsModalPage().waitForSoldStatusVisibleByIndex(1);
        int partBalance = balance * Integer.parseInt(percent) / 100;
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkPartBalance(partBalance + ".00")
                , new CustomStepResult("Part Balance is correct", "Part Balance is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkPercentageSold(percent), new CustomStepResult(
                "Percentage sold is correct", "Percentage sold is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().getParticipantStatusByIndex(1).equals("Sold"),
                new CustomStepResult("'Status' is not valid", "'Status' is valid"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkPartCurrentBalance(partBalance + ".00"),
                new CustomStepResult("Part Current Balance is correct", "Part Current Balance is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkFiOwnedBalance((balance - partBalance) + ".00"),
                new CustomStepResult("Part Current Balance is correct", "Part Current Balance is not correct"));

        SelenideTools.openUrlInNewWindow(Constants.WEB_ADMIN_URL);
        SelenideTools.switchTo().window(1);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        String interestEarned = WebAdminActions.webAdminUsersActions().getParticipantInterestEarnedValueByIndexFromInterest(
                clientRootId, 1);
        String serviceFeeEarned = WebAdminActions.webAdminUsersActions().getParticipantServiceFeeEarnedValueByIndexFromInterest(
                clientRootId, 1);
        WebAdminActions.loginActions().doLogoutProgrammatically();
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);

        TestRailAssert.assertTrue(Pages.participationsModalPage().checkPartAccruedInterest(String.format("%.2f",Double.parseDouble(interestEarned))),
                new CustomStepResult(
                "Participant accrued interest is correct", "Participant accrued interest is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkFIOwnedAccruedInterest(
                String.format("%.2f",Double.parseDouble(accruedInterest)-Double.parseDouble(interestEarned))), new CustomStepResult(
                "FI owned accrued interest is correct", "FI owned accrued interest is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkPartServicingFee(String.format("%.2f",Double.parseDouble(serviceFeeEarned)))
                , new CustomStepResult(
                "Part Servicing Fee is correct", "Part Servicing Fee is not correct"));
        double participantBalance = Double.parseDouble(Pages.participationsModalPage().getParticipantBalance());

        logInfo("Step 9: Go to the \"Transactions\" tab and verify generated transaction");
        Pages.participationsModalPage().clickCloseButton();// Get data
        Pages.accountDetailsPage().clickDetailsTab();
        double currentBalance = Double.parseDouble(Pages.accountDetailsPage().getCurrentBalanceFromHeaderMenu());
        double participationPercentSold = Integer.parseInt(Pages.accountDetailsPage().getParticipationPercentSold()) / (double) 100;
        Pages.accountDetailsPage().clickTransactionsTab();

        String effectiveDateValue = Pages.accountTransactionPage().getEffectiveDateValue(1);
        String transactionCode = Pages.accountTransactionPage().getTransactionCodeByIndex(1);
        double amountValue = AccountActions.retrievingAccountData().getAmountValue(1);
        TestRailAssert.assertTrue(transactionCode.equals(TransactionCode.PARTICIPATION_SELL_471.getTransCode()),
                new CustomStepResult("'Transaction code' is not valid", "'Transaction code' is valid"));
        TestRailAssert.assertTrue(effectiveDateValue.equals(loanAccount.getDateOpened()),
                new CustomStepResult("'Effective date' is not valid", "'Effective date' is valid"));
        TestRailAssert.assertTrue(amountValue == currentBalance * participationPercentSold,
                new CustomStepResult("'Amount' is not valid", "'Amount' is valid"));

        logInfo("Step 10: Go back to loan account from preconditions -> Maintenance -> Tools -> \"Loan Participations\" tool");
        Pages.aSideMenuPage().clickClientMenuItem();
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(loanAccount.getAccountNumber());
        Pages.accountDetailsPage().clickMaintenanceTab();
        AccountActions.accountMaintenanceActions().setTool(Tool.LOAN_PARTICIPATIONS);
        Pages.accountMaintenancePage().clickToolsLaunchButton();

        logInfo("Step 11: Select Participant record in the left part of the screen and click the \"Repurchase\" button");
        Pages.participationsModalPage().clickParticipantRowByIndex(1);
        Pages.participationsModalPage().clickRepurchaseButton();
        alertMessageModalText = Pages.alertMessageModalPage().getAlertMessageModalText();
        TestRailAssert.assertTrue(alertMessageModalText.equals("The Repurchase will post transactions that can be viewed on Transaction History"),
                new CustomStepResult("'Modal text' is not valid", "'Modal text' is valid"));
        Pages.alertMessageModalPage().clickOkButton();
        Pages.participationsModalPage().waitForRepurchaseStatusVisibleByIndex(1);
        TestRailAssert.assertTrue(Pages.participationsModalPage().getParticipantStatusByIndex(1).equals("Repurchased"),
                new CustomStepResult("'Status' is not valid", "'Status' is valid"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkPartBalance("0.00"), new CustomStepResult(
                "Part Balance is correct", "Part Balance is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkRepurchaseDate(DateTime.getDatePlusMonth(loanAccount.getDateOpened(),1)),
                new CustomStepResult(
                "Repurchase date is correct", "Repurchase date is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkRepurchaseAmount(partBalance + ".00"), new CustomStepResult(
                "Repurchase amount is correct", "Repurchase amount is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkPartPercentageSold("0"), new CustomStepResult(
                "Percentage sold is correct", "Percentage sold is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkPartCurrentBalance("0.00"),
                new CustomStepResult("Part Current Balance is correct", "Part Current Balance is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().checkFiOwnedBalance(balance + ".00"),
                new CustomStepResult("Part Current Balance is correct", "Part Current Balance is not correct"));
        TestRailAssert.assertTrue(Pages.participationsModalPage().isRepurchaseButtonDisabled(), new CustomStepResult(
                "Repurchase button is disabled", "Repurchase button is enabled"));
        Pages.participationsModalPage().clickCloseButton();

        logInfo("Step 12: Go to the \"Transactions\" tab and verify generated transaction");
        Pages.accountDetailsPage().clickTransactionsTab();
        TestRailAssert.assertTrue(Pages.accountTransactionPage().getTransactionCodeByIndex(1).equals(TransactionCode.PARTICIPATION_REPURCHASE_472.getTransCode()),
                new CustomStepResult("'Transaction code' is not valid", "'Transaction code' is valid"));
        TestRailAssert.assertTrue(Pages.accountTransactionPage().getEffectiveDateValue(1).equals(DateTime.getLocalDateOfPattern("MM/dd/yyyy")),
                new CustomStepResult("'Effective date' is not valid", "'Effective date' is valid"));
        TestRailAssert.assertTrue(AccountActions.retrievingAccountData().getAmountValue(1) == participantBalance,
                new CustomStepResult("'Amount' is not valid", "'Amount' is valid"));
    }


}
