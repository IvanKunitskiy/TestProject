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

@Owner("Petro")
@Epic("Frontoffice")
@Feature("Transactions")
public class C25360_ChkAtmBalanceInquiryForeignTest extends BaseTest {

    private IndividualClient client;
    private NonTellerTransactionData nonTellerTransactionData;
    private String checkingAccountNumber;
    private BalanceDataForCHKAcc expectedBalanceDataForCheckingAcc;
    private TransactionData chkAccTransactionData;
    private double transactionAmount;
    private double foreignFeeBalanceInquiryValue;

    @BeforeMethod
    public void preCondition() {

        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        // Set up CHK account
        Account chkAccount = new Account().setCHKAccountData();

        // Set up transaction (transaction is made to assign balance to CHK account)
        Transaction glDebitMiscCreditTransaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        glDebitMiscCreditTransaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());
        glDebitMiscCreditTransaction.getTransactionDestination().setTransactionCode("109 - Deposit");
        transactionAmount = glDebitMiscCreditTransaction.getTransactionDestination().getAmount();

        // Set up nonTeller transaction data (api request data)
        nonTellerTransactionData = new NonTellerTransactionData();

        // Get the value of ForeignATMFee bank control setting
        foreignFeeBalanceInquiryValue = Actions.nonTellerTransactionActions().getForeignATMFeeBalanceInquiry(1);

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
        checkingAccountNumber = chkAccount.getAccountNumber();

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

        Actions.clientPageActions().searchAndOpenClientByName(checkingAccountNumber);
        expectedBalanceDataForCheckingAcc = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        // Set up nonTeller transaction data
        chkAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedBalanceDataForCheckingAcc.getCurrentBalance(), foreignFeeBalanceInquiryValue);

        Actions.loginActions().doLogOut();
    }

    @Test(description = "C25360, CHK ATM Balance Inquiry FOREIGN")
    @Severity(SeverityLevel.CRITICAL)
    public void chkAtmBalanceInquiryForeign() {

        logInfo("Step 1: Go to the Swagger and log in as the User from the preconditions");
        logInfo("Step 2: Expand widgets-controller and run the following request with FOREIGN terminal ID\n" +
                "(TermId is not listed in 'bank owned atm locations/bank.data.datmlc'):");
        logInfo("Step 3: Check the DE54 in the response.\n" +
                "Make sure that account's Current Balance and Available Balance were returned in this field");
        final String FIELD = "54";
        String fieldValueFromResponse = Actions.nonTellerTransactionActions().getFieldValueFromATMTransaction(getFieldsMap(nonTellerTransactionData), FIELD);
        Actions.nonTellerTransactionActions().verifyCurrentAndAvailableBalance(fieldValueFromResponse, transactionAmount);
        String transcode = TransactionCode.ATM_USAGE_129_FEE.getTransCode().split("\\s+")[0];
        WebAdminActions.webAdminTransactionActions().setTransactionPostDateAndEffectiveDate(chkAccTransactionData, checkingAccountNumber, transcode);
        expectedBalanceDataForCheckingAcc.reduceAmount(foreignFeeBalanceInquiryValue);

        logInfo("Step 4: Search for CHK account from the precondition and go to the Transactions history tab.\n" +
                "Verify that 129-Usage fee transaction was generated with NOT ON-US Terminal with an amount= ForeignATMFeeBalanceInquiry bcsetting");
        logInfo("Step 5: Pay attention to CHK account Current Balance and Available Balance");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.clientPageActions().searchAndOpenClientByName(checkingAccountNumber);

        Assert.assertEquals(AccountActions.retrievingAccountData().getCurrentBalance(),
                expectedBalanceDataForCheckingAcc.getCurrentBalance(), "CHK account current balance is not correct!");
        Assert.assertEquals(AccountActions.retrievingAccountData().getAvailableBalance(),
                expectedBalanceDataForCheckingAcc.getAvailableBalance(), "CHK account available balance is not correct!");
        chkAccTransactionData.setBalance(expectedBalanceDataForCheckingAcc.getCurrentBalance());
        chkAccTransactionData.setAmount(foreignFeeBalanceInquiryValue);

        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset);
        Assert.assertEquals(actualTransactionData, chkAccTransactionData, "Transaction data doesn't match!");
        Assert.assertTrue(Actions.transactionActions().isTransactionCodePresent(TransactionCode.ATM_USAGE_129.getTransCode()),
                "129-ATM Usage Fee presents in transaction list");

        logInfo("Step 8: Go to Client Maintenance and click [View all Cards] button in 'Cards Management' widget");
        logInfo("Step 9: Click [View History] link on the Debit Card from the precondition");
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Actions.debitCardModalWindowActions().goToCardHistory(1);
        double actualFeeAmount = Actions.debitCardModalWindowActions().getTransactionFeeAmount(1);
        Assert.assertEquals(actualFeeAmount, foreignFeeBalanceInquiryValue, "Transaction amount is incorrect!");
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
        result.put("11", String.valueOf(Generator.genInt(100000000, 922337203)));
        result.put("18", "4900");
        result.put("22", "012");
        result.put("32", "469212");
        result.put("39", "00");
        result.put("35", String.format("%s=%s", transactionData.getCardNumber(), transactionData.getExpirationDate()));
        result.put("41", "3289497");
        result.put("43", "123456789012345678901234567890123456TXUS");
        result.put("58", "10111000251");

        return  result;
    }

}
