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
import com.nymbus.newmodels.transaction.verifyingModels.BalanceData;
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

public class C22765_SavAtmBalanceInquiryForeignTest extends BaseTest {
    private BalanceData expectedBalanceDataForSavingAcc;
    private TransactionData savingAccTransactionData;
    private NonTellerTransactionData nonTellerTransactionData;
    private String savingsAccountNumber;
    private IndividualClient client;
    private final String FIELD = "54";

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client, Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();
        Account savingsAccount = new Account().setSavingsAccountData();
        Transaction glDebitMiscCreditTransaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        glDebitMiscCreditTransaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());

        // Init nonTellerTransactionData
        nonTellerTransactionData = new NonTellerTransactionData();

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
        debitCard.setNameOnCard(client.getNameForDebitCard());
        debitCard.setAllowForeignTransactions(true);
        debitCard.setChargeForCardReplacement(true);
        debitCard.setTranslationTypeAllowed(TranslationTypeAllowed.BOTH_PIN_AND_SIGNATURE);

        double foreignFeeValue = Actions.nonTellerTransactionActions().getForeignATMFeeBalanceInquiry(1);

        // Log in
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create Savings  account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createSavingAccountForTransactionPurpose(savingsAccount);
        savingsAccountNumber = savingsAccount.getAccountNumber();

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
        String INSTRUCTION_REASON = "Reg CC";
        AccountActions.createInstruction().deleteInstructionByReasonText(INSTRUCTION_REASON);
        AccountActions.editAccount().goToDetailsTab();
        expectedBalanceDataForSavingAcc = AccountActions.retrievingAccountData().getBalanceData();

        // Set up nonTeller transaction data
        nonTellerTransactionData.setAmount(0);
        nonTellerTransactionData.setTerminalId("notonus");
        savingAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedBalanceDataForSavingAcc.getCurrentBalance(), foreignFeeValue);

        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22765, SAV ATM Balance Inquiry FOREIGN")
    @Severity(SeverityLevel.CRITICAL)
    public void verifySAVATMBalanceInquiryForeign() {
        logInfo("Step 1: Go to the Swagger and log in as the User from the preconditions");
        logInfo("Step 2: Expand widgets-controller->widget._GenericProcess and run the following request \n" +
                "with NOT ON-US Terminal (TermId is not listed in 'bank owned atm locations/bank.data.datmlc')");
        String fieldValueFromResponse = Actions.nonTellerTransactionActions().getFieldValueFromATMTransaction(getFieldsMap(nonTellerTransactionData), FIELD);

        String transcode = TransactionCode.ATM_USAGE_229_FEE.getTransCode().split("\\s+")[0];
        WebAdminActions.webAdminTransactionActions().setTransactionPostDateAndEffectiveDate(savingAccTransactionData, savingsAccountNumber, transcode);

        logInfo("Step 3: Check the DE54 in the response. \n" +
                "Make sure that account's Current Balance and Available Balance were returned in this field");
        BalanceDataForCHKAcc actualBalance = Actions.nonTellerTransactionActions().getBalanceDataFromField54(fieldValueFromResponse);
        Assert.assertEquals(actualBalance.getCurrentBalance(), expectedBalanceDataForSavingAcc.getCurrentBalance(), "Current balance in 54 field is not correct!");
        Assert.assertEquals(actualBalance.getAvailableBalance(), expectedBalanceDataForSavingAcc.getAvailableBalance(), "Available balance in 54 field is not correct!");
        expectedBalanceDataForSavingAcc.subtractAmount(savingAccTransactionData.getAmount());
        savingAccTransactionData.setBalance(expectedBalanceDataForSavingAcc.getCurrentBalance());

        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 4: Search for Savings account from the precondition and go to the Transactions history tab. \n" +
                "Verify that 229-Usage fee transaction was generated with NOT ON-US Terminal with an amount=ForeignATMFeeBalanceInquiry bcsetting");
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccountNumber);
        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset);
        Assert.assertEquals(actualTransactionData, savingAccTransactionData, "Transaction data doesn't match!");
        Assert.assertTrue(Actions.transactionActions().isTransactionCodePresent(TransactionCode.ATM_USAGE_229_FEE.getTransCode(), offset),
                "229 - ATM Usage Fee is not present in transaction list!");

        logInfo("Step 5: Pay attention to Savings account Current Balance and Available Balance");
        AccountActions.editAccount().goToDetailsTab();
        BalanceData actualBalanceDataForSavingsAcc = AccountActions.retrievingAccountData().getBalanceData();
        Assert.assertEquals(actualBalanceDataForSavingsAcc.getCurrentBalance(), expectedBalanceDataForSavingAcc.getCurrentBalance(), "Saving account current balance is not correct!");
        Assert.assertEquals(actualBalanceDataForSavingsAcc.getAvailableBalance(), expectedBalanceDataForSavingAcc.getAvailableBalance(), "Saving account available balance is not correct!");

        logInfo("Step 6: Go to Client Maintenance and click [View all Cards] button in 'Cards Management' widget");
        logInfo("Step 7: Click [View History] link on the Debit Card from the precondition");
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Actions.debitCardModalWindowActions().goToCardHistory(1);
        double actualFeeAmount = Actions.debitCardModalWindowActions().getTransactionFeeAmount(1);
        Assert.assertEquals(actualFeeAmount, savingAccTransactionData.getAmount(), "Transaction fee amount is incorrect!");
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
        result.put("3", "311000");
        result.put("11", String.valueOf(Generator.genInt(100000000, 922337203)));
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
}