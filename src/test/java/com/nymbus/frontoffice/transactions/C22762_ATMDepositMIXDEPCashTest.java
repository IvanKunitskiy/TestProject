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
import com.nymbus.newmodels.generation.bincontrol.BinControlConstructor;
import com.nymbus.newmodels.generation.bincontrol.builder.BinControlBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.debitcard.DebitCardConstructor;
import com.nymbus.newmodels.generation.debitcard.builder.DebitCardBuilder;
import com.nymbus.newmodels.settings.bincontrol.BinControl;
import com.nymbus.newmodels.transaction.verifyingModels.*;
import com.nymbus.pages.Pages;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class C22762_ATMDepositMIXDEPCashTest extends BaseTest {
    private BalanceData expectedBalanceDataForSavingAcc;
    private BalanceDataForCHKAcc expectedBalanceDataForCheckingAcc;
    private TransactionData savingAccTransactionData;
    private TransactionData chkAccTransactionData;
    private NonTellerTransactionData mixDepTransactionData;
    private NonTellerTransactionData cashTransactionData;
    private String savingsAccountNumber;
    private String checkingAccountNumber;
    private double mixDepTransactionAmount;
    private double cashTransactionAmount;
    private IndividualClient client;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client, Account
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();
        Account savingsAccount = new Account().setSavingsAccountData();
        Account checkAccount = new Account().setCHKAccountData();

        // Init nonTellerTransactionData
        mixDepTransactionData = new NonTellerTransactionData();
        cashTransactionData = new NonTellerTransactionData();
        mixDepTransactionAmount = 100.00;
        cashTransactionAmount = 100.00;

        // Set up debit card and bin control
        DebitCardConstructor debitCardConstructor = new DebitCardConstructor();
        DebitCardBuilder debitCardBuilder = new DebitCardBuilder();
        debitCardConstructor.constructDebitCard(debitCardBuilder);
        DebitCard debitCardForSavingsAcc = debitCardBuilder.getCard();
        DebitCard debitCardForCheckingAcc = debitCardBuilder.getCard();

        BinControlConstructor binControlConstructor = new BinControlConstructor();
        BinControlBuilder binControlBuilder = new BinControlBuilder();
        binControlConstructor.constructBinControl(binControlBuilder);
        BinControl binControl = binControlBuilder.getBinControl();
        binControl.setBinNumber("510986");
        binControl.setCardDescription("Consumer Debit");

        Actions.debitCardModalWindowActions().setDebitCardWithBinControl(debitCardForSavingsAcc, binControl);
        debitCardForSavingsAcc.getAccounts().add(savingsAccount.getAccountNumber());
        debitCardForSavingsAcc.setNameOnCard(client.getNameForDebitCard());

        Actions.debitCardModalWindowActions().setDebitCardWithBinControl(debitCardForCheckingAcc, binControl);
        debitCardForCheckingAcc.getAccounts().add(checkAccount.getAccountNumber());
        debitCardForCheckingAcc.setNameOnCard(client.getNameForDebitCard());

        // Log in and create client
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        // Get terminal ID
        String terminalId = Actions.nonTellerTransactionActions().getTerminalID(1);

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create Savings  account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createSavingAccountForTransactionPurpose(savingsAccount);
        savingsAccountNumber = savingsAccount.getAccountNumber();

        // Create CHK account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);
        checkingAccountNumber = checkAccount.getAccountNumber();

        createDebitCard(client.getInitials(), debitCardForSavingsAcc);
        Actions.debitCardModalWindowActions().setExpirationDateAndCardNumber(mixDepTransactionData, 1);

        createDebitCard(client.getInitials(), debitCardForCheckingAcc);
        Actions.debitCardModalWindowActions().setExpirationDateAndCardNumber(cashTransactionData, 2);

        Actions.clientPageActions().searchAndOpenClientByName(savingsAccountNumber);
        expectedBalanceDataForSavingAcc = AccountActions.retrievingAccountData().getBalanceData();

        Actions.clientPageActions().searchAndOpenClientByName(checkingAccountNumber);
        expectedBalanceDataForCheckingAcc = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        // Set up nonTeller transaction data
        mixDepTransactionData.setAmount(mixDepTransactionAmount);
        mixDepTransactionData.setTerminalId(terminalId);
        savingAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "+", expectedBalanceDataForSavingAcc.getCurrentBalance(), mixDepTransactionAmount);
        cashTransactionData.setAmount(cashTransactionAmount);
        cashTransactionData.setTerminalId(terminalId);
        chkAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "+", expectedBalanceDataForCheckingAcc.getCurrentBalance(), cashTransactionAmount);
    }

    @Test(description = " C22762, ATM ATM Deposit")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyATMDepositTransaction() {
        String field54MixDep = "1093840C0000000100001094840C000000005000";
        logInfo("Step 1: Go to the Swagger and log in as the User from the preconditions");
        logInfo("Step 2: Expand widgets-controller->widget._GenericProcess and run the following \n" +
                "request for the Debit Card assigned to the Savings account from the precondition:");
        Actions.nonTellerTransactionActions().performATMTransaction(generateFieldsMap(mixDepTransactionData, field54MixDep, "MIXDEP"));
    }

    private void createDebitCard(String clientInitials, DebitCard debitCard) {
        Actions.clientPageActions().searchAndOpenClientByName(clientInitials);
        Pages.clientDetailsPage().clickOnMaintenanceTab();
        Pages.clientDetailsPage().clickOnNewDebitCardButton();
        Actions.debitCardModalWindowActions().fillDebitCard(debitCard);
        Pages.debitCardModalWindow().clickOnSaveAndFinishButton();
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowInvisibility();
    }

    private Map<String, String> generateFieldsMap(NonTellerTransactionData transactionData, String field54, String field104) {
        Map<String, String > result = new HashMap<>();
        result.put("0", "0200");
        result.put("3", "210010");
        result.put("4", transactionData.getAmount());
        result.put("11", "214003");
        result.put("18", "6011");
        result.put("22", "021");
        result.put("35", String.format("%s=%s", transactionData.getCardNumber(), transactionData.getExpirationDate()));
        result.put("41", transactionData.getTerminalId());
        result.put("43", "Long ave. bld. 34 Nashville US");
        result.put("54", field54);
        result.put("96", "89379174");
        result.put("104", field104);

        return  result;
    }
}