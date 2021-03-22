package com.nymbus.frontoffice.transactions;

import com.codeborne.selenide.Selenide;
import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
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
import com.nymbus.newmodels.transaction.apifieldsmodels.Field54Model;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.NonTellerTransactionData;
import com.nymbus.newmodels.transaction.verifyingModels.TransactionData;
import com.nymbus.pages.Pages;
import com.nymbus.pages.webadmin.WebAdminPages;
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
public class C26518_PinPurchaseFndWithCashbackTest extends BaseTest {

    private String chkAccountNumber;
    private IndividualClient client;
    private NonTellerTransactionData nonTellerTransactionData;
    private BalanceDataForCHKAcc expectedBalanceDataForCheckingAcc;
    private TransactionData chkAccTransactionData;
    private String clientRootId;
    private final double requestTransactionAmount = 60.00;
    private final double cashbackAmount = 20.00;
    private final Field54Model field_54 = new Field54Model("20", "40", "840", "C", cashbackAmount);

    // Fields to verify on web-admin page
    private final String TOTAL_CASH_OUT_AS_BENEFICIARY = "totalCashOutAsBeneficiary";
    private final String TOTAL_CASH_OUT_AS_CONDUCTOR = "totalCashOutAsConductor";

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

        // Set up nonTeller transaction data
        nonTellerTransactionData = new NonTellerTransactionData();
        nonTellerTransactionData.setAmount(requestTransactionAmount);

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

        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        clientRootId = ClientsActions.createClient().getClientIdFromUrl();

        // Create CHK account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(chkAccount);

        // Create debit card for CHK acc
        createDebitCard(client.getInitials(), debitCard);
        Actions.debitCardModalWindowActions().setExpirationDateAndCardNumber(nonTellerTransactionData, 1);

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
                "-", glDebitMiscCreditTransaction.getTransactionDestination().getAmount() - requestTransactionAmount, requestTransactionAmount);
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 26518, testRunName = TEST_RUN_NAME)
    @Test(description = "C26518, PIN Purchase FND with Cashback")
    @Severity(SeverityLevel.CRITICAL)
    public void pinPurchaseFndWithCashbackTest() {

        logInfo("Step 1: Go to the Swagger and log in as the User from the preconditions");
        logInfo("Step 2: Expand widgets-controller and run the following request");
        Actions.nonTellerTransactionActions().performATMTransaction(getFieldsMap(nonTellerTransactionData));
        expectedBalanceDataForCheckingAcc.reduceCurrentBalance(requestTransactionAmount);
        expectedBalanceDataForCheckingAcc.reduceAvailableBalance(requestTransactionAmount);

        logInfo("Step 3: Log in to the system as the User from the preconditions");
        logInfo("Step 4: Search for CHK account from the precondition and verify its current and available balance");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenClientByName(chkAccountNumber);

        Assert.assertEquals(AccountActions.retrievingAccountData().getCurrentBalance(),
                expectedBalanceDataForCheckingAcc.getCurrentBalance(), "CHK account current balance is not correct!");
        Assert.assertEquals(AccountActions.retrievingAccountData().getAvailableBalance(),
                expectedBalanceDataForCheckingAcc.getAvailableBalance(), "CHK account available balance is not correct!");

        logInfo("Step 5: Open CHK account on Transactions history and verify created transaction");
        String transcode = TransactionCode.ATM_DEBIT_PURCHASE_123.getTransCode().split("\\s+")[0];
        WebAdminActions.webAdminTransactionActions().setTransactionPostDateAndEffectiveDate(chkAccTransactionData, chkAccountNumber, transcode);
        AccountActions.retrievingAccountData().goToTransactionsTab();

        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset);

        Assert.assertEquals(actualTransactionData, chkAccTransactionData, "Transaction data doesn't match!");
        Assert.assertEquals(Pages.accountTransactionPage().getTransactionItemsCount(), 2,
                "Transaction count is incorrect!");
        Assert.assertTrue(Actions.transactionActions().isTransactionCodePresent(TransactionCode.ATM_DEBIT_PURCHASE_123.getTransCode(), offset),
                "'123 - ATM Debit Purchase' presents in transaction list");

        logInfo("Step 6: Go to Client Maintenance and click [View all Cards] button in 'Cards Management' widget");
        logInfo("Step 7: Click [View History] link on the Debit Card from the precondition");
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Actions.debitCardModalWindowActions().goToCardHistory(1);

        String transactionReasonCode = Pages.cardsManagementPage().getTransactionReasonCode(1);
        Assert.assertEquals(transactionReasonCode, "00 -- Approved or completed successfully",
                "'Transaction Reason Code' is not equal to '00 -- Approved or completed successfully'");

        String transactionDescription = Pages.cardsManagementPage().getTransactionDescription(1);
        Assert.assertEquals(transactionDescription, "Cash Back",
                "Transaction description is not equal to 'Cash Back'");

        String transactionAmount = Pages.cardsManagementPage().getTransactionAmount(1);
        Assert.assertEquals(Double.parseDouble(transactionAmount), requestTransactionAmount,
                "'Cash Withdrawal' amount is not equal to transaction amount");

        logInfo("Step 8: Go to the WebAdmin and log in as the User from the preconditions");
        Selenide.open(Constants.WEB_ADMIN_URL);
        WebAdminActions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 9: Go to DRL Caches -> bankingcore and search for CUSTOMER_CASH click [Keys]");
        WebAdminActions.webAdminUsersActions().goToDRLCaches();
        WebAdminPages.drlCachesPage().clickBankingCoreRadio();
        WebAdminPages.drlCachesPage().clickKeysItemByValue("CUSTOMER_CASH");

        logInfo("Step 10: Search for the Client from the preconditions by Client Rootid and click [Value]");
        WebAdminActions.webAdminUsersActions().goToCashValues(1, clientRootId);

        logInfo("Step 11: Verify that totalCashInAsBeneficiary and totalCashInAsConductor values were increased \n" +
                "by Cash Amount from DE054(e.g. 20.00) from Step2");
        Map<String, String> totalCashValues = getBeneficiaryConductorMap();
        WebAdminActions.webAdminUsersActions().setFieldsMapWithValues(2, totalCashValues);
        double actualBeneficiaryValue = Double.parseDouble(totalCashValues.get(TOTAL_CASH_OUT_AS_BENEFICIARY));
        double actualConductorValue = Double.parseDouble(totalCashValues.get(TOTAL_CASH_OUT_AS_CONDUCTOR));

        Assert.assertEquals(actualBeneficiaryValue, cashbackAmount, "totalCashOutAsBeneficiary value is incorrect!");
        Assert.assertEquals(actualConductorValue, cashbackAmount, "totalCashOutAsConductor value is incorrect!");
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
        result.put("3", "090000");
        result.put("4", transactionData.getAmount());
        result.put("11", String.valueOf(Generator.genInt(100000000, 922337203)));
        result.put("18", "5541");
        result.put("22", "021");
        result.put("35", String.format("%s=%s", transactionData.getCardNumber(), transactionData.getExpirationDate()));
        result.put("37", "201206102");
        result.put("43", "Long ave. bld. 34      Nashville      US");
        result.put("48", "SHELL");
        result.put("54", field_54.getData());
        result.put("58", "01000000012");

        return  result;
    }

    private Map<String, String> getBeneficiaryConductorMap() {
        Map<String, String > result = new HashMap<>();
        result.put(TOTAL_CASH_OUT_AS_BENEFICIARY, "0");
        result.put(TOTAL_CASH_OUT_AS_CONDUCTOR, "0");
        return  result;
    }
}
