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
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.settings.bincontrol.BinControl;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
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
public class C26758_ChipMerchantCompletionTest extends BaseTest {

    private IndividualClient client;
    private String chkAccountNumber;
    private BalanceDataForCHKAcc expectedBalanceDataForCheckingAcc;
    private NonTellerTransactionData chipMerchantCompletionTransactionData;
    private TransactionData chkAccTransactionData;
    private final double requestTransactionAmount = 21.90;
    private final String uniqueValueField11 = Generator.getRandomStringNumber(6);

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up CHK and Savings accounts
        Account chkAccount = new Account().setCHKAccountData();
        chkAccountNumber = chkAccount.getAccountNumber();

        // Set up transaction
        Transaction glDebitMiscCreditTransaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        glDebitMiscCreditTransaction.getTransactionDestination().setAccountNumber(chkAccountNumber);
        glDebitMiscCreditTransaction.getTransactionDestination().setTransactionCode("109 - Deposit");

        // Set up nonTeller transaction data for 'Chip Merchant Auth'
        NonTellerTransactionData chipMerchantAuthTransactionData = new NonTellerTransactionData();
        chipMerchantAuthTransactionData.setAmount(requestTransactionAmount);

        // Set up nonTeller transaction data for 'Chip Merchant Completion'
        chipMerchantCompletionTransactionData = new NonTellerTransactionData();
        chipMerchantCompletionTransactionData.setAmount(requestTransactionAmount);

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

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(chkAccount);

        // Create debit card for CHK acc
        createDebitCard(client.getInitials(), debitCard);
        Actions.debitCardModalWindowActions().setExpirationDateAndCardNumber(chipMerchantCompletionTransactionData, 1);
        Pages.cardsManagementPage().clickBackToMaintenanceButton();
        Actions.debitCardModalWindowActions().setExpirationDateAndCardNumber(chipMerchantAuthTransactionData, 1);

        // Re-login in system for updating teller session and capture account balances
        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Perform transaction
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(glDebitMiscCreditTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();

        // Login, capture current and available balance of the account and logout
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.clientPageActions().searchAndOpenClientByName(chkAccountNumber);
        expectedBalanceDataForCheckingAcc = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Actions.loginActions().doLogOutProgrammatically();

        // Perform 'Chip Merchant Auth' request
        String[] actions = {"0100"};
        Actions.nonTellerTransactionActions().performATMTransaction(getChipMerchantAuthFieldsMap(chipMerchantAuthTransactionData), actions);
        // update the expected available balance
        expectedBalanceDataForCheckingAcc.reduceAvailableBalance(requestTransactionAmount);

        chkAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedBalanceDataForCheckingAcc.getAvailableBalance(), requestTransactionAmount);
    }

    @Test(description = "C26758, Chip Merchant Completion")
    @Severity(SeverityLevel.CRITICAL)
    public void chipMerchantCompletionTest() {

        logInfo("Step 1: Go to the Swagger and log in as the User from the preconditions");
        logInfo("Step 2: Expand widgets-controller and run the following request");
        String[] actions = {"0220"};
        Actions.nonTellerTransactionActions().performATMTransaction(getChipMerchantCompletionFieldsMap(chipMerchantCompletionTransactionData), actions);
        expectedBalanceDataForCheckingAcc.reduceCurrentBalance(requestTransactionAmount);

        logInfo("Step 3: Log in to the system as the User from the preconditions");
        logInfo("Step 4: Search for CHK account from the precondition and verify its current and available balance");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.clientPageActions().searchAndOpenClientByName(chkAccountNumber);

        Assert.assertEquals(AccountActions.retrievingAccountData().getCurrentBalance(),
                expectedBalanceDataForCheckingAcc.getCurrentBalance(), "CHK account current balance is not correct!");
        Assert.assertEquals(AccountActions.retrievingAccountData().getAvailableBalance(),
                expectedBalanceDataForCheckingAcc.getAvailableBalance(), "CHK account available balance is not correct!");

        logInfo("Step 5: Open CHK account on Transactions tab and verify Transactions history.\n" +
                "Make sure that there is no ATM transaction in Transactions tab");
        String transcode = TransactionCode.DEBIT_PURCHASE_123.getTransCode().split("\\s+")[0];
        WebAdminActions.webAdminTransactionActions().setTransactionPostDateAndEffectiveDate(chkAccTransactionData, chkAccountNumber, transcode);
        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset);

        Assert.assertEquals(actualTransactionData, chkAccTransactionData, "Transaction data doesn't match!");
        Assert.assertEquals(Pages.accountTransactionPage().getTransactionItemsCount(), 2,
                "Transaction count is incorrect!");
        Assert.assertTrue(Actions.transactionActions().isTransactionCodePresent(TransactionCode.DEBIT_PURCHASE_123.getTransCode(), offset),
                "'123 - Debit Purchase' presents in transaction list");

        logInfo("Step 6: Open CHK account on Instructions tab and verify that there is a Hold with amount == transaction amount");
        AccountActions.editAccount().goToInstructionsTab();
        Assert.assertEquals(Pages.accountInstructionsPage().getCreatedInstructionsCount(), 0,
                "Hold instruction was not created");

        logInfo("Step 7: Go to Client Maintenance and click [View all Cards] button in 'Cards Management' widget");
        logInfo("Step 8: Click [View History] link on the Debit Card from the precondition");
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Actions.debitCardModalWindowActions().goToCardHistory(1);

        String transactionReasonCode = Pages.cardsManagementPage().getTransactionReasonCode(1);
        Assert.assertEquals(transactionReasonCode, "00 -- Approved or completed successfully",
                "'Transaction Reason Code' is not equal to '00 -- Approved or completed successfully'");

        String transactionDescription = Pages.cardsManagementPage().getTransactionDescription(1);
        Assert.assertEquals(transactionDescription, "Purchase Completion",
                "Transaction description is not equal to 'Purchase Completion'");

        String transactionAmount = Pages.cardsManagementPage().getTransactionAmount(1);
        Assert.assertEquals(Double.parseDouble(transactionAmount), requestTransactionAmount,
                "'Purchase Completion' amount is not equal to transaction amount");
    }

    private void createDebitCard(String clientInitials, DebitCard debitCard) {
        Actions.clientPageActions().searchAndOpenClientByName(clientInitials);
        Pages.clientDetailsPage().clickOnMaintenanceTab();
        Pages.clientDetailsPage().clickOnNewDebitCardButton();
        Actions.debitCardModalWindowActions().fillDebitCard(debitCard);
        Pages.debitCardModalWindow().clickOnSaveAndFinishButton();
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowInvisibility();
    }

    private Map<String, String> getChipMerchantCompletionFieldsMap(NonTellerTransactionData transactionData) {
        Map<String, String > result = new HashMap<>();
        result.put("0", "0220");
        result.put("2", transactionData.getCardNumber());
        result.put("3", "000000");
        result.put("4", transactionData.getAmount());
        result.put("11", "165444");
        result.put("18", "0742");
        result.put("22", "052");
        result.put("23", "001");
        result.put("42", "01 sample av.  ");
        result.put("43", "Long ave. bld. 34      Nashville      US");
        result.put("48", "SHELL");
        result.put("52", "1234567890ABCDEF");
        result.put("55", "9F34123");
        result.put("58", "01100000015");
        result.put("63", "B0IN9015DSDACQRID");
        result.put("111", "12345678901111122011");

        return  result;
    }

    private Map<String, String> getChipMerchantAuthFieldsMap(NonTellerTransactionData transactionData) {
        Map<String, String > result = new HashMap<>();
        result.put("0", "0100");
        result.put("3", "000000");
        result.put("4", transactionData.getAmount());
        result.put("11", uniqueValueField11);
        result.put("18", "0742");
        result.put("22", "051");
        result.put("23", "001");
        result.put("35", String.format("%s=%s", transactionData.getCardNumber(), transactionData.getExpirationDate()));
        result.put("42", "01 sample av.  ");
        result.put("43", "Long ave. bld. 34      Nashville      US");
        result.put("48", "SHELL");
        result.put("52", "1234567890ABCDEF");
        result.put("55", "9F34123");
        result.put("57", "130");
        result.put("58", "01100000015");
        result.put("63", "B0IN9015DSDACQRID");
        result.put("111", "12345678901111122011");

        return  result;
    }
}
