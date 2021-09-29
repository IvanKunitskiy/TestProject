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
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.GLDebitMiscCreditBuilder;
import com.nymbus.newmodels.settings.bincontrol.BinControl;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.NonTellerTransactionData;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Petro")
public class C26510_WithdrawalFndFastCash80FrgnTest extends BaseTest {

    private IndividualClient client;
    private String chkAccountNumber;
    private NonTellerTransactionData fastCashTransactionData;
    private BalanceDataForCHKAcc expectedBalanceDataForCheckingAcc;
    private final String uniqueValueField11 = Generator.getRandomStringNumber(6);
    private TransactionData chkAccTransactionData;
    private TransactionData transactionFeeData;
    private double atmFee;
    private double transactionAmountWithFee;

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

        // Get "Foreign ATM Fee Balance Inquiry" value
        atmFee = Actions.nonTellerTransactionActions().getForeignFee(2);
        double requestTransactionAmount = 80.00;
        transactionAmountWithFee = requestTransactionAmount + atmFee;

        // Set up nonTeller transaction data
        fastCashTransactionData = new NonTellerTransactionData();
        fastCashTransactionData.setAmount(requestTransactionAmount);

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
        logInFile("Create client - " + client.getFullName());

        // Create CHK account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(chkAccount);
        logInFile("Create CHK account - " + chkAccount.getAccountNumber());

        // Create debit card for CHK acc
        createDebitCard(client.getInitials(), debitCard);
        Actions.debitCardModalWindowActions().setExpirationDateAndCardNumber(fastCashTransactionData, 1);
        logInFile("Create debit card - " + fastCashTransactionData.getCardNumber());

        // Re-login in system for updating teller session and capture account balances
        Actions.loginActions().doLogOut();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Perform transaction
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(glDebitMiscCreditTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();
        Actions.loginActions().doLogOutProgrammatically();

        // Login, capture current and available balance of the account and logout
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenClientByName(chkAccountNumber);
        expectedBalanceDataForCheckingAcc = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Actions.loginActions().doLogOut();

        chkAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", glDebitMiscCreditTransaction.getTransactionDestination().getAmount() - transactionAmountWithFee, requestTransactionAmount);
        transactionFeeData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", glDebitMiscCreditTransaction.getTransactionDestination().getAmount() - atmFee, atmFee);
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 26510, testRunName = TEST_RUN_NAME)
    @Test(description = "C26510, Withdrawal FND Fast Cash 80$ FRGN")
    @Severity(SeverityLevel.CRITICAL)
    public void withdrawalFndFastCash80Frgn() {

        logInfo("Step 1: Go to the Swagger and log in as the User from the preconditions");
        logInfo("Step 2: Expand widgets-controller and run the following request");
        Actions.nonTellerTransactionActions().performATMTransaction(getFastCashTransactionFieldsMap(fastCashTransactionData));
        expectedBalanceDataForCheckingAcc.reduceCurrentBalance(transactionAmountWithFee);
        expectedBalanceDataForCheckingAcc.reduceAvailableBalance(transactionAmountWithFee);

        logInfo("Step 3: Log in to the system as the User from the preconditions");
        logInfo("Step 4: Search for CHK account from the precondition and verify its current and available balance");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenClientByName(chkAccountNumber);

        Assert.assertEquals(AccountActions.retrievingAccountData().getCurrentBalance(),
                expectedBalanceDataForCheckingAcc.getCurrentBalance(), "CHK account current balance is not correct!");
        Assert.assertEquals(AccountActions.retrievingAccountData().getAvailableBalance(),
                expectedBalanceDataForCheckingAcc.getAvailableBalance(), "CHK account available balance is not correct!");

        String transcode_124 = TransactionCode.ATM_WITHDRAWAL_124.getTransCode().split("\\s+")[0];
        String transcode_129 = TransactionCode.ATM_USAGE_129_FEE.getTransCode().split("\\s+")[0];
        WebAdminActions.webAdminTransactionActions().setTransactionPostDateAndEffectiveDate(chkAccTransactionData, chkAccountNumber, transcode_124);
        WebAdminActions.webAdminTransactionActions().setTransactionPostDateAndEffectiveDate(transactionFeeData, chkAccountNumber, transcode_129);
        AccountActions.retrievingAccountData().goToTransactionsTab();

        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset);
        TransactionData actualFeeTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset, 2);

        Assert.assertEquals(actualTransactionData, chkAccTransactionData, "Transaction data doesn't match!");
        Assert.assertEquals(actualFeeTransactionData, transactionFeeData, "Transaction Fee data doesn't match!");

        Assert.assertEquals(Pages.accountTransactionPage().getTransactionItemsCount(), 3,
                "Transaction count is incorrect!");
        Assert.assertTrue(Actions.transactionActions().isTransactionCodePresent(TransactionCode.ATM_WITHDRAWAL_124.getTransCode(), offset),
                "'124 - ATM Withdrawal' presents in transaction list");
        Assert.assertTrue(Actions.transactionActions().isTransactionCodePresent(TransactionCode.ATM_USAGE_129_FEE.getTransCode(), offset),
                "'129 - ATM Usage Fee' presents in transaction list");

        logInfo("Step 5: Go to Client Maintenance and click [View all Cards] button in 'Cards Management' widget");
        logInfo("Step 6: Click [View History] link on the Debit Card from the precondition");
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Actions.debitCardModalWindowActions().goToCardHistory(1);

        String transactionReasonCode = Pages.cardsManagementPage().getTransactionReasonCode(1);
        Assert.assertEquals(transactionReasonCode, "00 -- Approved or completed successfully",
                "'Transaction Reason Code' is not equal to '00 -- Approved or completed successfully'");

        String transactionDescription = Pages.cardsManagementPage().getTransactionDescription(1);
        Assert.assertEquals(transactionDescription, "Cash Withdrawal",
                "Transaction description is not equal to 'Cash Withdrawal'");

        String transactionAmount = Pages.cardsManagementPage().getTransactionAmount(1);
        Assert.assertEquals(Double.parseDouble(transactionAmount), transactionAmountWithFee,
                "'Cash Withdrawal' amount is not equal to transaction amount");

        String transactionFeeAmount = Pages.cardsManagementPage().getTransactionFeeAmount(1);
        Assert.assertEquals(Double.parseDouble(transactionFeeAmount), atmFee,
                "'Fee Amount' amount is not equal to 'ATM Fee' amount");
    }

    private void createDebitCard(String clientInitials, DebitCard debitCard) {
        Actions.clientPageActions().searchAndOpenClientByName(clientInitials);
        Pages.clientDetailsPage().clickOnMaintenanceTab();
        Pages.clientDetailsPage().clickOnNewDebitCardButton();
        Actions.debitCardModalWindowActions().fillDebitCard(debitCard);
        Pages.debitCardModalWindow().clickOnSaveAndFinishButton();
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowInvisibility();
    }

    private Map<String, String> getFastCashTransactionFieldsMap(NonTellerTransactionData transactionData) {
        Map<String, String > result = new HashMap<>();

        result.put("0", "0200");
        result.put("3", "010000");
        result.put("4", transactionData.getAmount());
        result.put("11", uniqueValueField11);
        result.put("18", "6011");
        result.put("22", "051");
        result.put("23", "001");
        result.put("35", String.format("%s=%s", transactionData.getCardNumber(), transactionData.getExpirationDate()));
        result.put("37", "201206102");
        result.put("43", "Long ave. bld. 34      Nashville      US");
        result.put("48", "SHELL");
        result.put("55", "9F34123");
        result.put("58", "0000000002U");

        return  result;
    }
}
