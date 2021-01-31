package com.nymbus.frontoffice.transactions;

import com.nymbus.actions.Actions;
import com.nymbus.actions.account.AccountActions;
import com.nymbus.actions.client.ClientsActions;
import com.nymbus.actions.webadmin.WebAdminActions;
import com.nymbus.core.base.BaseTest;
import com.nymbus.core.utils.Constants;
import com.nymbus.core.utils.SelenideTools;
import com.nymbus.newmodels.account.Account;
import com.nymbus.newmodels.account.product.AccountType;
import com.nymbus.newmodels.account.product.Products;
import com.nymbus.newmodels.account.product.RateType;
import com.nymbus.newmodels.client.IndividualClient;
import com.nymbus.newmodels.generation.client.builder.IndividualClientBuilder;
import com.nymbus.newmodels.generation.client.builder.type.individual.IndividualBuilder;
import com.nymbus.newmodels.generation.tansactions.TransactionConstructor;
import com.nymbus.newmodels.generation.tansactions.builder.CashInDepositCHKAccBuilder;
import com.nymbus.newmodels.transaction.Transaction;
import com.nymbus.newmodels.transaction.enums.Denominations;
import com.nymbus.pages.Pages;
import com.nymbus.pages.webadmin.WebAdminPages;
import com.nymbus.testrail.TestRailIssue;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@Epic("Frontoffice")
@Feature("Transactions")
@Owner("Dmytro")
public class C37315_CTRAlertForCashInvolvedTransactions extends BaseTest {
    private Account savingsAccount;
    private IndividualClient client;
    private Transaction transaction;
    private Transaction transactionWithMoreLimit;
    private String clientRootId;
    private int amount = 100;
    private int moreLimitAmount = 11000;
    private String ctrLimit = "10000";
    private final String TOTAL_CASH_IN_AS_BENEFICIARY = "totalCashInAsBeneficiary";
    private final String TOTAL_CASH_IN_AS_CONDUCTOR = "totalCashInAsConductor";
    private boolean limitIsChanged = false;

    @BeforeMethod
    public void preCondition() {
        // Set up Client and Account
        IndividualClientBuilder individualClientBuilder = new IndividualClientBuilder();
        individualClientBuilder.setIndividualClientBuilder(new IndividualBuilder());
        client = individualClientBuilder.buildClient();
        savingsAccount = new Account().setSavingsAccountData();

        // Log in
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        // Set products
        savingsAccount.setProduct(Actions.productsActions().getProduct(Products.SAVINGS_PRODUCTS, AccountType.REGULAR_SAVINGS, RateType.FIXED));

        // Create client
        ClientsActions.individualClientActions().createClient(client);
        ClientsActions.individualClientActions().setClientDetailsData(client);
        ClientsActions.individualClientActions().setDocumentation(client);
        client.getIndividualType().setClientID(Pages.clientDetailsPage().getClientID());
        clientRootId = ClientsActions.createClient().getClientIdFromUrl();

        // Create account
        AccountActions.createAccount().createSavingsAccount(savingsAccount);

        //Create transactions
        transaction = new TransactionConstructor(new CashInDepositCHKAccBuilder()).constructTransaction();
        transaction.getTransactionDestination().setAmount(amount);
        transaction.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());
        transaction.getTransactionDestination().setTransactionCode("209 - Deposit");
        transaction.getTransactionSource().setAmount(amount);
        transactionWithMoreLimit = new TransactionConstructor(new CashInDepositCHKAccBuilder()).constructTransaction();
        transactionWithMoreLimit.getTransactionDestination().setAmount(moreLimitAmount);
        transactionWithMoreLimit.getTransactionDestination().setAccountNumber(savingsAccount.getAccountNumber());
        transactionWithMoreLimit.getTransactionDestination().setTransactionCode("209 - Deposit");
        transactionWithMoreLimit.getTransactionSource().setAmount(moreLimitAmount);
        transactionWithMoreLimit.getTransactionSource().setDenominationsHashMap(new HashMap<>());
        transactionWithMoreLimit.getTransactionSource().getDenominationsHashMap().put(Denominations.HUNDREDS, 11000.00);

        //Set CTR Online Alert
        limitIsChanged = Actions.usersActions().setCTROnlineAlert(ctrLimit);
        Actions.loginActions().doLogOutProgrammatically();
    }


    private final String TEST_RUN_NAME = "Transactions";

    @TestRailIssue(issueID = 37315, testRunName = TEST_RUN_NAME)
    @Test(description = "C37315, CTR Alert for Cash involved transactions")
    @Severity(SeverityLevel.CRITICAL)
    public void printTellerReceiptWithoutBalance() {
        logInfo("Step 1: Log in to the system as User from the preconditions");
        Actions.loginActions().doLogin(userCredentials.getUserName(), userCredentials.getPassword());

        logInfo("Step 2: Go to Teller screen and log in to proof date");
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().goToTellerPage();

        logInfo("Step 3: Select such fund types:\n" +
                "Cash In\n" +
                "Destination: Deposit and CHK/Savings account from the precondition, select 109/209 - Deposit Trancode");
        logInfo("Step 4: Set Transaction Amount < CTR limit value for both items");
        Actions.transactionActions().createTransaction(transaction);

        logInfo("Step 5: Click [Commit Transaction] button and confirm verify screen");
        Actions.transactionActions().clickCommitButton();
        Pages.verifyConductorModalPage().clickVerifyButton();
        Pages.tellerPage().closeModal();

        logInfo("Step 6: In another tab go to the WebAdmin and log in");
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);

        logInfo("Step 7: Go to DRL Caches (bankingcore) > CUSTOMER_CASH (REDIS) > " +
                "\"Keys\" and search for Customer cash for selected Client by Customer ID");
        WebAdminActions.webAdminUsersActions().goToDRLCaches();
        WebAdminPages.drlCachesPage().clickBankingCoreRadio();
        WebAdminPages.drlCachesPage().clickKeysItemByValue("CUSTOMER_CASH");
        WebAdminActions.webAdminUsersActions().goToCashValues(1, clientRootId);
        Map<String, String> totalCashValues = getBeneficiaryConductorMap();
        WebAdminActions.webAdminUsersActions().setFieldsMapWithValues(2, totalCashValues);
        double expectedResult = amount;
        double actualBeneficiaryValue = Double.parseDouble(totalCashValues.get(TOTAL_CASH_IN_AS_BENEFICIARY));
        double actualConductorValue = Double.parseDouble(totalCashValues.get(TOTAL_CASH_IN_AS_CONDUCTOR));

        Assert.assertEquals(actualBeneficiaryValue, expectedResult, "totalCashInAsBeneficiary value is incorrect!");
        Assert.assertEquals(actualConductorValue, expectedResult, "totalCashInAsConductor value is incorrect!");

        WebAdminActions.loginActions().doLogoutProgrammatically();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();

        logInfo("Step 8: On Teller screen perform one more cash In transaction on selected account:\n" +
                "- source: Cash In\n" +
                "- destination: Deposit and CHK/Savings account from the precondition, select 109/209 - Deposit Trancode");
        logInfo("Step 9: Set Transaction Amount so that CTR limit will be exceeded\n" +
                "(e.g. if CTR Limit = $10000.00, and totalCashInAsConductor/totalCashInAsBeneficiary = " +
                "$ 6000.00, then Transaction Amount can be $4000.01");
        Actions.transactionActions().loginTeller();
        Actions.transactionActions().goToTellerPage();
        Actions.transactionActions().createTransaction(transactionWithMoreLimit);

        logInfo("Step 10: Click [Commit transaction] button and click [Verify] in Verify Client popup");
        Actions.transactionActions(). clickCommitButton();
        Pages.verifyConductorModalPage().clickVerifyButton();

        logInfo("Step 11: Click [OK] button");
        Pages.confirmModalPage().clickOk();
        Pages.tellerPage().closeModal();

        logInfo("Step 12: In Webadmin refresh DRL Caches (bankingcore) page and verify Customer Cash" +
                " again for the Client from the precondition");
        WebAdminActions.loginActions().openWebAdminPageInNewWindow();
        WebAdminActions.loginActions().doLogin(Constants.USERNAME, Constants.PASSWORD);
        WebAdminActions.webAdminUsersActions().goToDRLCaches();
        WebAdminPages.drlCachesPage().clickBankingCoreRadio();
        WebAdminPages.drlCachesPage().clickKeysItemByValue("CUSTOMER_CASH");
        WebAdminActions.webAdminUsersActions().goToCashValues(1, clientRootId);
        Map<String, String> totalCashValuesAfterMoreLimit = getBeneficiaryConductorMap();
        WebAdminActions.webAdminUsersActions().setFieldsMapWithValues(2, totalCashValuesAfterMoreLimit);
        double expectedResultAfterMoreLimit = amount + moreLimitAmount;
        double actualBeneficiaryValueAfterMoreLimit = Double.parseDouble(totalCashValuesAfterMoreLimit.get(TOTAL_CASH_IN_AS_BENEFICIARY));
        double actualConductorValueAfterMoreLimit = Double.parseDouble(totalCashValuesAfterMoreLimit.get(TOTAL_CASH_IN_AS_CONDUCTOR));

        Assert.assertEquals(actualBeneficiaryValueAfterMoreLimit, expectedResultAfterMoreLimit, "totalCashInAsBeneficiary value is incorrect!");
        Assert.assertEquals(actualConductorValueAfterMoreLimit, expectedResultAfterMoreLimit, "totalCashInAsConductor value is incorrect!");

        WebAdminActions.loginActions().doLogoutProgrammatically();
        WebAdminActions.loginActions().closeWebAdminPageAndSwitchToPreviousTab();
        SelenideTools.sleep(30);

    }

    @AfterMethod(description = "Return CTR Limit.")
    public void postCondition() {
        if (limitIsChanged) {
            logInfo("Return CTR Limit");
            Actions.usersActions().offCTROnlineAlert();
        }
    }

    private Map<String, String> getBeneficiaryConductorMap() {
        Map<String, String> result = new HashMap<>();
        result.put(TOTAL_CASH_IN_AS_BENEFICIARY, "0");
        result.put(TOTAL_CASH_IN_AS_CONDUCTOR, "0");
        return result;
    }

}
