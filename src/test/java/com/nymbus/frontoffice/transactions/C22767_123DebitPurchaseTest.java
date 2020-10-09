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
import com.nymbus.newmodels.generation.bincontrol.BinControlConstructor;
import com.nymbus.newmodels.generation.bincontrol.builder.BinControlBuilder;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.debitcard.DebitCardConstructor;
import com.nymbus.newmodels.generation.debitcard.builder.DebitCardBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.GLDebitMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.settings.bincontrol.BinControl;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.TransactionCode;
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

public class C22767_123DebitPurchaseTest extends BaseTest {
    private BalanceDataForCHKAcc expectedBalanceDataForCheckingAcc;
    private TransactionData chkAccTransactionData;
    private NonTellerTransactionData nonTellerTransactionData;
    private String checkingAccountNumber;
    private double transactionAmount;
    private IndividualClient client;

    @BeforeMethod
    public void prepareTransactionData() {
        // Set up Client, Account and Transaction
        IndividualClientBuilder individualClientBuilder =  new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();
        Account checkAccount = new Account().setCHKAccountData();
        Transaction glDebitMiscCreditTransaction = new TransactionConstructor(new GLDebitMiscCreditCHKAccBuilder()).constructTransaction();

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
        debitCard.getAccounts().add(checkAccount.getAccountNumber());
        debitCard.setNameOnCard(client.getNameForDebitCard());

        // Log in and create client
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set product
        checkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);

        // Create CHK account
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(checkAccount);
        checkingAccountNumber = checkAccount.getAccountNumber();

        // Set up transaction with account number
        glDebitMiscCreditTransaction.getTransactionDestination().setAccountNumber(checkAccount.getAccountNumber());

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

        transactionAmount = glDebitMiscCreditTransaction.getTransactionDestination().getAmount();
        Actions.clientPageActions().searchAndOpenClientByName(checkingAccountNumber);
        expectedBalanceDataForCheckingAcc = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();

        // Set up nonTeller transaction data
        nonTellerTransactionData.setAmount(transactionAmount);
        chkAccTransactionData = new TransactionData(DateTime.getLocalDateOfPattern("MM/dd/yyyy"), DateTime.getLocalDateOfPattern("MM/dd/yyyy"),
                "-", expectedBalanceDataForCheckingAcc.getCurrentBalance(), transactionAmount);

        Actions.loginActions().doLogOut();
    }

    @Test(description = "C22767, 123 - Debit Purchase")
    @Severity(SeverityLevel.CRITICAL)
    public void verify123DebitPurchaseTest() {
        logInfo("Step 1: Go to the Swagger and log in as the User from the preconditions");
        logInfo("Step 2: Expand widgets-controller/widget._GenericProcess and run the following request:");
        Actions.nonTellerTransactionActions().performATMTransaction(getFieldsMap(nonTellerTransactionData));

        String transcode = TransactionCode.ATM_DEBIT_PURCHASE_123.getTransCode().split("\\s+")[0];
        WebAdminActions.webAdminTransactionActions().setTransactionPostDateAndEffectiveDate(chkAccTransactionData, checkingAccountNumber, transcode);

        logInfo("Step 3: Log in to the system");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        expectedBalanceDataForCheckingAcc.reduceAmount(transactionAmount);

        logInfo("Step 4: Search for CHK account from the precondition and Verify Account's: \n" +
                "- current balance \n" +
                "- available balance \n" +
                "- Transaction history");
        Actions.clientPageActions().searchAndOpenClientByName(checkingAccountNumber);
        BalanceDataForCHKAcc actualBalanceData = AccountActions.retrievingAccountData().getBalanceDataForCHKAcc();
        Assert.assertEquals(actualBalanceData.getCurrentBalance(), expectedBalanceDataForCheckingAcc.getCurrentBalance(), "Checking account current balance is not correct!");
        Assert.assertEquals(actualBalanceData.getAvailableBalance(), expectedBalanceDataForCheckingAcc.getAvailableBalance(), "Checking account available balance is not correct!");
        chkAccTransactionData.setBalance(expectedBalanceDataForCheckingAcc.getCurrentBalance());

        AccountActions.retrievingAccountData().goToTransactionsTab();
        int offset = AccountActions.retrievingAccountData().getOffset();
        TransactionData actualTransactionData = AccountActions.retrievingAccountData().getTransactionDataWithOffset(offset);
        Assert.assertEquals(actualTransactionData, chkAccTransactionData, "Transaction data doesn't match!");

        logInfo("Step 5: Go to Client Maintenance and click [View all Cards] button in 'Cards Management' widget");
        logInfo("Step 6: Click [View History] link on the Debit Card from the precondition");
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
        result.put("3", "000000");
        result.put("4", transactionData.getAmount());
        result.put("11", String.valueOf(Generator.genInt(100000000, 922337203)));
        result.put("18", "6010");
        result.put("22", "801");
        result.put("35", String.format("%s=%s", transactionData.getCardNumber(), transactionData.getExpirationDate()));
        result.put("43", "Long ave. bld. 34      Nashville      US");
        result.put("58", "0000000002U");

        return  result;
    }
}