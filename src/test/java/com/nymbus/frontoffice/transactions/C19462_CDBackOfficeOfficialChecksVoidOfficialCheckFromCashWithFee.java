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
public class C19462_CDBackOfficeOfficialChecksVoidOfficialCheckFromCashWithFee extends BaseTest {

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


    @BeforeMethod
    public void prepareTransactionData() {
        //Check CFMIntegrationEnabled
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(),userCredentials.getPassword());
        if (WebAdminActions.webAdminUsersActions().isCFMIntegrationEnabled()) {
            throw new SkipException("CFMIntegrationEnabled = 1");
        }
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
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().openProofDateLoginModalWindow();
        String bankBranch = Pages.tellerModalPage().getBankBranch();
        fullCheck.setBranch(bankBranch);
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
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 19462, testRunName = TEST_RUN_NAME)
    @Test(description = "C19462, [CD] BackOffice->Official Checks: Void official check from cash with fee")
    @Severity(SeverityLevel.CRITICAL)
    public void cDTTellerSessionCommitOfficialCheckFromCashWithFee() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Back Office -> Official Checks and search for the Transaction from the preconditions");
        Pages.aSideMenuPage().clickBackOfficeMenuItem();
        Pages.backOfficePage().clickOfficialChecks();

        logInfo("Step 3: Open Official check on Details");
        Pages.checkPage().clickToCheck(checkNumber);
        TestRailAssert.assertFalse(Pages.fullCheckPage().isVoidDisabled(),new CustomStepResult("Void button is not disabled",
                "Void button is disabled"));

        logInfo("Step 4: Click [Void] button");
        Pages.fullCheckPage().clickVoid();
        TestRailAssert.assertTrue(Pages.fullCheckPage().checkConfirmation(), new CustomStepResult("Confirmation is present",
                "Confirmation is not present"));
        TestRailAssert.assertFalse(Pages.fullCheckPage().isVoidDisabled(),new CustomStepResult("Void button is disabled",
                "Void button is not disabled"));

        logInfo("Step 5: Select Yes option and verify the Status field");
        Pages.fullCheckPage().clickYes();
        SelenideTools.sleep(Constants.MINI_TIMEOUT);
        TestRailAssert.assertEquals(Pages.fullCheckPage().getStatus(),"Void", new CustomStepResult("Status match",
                "Status doesn't match"));

        logInfo("Step 6: Navigate to Cash Drawer screen");
        logInfo("Step 7: Verify Cash Drawer\n" +
                "- denominations\n" +
                "- total cash in");
        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        SelenideTools.sleep(Constants.MICRO_TIMEOUT);
        Pages.tellerModalPage().clickEnterButton();
        Pages.tellerModalPage().waitForModalInvisibility();
        TestRailAssert.assertEquals(Actions.cashDrawerAction().getCashDrawerData(),
                cashData,
                new CustomStepResult("Cash data is correct", "Cash data not correct"));
    }
}
