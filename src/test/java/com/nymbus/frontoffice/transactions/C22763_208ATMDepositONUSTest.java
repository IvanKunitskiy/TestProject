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
import com.nymbus.newmodels.settings.bincontrol.BinControl;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
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

public class C22763_208ATMDepositONUSTest extends BaseTest {
    private BalanceData expectedBalanceData;
    private NonTellerTransactionData nonTellerTransactionData;
    private TransactionData savingAccTransactionData;
    private String savingsAccountNumber;
    private double transactionAmount;
    private IndividualClient client;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();
        Account savingsAccount = new Account().setSavingsAccountData();

        // Init nonTellerTransactionData
        nonTellerTransactionData = new NonTellerTransactionData();
        transactionAmount = 100.00;

        // Set up debit card and bin control
        DebitCardConstructor debitCardConstructor = new DebitCardConstructor();
        DebitCardBuilder debitCardBuilder = new DebitCardBuilder();
        debitCardConstructor.constructDebitCard(debitCardBuilder);
        DebitCard debitCard = debitCardBuilder.getCard();
        nonTellerTransactionData = new NonTellerTransactionData();

        BinControlConstructor binControlConstructor = new BinControlConstructor();
        BinControlBuilder binControlBuilder = new BinControlBuilder();
        binControlConstructor.constructBinControl(binControlBuilder);
        BinControl binControl = binControlBuilder.getBinControl();
        binControl.setBinNumber("510986");
        binControl.setCardDescription("Consumer Debit");

        Actions.debitCardModalWindowActions().setDebitCardWithBinControl(debitCard, binControl);
        debitCard.getAccounts().add(savingsAccount.getAccountNumber());
        debitCard.setNameOnCard(client.getNameForDebitCard());

        // Log in and create client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        String terminalId = Actions.nonTellerTransactionActions().getTerminalID(1);

        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create Savings account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createSavingAccountForTransactionPurpose(savingsAccount);
        savingsAccountNumber = savingsAccount.getAccountNumber();

        createDebitCard(client.getInitials(), debitCard);
        Actions.debitCardModalWindowActions().setExpirationDateAndCardNumber(nonTellerTransactionData, 1);

        Actions.clientPageActions().searchAndOpenClientByName(savingsAccountNumber);
        expectedBalanceData = AccountActions.retrievingAccountData().getBalanceData();

        // Set up nonTeller transaction data
        nonTellerTransactionData.setAmount(transactionAmount);
        nonTellerTransactionData.setTerminalId(terminalId);
        savingAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "+", expectedBalanceData.getCurrentBalance(), transactionAmount);

        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22763, 208 ATM Deposit ONUS")
    @Severity(SeverityLevel.CRITICAL)
    public void verify208ATMDepositOnus() {
        logInfo("Step 1: Go to the Swagger and log in as the User from the preconditions");
        logInfo("Step 2:Expand widgets-controller/widget._GenericProcess and run the following request with ONUS terminal ID");
        Actions.nonTellerTransactionActions().performATMTransaction(getFieldsMap(nonTellerTransactionData));

        String transcode = TransactionCode.ATM_DEPOSIT_208.getTransCode().split("\\s+")[0];
        WebAdminActions.webAdminTransactionActions().setTransactionPostDateAndEffectiveDate(savingAccTransactionData, savingsAccountNumber, transcode);

        logInfo("Step 3: Log in to the system as the User from the preconditions");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 4: Search for Savings account from the precondition and open it on Instructions tab");
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccountNumber);

        logInfo("Step 5: Check the list of instructions for the account (if exist) and delete the Hold with type Reg CC");
        AccountActions.editAccount().goToInstructionsTab();
        String INSTRUCTION_REASON = "Reg CC";
        AccountActions.createInstruction().deleteInstructionByReasonText(INSTRUCTION_REASON);

        logInfo("Step 6: Verify Account's: \n" +
                "- current balance \n" +
                "- available balance \n" +
                "- Transactions history");
        AccountActions.editAccount().goToDetailsTab();
        expectedBalanceData.addAmount(transactionAmount);
        BalanceData actualBalanceDataForSavingsAcc = AccountActions.retrievingAccountData().getBalanceData();
        Assert.assertEquals(actualBalanceDataForSavingsAcc.getCurrentBalance(), expectedBalanceData.getCurrentBalance(), "Saving account current balance is not correct!");
        Assert.assertEquals(actualBalanceDataForSavingsAcc.getAvailableBalance(), expectedBalanceData.getAvailableBalance(), "Saving account available balance is not correct!");
        savingAccTransactionData.setBalance(expectedBalanceData.getCurrentBalance());

        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset);
        Assert.assertEquals(actualTransactionData, savingAccTransactionData, "Transaction data doesn't match!");

        logInfo("Step 7: Go to Client Maintenance and click [View all Cards] button in 'Cards Management' widget");
        logInfo("Step 8: Click [View History] link on the Debit Card from the precondition");
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Actions.debitCardModalWindowActions().goToCardHistory(1);
        double actualAmount = Actions.debitCardModalWindowActions().getTransactionAmount(1);
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

    private Map<String, String> getFieldsMap(NonTellerTransactionData transactionData) {
        Map<String, String > result = new HashMap<>();
        result.put("0", "0200");
        result.put("3", "211000");
        result.put("4", transactionData.getAmount());
        result.put("11", String.valueOf(Generator.genInt(100000000, 922337203)));
        result.put("18", "5542");
        result.put("22", "022");
        result.put("35", String.format("%s=%s", transactionData.getCardNumber(), transactionData.getExpirationDate()));
        result.put("41", transactionData.getTerminalId());
        result.put("42", "01 sample av.");
        result.put("43", "Long ave. bld. 34      Nashville      US");
        result.put("48", "SHELL");
        result.put("49", "840");
        result.put("58", "10000000612");

        return  result;
    }
}