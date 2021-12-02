package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.transactions.TransactionConstructor;
import com.nymbus.newmodels.generation.transactions.builder.CashInMiscCreditCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.Denominations;
import com.nymbus.pages.Pages;
import com.nymbus.testrail.CustomStepResult;
import com.nymbus.testrail.TestRailAssert;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C25474_CashDrawerBalanceSectionCalculationRules extends BaseTest {
    private IndividualClient client;
    private Account chkAccount;
    private Transaction transaction;
    private double amount = 300;
    private double hundredAmount = 100;
    private String beginningCash;
    private String cashIn;
    private String cashOver;
    private String cashOut;
    private String calculatedCash;
    private String countedCash;
    private String cashShort;
    private double sumCash = 2.0;

    @BeforeMethod
    public void preCondition() {

        // Set up Client and Accounts
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();

        chkAccount = new Account().setCHKAccountData();

        // Set up  transaction
        transaction = new TransactionConstructor(new CashInMiscCreditCHKAccBuilder()).constructTransaction();

        // Set up transaction with account number
        transaction.getTransactionDestination().setAccountNumber(chkAccount.getAccountNumber());

        //Get denominations
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());
        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        Actions.transactionActions().doLoginTeller();
        beginningCash = Pages.cashDrawerBalancePage().getBeginningCash();
        cashIn = Pages.cashDrawerBalancePage().getCashIn();
        cashOut = Pages.cashDrawerBalancePage().getCashOut();
        calculatedCash = Pages.cashDrawerBalancePage().getCalculatedCash();
        countedCash = Pages.cashDrawerBalancePage().getCountedCash();
        cashOver = Pages.cashDrawerBalancePage().getCashOver();
        cashShort = Pages.cashDrawerBalancePage().getCashShort();
        String ones = Pages.cashDrawerBalancePage().getOnes();
        Pages.cashDrawerBalancePage().clickEnterAmounts();
        Pages.cashDrawerBalancePage().setOnes(Double.parseDouble(ones) + sumCash);
        Pages.cashDrawerBalancePage().clickSave();
        Actions.loginActions().doLogOutProgrammatically();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        chkAccount.setProduct(Actions.productsActions().getProduct(Products.CHK_PRODUCTS, AccountType.CHK, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());

        // Create CHK account
        AccountActions.createAccount().createCHKAccountForTransactionPurpose(chkAccount);
        Actions.clientPageActions().searchAndOpenClientByName(client.getInitials());

        transaction.getTransactionSource().setAmount(hundredAmount);
        transaction.getTransactionDestination().setAmount(hundredAmount);
        Pages.aSideMenuPage().clickTellerMenuItem();
        SelenideTools.sleep(2);
        Pages.tellerModalPage().clickEnterButton();
        Pages.tellerModalPage().waitForModalInvisibility();
        Actions.transactionActions().createTransaction(transaction);
        Pages.tellerPage().clickCommitButton();
        Pages.verifyConductorModalPage().clickVerifyButton();
        Pages.tellerPage().closeModal();

        transaction.getTransactionSource().setAmount(amount);
        transaction.getTransactionSource().setDenominationsHashMap(new HashMap<>());
        transaction.getTransactionSource().getDenominationsHashMap().put(Denominations.HUNDREDS, 300.00);
        transaction.getTransactionDestination().setAmount(amount);
        SelenideTools.sleep(2);
        Actions.transactionActions().createTransaction(transaction);
        Pages.tellerPage().clickCommitButton();
        Pages.verifyConductorModalPage().clickVerifyButton();
        Pages.tellerPage().closeModal();

        Actions.loginActions().doLogOutProgrammatically();
    }

    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 25474, testRunName = TEST_RUN_NAME)
    @Test(description = "C25474, Cash Drawer - Balance section - Calculation rules")
    @Severity(SeverityLevel.CRITICAL)
    public void cashDrawerBalanceSectionCalculationRules() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Cash Drawer screen and log in to proof date");
        Pages.aSideMenuPage().clickCashDrawerMenuItem();
        Actions.transactionActions().doLoginTeller();

        logInfo("Step 3: Verify the 'Beginning Cash' field's value");
        String beginningCash = Pages.cashDrawerBalancePage().getBeginningCash();
        TestRailAssert.assertEquals(beginningCash, this.beginningCash, new CustomStepResult("Beginning cash is correct",
                "Beginning cash is not correct"));

        logInfo("Step 4: Verify the 'Cash In' field's value");
        String cashIn = Pages.cashDrawerBalancePage().getCashIn();
        TestRailAssert.assertEquals(cashIn, Double.parseDouble(this.cashIn) + amount + hundredAmount + "0",
                new CustomStepResult("Cash In is correct","Cash In is not correct"));

        logInfo("Step 5: Verify the 'Cash Out' field's value");
        String cashOut = Pages.cashDrawerBalancePage().getCashOut();
        TestRailAssert.assertEquals(cashOut, this.cashOut, new CustomStepResult("Cash Out is correct",
                "Cash Out is not correct"));

        logInfo("Step 6: Verify the 'Calculated Cash' field's value");
        String calculatedCash = Pages.cashDrawerBalancePage().getCalculatedCash();
        TestRailAssert.assertEquals(calculatedCash, Double.parseDouble(this.calculatedCash) + amount + hundredAmount + "0",
                new CustomStepResult("Calculated Cash is correct","Calculated Cash is not correct"));

        logInfo("Step 7: Verify the 'Counted Cash' field's value");
        String countedCash = Pages.cashDrawerBalancePage().getCountedCash();
        String cashOver = Pages.cashDrawerBalancePage().getCashOver();
        TestRailAssert.assertEquals(countedCash, Double.parseDouble(calculatedCash) + Double.parseDouble(cashOver) + "0" ,
                new CustomStepResult("Cash Over is correct","Cash Over is not correct"));

        logInfo("Step 8: Verify the 'Cash Over' field's value");
        TestRailAssert.assertEquals(cashOver, Double.parseDouble(this.cashOver) + sumCash + "0",
                new CustomStepResult("Cash Over is correct","Cash Over is not correct"));

        logInfo("Step 9: Verify the 'Cash Short' field's value");
        String cashShort = Pages.cashDrawerBalancePage().getCashShort();
        TestRailAssert.assertEquals(cashShort,  "0.00",
                new CustomStepResult("Cash Short is correct","Cash Short is not correct"));
    }
}
