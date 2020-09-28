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
import com.nymbus.newmodels.transaction.enums.TransactionSignIndicator;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceData;
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

public class C26512_WithdrawalSAVSurchargeFRGNTest extends BaseTest {
    private IndividualClient client;
    private String savingsAccountNumber;
    private NonTellerTransactionData nonTellerTransactionData;
    private BalanceData expectedBalanceData;
    private TransactionData savingAccTransactionData;
    private double transactionAmount = 60.00;
    private double surchargeAmount = 3.00;
    private double foreignFeeValue;

    @BeforeMethod
    public void preCondition() {
        double glDebitTransactionAmount = 100.00;

        // Set up Client
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();
        Transaction glDebitMiscCreditTransaction = new TransactionConstructor(new GLDebitMiscCreditBuilder()).constructTransaction();
        glDebitMiscCreditTransaction.getTransactionSource().setAmount(glDebitTransactionAmount);

        // Set up savings account
        Account savingAccount = new Account().setSavingsAccountData();
        savingsAccountNumber = savingAccount.getAccountNumber();
        glDebitMiscCreditTransaction.getTransactionDestination().setAccountNumber(savingsAccountNumber);

        // Set up nonTeller transaction data
        nonTellerTransactionData = new NonTellerTransactionData();
        nonTellerTransactionData.setAmount(transactionAmount);

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
        debitCard.getAccounts().add(savingAccount.getAccountNumber());
        debitCard.setNameOnCard(client.getNameForDebitCard());

        String terminalId = Actions.nonTellerTransactionActions().getTerminalID(1);

        // Log in
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        foreignFeeValue = Actions.nonTellerTransactionActions().getForeignFee(1);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(savingAccount);

        // Create debit card for checking acc
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
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccountNumber);
        expectedBalanceData = AccountActions.retrievingAccountData().getBalanceData();
        nonTellerTransactionData.setTerminalId(terminalId);
        savingAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedBalanceData.getCurrentBalance(),
                transactionAmount + surchargeAmount);
        Actions.loginActions().doLogOut();
    }

    @Test(description = "C26512, Withdrawal - SAV $60+$3 Surcharge FRGN")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyWithdrawalSAV() {
        Map<String, String> fields = getFieldsMap(nonTellerTransactionData);
        fields.put("28", Actions.nonTellerTransactionActions().getTransaction28FieldValue(TransactionSignIndicator.DEBIT.getSignIndicator(), surchargeAmount));

        logInfo("Step 1: Go to the Swagger and log in as the User from the preconditions");
        logInfo("Step 2: Expand widgets-controller and run the following request with FOREIGN terminal ID\n" +
                "(TermId is not listed in 'bank owned atm locations/bank.data.datmlc'):");
        Actions.nonTellerTransactionActions().performATMTransaction(fields);

        String transcode = TransactionCode.ATM_WITHDRAWAL_224.getTransCode().split("\\s+")[0];
        WebAdminActions.webAdminTransactionActions().setTransactionPostDateAndEffectiveDate(savingAccTransactionData, savingsAccountNumber, transcode);

        logInfo("Step 3: Log in to the system as the User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        expectedBalanceData.subtractAmount(transactionAmount + surchargeAmount + foreignFeeValue);

        logInfo("Step 4: Search for CHK account from the precondition and Verify Account's: \n" +
                "- current balance \n" +
                "- available balance \n" +
                "- Transactions history");
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccountNumber);
        BalanceData actualBalanceData = AccountActions.retrievingAccountData().getBalanceData();
        Assert.assertEquals(actualBalanceData.getCurrentBalance(), expectedBalanceData.getCurrentBalance(),
                "CHK account current balance is not correct!");
        Assert.assertEquals(actualBalanceData.getAvailableBalance(), expectedBalanceData.getAvailableBalance(),
                "CHK account available balance is not correct!");
        savingAccTransactionData.setBalance(expectedBalanceData.getCurrentBalance());

        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset);
        Assert.assertEquals(actualTransactionData, savingAccTransactionData, "Transaction data doesn't match!");

        logInfo("Step 5: Verify that 229-Usage fee transaction was generated with an amount= ForeignATMFee bcsetting");
        Assert.assertTrue(Actions.transactionActions().isTransactionCodePresent(TransactionCode.ATM_USAGE_229_FEE.getTransCode(), offset),
                "229-ATM Usage Fee isn't present in transaction list");
        Assert.assertEquals(AccountActions.retrievingAccountData().getAmountValue(2, offset), foreignFeeValue, "Fee amount is incorrect!");
        Assert.assertEquals(Pages.accountTransactionPage().getAmountSymbol(2, offset), "-", "Fee amount symbol is incorrect!");

        logInfo("Step 6: Go to Client Maintenance and click [View all Cards] button in 'Cards Management' widget");
        logInfo("Step 7: Click [View History] link on the Debit Card from the precondition");
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Actions.debitCardModalWindowActions().goToCardHistory(1);

        String transactionReasonCode = Pages.cardsManagementPage().getTransactionReasonCode(1);
        Assert.assertEquals(transactionReasonCode, "00 -- Approved or completed successfully",
                "'Transaction Reason Code' is not equal to '00 -- Approved or completed successfully'");

        String transactionDescription = Pages.cardsManagementPage().getTransactionDescription(1);
        Assert.assertEquals(transactionDescription, "Cash Withdrawal",
                "Transaction description is not equal to 'Cash Withdrawal'");
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
        result.put("3", "011000");
        result.put("4", transactionData.getAmount());
        result.put("11", String.valueOf(Generator.genInt(100000000, 922337203)));
        result.put("18", "6011");
        result.put("22", "021");
        result.put("35", String.format("%s=%s", transactionData.getCardNumber(), transactionData.getExpirationDate()));
        result.put("37", "201206102");
        result.put("43", "Long ave. bld. 34      Nashville      US");
        result.put("48", "SHELL");
        result.put("58", "00000000022");

        return  result;
    }
}