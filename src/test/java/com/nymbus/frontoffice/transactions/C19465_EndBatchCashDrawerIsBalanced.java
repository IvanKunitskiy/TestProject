package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.backoffice.Check;
import com.nymbus.newmodels.backoffice.FullCheck;
import com.nymbus.newmodels.cashier.CashierDefinedTransactions;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.CashInDepositCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.Denominations;
import com.nymbus.newmodels.transaction.verifyingModels.CashDrawerData;
import com.nymbus.pages.Pages;
import com.nymbus.pages.settings.SettingsPage;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C19465_EndBatchCashDrawerIsBalanced extends BaseTest {
    private Transaction transaction;
    private double returnTransactionAmount = 100.00;
    private double fee = 5.00;
    private int checkAccountNumber;
    private Check check;
    private String name = "John";
    private FullCheck fullCheck;
    private String clientId;
    private CashDrawerData cashData;
    private String checkNumber;
    private String cashRecycler;
    private String teller;

    @BeforeMethod
    public void prepareTransactionData() {
        //Check CFMIntegrationEnabled
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        if (WebAdminActions.webAdminUsersActions().isCFMIntegrationEnabled()) {
            throw new SkipException("CFMIntegrationEnabled = 1");
        }
        cashRecycler = WebAdminActions.webAdminUsersActions().getCashRecyclerNameByRawIndex(1);
        WebAdminActions.loginActions().doLogout();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();

        // Set up Client and Accounts
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        transaction = new TransactionConstructor(new CashInDepositCHKAccBuilder()).constructTransaction();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        clientId = Pages.clientDetailsPage().getClientID();


        // Set up transactions with account number
        transaction.getTransactionSource().setAmount(returnTransactionAmount);
        transaction.getTransactionDestination().setAccountNumber("4800200");
        transaction.getTransactionDestination().setAmount(returnTransactionAmount);
        transaction.getTransactionSource().setAccountNumber("4800200");
        transaction.getTransactionSource().setDenominationsHashMap(new HashMap<>());
        transaction.getTransactionSource().getDenominationsHashMap().put(Denominations.HUNDREDS, 100.00);

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        //Check Official check and printer
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().clickViewAllPrinters();
        SettingsPage.printerPage().clickToPrinter("Xerox_Phaser_3260 Moscow 2nd floor");
        boolean isOfficial = SettingsPage.printerPage().checkIsOfficialPrinter();
        Assert.assertTrue(isOfficial, "Is not official printer");
        Pages.aSideMenuPage().clickSettingsMenuItem();
        Pages.settings().waitForSettingsPageLoaded();
        SettingsPage.mainPage().clickViewControls();
        checkAccountNumber = Integer.parseInt(SettingsPage.officialComtrolPage().checkAccountNumber());

        //New Checks
        check = new Check();
        check.setDate(DateTime.getLocalDateOfPattern("MM/dd/yyyy"));
        check.setCheckType("Cashiers Check");
        check.setPayee(name);
        check.setPurchaser(client.getInitials());
        check.setInitials(userCredentials.getUserName() + " " + userCredentials.getUserName());
        check.setAmount(returnTransactionAmount - fee);
        check.setStatus("Outstanding");
        fullCheck = new FullCheck();
        fullCheck.fromCheck(check);
        fullCheck.setFee(fee);
        fullCheck.setCashPurchased("YES");
        fullCheck.setRemitter(client.getNameForDebitCard());
        fullCheck.setDocumentType(client.getIndividualClientDetails().getDocuments().get(0).getIdType().getIdType());
        fullCheck.setDocumentID(client.getIndividualClientDetails().getDocuments().get(0).getIdNumber());
        fullCheck.setPhone(client.getIndividualClientDetails().getPhones().get(client.getIndividualClientDetails().getPhones().size() - 1).getPhoneNumber());
        fullCheck.setPurchaseAccount("");

        //Check CDT template
        boolean templateNotExists = Actions.cashierDefinedActions().checkCDTTemplateIsExist(CashierDefinedTransactions.OFFICIAL_CHECK_WITH_CASH);
        if (templateNotExists) {
            boolean isCreated = Actions.cashierDefinedActions().createOfficialCheckWithCash();
            Assert.assertTrue(isCreated, "CDT template not created");
        }


        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        Actions.transactionActions().doLoginTeller();
        cashData = Actions.cashDrawerAction().getCashDrawerData();
        Actions.loginActions().doLogOut();

        //Commit official check with fee
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.transactionActions().openProofDateLoginModalWindow();
        String bankBranch = Pages.tellerModalPage().getBankBranch();
        fullCheck.setBranch(bankBranch);
        Pages.tellerModalPage().clickTeller();
        Pages.tellerModalPage().clickTellerOption(userCredentials.getUserName() + " " + userCredentials.getUserName());
        Pages.tellerModalPage().clickCashRecycler();
        Pages.tellerModalPage().clickCashRecyclerItem(cashRecycler);
        Pages.tellerModalPage().clickSide();
        Pages.tellerModalPage().clickLeftSide();
        Actions.transactionActions().doLoginProofDate();
        Pages.aSideMenuPage().waitForASideMenu();
        Pages.aSideMenuPage().clickCashierDefinedTransactionsMenuItem();

        Actions.cashierDefinedActions().createOfficialCheckTransaction(CashierDefinedTransactions.OFFICIAL_CHECK_WITH_CASH,
                transaction, false, name);
        Actions.transactionActions().clickCommitButton();
        Pages.verifyConductorModalPage().typeSearchField(clientId);
        Pages.verifyConductorModalPage().clickSearchDiv();
        Pages.verifyConductorModalPage().clickVerifyButton();
        SelenideTools.sleep(Constants.SMALL_TIMEOUT);
        checkNumber = Pages.confirmModalPage().getReprintCheckNumber();
        check.setCheckNumber(checkNumber);
        fullCheck.setCheckNumber(checkNumber);
        Pages.confirmModalPage().clickYes();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        Pages.confirmModalPage().clickYes();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        Pages.confirmModalPage().clickNo();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        Actions.loginActions().doLogOutProgrammatically();
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 19465, testRunName = TEST_RUN_NAME)
    @Test(description = "C19465, End batch - Cash Drawer is balanced")
    @Severity(SeverityLevel.CRITICAL)
    public void cDTTellerSessionCommitOfficialCheckFromCashWithFee() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Journal or Teller page and log in to proof date");
        Pages.aSideMenuPage().waitForASideMenu();
        Pages.aSideMenuPage().clickJournalMenuItem();
        Pages.journalPage().waitForProofDateSpan();
        Pages.tellerModalPage().clickTeller();
        Pages.tellerModalPage().clickTellerOption(userCredentials.getUserName() + " " + userCredentials.getUserName());
        Pages.tellerModalPage().clickCashRecycler();
        Pages.tellerModalPage().clickCashRecyclerItem(cashRecycler);
        Pages.tellerModalPage().clickSide();
        Pages.tellerModalPage().clickLeftSide();
        Actions.transactionActions().doLoginProofDate();

        logInfo("Step 3: Click [End Batch] button");
        Pages.journalPage().clickEndBatchButton();

        logInfo("Step 4: Click [Enter Amounts] and then click [Commit] button");
        Pages.journalPage().clickEnterAmountsButton();
        Pages.journalPage().clickCommitButton();
        Pages.journalPage().clickCancelButton();

        logInfo("Step 5: Log in to the Proof Date again");
        Pages.journalPage().waitForProofDateSpan();
        Pages.tellerModalPage().clickTeller();
        teller = userCredentials.getUserName() + " " + userCredentials.getUserName();
        Pages.tellerModalPage().clickTellerOption(teller);
        Pages.tellerModalPage().clickCashRecycler();
        Pages.tellerModalPage().clickCashRecyclerItem(cashRecycler);
        Pages.tellerModalPage().clickSide();
        Pages.tellerModalPage().clickLeftSide();
        Actions.transactionActions().doLoginProofDate();

        logInfo("Step 6: Search for transactions from preconditions on the Journal after end batch is done");
        Pages.journalPage().searchCDT(CashierDefinedTransactions.OFFICIAL_CHECK_WITH_CASH.getOperation());
        Pages.journalPage().clickCheckbox("Cashier-Defined", 1);
        Pages.journalPage().clickCheckbox("Transaction", 2);
        Pages.journalPage().clickCheckbox("Transaction", 3);
        SelenideTools.sleep(Constants.MINI_TIMEOUT);

        logInfo("Step 7: Open any batched transaction on Details and search for [Void] button");
        Pages.journalPage().clickItemInTable(1);
        Pages.journalPage().isVoidNotVisible();

        logInfo("Step 8: Go to BackOffice->Batch Processing / Outgoing Cash Letter page");
        Pages.aSideMenuPage().clickBackOfficeMenuItem();
        Pages.backOfficePage().clickBatchProcessingButton();

        logInfo("Step 9: Search for performed batch");
        Pages.batchProcessingPage().clickTellerSearch();
        Pages.batchProcessingPage().clickTellerOption(teller);
        Pages.batchProcessingPage().clickSearchButton();
        TestRailAssert.assertEquals(Pages.batchProcessingPage().getStatusTransaction(),"Balanced - Not Exported",
                new CustomStepResult("Status is ok", "Status is not ok"));

    }
}
