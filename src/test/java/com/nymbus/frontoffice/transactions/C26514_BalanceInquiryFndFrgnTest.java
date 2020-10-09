package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.DateTime;
import com.nymbus.core.utils.Generator;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
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
public class C26514_BalanceInquiryFndFrgnTest extends BaseTest {

    private IndividualClient client;
    private String chkAccountNumber;
    private double transactionAmount;
    private TransactionData chkAccTransactionData;
    private BalanceDataForCHKAcc expectedBalanceDataForCheckingAcc;
    private NonTellerTransactionData nonTellerTransactionData;
    private double foreignFeeBalanceInquiry;
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
        transactionAmount = glDebitMiscCreditTransaction.getTransactionDestination().getAmount();

        // Get terminal ID
        String terminalId = Actions.nonTellerTransactionActions().getTerminalID(1);

        // Get "Foreign ATM Fee Balance Inquiry" value
        foreignFeeBalanceInquiry = Actions.nonTellerTransactionActions().getForeignATMFeeBalanceInquiry(1);

        // Set up nonTeller transaction data
        nonTellerTransactionData = new NonTellerTransactionData();
        nonTellerTransactionData.setTerminalId(terminalId);

        // Get the value of ForeignATMFeeBalanceInquiry bank control setting
        Assert.assertTrue(Actions.nonTellerTransactionActions().isATMFeeBalanceInquiryValuePresent(),
                "WaiveATUsageFeeAcronym setting is not filled in 'bank.data.bcfile'");

        // Make sure that there is filled in bank control setting 'WaiveATUsageFeeAcronym '
        Assert.assertTrue(Actions.nonTellerTransactionActions().isWaiveATUsageFeeAcronymValuePresent(),
                "WaiveATUsageFeeAcronym setting is not filled in 'bank.data.bcfile'");

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

        // Set product
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

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
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Perform transaction
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(glDebitMiscCreditTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        Actions.clientPageActions().searchAndOpenClientByName(chkAccountNumber);
        expectedBalanceDataForCheckingAcc = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        // Set up nonTeller transaction data
        chkAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedBalanceDataForCheckingAcc.getCurrentBalance(), foreignFeeBalanceInquiry);

        Actions.loginActions().doLogOut();
    }

    @Test(description = "C26514, Balance Inquiry FND FRGN")
    @Severity(SeverityLevel.CRITICAL)
    public void balanceInquiryFndFrgn() {

        logInfo("Step 1: Go to the Swagger and log in as the User from the preconditions");
        logInfo("Step 2: Expand widgets-controller->widget._GenericProcess and run the following request with FOREIGN Terminal\n"
                + "(TermId is not listed in 'bank owned atm locations/bank.data.datmlc')");
        final String FIELD = "54";
        String fieldValueFromResponse = Actions.nonTellerTransactionActions()
                .getFieldValueFromATMTransaction(getFieldsMap(nonTellerTransactionData), FIELD);

        logInfo("Step 3: Check the DE54 in the response.\n" +
                "Make sure that account's Current Balance and Available Balance were returned in this field");
        Actions.nonTellerTransactionActions().verifyCurrentAndAvailableBalance(fieldValueFromResponse, transactionAmount);
        String transcode = TransactionCode.ATM_USAGE_129_FEE.getTransCode().split("\\s+")[0];
        WebAdminActions.webAdminTransactionActions().setTransactionPostDateAndEffectiveDate(chkAccTransactionData, chkAccountNumber, transcode);
        expectedBalanceDataForCheckingAcc.reduceAmount(foreignFeeBalanceInquiry);

        logInfo("Step 4: Search for CHK account from the precondition and go to the Transactions history tab.\n" +
                "Verify that 129-Usage fee transaction was generated with NOT ON-US Terminal with an amount= ForeignATMFeeBalanceInquiry bcsetting");
        logInfo("Step 5: Pay attention to CHK account Current Balance and Available Balance");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenClientByName(chkAccountNumber);

        Assert.assertEquals(AccountActions.retrievingAccountData().getCurrentBalance(),
                expectedBalanceDataForCheckingAcc.getCurrentBalance(), "CHK account current balance is not correct!");
        Assert.assertEquals(AccountActions.retrievingAccountData().getAvailableBalance(),
                expectedBalanceDataForCheckingAcc.getAvailableBalance(), "CHK account available balance is not correct!");
        chkAccTransactionData.setBalance(expectedBalanceDataForCheckingAcc.getCurrentBalance());
        chkAccTransactionData.setAmount(foreignFeeBalanceInquiry);

        AccountActions.retrievingAccountData().goToTransactionsTab();

        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset);
        Assert.assertEquals(actualTransactionData, chkAccTransactionData, "Transaction data doesn't match!");
        Assert.assertTrue(Actions.transactionActions().isTransactionCodePresent(TransactionCode.ATM_USAGE_129.getTransCode()),
                "129-ATM Usage Fee is not present in transaction list");

        logInfo("Step 6: Go to Client Maintenance and click [View all Cards] button in 'Cards Management' widget");
        logInfo("Step 7: Click [View History] link on the Debit Card from the precondition");
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Actions.debitCardModalWindowActions().goToCardHistory(1);

        String transactionReasonCode = Pages.cardsManagementPage().getTransactionReasonCode(1);
        Assert.assertEquals(transactionReasonCode, "00 -- Approved or completed successfully",
                "'Transaction Reason Code' is not equal to '00 -- Approved or completed successfully'");

        String transactionDescription = Pages.cardsManagementPage().getTransactionDescription(1);
        Assert.assertEquals(transactionDescription, "Balance Inquiry",
                "Transaction description is not equal to 'Balance Inquiry'");

        String actualFeeAmount = Pages.cardsManagementPage().getTransactionFeeAmount(1);
        Assert.assertEquals(Double.parseDouble(actualFeeAmount), foreignFeeBalanceInquiry, "Transaction amount is incorrect!");

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
        result.put("3", "310000");
        result.put("11", uniqueValueField11);
        result.put("18", "6011");
        result.put("22", "051");
        result.put("35", String.format("%s=%s", transactionData.getCardNumber(), transactionData.getExpirationDate()));
        result.put("42", "01 sample av. ");
        result.put("43", "Long ave. bld. 34      Nashville      US");
        result.put("58", "10000000612");

        return  result;
    }
}
