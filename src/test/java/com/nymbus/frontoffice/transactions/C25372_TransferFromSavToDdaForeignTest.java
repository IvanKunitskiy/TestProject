package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.debitcard.DebitCard;
import com.nymbus.newmodels.generation.bincontrol.BinControlConstructor;
import com.nymbus.newmodels.generation.bincontrol.builder.BinControlBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.debitcard.DebitCardConstructor;
import com.nymbus.newmodels.generation.debitcard.builder.DebitCardBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.settings.bincontrol.BinControl;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceData;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.NonTellerTransactionData;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Petro")
public class C25372_TransferFromSavToDdaForeignTest extends BaseTest {

    private final String INSTRUCTION_REASON = "Reg CC";
    private final String terminalId = "FNBWT003";
    private final double foreignTransactionValue = 9.00;
    private NonTellerTransactionData nonTellerTransactionData;
    private IndividualClient client;
    private String savingsAccountNumber;
    private String checkingAccountNumber;
    private TransactionData savingAccTransactionData;
    private BalanceDataForCHKAcc expectedBalanceDataForCheckingAcc;
    private BalanceData expectedBalanceDataForSavingAcc;
    private TransactionData chkAccTransactionData;
    private double transactionWithForeignFeeAmount;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up CHK and Savings accounts
        Account savingsAccount = new Account().setSavingsAccountData();
        Account checkAccount = new Account().setCHKAccountData();

        // Set up transaction
        Transaction glDebitMiscCreditTransaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        glDebitMiscCreditTransaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());
        double transactionAmount = glDebitMiscCreditTransaction.getTransactionDestination().getAmount();

        // Set up nonTeller transaction data
        nonTellerTransactionData = new NonTellerTransactionData();
        nonTellerTransactionData.setAmount(foreignTransactionValue);
        nonTellerTransactionData.setTerminalId(terminalId);

        // Ge the value of ForeignATMFee bank control setting
        double foreignFeeValue = Actions.nonTellerTransactionActions().getForeignFee(1);
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
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create Savings account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createSavingAccountForTransactionPurpose(savingsAccount);
        savingsAccountNumber = savingsAccount.getAccountNumber();

        // Create CHK account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);
        checkingAccountNumber = checkAccount.getAccountNumber();

        // Create debit card for saving acc
        createDebitCard(client.getInitials(), debitCard);
        Actions.debitCardModalWindowActions().setExpirationDateAndCardNumber(nonTellerTransactionData, 1);

        // Re-login in system for updating teller session
        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Perform transaction
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(glDebitMiscCreditTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        Actions.clientPageActions().searchAndOpenClientByName(savingsAccountNumber);
        AccountActions.editAccount().goToInstructionsTab();
        AccountActions.createInstruction().deleteInstructionByReasonText(INSTRUCTION_REASON);
        AccountActions.editAccount().goToDetailsTab();
        expectedBalanceDataForSavingAcc = AccountActions.retrievingAccountData().getBalanceData();

        Actions.clientPageActions().searchAndOpenClientByName(checkingAccountNumber);
        expectedBalanceDataForCheckingAcc = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        savingAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedBalanceDataForSavingAcc.getCurrentBalance(), transactionAmount);
        chkAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "+", expectedBalanceDataForCheckingAcc.getCurrentBalance(), transactionAmount);

        Actions.loginActions().doLogOut();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    public void transferFromSavToDdaForeign() {

        logInfo("Step 1: Go to the Swagger and log in as the User from the preconditions");
        logInfo("Step 2: Expand widgets-controller and run the following request with FOREIGN terminal ID\n" +
                "(TermId is not listed in 'bank owned atm locations/bank.data.datmlc'):");
        Actions.nonTellerTransactionActions().performATMTransaction(getFieldsMap(nonTellerTransactionData));

        String transcode = TransactionCode.ATM_DEBIT_MEMO_219.getTransCode().split("\\s+")[0];
        WebAdminActions.webAdminTransactionActions().setTransactionPostDateAndEffectiveDate(savingAccTransactionData,
                savingsAccountNumber, transcode);
        expectedBalanceDataForSavingAcc.subtractAmount(transactionWithForeignFeeAmount);

        logInfo("Step 3: Log in to the system as the User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 4: Search for Saving account from the precondition and Verify Account's: \n" +
                "- current balance \n" +
                "- available balance \n" +
                "- Transactions history");
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccountNumber);
        BalanceData actualBalanceDataForSavingsAcc = AccountActions.retrievingAccountData().getBalanceData();
        Assert.assertEquals(actualBalanceDataForSavingsAcc.getCurrentBalance(), expectedBalanceDataForSavingAcc.getCurrentBalance(), "Saving account current balance is not correct!");
        Assert.assertEquals(actualBalanceDataForSavingsAcc.getAvailableBalance(), expectedBalanceDataForSavingAcc.getAvailableBalance(), "Saving account available balance is not correct!");
        savingAccTransactionData.setBalance(expectedBalanceDataForSavingAcc.getCurrentBalance());
        savingAccTransactionData.setAmount(foreignTransactionValue);

        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset);
        Assert.assertEquals(actualTransactionData, savingAccTransactionData, "Transaction data doesn't match!");
        Assert.assertEquals(Pages.accountTransactionPage().getTransactionItemsCount(), 3,
                "Transaction count is incorrect!");
        Assert.assertTrue(Actions.transactionActions().isTransactionCodePresent(TransactionCode.ATM_DEBIT_MEMO_219.getTransCode()),
                "219 - Debit Memo is present in transaction list");
        Assert.assertTrue(Actions.transactionActions().isTransactionCodePresent(TransactionCode.ATM_USAGE_229_FEE.getTransCode()),
                "229 - ATM Usage Fee is present in transaction list");

        transcode = TransactionCode.ATM_CREDIT_MEMO_103.getTransCode().split("\\s+")[0];
        WebAdminActions.webAdminTransactionActions().setTransactionPostDateAndEffectiveDate(chkAccTransactionData, checkingAccountNumber, transcode);

        logInfo("Step 5: Search for the CHK account that is assigned to the Debit Card from the precondition and open it on Instructions tab");
        logInfo("Step 6: Check the list of instructions for the account (if exist) and delete the Hold with type Reg CC");
        Actions.clientPageActions().searchAndOpenClientByName(checkingAccountNumber);
        AccountActions.editAccount().goToInstructionsTab();
        AccountActions.createInstruction().deleteInstructionByReasonText(INSTRUCTION_REASON);

        logInfo("Step 7: Verify Account's: \n" +
                "- current balance \n" +
                "- available balance \n" +
                "- Transactions history");
        AccountActions.editAccount().goToDetailsTab();
        expectedBalanceDataForCheckingAcc.addAmount(foreignTransactionValue);
        BalanceDataForCHKAcc actualBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Assert.assertEquals(actualBalanceData.getCurrentBalance(), expectedBalanceDataForCheckingAcc.getCurrentBalance(), "Checking account current balance is not correct!");
        Assert.assertEquals(actualBalanceData.getAvailableBalance(), expectedBalanceDataForCheckingAcc.getAvailableBalance(), "Checking account available balance is not correct!");
        chkAccTransactionData.setBalance(expectedBalanceDataForCheckingAcc.getCurrentBalance());
        chkAccTransactionData.setAmount(foreignTransactionValue);

        AccountActions.retrievingAccountData().goToTransactionsTab();
        offset = AccountActions.retrievingAccountData().getOffset();
        actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset);
        Assert.assertEquals(actualTransactionData, chkAccTransactionData, "Transaction data doesn't match!");

        logInfo("Step 8: Go to Client Maintenance and click [View all Cards] button in 'Cards Management' widget");
        logInfo("Step 9: Click [View History] link on the Debit Card from the precondition");
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
        result.put("3", "4010200");
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
