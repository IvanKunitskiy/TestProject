package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.debitcard.DebitCard;
import com.nymbus.newmodels.generation.bincontrol.BinControlConstructor;
import com.nymbus.newmodels.generation.bincontrol.builder.BinControlBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.debitcard.DebitCardConstructor;
import com.nymbus.newmodels.generation.debitcard.builder.DebitCardBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.settings.bincontrol.BinControl;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceData;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.NonTellerTransactionData;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Petro")
public class C25368_TransferFromDdaToSavForeignTest extends BaseTest {

    private final double foreignTransactionValue = 5.00;
    private BalanceData expectedBalanceDataForSavingAcc;
    private BalanceDataForCHKAcc expectedBalanceDataForCheckingAcc;
    private TransactionData savingAccTransactionData;
    private TransactionData chkAccTransactionData;
    private NonTellerTransactionData nonTellerTransactionData;
    private String savingsAccountNumber;
    private String checkingAccountNumber;
    private IndividualClient client;
    private double transactionWithForeignFeeAmount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client, Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up CHK and Savings accounts
        Account savingsAccount = new Account().setSavingsAccountData();
        Account checkAccount = new Account().setCHKAccountData();

        // Set up transaction
        Transaction glDebitMiscCreditTransaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        glDebitMiscCreditTransaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());
        double transactionAmount = glDebitMiscCreditTransaction.getTransactionDestination().getAmount();

        // Init nonTellerTransactionData
        nonTellerTransactionData = new NonTellerTransactionData();
        nonTellerTransactionData.setAmount(foreignTransactionValue);
        String terminalId = "FNBWT003";
        nonTellerTransactionData.setTerminalId(terminalId);

        // Ge the value of ForeignATMFee bank control setting
        double foreignFeeValue = Actions.nonTellerTransactionActions().getForeignFee(2);
        transactionWithForeignFeeAmount = foreignTransactionValue + foreignFeeValue;

        // Set up debit card and bin control
        DebitCardConstructor debitCardConstructor = new DebitCardConstructor();
        DebitCardBuilder debitCardBuilder = new DebitCardBuilder();
        debitCardConstructor.constructDebitCard(debitCardBuilder);
        DebitCard debitCard = debitCardBuilder.getCard();

        BinControlConstructor binControlConstructor = new BinControlConstructor();
        BinControlBuilder binControlBuilder = new BinControlBuilder();
        binControlConstructor.constructBinControl(binControlBuilder);
        BinControl binControl = binControlBuilder.getBinControl();
        binControl.setBinNumber("510986");
        binControl.setCardDescription("Consumer Debit");

        Actions.debitCardModalWindowActions().setDebitCardWithBinControl(debitCard, binControl);
        debitCard.getAccounts().add(savingsAccount.getAccountNumber());
        debitCard.getAccounts().add(checkAccount.getAccountNumber());
        debitCard.setNameOnCard(client.getNameForDebitCard());

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        checkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        logInFile("Create client - " + client.getFullName());

        // Create Savings  account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createSavingAccountForTransactionPurpose(savingsAccount);
        savingsAccountNumber = savingsAccount.getAccountNumber();
        logInFile("Create Sav account - " + savingsAccountNumber);

        // Create CHK account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);
        checkingAccountNumber = checkAccount.getAccountNumber();
        logInFile("Create CHK account - " + checkingAccountNumber);

        // Create debit card for saving acc
        createDebitCard(client.getInitials(), debitCard);
        Actions.debitCardModalWindowActions().setExpirationDateAndCardNumber(nonTellerTransactionData, 1);
        logInFile("Create debit card for Sav - " + nonTellerTransactionData.getCardNumber());

        // Re-login in system for updating teller session
        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Perform transaction
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(glDebitMiscCreditTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        Actions.clientPageActions().searchAndOpenClientByName(savingsAccountNumber);
        expectedBalanceDataForSavingAcc = AccountActions.retrievingAccountData().getBalanceData();

        Actions.clientPageActions().searchAndOpenClientByName(checkingAccountNumber);
        expectedBalanceDataForCheckingAcc = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        // Set up nonTeller transaction data
        savingAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "+", expectedBalanceDataForSavingAcc.getCurrentBalance(), transactionAmount);
        chkAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedBalanceDataForCheckingAcc.getCurrentBalance(), transactionAmount);

        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 25368, testRunName = TEST_RUN_NAME)
    @Test(description = "C25368, Transfer from DDA to SAV Foreign")
    @Severity(SeverityLevel.CRITICAL)
    public void transferFromDdaToSavForeignTest() {

        logInfo("Step 1: Go to the Swagger and log in as the User from the preconditions");
        logInfo("Step 2: Expand widgets-controller and run the following request  \n" +
                "with ONUS terminal ID (TermId is listed in 'bank owned atm ocations/bank.data.datmlc')");
        Actions.nonTellerTransactionActions().performATMTransaction(getFieldsMap(nonTellerTransactionData));

        String transcode = TransactionCode.ATM_DEBIT_MEMO_119.getTransCode().split("\\s+")[0];
        WebAdminActions.webAdminTransactionActions().setTransactionPostDateAndEffectiveDate(chkAccTransactionData, checkingAccountNumber, transcode);
        expectedBalanceDataForCheckingAcc.reduceAmount(transactionWithForeignFeeAmount);

        logInfo("Step 3: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 4: Search for CHK account that is assigned to the Debit Card from the precondition and verify its: \n" +
                "- current balance \n" +
                "- available balance \n" +
                "- Transactions history");
        Actions.clientPageActions().searchAndOpenClientByName(checkingAccountNumber);
        BalanceDataForCHKAcc actualBalanceDataForCHKAcc = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Assert.assertEquals(actualBalanceDataForCHKAcc.getCurrentBalance(), expectedBalanceDataForCheckingAcc.getCurrentBalance(),
                "CHK account current balance is not correct!");
        Assert.assertEquals(actualBalanceDataForCHKAcc.getAvailableBalance(), expectedBalanceDataForCheckingAcc.getAvailableBalance(),
                "CHK account available balance is not correct!");
        chkAccTransactionData.setBalance(expectedBalanceDataForCheckingAcc.getCurrentBalance());
        chkAccTransactionData.setAmount(foreignTransactionValue);

        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset);
        Assert.assertEquals(actualTransactionData, chkAccTransactionData, "Transaction data doesn't match!");
        Assert.assertEquals(Pages.accountTransactionPage().getTransactionItemsCount(), 3,
                "Transaction count is incorrect!");
        Assert.assertTrue(Actions.transactionActions().isTransactionCodePresent(TransactionCode.ATM_DEBIT_MEMO_119.getTransCode()),
                "119 - Debit Memo is present in transaction list");
        Assert.assertTrue(Actions.transactionActions().isTransactionCodePresent(TransactionCode.ATM_USAGE_129.getTransCode()),
                "129-ATM Usage Fee presents in transaction list");

        transcode = TransactionCode.ATM_CREDIT_MEMO_203.getTransCode().split("\\s+")[0];
        WebAdminActions.webAdminTransactionActions().setTransactionPostDateAndEffectiveDate(savingAccTransactionData, savingsAccountNumber, transcode);

        logInfo("Step 5: Search for the Savings account that is assigned to the Debit Card from the precondition and open it on Instructions tab");
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccountNumber);
        AccountActions.editAccount().goToInstructionsTab();

        logInfo("Step 6: Check the list of instructions for the account (if exist) and delete the Hold with type Reg CC");
        String INSTRUCTION_REASON = "Reg CC";
        AccountActions.createInstruction().deleteInstructionByReasonText(INSTRUCTION_REASON);
        AccountActions.editAccount().goToDetailsTab();

        logInfo("Step 7: Verify Savings account's: \n" +
                "- current balance \n" +
                "- available balance \n" +
                "- Transactions history");
        expectedBalanceDataForSavingAcc.addAmount(foreignTransactionValue);
        BalanceData actualBalanceDataForeSavingAcc = AccountActions.retrievingAccountData().getBalanceData();
        Assert.assertEquals(actualBalanceDataForeSavingAcc.getCurrentBalance(), expectedBalanceDataForSavingAcc.getCurrentBalance(),
                "Saving account current balance is not correct!");
        Assert.assertEquals(actualBalanceDataForeSavingAcc.getAvailableBalance(), expectedBalanceDataForSavingAcc.getAvailableBalance(),
                "Saving account available balance is not correct!");
        savingAccTransactionData.setBalance(expectedBalanceDataForSavingAcc.getCurrentBalance());
        savingAccTransactionData.setAmount(foreignTransactionValue);

        AccountActions.retrievingAccountData().goToTransactionsTab();
        offset = AccountActions.retrievingAccountData().getOffset();
        actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset);
        Assert.assertEquals(actualTransactionData, savingAccTransactionData, "Transaction data doesn't match!");

        logInfo("Step 8: Go to Client Maintenance and click [View all Cards] button in 'Cards Management' widget");
        logInfo("Step 9: Click [View History] link on the Debit Card from the precondition and \n" +
                "verify that transfer transaction is written to Debit Card History");
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Actions.debitCardModalWindowActions().goToCardHistory(1);
        double actualAmount = Actions.debitCardModalWindowActions().getTransactionAmount(1);
        Assert.assertEquals(actualAmount, transactionWithForeignFeeAmount, "Transaction amount is incorrect!");
    }

    private void createDebitCard(String clientInitials, DebitCard debitCard) {
        Actions.clientPageActions().searchAndOpenClientByName(clientInitials);
        Pages.clientDetailsPage().clickOnMaintenanceTab();
        Pages.clientDetailsPage().clickOnNewDebitCardButton();
        Actions.debitCardModalWindowActions().fillDebitCard(debitCard);
        Pages.debitCardModalWindow().clickOnSaveAndFinishButton();
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowInvisibility();
    }

    private Map<String, String> getFieldsMap(NonTellerTransactionData transactionData) {
        Map<String, String > result = new HashMap<>();
        result.put("0", "0200");
        result.put("3", "402010");
        result.put("4", transactionData.getAmount());
        result.put("11", String.valueOf(Generator.genInt(100000000, 922337203)));
        result.put("18", "6011");
        result.put("22", "801");
        result.put("35", String.format("%s=%s", transactionData.getCardNumber(), transactionData.getExpirationDate()));
        result.put("41", transactionData.getTerminalId());
        result.put("43", "Long ave. bld. 34      Nashville      US");
        result.put("58", "0000000002U");

        return  result;
    }
}
