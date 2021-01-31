package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
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
import com.nymbus.newmodels.transaction.verifyingModels.ATMRequiredValueModel;
import com.nymbus.newmodels.transaction.verifyingModels.BalanceDataForCHKAcc;
import com.nymbus.newmodels.transaction.verifyingModels.NonTellerTransactionData;
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
@Owner("Dmytro")
public class C22768_SavAtmBalanceInquiryOnusTest extends BaseTest {
    private BalanceDataForCHKAcc expectedBalanceData;
    private NonTellerTransactionData nonTellerTransactionData;
    private String savingsAccountNumber;
    private IndividualClient client;

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

        ATMRequiredValueModel requiredFieldsData = Actions.nonTellerTransactionActions().getATMRequiredValue(1, 1, 1);
        Assert.assertTrue(requiredFieldsData.getAcronymValue()!= null && !requiredFieldsData.getAcronymValue().isEmpty(),
                        "WaiveATUsageFeeAcronym is incorrect!");

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set product
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));

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
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Perform transaction
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().createGlDebitMiscCreditTransaction(glDebitMiscCreditTransaction);
        Actions.transactionActions().clickCommitButton();
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        Actions.clientPageActions().searchAndOpenClientByName(savingsAccountNumber);
        AccountActions.editAccount().goToInstructionsTab();
        String INSTRUCTION_REASON = "Reg CC";
        AccountActions.createInstruction().deleteInstructionByReasonText(INSTRUCTION_REASON);
        AccountActions.editAccount().goToDetailsTab();
        expectedBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        // Set up nonTeller transaction data
        nonTellerTransactionData.setAmount(0);
        nonTellerTransactionData.setTerminalId(requiredFieldsData.getTerminalId());
        Actions.loginActions().doLogOut();
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 22768, testRunName = TEST_RUN_NAME)
    @Test(description = "C22768, SAV ATM Balance Inquiry ONUS")
    @Severity(SeverityLevel.CRITICAL)
    public void verifySavAtmBalanceInquiryTransaction() {
        Map <String, String> fieldsMap = getFieldsMapForOnusRequest(nonTellerTransactionData);
        String field39ExpectedResponse = "00";
        logInfo("Step 1: Go to the Swagger and log in as the User from the preconditions");
        logInfo("Step 2: Expand widgets-controller->widget._GenericProcess and run the following request \n" +
                "with NOT ON-US Terminal (TermId is not listed in 'bank owned atm locations/bank.data.datmlc')");
        Map <String, String> response = Actions.nonTellerTransactionActions().getDataMap(fieldsMap);
        Assert.assertEquals(response.get("39"), field39ExpectedResponse, "Field DE 39 is incorrect!");

        logInfo("Step 3: Check the DE 54 in the response. Make sure that account's \n" +
                        "Current Balance and Available Balance were returned in this field");
        BalanceDataForCHKAcc actualBalanceData =  Actions.nonTellerTransactionActions().getBalanceDataFromField54(response.get("54"));
        Assert.assertEquals(actualBalanceData.getCurrentBalance(), expectedBalanceData.getCurrentBalance(), "Checking account current balance is not correct!");
        Assert.assertEquals(actualBalanceData.getAvailableBalance(), expectedBalanceData.getAvailableBalance(), "Checking account available balance is not correct!");

        logInfo("Step 4: Search for Savings account from the precondition and go to the Transactions history tab. \n" +
                        "Verify that 229-Usage fee transaction was not generated with ON-US Terminal");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccountNumber);
        AccountActions.retrievingAccountData().goToTransactionsTab();
        Pages.accountTransactionPage().waitForTransactionSection();
        Assert.assertEquals(Pages.accountTransactionPage().getTransactionItemsCountWithZeroOption(), 1,
                "Transaction items count is incorrect!");
        int offset = AccountActions.retrievingAccountData().getOffset();
        Assert.assertFalse(Actions.transactionActions().isTransactionCodePresent(TransactionCode.ATM_USAGE_229_FEE.getTransCode(), offset),
                "229 - Usage fee transaction is present in transaction list");
        Actions.loginActions().doLogOut();

        logInfo("Step 5: Go to the swagger and run the request with FOREIGN Terminal (TermId is not listed in 'bank owned atm \n" +
                "locations/bank.data.datmlc') BUT with WaiveATUsageFeeAcronym bcsetting value in DE 063 (position 9-11)");
        String notOnusTerminalId = String.valueOf(Generator.genInt(100000000, 922337203));
        fieldsMap.put("41", notOnusTerminalId);
        fieldsMap.put("63", "B0IN9015CPNACQRID");
        response = Actions.nonTellerTransactionActions().getDataMap(fieldsMap);
        Assert.assertEquals(response.get("39"), field39ExpectedResponse, "Field DE 39 is incorrect!");

        logInfo("Step 6: Check the DE 54 in the response. Make sure that account's \n" +
                "Current Balance and Available Balance were returned in this field");
        actualBalanceData =  Actions.nonTellerTransactionActions().getBalanceDataFromField54(response.get("54"));
        Assert.assertEquals(actualBalanceData.getCurrentBalance(), expectedBalanceData.getCurrentBalance(), "Checking account current balance is not correct!");
        Assert.assertEquals(actualBalanceData.getAvailableBalance(), expectedBalanceData.getAvailableBalance(), "Checking account available balance is not correct!");

        logInfo("Step 7: Search for Savings account from the precondition and go to the Transactions history tab. \n" +
                "Verify that 229-Usage fee transaction was not generated with NOT ON-US Terminal and with WaiveATUsageFeeAcronym bcsetting value in DE 063 (position 9-11)");
        Actions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        Actions.clientPageActions().searchAndOpenClientByName(savingsAccountNumber);
        AccountActions.retrievingAccountData().goToTransactionsTab();
        Pages.accountTransactionPage().waitForTransactionSection();
        Assert.assertEquals(Pages.accountTransactionPage().getTransactionItemsCountWithZeroOption(), 1,
                "Transaction items count is incorrect!");
        offset = AccountActions.retrievingAccountData().getOffset();
        Assert.assertFalse(Actions.transactionActions().isTransactionCodePresent(TransactionCode.ATM_USAGE_229_FEE.getTransCode(), offset),
                "229 - Usage fee transaction is present in transaction list");

        logInfo("Step 8: Go to Client Maintenance and click [View all Cards] button in 'Cards Management' widget");
        logInfo("Step 9: Click [View History] link on the Debit Card from the precondition");
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        Actions.debitCardModalWindowActions().goToCardHistory(1);
        int transactionsCount = Pages.cardsManagementPage().getTransactionRowsCount();
        Assert.assertEquals(transactionsCount, 2, "Transaction count is incorrect!");
    }

    private void createDebitCard(String clientInitials, DebitCard debitCard) {
        Actions.clientPageActions().searchAndOpenClientByName(clientInitials);
        Pages.clientDetailsPage().clickOnMaintenanceTab();
        Pages.clientDetailsPage().clickOnNewDebitCardButton();
        Actions.debitCardModalWindowActions().fillDebitCard(debitCard);
        Pages.debitCardModalWindow().clickOnSaveAndFinishButton();
        Pages.debitCardModalWindow().waitForAddNewDebitCardModalWindowInvisibility();
    }

    private Map<String, String> getFieldsMapForOnusRequest(NonTellerTransactionData transactionData) {
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