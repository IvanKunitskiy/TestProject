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
import com.nymbus.newmodels.client.other.debitcard.types.TranslationTypeAllowed;
import com.nymbus.newmodels.generation.bincontrol.BinControlConstructor;
import com.nymbus.newmodels.generation.bincontrol.builder.BinControlBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.debitcard.DebitCardConstructor;
import com.nymbus.newmodels.generation.debitcard.builder.DebitCardBuilder;
import com.nymbus.newmodels.settings.bincontrol.BinControl;
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

public class C26508_DepositToDDAFallbackWithFullReversalOnusTest extends BaseTest {
    private IndividualClient client;
    private String chkAccountNumber;
    private double transactionAmount = 100.00;
    private TransactionData chkAccTransactionData;
    private BalanceDataForCHKAcc expectedBalanceDataForCheckingAcc;
    private NonTellerTransactionData nonTellerTransactionData;

    @BeforeMethod
    public void preCondition() {
        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up CHK account
        Account chkAccount = new Account().setCHKAccountData();
        chkAccountNumber = chkAccount.getAccountNumber();

        // Set up nonTeller transaction data
        nonTellerTransactionData = new NonTellerTransactionData();

        // Set up debit card and bin control
        DebitCardConstructor debitCardConstructor = new DebitCardConstructor();
        DebitCardBuilder debitCardBuilder = new DebitCardBuilder();
        debitCardConstructor.constructDebitCard(debitCardBuilder);
        DebitCard debitCard = debitCardBuilder.getCard();
        debitCard.setTranslationTypeAllowed(TranslationTypeAllowed.BOTH_PIN_AND_SIGNATURE);

        BinControlConstructor binControlConstructor = new BinControlConstructor();
        BinControlBuilder binControlBuilder = new BinControlBuilder();
        binControlConstructor.constructBinControl(binControlBuilder);
        BinControl binControl = binControlBuilder.getBinControl();
        binControl.setBinNumber("510986");
        binControl.setCardDescription("Consumer Debit");

        Actions.debitCardModalWindowActions().setDebitCardWithBinControl(debitCard, binControl);
        debitCard.getAccounts().add(chkAccount.getAccountNumber());
        debitCard.setNameOnCard(client.getNameForDebitCard());

        // Log in
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Get terminal ID
        String terminalId = Actions.nonTellerTransactionActions().getTerminalID(1);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(chkAccount);

        // Create debit card for CHK acc
        createDebitCard(client.getInitials(), debitCard);
        Actions.debitCardModalWindowActions().setExpirationDateAndCardNumber(nonTellerTransactionData, 1);

        Actions.clientPageActions().searchAndOpenClientByName(chkAccountNumber);
        expectedBalanceDataForCheckingAcc = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        // Set up nonTeller transaction data
        nonTellerTransactionData.setAmount(transactionAmount);
        nonTellerTransactionData.setTerminalId(terminalId);
        chkAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "+", expectedBalanceDataForCheckingAcc.getCurrentBalance(), transactionAmount);

        Actions.loginActions().doLogOut();
    }

    @Test(description = "C26508, Deposit to DDA Fallback with Full Reversal ONUS")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyDepositToDDAFallback() {
        Map<String, String> fields = getDepositTransactionFieldsMap(nonTellerTransactionData);

        logInfo("Step 1: Go to the Swagger and log in as the User from the preconditions");
        logInfo("Step 2: Expand widgets-controller->Bwidget._GenericProcess and run the following request:");
        Actions.nonTellerTransactionActions().performATMTransaction(fields, new String[] {"0200"});

        String transcode = TransactionCode.ATM_DEPOSIT_108.getTransCode().split("\\s+")[0];
        WebAdminActions.webAdminTransactionActions().setTransactionPostDateAndEffectiveDate(chkAccTransactionData, chkAccountNumber, transcode);

        logInfo("Step 3: Log in to the system as the User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 4: Search for CHK account from the precondition and open it on Instructions tab");
        String INSTRUCTION_REASON = "Reg CC";
        Actions.clientPageActions().searchAndOpenClientByName(chkAccountNumber);
        AccountActions.editAccount().goToInstructionsTab();

        logInfo("Step 5: Check the list of instructions for the account (if exist) and delete the Hold with type Reg CC");
        AccountActions.createInstruction().deleteInstructionByReasonText(INSTRUCTION_REASON);
        AccountActions.editAccount().goToDetailsTab();
        expectedBalanceDataForCheckingAcc.addAmount(transactionAmount);

        logInfo("Step 6: Verify Account's: \n" +
                "- current balance \n" +
                "- available balance \n" +
                "- Transactions history");
        BalanceDataForCHKAcc actualBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Assert.assertEquals(actualBalanceData.getCurrentBalance(), expectedBalanceDataForCheckingAcc.getCurrentBalance(), "CHK account current balance is not correct!");
        Assert.assertEquals(actualBalanceData.getAvailableBalance(), expectedBalanceDataForCheckingAcc.getAvailableBalance(), "CHK account available balance is not correct!");
        chkAccTransactionData.setBalance(expectedBalanceDataForCheckingAcc.getCurrentBalance());

        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset);
        Assert.assertEquals(actualTransactionData, chkAccTransactionData, "Transaction data doesn't match!");
        Actions.loginActions().doLogOut();

        logInfo("Step 7: Go back to Swagger and run the following request:");
        Map<String, String> debitMemoTransactionFields = getDebitMemoTransactionFieldsMap(nonTellerTransactionData);
        debitMemoTransactionFields.put("11", fields.get("11"));
        Actions.nonTellerTransactionActions().performATMTransaction(debitMemoTransactionFields, new String[] {"0420"});
        expectedBalanceDataForCheckingAcc.reduceAmount(transactionAmount);
        chkAccTransactionData.setAmountSymbol("-");

        logInfo("Step 8: Go back to the system; \n" +
                "Search for CHK account from the precondition and verify its: \n" +
                "- current balance \n" +
                "- available balance \n" +
                "- Transactions history");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.clientPageActions().searchAndOpenClientByName(chkAccountNumber);
        actualBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Assert.assertEquals(actualBalanceData.getCurrentBalance(), expectedBalanceDataForCheckingAcc.getCurrentBalance(), "CHK account current balance is not correct!");
        Assert.assertEquals(actualBalanceData.getAvailableBalance(), expectedBalanceDataForCheckingAcc.getAvailableBalance(), "CHK account available balance is not correct!");
        chkAccTransactionData.setAmountSymbol("-");
        chkAccTransactionData.setBalance(expectedBalanceDataForCheckingAcc.getCurrentBalance());

        AccountActions.retrievingAccountData().goToTransactionsTab();
        offset = AccountActions.retrievingAccountData().getOffset();
        actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset);
        Assert.assertEquals(actualTransactionData, chkAccTransactionData, "Transaction data doesn't match!");

        logInfo("Step 9: Go to Client Maintenance and click [View all Cards] button in 'Cards Management' widget");
        logInfo("Step 10: Click [View History] link on the Debit Card from the precondition");
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Actions.debitCardModalWindowActions().goToCardHistory(1);
        int transactionsCount = Pages.cardsManagementPage().getTransactionRowsCount();
        Assert.assertEquals(transactionsCount, 2, "Transaction count is incorrect!");
    }

    private void createDebitCard(String clientInitials, DebitCard debitCard) {
        Actions.clientPageActions().searchAndOpenClientByName(clientInitials);
        Pages.clientDetailsPage().clickOnMaintenanceTab();
        Pages.clientDetailsPage().clickOnNewDebitCardButton();
        Actions.debitCardModalWindowActions().fillDebitCard(debitCard);
        Pages.debitCardModalWindow().clickOnSaveAndFinishButton();
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowInvisibility();
    }

    private Map<String, String> getDepositTransactionFieldsMap(NonTellerTransactionData transactionData) {
        Map<String, String > result = new HashMap<>();
        result.put("0", "0200");
        result.put("3", "210020");
        result.put("4", transactionData.getAmount());
        result.put("11", String.valueOf(Generator.genInt(100000000, 922337203)));
        result.put("18", "6011");
        result.put("22", "021");
        result.put("23", "001");
        result.put("35", String.format("%s=%s", transactionData.getCardNumber(), transactionData.getExpirationDate()));
        result.put("37", "201206102");
        result.put("41", transactionData.getTerminalId());
        result.put("43", "Long ave. bld. 34      Nashville      US");
        result.put("48", "SHELL");
        result.put("58", "1000000001U");

        return  result;
    }

    private Map<String, String> getDebitMemoTransactionFieldsMap(NonTellerTransactionData transactionData) {
        Map<String, String > result = new HashMap<>();
        result.put("0", "0420");
        result.put("2", transactionData.getCardNumber());
        result.put("3", "210020");
        result.put("4", transactionData.getAmount());
        result.put("11", "123456");
        result.put("18", "6011");
        result.put("22", "021");
        result.put("23", "001");
        result.put("37", "201206102");
        result.put("41", transactionData.getTerminalId());
        result.put("43", "Long ave. bld. 34      Nashville      US");
        result.put("48", "SHELL");
        result.put("58", "1000000001U");
        result.put("90", "010000000000000000000000000000000000000000");

        return  result;
    }
}