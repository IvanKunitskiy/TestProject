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

public class C26517_AFTMoneyTransferTest extends BaseTest {

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

        // Set up CHK and Savings accounts
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
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

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
        chkAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "+", expectedBalanceDataForCheckingAcc.getCurrentBalance(), transactionAmount);

        Actions.loginActions().doLogOut();
    }

    @Test(description = "C26517, AFT Money Transfer")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyAFTMoneyTransfer() {
        Map<String, String> fields = getPreAuthFieldsMap(nonTellerTransactionData);

        logInfo("Step 1: Go to the Swagger and log in as the User from the preconditions");
        logInfo("Step 2: Expand widgets-controller->Bwidget._GenericProcess and run the following Pre-Auth request :");
        Actions.nonTellerTransactionActions().performATMTransaction(fields, new String[] {"0100"});

        logInfo("Step 3: Log in to the system as the User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 4: Search for CHK account from the precondition and verify its: \n" +
                "- Current balance \n" +
                "- Available balance");
        Actions.clientPageActions().searchAndOpenClientByName(chkAccountNumber);
        BalanceDataForCHKAcc actualBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Assert.assertEquals(actualBalanceData.getCurrentBalance(), expectedBalanceDataForCheckingAcc.getCurrentBalance(), "CHK account current balance is not correct!");
        Assert.assertEquals(actualBalanceData.getAvailableBalance(), expectedBalanceDataForCheckingAcc.getAvailableBalance(), "CHK account available balance is not correct!");

        logInfo("Step 5: Open account on Transactions tab and verify Transactions history. \n" +
                "Make sure that there is no ATM transaction in Transactions tab");
        AccountActions.retrievingAccountData().goToTransactionsTab();
        Pages.accountTransactionPage().waitForTransactionSection();
        Assert.assertEquals(Pages.accountTransactionPage().getTransactionItemsCountWithZeroOption(), 0,
                "Transaction items count is incorrect!");

        logInfo("Step 6: Go to Client Maintenance and click [View all Cards] button in 'Cards Management' widget; \n" +
                "Click [View History] link on the Debit Card from the precondition and verify that transfer transaction is written to Debit Card History");
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Actions.debitCardModalWindowActions().goToCardHistory(1);

        String transactionReasonCode = Pages.cardsManagementPage().getTransactionReasonCode(1);
        Assert.assertEquals(transactionReasonCode, "00 -- Approved or completed successfully",
                "'Transaction Reason Code' is not equal to '00 -- Approved or completed successfully'");

        String transactionDescription = Pages.cardsManagementPage().getTransactionDescription(1);
        Assert.assertEquals(transactionDescription, "",
                "Transaction description is not equal to ''");

        double actualAmount = Actions.debitCardModalWindowActions().getTransactionAmount(1);
        Assert.assertEquals(actualAmount, transactionAmount, "Transaction amount is incorrect!");

        Map<String, String> completionFields = getCompletionFieldsMap(nonTellerTransactionData);
        completionFields.put("11", fields.get("11"));

        logInfo("Step 7: Go back to swagger; \n" +
                "Expand widgets-controller and run the following Completion request :");
        Actions.nonTellerTransactionActions().performATMTransaction(completionFields, new String[] {"0200"});

        String transcode = TransactionCode.ATM_CREDIT_MEMO_103.getTransCode().split("\\s+")[0];
        WebAdminActions.webAdminTransactionActions().setTransactionPostDateAndEffectiveDate(chkAccTransactionData, chkAccountNumber, transcode);

        logInfo("Step 8: Search for CHK account from the precondition and open it on Instructions tab");
        expectedBalanceDataForCheckingAcc.addAmount(transactionAmount);
        Actions.clientPageActions().searchAndOpenClientByName(chkAccountNumber);
        AccountActions.editAccount().goToInstructionsTab();

        logInfo("Step 9: Check the list of instructions for the account (if exist) and delete the Hold with type Reg CC");
        String INSTRUCTION_REASON = "Reg CC";
        AccountActions.createInstruction().deleteInstructionByReasonText(INSTRUCTION_REASON);

        logInfo("Step 10: Search for CHK account from the precondition and verify its: \n" +
                "- Current balance \n" +
                "- Available balance \n" +
                "- Transactions history \n");
        AccountActions.editAccount().goToDetailsTab();
        actualBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Assert.assertEquals(actualBalanceData.getCurrentBalance(), expectedBalanceDataForCheckingAcc.getCurrentBalance(), "Checking account current balance is not correct!");
        Assert.assertEquals(actualBalanceData.getAvailableBalance(), expectedBalanceDataForCheckingAcc.getAvailableBalance(), "Checking account available balance is not correct!");
        chkAccTransactionData.setBalance(expectedBalanceDataForCheckingAcc.getCurrentBalance());

        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset);
        Assert.assertTrue(Actions.transactionActions().isTransactionCodePresent(TransactionCode.ATM_CREDIT_MEMO_103.getTransCode(), offset),
                "103 - Credit Memo isn't present in transaction list!");
        Assert.assertEquals(actualTransactionData, chkAccTransactionData, "Transaction data doesn't match!");

        logInfo("Step 11: Go to Client Maintenance and click [View all Cards] button in 'Cards Management' widget; \n" +
                "Click [View History] link on the Debit Card from the precondition and verify that transfer transaction is written to Debit Card History");
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Actions.debitCardModalWindowActions().goToCardHistory(1);

        transactionReasonCode = Pages.cardsManagementPage().getTransactionReasonCode(1);
        Assert.assertEquals(transactionReasonCode, "00 -- Approved or completed successfully",
                "'Transaction Reason Code' is not equal to '00 -- Approved or completed successfully'");

        transactionDescription = Pages.cardsManagementPage().getTransactionDescription(1);
        Assert.assertEquals(transactionDescription, "Card to Card Credit",
                "Transaction description is not equal to 'Card to Card Credit'");

        actualAmount = Actions.debitCardModalWindowActions().getTransactionAmount(1);
        Assert.assertEquals(actualAmount, transactionAmount, "Transaction amount is incorrect!");
    }

    private void createDebitCard(String clientInitials, DebitCard debitCard) {
        Actions.clientPageActions().searchAndOpenClientByName(clientInitials);
        Pages.clientDetailsPage().clickOnMaintenanceTab();
        Pages.clientDetailsPage().clickOnNewDebitCardButton();
        Actions.debitCardModalWindowActions().fillDebitCard(debitCard);
        Pages.debitCardModalWindow().clickOnSaveAndFinishButton();
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowInvisibility();
    }

    private Map<String, String> getPreAuthFieldsMap(NonTellerTransactionData transactionData) {
        Map<String, String > result = new HashMap<>();
        result.put("0", "0100");
        result.put("2", transactionData.getCardNumber());
        result.put("3", "560020");
        result.put("4", transactionData.getAmount());
        result.put("11", String.valueOf(Generator.genInt(100000000, 922337203)));
        result.put("18", "6012");
        result.put("22", "012");
        result.put("37", "201206102");
        result.put("43", "Long ave. bld. 34      Nashville      US");
        result.put("48", "MerName 01");
        result.put("58", "10000000451");
        result.put("109", "GI02FDR1191224               xxxxxxxxxxxxxxxxxxxAN34xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxN124xxxxxxxxxxxxxxxxxxxxxxxxA150xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxA325xxxxxxxxxxxxxxxxxxxxxxxxxA702TXA803840A4100125467895SF0201DB0808011974");
        result.put("110", "N124Test2 Recipient");
        result.put("111", "1200403CD");
        result.put("127", "001100000001827756");

        return  result;
    }

    private Map<String, String> getCompletionFieldsMap(NonTellerTransactionData transactionData) {
        Map<String, String > result = new HashMap<>();
        result.put("0", "0200");
        result.put("2", transactionData.getCardNumber());
        result.put("3", "560020");
        result.put("4", transactionData.getAmount());
        result.put("11", "321843");
        result.put("18", "6012");
        result.put("22", "002");
        result.put("42", "01 sample av. ");
        result.put("43", "Long ave. bld. 34      Nashville      US");
        result.put("58", "10000000451");
        result.put("63", "B0IN9015DSDACQRID");
        result.put("111", "1200403CD");
        result.put("124", "NI02PS00");
        result.put("127", "001100000001827756");

        return  result;
    }
}