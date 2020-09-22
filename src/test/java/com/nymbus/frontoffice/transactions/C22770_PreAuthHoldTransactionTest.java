package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.client.other.debitcard.DebitCard;
import com.nymbus.newmodels.client.other.debitcard.types.TranslationTypeAllowed;
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
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.NonTellerTransactionData;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class C22770_PreAuthHoldTransactionTest extends BaseTest {
    private NonTellerTransactionData nonTellerTransactionData;
    private IndividualClient client;
    private String checkingAccountNumber;
    private BalanceDataForCHKAcc expectedBalanceDataForCheckingAcc;
    private double holdAmount = 20.00;
    private TransactionData transactionData;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client, Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();
        Account checkAccount = new Account().setCHKAccountData();

        // Init nonTellerTransactionData
        nonTellerTransactionData = new NonTellerTransactionData();

        // Set up transaction
        Transaction glDebitMiscCreditTransaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();
        glDebitMiscCreditTransaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());

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
        debitCard.getAccounts().add(checkAccount.getAccountNumber());
        debitCard.setNameOnCard(client.getNameForDebitCard());
        debitCard.setAllowForeignTransactions(true);
        debitCard.setChargeForCardReplacement(true);
        debitCard.setTranslationTypeAllowed(TranslationTypeAllowed.BOTH_PIN_AND_SIGNATURE);

        // Log in and create client
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);
        checkingAccountNumber = checkAccount.getAccountNumber();

        // Create debit card for saving acc
        createDebitCard(client.getInitials(), debitCard);
        Actions.debitCardModalWindowActions().setExpirationDateAndCardNumber(nonTellerTransactionData, 1);

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

        String INSTRUCTION_REASON = "Reg CC";
        Actions.clientPageActions().searchAndOpenClientByName(checkingAccountNumber);
        AccountActions.editAccount().goToInstructionsTab();
        AccountActions.createInstruction().deleteInstructionByReasonText(INSTRUCTION_REASON);
        AccountActions.editAccount().goToDetailsTab();

        expectedBalanceDataForCheckingAcc = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        nonTellerTransactionData.setAmount(holdAmount);
        transactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedBalanceDataForCheckingAcc.getCurrentBalance(), holdAmount);

        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22770, Preauth (Hold) + Transaction")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyPreAuthHoldTransaction() {
        Map<String, String> fields = getFieldsMap(nonTellerTransactionData);

        logInfo("Step 1: Go to the Swagger and log in as the User from the preconditions");
        logInfo("Step 2: Expand widgets-controller->widget._GenericProcess and run the following request:");
        Actions.nonTellerTransactionActions().performATMTransaction(fields, new String[] {"0100"});
        expectedBalanceDataForCheckingAcc.reduceAvailableBalance(holdAmount);

        logInfo("Step 3: Log in to the system as the User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 4: Search for CHK account from the precondition and verify its: \n" +
                "- Current balance \n" +
                "- Available balance \n" +
                "- Transactions history \n" +
                "- Instructions");
        Actions.clientPageActions().searchAndOpenClientByName(checkingAccountNumber);
        BalanceDataForCHKAcc actualBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Assert.assertEquals(actualBalanceData.getCurrentBalance(), expectedBalanceDataForCheckingAcc.getCurrentBalance(), "CHK account current balance is not correct!");
        Assert.assertEquals(actualBalanceData.getAvailableBalance(), expectedBalanceDataForCheckingAcc.getAvailableBalance(), "CHK account available balance is not correct!");

        AccountActions.retrievingAccountData().goToTransactionsTab();
        Pages.accountTransactionPage().waitForTransactionSection();
        Assert.assertEquals(Pages.accountTransactionPage().getTransactionItemsCountWithZeroOption(), 1,
                "Transactions items count is incorrect!");
        int offset = AccountActions.retrievingAccountData().getOffset();
        Assert.assertFalse(Actions.transactionActions().isTransactionCodePresent(TransactionCode.DEBIT_PURCHASE_123.getTransCode(), offset),
                "123 - Debit Purchase is present in transaction list!");

        AccountActions.editAccount().goToInstructionsTab();
        Pages.accountInstructionsPage().clickInstructionInListByIndex(1);
        double actualHoldAmount = AccountActions.retrievingAccountData().getInstructionAmount();
        Assert.assertEquals(actualHoldAmount, holdAmount, "Hold amount is incorrect!");
        Actions.loginActions().doLogOut();
        fields.put("0", "200");

        logInfo("Step 5: Go to the Swagger and run the same request from Step2");
        Actions.nonTellerTransactionActions().performATMTransaction(fields, new String[] {"0200"});

        String transcode = TransactionCode.DEBIT_PURCHASE_123.getTransCode().split("\\s+")[0];
        WebAdminActions.webAdminTransactionActions().setTransactionPostDateAndEffectiveDate(transactionData, checkingAccountNumber, transcode);
        expectedBalanceDataForCheckingAcc.reduceCurrentBalance(holdAmount);
        transactionData.setBalance(expectedBalanceDataForCheckingAcc.getCurrentBalance());
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 6: Search for CHK account from the precondition and verify its: \n" +
                "- Current balance \n" +
                "- Available balance \n" +
                "- Transactions history \n" +
                "- Instructions");
        Actions.clientPageActions().searchAndOpenClientByName(checkingAccountNumber);
        actualBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Assert.assertEquals(actualBalanceData.getCurrentBalance(), expectedBalanceDataForCheckingAcc.getCurrentBalance(), "CHK account current balance is not correct!");
        Assert.assertEquals(actualBalanceData.getAvailableBalance(), expectedBalanceDataForCheckingAcc.getAvailableBalance(), "CHK account available balance is not correct!");

        AccountActions.retrievingAccountData().goToTransactionsTab();
        Pages.accountTransactionPage().waitForTransactionSection();
        offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset);
        Assert.assertEquals(actualTransactionData, transactionData, "Transaction data doesn't match!");
        Assert.assertTrue(Actions.transactionActions().isTransactionCodePresent(TransactionCode.DEBIT_PURCHASE_123.getTransCode(), offset),
                "123 - Debit Purchase is not present in transaction list!");

        AccountActions.editAccount().goToInstructionsTab();
        Pages.accountInstructionsPage().clickViewExpiredAndDeletedHold();
        Assert.assertEquals(Pages.accountInstructionsPage().getRowsCount(), 1, "Deleted hold rows count is incorrect!");
        Assert.assertEquals(AccountActions.retrievingAccountData().getDeletedInstructionAmount(1), holdAmount,"Deleted hold amount is incorrect!");
        Pages.accountInstructionsPage().clickCloseModalButton();
        Pages.accountInstructionsPage().waitForModalWindowInvisibility();

        logInfo("Step 7: Go to Client Maintenance and click [View all Cards] button in 'Cards Management' widget");
        logInfo("Step 8: Click [View History] link on the Debit Card from the precondition");
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Actions.debitCardModalWindowActions().goToCardHistory(1);
        int transactionsCount = Pages.cardsManagementPage().getTransactionRowsCount();
        Assert.assertEquals(transactionsCount, 2, "Transaction count is incorrect!");
    }

    private Map<String, String> getFieldsMap(NonTellerTransactionData transactionData) {
        Map<String, String > result = new HashMap<>();
        result.put("0", "0100");
        result.put("3", "000000");
        result.put("4", transactionData.getAmount());
        result.put("11", String.valueOf(Generator.genInt(100000000, 922337203)));
        result.put("18", "5541");
        result.put("22", "022");
        result.put("35", String.format("%s=%s", transactionData.getCardNumber(), transactionData.getExpirationDate()));
        result.put("43", "Long ave. bld. 34      Nashville      US");
        result.put("48", "SHELL");
        result.put("49", "840");
        result.put("57", "266");
        result.put("58", "10000000012");

        return  result;
    }

    private void createDebitCard(String clientInitials, DebitCard debitCard) {
        Actions.clientPageActions().searchAndOpenClientByName(clientInitials);
        Pages.clientDetailsPage().clickOnMaintenanceTab();
        Pages.clientDetailsPage().clickOnNewDebitCardButton();
        Actions.debitCardModalWindowActions().fillDebitCard(debitCard);
        Pages.debitCardModalWindow().clickOnSaveAndFinishButton();
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowInvisibility();
    }
}