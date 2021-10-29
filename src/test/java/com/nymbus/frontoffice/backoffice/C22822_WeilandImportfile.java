package com.nymbus.frontoffice.backoffice;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.*;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.accountinstructions.ActivityHoldInstruction;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.accountinstructions.InstructionConstructor;
import com.nymbus.newmodels.generation.accountinstructions.builder.ActivityHoldInstructionBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.GLDebitDepositCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

@Epic("Backoffice")
@Feature("Account Analysis")
@Owner("Dmytro")
public class C22822_WeilandImportfile extends BaseTest {

    private String chkAccountNumber;
    private String chkAccountNumber2;
    private String chkAccountNumber3;
    private String chkAccountNumber4;
    private String chkAccountNumber5;
    private ActivityHoldInstruction instruction;
    private String systemDate;
    private double amount = 1.99;
    private String overdraftLimit = "$ 500.00";
    TransactionData chkTransactionData;

    @BeforeMethod
    public void preCondition() {
        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        IndividualClient client = individualClientBuilder.buildClient();
        Transaction depositTransaction = new TransactionConstructor(new GLDebitDepositCHKAccBuilder()).constructTransaction();

        // Set up CHK  account
        Account chkAccount = new Account().setCHKAccountData();
        Account chkAccount2 = new Account().setCHKAccountData();
        Account chkAccount3 = new Account().setCHKAccountData();
        Account chkAccount4 = new Account().setCHKAccountData();
        Account chkAccount5 = new Account().setCHKAccountData();
        systemDate = WebAdminActions.loginActions().getSystemDate();

        // Set up instruction
        InstructionConstructor instructionConstructor = new InstructionConstructor(new ActivityHoldInstructionBuilder());
        instruction = instructionConstructor.constructInstruction(ActivityHoldInstruction.class);

        // Log in
        SelenideTools.openUrl(Constants.URL);
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set product
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, "MSBC"));
        chkAccount2.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, "MSBC"));
        chkAccount3.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, "MSBC"));
        chkAccount4.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, "MSBC"));
        chkAccount5.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, "MSBC"));
        chkAccount.setAccountNumber("12400"+ chkAccount.getAccountNumber().substring(0,6));
        chkAccount2.setAccountNumber("12400"+ chkAccount2.getAccountNumber().substring(0,6));
        chkAccount3.setAccountNumber("12400"+ chkAccount3.getAccountNumber().substring(0,6));
        chkAccount4.setAccountNumber("12400"+ chkAccount4.getAccountNumber().substring(0,6));
        chkAccount5.setAccountNumber("12400"+ chkAccount5.getAccountNumber().substring(0,6));
        chkAccountNumber = chkAccount.getAccountNumber();
        chkAccountNumber2 = chkAccount2.getAccountNumber();
        chkAccountNumber3 = chkAccount3.getAccountNumber();
        chkAccountNumber4 = chkAccount4.getAccountNumber();
        chkAccountNumber5 = chkAccount5.getAccountNumber();

        //Create file
        List<String> accounts = Arrays.asList(chkAccountNumber, chkAccountNumber2, chkAccountNumber3,
                chkAccountNumber4, chkAccountNumber5);
        IOUtils io = new IOUtils();
        io.createWeilandFile(accounts,amount);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK accounts
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccount(chkAccount);
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccount(chkAccount2);
        AccountActions.editAccount().editOverdraftStatusAndLimit(overdraftLimit);
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccount(chkAccount3);
        Pages.accountDetailsPage().clickCloseAccountButton();
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccount(chkAccount4);
        Pages.accountNavigationPage().clickInstructionsTab();
        int instructionsCount = AccountActions.createInstruction().getInstructionCount();
        AccountActions.createInstruction().createActivityHoldInstruction(instruction);
        Pages.accountInstructionsPage().waitForCreatedInstruction(instructionsCount + 1);
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccount(chkAccount5);
        // Set up transaction with account number
        // Perform deposit transactions
        depositTransaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();

        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        depositTransaction.getTransactionDestination().setAccountNumber(chkAccount4.getAccountNumber());
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().doLoginTeller();
        Actions.transactionActions().createTransaction(depositTransaction);
        Actions.transactionActions().clickCommitButton();
        Actions.transactionActions().fillingSupervisorModal(userCredentials);
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();

        chkTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", depositTransaction.getTransactionSource().getAmount() - amount, amount);
    }


    private final String TEST_RUN_NAME = "Account Analysis";

    @TestRailIssue(issueID = 22822, testRunName = TEST_RUN_NAME)
    @Test(description = "C22822, Weiland: Import file")
    @Severity(SeverityLevel.CRITICAL)
    public void weilandImportFile() {
        logInfo("Step 1: Log in to the system as the user from the precondition");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to BackOffice page");
        Pages.aSideMenuPage().clickBackOfficeMenuItem();

        logInfo("Step 3: Open \"File Import\" widget drop-down - select \"Weilad Charge File\" - click \"Import\"");
        Pages.backOfficePage().chooseFileImportType("Weiland");
        Pages.backOfficePage().clickImport();

        logInfo("Step 4: Drag&Drop Weiland file from preconditions and click [Import] button");
        Pages.backOfficePage().importFile(Functions.getFilePathByName("weiland.IF3"));

        logInfo("Step 5: Verify \"Import Files\" window");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(1,chkAccountNumber),
                "Import table does not match");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(2,String.valueOf(amount)),
                "Import table does not match");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(3,"Analysis Charges AutoTest"),
                "Import table does not match");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(4,chkAccountNumber2),
                "Import table does not match");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(5,String.valueOf(amount)),
                "Import table does not match");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(6,"Analysis Charges AutoTest"),
                "Import table does not match");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(7,chkAccountNumber3),
                "Import table does not match");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(8,String.valueOf(amount)),
                "Import table does not match");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(9,"Analysis Charges AutoTest"),
                "Import table does not match");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(10,chkAccountNumber4),
                "Import table does not match");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(11,String.valueOf(amount)),
                "Import table does not match");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(12,"Analysis Charges AutoTest"),
                "Import table does not match");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(13,chkAccountNumber5),
                "Import table does not match");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(14,String.valueOf(amount)),
                "Import table does not match");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(15,"Analysis Charges AutoTest"),
                "Import table does not match");
        if (Constants.getEnvironment().equals("dev29")){
            Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(16,"1111100"),
                    "Import table does not match");
        } else {
            Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(16,"1001000"),
                    "Import table does not match");
        }

        Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(17,String.valueOf(amount*5)),
                "Import table does not match");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromImportFilesTable(18,"Analysis Charges AutoTest"),
                "Import table does not match");

        logInfo("Step 6: Click [Post] button");
        Pages.backOfficePage().clickPostButton();

        logInfo("Step 7: Verify results for CHK#1");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromSpanImportFilesTable(3,""),
                "Reject reason does not match");

        logInfo("Step 8: In another tab search for CHK#1 and open it on Transactions tab\n" +
                "Verify that \"120 - Service charge\" transaction was generated for account");
        SelenideTools.openUrlInNewWindow(Constants.URL);
        SelenideTools.switchTo().window(1);
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccountNumber);
        Pages.accountDetailsPage().clickTransactionsTab();
        TransactionData actualSavFeeTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithBalanceSymbol();
        Assert.assertEquals(actualSavFeeTransactionData, chkTransactionData, "Transaction data doesn't match!");
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);

        logInfo("Step 9: Verify results for CHK#2");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromSpanImportFilesTable(6,""),
                "Reject reason does not match");

        logInfo("Step 10: In another tab search for CHK#2 and open it on Transactions tab\n" +
                "Verify that \"120 - Service charge\" transaction was generated for account");
        SelenideTools.openUrlInNewWindow(Constants.URL);
        SelenideTools.switchTo().window(1);
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccountNumber2);
        Pages.accountDetailsPage().clickTransactionsTab();
        actualSavFeeTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithBalanceSymbolForMinus();
        chkTransactionData.setBalance(amount);
        actualSavFeeTransactionData.setEffectiveDate(actualSavFeeTransactionData.getPostingDate());
        Assert.assertEquals(actualSavFeeTransactionData, chkTransactionData, "Transaction data doesn't match!");
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);

        logInfo("Step 11: Verify results for CHK#3");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromSpanImportFilesTable(9,"This account is closed."),
                "Reject reason does not match");

        logInfo("Step 12: In another tab search for CHK#3 and open it on Transactions tab\n" +
                "Verify that \"120 - Service charge\" transaction was NOT generated on account");
        SelenideTools.openUrlInNewWindow(Constants.URL);
        SelenideTools.switchTo().window(1);
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccountNumber3);
        Pages.accountDetailsPage().clickTransactionsTab();
        actualSavFeeTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithBalanceSymbolForMinusAmount();
        chkTransactionData.setBalance(0);
        chkTransactionData.setAmount(0);
        chkTransactionData.setAmountSymbol("+");
        actualSavFeeTransactionData.setEffectiveDate(actualSavFeeTransactionData.getPostingDate());
        Assert.assertEquals(actualSavFeeTransactionData, chkTransactionData, "Transaction data doesn't match!");
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);

        logInfo("Step 13: Verify results for CHK#4");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromSpanImportFilesTable(12,"Activity hold entry found"),
                "Reject reason does not match");

        logInfo("Step 14: In another tab search for CHK#4 and open it on Transactions tab\n" +
                "Verify that \"120 - Service charge\" transaction was NOT generated on account");
        SelenideTools.openUrlInNewWindow(Constants.URL);
        SelenideTools.switchTo().window(1);
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccountNumber4);
        Pages.accountDetailsPage().clickTransactionsTab();
        actualSavFeeTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithBalanceSymbol();
        chkTransactionData.setBalance(100);
        chkTransactionData.setAmount(100);
        chkTransactionData.setAmountSymbol("+");
        Assert.assertEquals(actualSavFeeTransactionData, chkTransactionData, "Transaction data doesn't match!");
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);

        logInfo("Step 15: Verify results for CHK#5");
        Assert.assertTrue(Pages.backOfficePage().checkValueFromSpanImportFilesTable(15,"NSF on account"),
                "Reject reason does not match");

        logInfo("Step 16: In another tab search for CHK#5 and open it on Transactions tab\n" +
                "Verify that \"120 - Service charge\" transaction was NOT generated on account");
        SelenideTools.openUrlInNewWindow(Constants.URL);
        SelenideTools.switchTo().window(1);
        Actions.clientPageActions().searchAndOpenAccountByAccountNumber(chkAccountNumber5);
        Pages.accountDetailsPage().clickTransactionsTab();
        Assert.assertTrue(Pages.accountTransactionPage().isNoResultsVisible(), "Transaction data doesn't match!");
        SelenideTools.closeCurrentTab();
        SelenideTools.switchTo().window(0);
    }
}
