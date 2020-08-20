package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.DateTime;
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

@Epic("frontoffice")
@Feature("Transactions")
@Owner("Petro")
public class C25354_ChkAtmBalanceInquiryOnusTest extends BaseTest {

    private final String terminalId = "M408084";
    private final String foreignTerminalId = "M308084";
    private final String FIELD = "54";
    private IndividualClient client;
    private NonTellerTransactionData nonTellerTransactionData;
    private NonTellerTransactionData nonTellerForeignTransactionData;
    private BalanceDataForCHKAcc expectedBalanceDataForCheckingAcc;
    private TransactionData chkAccTransactionData;
    private String chkAccountNumber;
    private double transactionAmount;
    private double foreignFeeBalanceInquiryValue;

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
        transactionAmount = glDebitMiscCreditTransaction.getTransactionDestination().getAmount();

        // Set up nonTeller transaction data
        // Payload to be sent in step 2
        nonTellerTransactionData = new NonTellerTransactionData();
        nonTellerTransactionData.setTerminalId(terminalId);

        // Payload to be sent in step 5
        nonTellerForeignTransactionData = new NonTellerTransactionData();
        nonTellerForeignTransactionData.setTerminalId(foreignTerminalId);

        // Get the value of ForeignATMFee bank control setting
        foreignFeeBalanceInquiryValue = Actions.nonTellerTransactionActions().getForeignATMFeeBalanceInquiry(1);

        // TODO :
//        3. Make sure that there is at least one record in bank-owned atm locations (bank.data.datmlc) - See Query from Excel doc
//        5. Make sure that there is filled in bank control setting 'WaiveATUsageFeeAcronym ' (e.g. 'CPN') (on bank.data.bcfile)

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

        Actions.clientPageActions().searchAndOpenClientByName(chkAccountNumber);
        expectedBalanceDataForCheckingAcc = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        // Set up nonTeller transaction data
        chkAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedBalanceDataForCheckingAcc.getCurrentBalance(), foreignFeeBalanceInquiryValue);

        Actions.loginActions().doLogOut();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    public void chkAtmBalanceInquiryOnus() {

        logInfo("Step 1: Go to the Swagger and log in as the User from the preconditions");
        logInfo("Step 2: Expand widgets-controller and run the following request with FOREIGN terminal ID\n" +
                "(TermId is not listed in 'bank owned atm locations/bank.data.datmlc'):");
        String fieldValueFromResponse = Actions.nonTellerTransactionActions()
                .getFieldValueFromATMTransaction(getFieldsMap(nonTellerTransactionData), FIELD);

        logInfo("Step 3: Check the DE54 in the response.\n" +
                "Make sure that account's Current Balance and Available Balance were returned in this field");
        Actions.nonTellerTransactionActions().verifyCurrentAndAvailableBalance(fieldValueFromResponse, transactionAmount);

        logInfo("Step 4: Search for CHK account from the precondition and go to the Transactions history tab.\n" +
                "Verify that 129-Usage fee transaction was not generated with ON-US Terminal");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.clientPageActions().searchAndOpenClientByName(chkAccountNumber);
        AccountActions.retrievingAccountData().goToTransactionsTab();
        Assert.assertFalse(Actions.transactionActions().isTransactionCodePresent(TransactionCode.ATM_USAGE_129.getTransCode()),
                "129-ATM Usage Fee is present in transaction list");
        Actions.loginActions().doLogOut();

        logInfo("Step 5: Go to the swagger and run the request with FOREIGN Terminal"
                + "(TermId is not listed in 'bank owned atm locations/bank.data.datmlc')"
                + "BUT with WaiveATUsageFeeAcronym bcsetting value in DE 063 (position 9-11)");
        String fieldValueFromForeignResponse = Actions.nonTellerTransactionActions()
                .getFieldValueFromATMTransaction(getForeignFieldsMap(nonTellerTransactionData),FIELD);

        logInfo("Step 6: Check the DE54 in the response.\n" +
                "Make sure that account's Current Balance and Available Balance were returned in this field");
        Actions.nonTellerTransactionActions().verifyCurrentAndAvailableBalance(fieldValueFromForeignResponse, transactionAmount);

        logInfo("Step 7: Search for CHK account from the precondition and go to the Transactions history tab.\n" +
                "Verify that 129-Usage fee transaction was not generated with ON-US Terminal");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.clientPageActions().searchAndOpenClientByName(chkAccountNumber);
        AccountActions.retrievingAccountData().goToTransactionsTab();
        Assert.assertFalse(Actions.transactionActions().isTransactionCodePresent(TransactionCode.ATM_USAGE_129.getTransCode()),
                "129-ATM Usage Fee is present in transaction list");

        logInfo("Step 8: Go to Client Maintenance and click [View all Cards] button in 'Cards Management' widget");
        logInfo("Step 9: Click [View History] link on the Debit Card from the precondition");
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Actions.debitCardModalWindowActions().goToCardHistory(1);

        double feeAmountFirstTransaction = Actions.debitCardModalWindowActions().getTransactionFeeAmount(1);
        Assert.assertEquals(feeAmountFirstTransaction, 0.00, "Transaction fee amount is incorrect!");
        String firstTransactionDescription = Pages.cardsManagementPage().getTransactionDescription(1);
        Assert.assertEquals(firstTransactionDescription, "Balance Inquiry", "Transaction fee amount is incorrect!");

        double feeAmountSecondTransaction = Actions.debitCardModalWindowActions().getTransactionFeeAmount(2);
        Assert.assertEquals(feeAmountSecondTransaction, 0.00, "Transaction fee amount is incorrect!");
        String secondTransactionDescription = Pages.cardsManagementPage().getTransactionDescription(2);
        Assert.assertEquals(secondTransactionDescription, "Balance Inquiry", "Transaction fee amount is incorrect!");

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
        result.put("3", "312000");
        result.put("11", "430392");
        result.put("18", "4900");
        result.put("22", "012");
        result.put("32", "469212");
        result.put("39", "00");
        result.put("35", String.format("%s=%s", transactionData.getCardNumber(), transactionData.getExpirationDate()));
        result.put("41", transactionData.getTerminalId());
        result.put("43", "123456789012345678901234567890123456TXUS");
        result.put("58", "10111000251");

        return  result;
    }

    private Map<String, String> getForeignFieldsMap(NonTellerTransactionData transactionData) {
        Map<String, String > result = new HashMap<>();
        result.put("0", "0200");
        result.put("3", "312000");
        result.put("11", "430392");
        result.put("18", "4900");
        result.put("22", "012");
        result.put("32", "469212");
        result.put("39", "00");
        result.put("35", String.format("%s=%s", transactionData.getCardNumber(), transactionData.getExpirationDate()));
        result.put("41", transactionData.getTerminalId());
        result.put("43", "123456789012345678901234567890123456TXUS");
        result.put("58", "10111000251");
        result.put("63", "B0IN9015CPNACQRID");

        return  result;
    }
}
